/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.FacturaDTO;
import org.cristianlima.model.Compra;
import org.cristianlima.model.Factura;
import org.cristianlima.report.GenerarReporte;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class FacturasAntiguasController implements Initializable {

    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnSeleccionar, btnSalir, btnBuscar;

    @FXML
    TextField tfBuscar;

    @FXML
    TableView tblFacturas;

    @FXML
    TableColumn colId, colFecha, colHora, colCliente, colEmpleado, colTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSeleccionar) {
            GenerarReporte.getInstance().generarFactura(((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
        } else if (event.getSource() == btnSalir) {
            stage.menuFacturasView();
        } else if (event.getSource() == btnBuscar) {
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblFacturas.getItems().clear();
                tblFacturas.getItems().add(buscarFactura());
                colId.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("facturaId"));
                colFecha.setCellValueFactory(new PropertyValueFactory<Factura, Date>("fecha"));
                colHora.setCellValueFactory(new PropertyValueFactory<Factura, Time>("hora"));
                colCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("Cliente"));
                colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, String>("Empleado"));
                colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("total"));
            }
        }
    }

    public void cargarLista() {
        tblFacturas.setItems(listarFacturas());
        colId.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, Date>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Factura, Time>("hora"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("Cliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, String>("Empleado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("total"));
        tblFacturas.getSortOrder().add(colId);
    }

    public ObservableList<Factura> listarFacturas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String empleado = resultSet.getString("Empleado");
                String cliente = resultSet.getString("Cliente");
                Double total = resultSet.getDouble("total");
                facturas.add(new Factura(facturaId, fecha, hora, empleado, cliente, total));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return FXCollections.observableList(facturas);
    }

    public Factura buscarFactura() {
        Factura factura = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String empleado = resultSet.getString("Empleado");
                String cliente = resultSet.getString("Cliente");
                Double total = resultSet.getDouble("total");

                factura = new Factura(facturaId, fecha, hora, empleado, cliente, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return factura;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
