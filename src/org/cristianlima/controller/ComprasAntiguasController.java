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
import java.util.ArrayList;
import java.util.HashSet;
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
import org.cristianlima.dto.CompraDTO;
import org.cristianlima.model.Compra;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class ComprasAntiguasController implements Initializable {

    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TableView tblCompra;

    @FXML
    TableColumn colId, colFecha, colTotal;

    @FXML
    Button btnSeleccionar, btnSalir, btnBuscar;

    @FXML
    TextField tfBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSeleccionar) {
            CompraDTO.getCompraDTO().setCompra((Compra) tblCompra.getSelectionModel().getSelectedItem());
            System.out.println(CompraDTO.getCompraDTO().getCompra());
            stage.menuComprasView();
        } else if (event.getSource() == btnSalir) {
            stage.menuComprasView();
        } else if (event.getSource() == btnBuscar) {
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblCompra.getItems().clear();
                tblCompra.getItems().add(buscarCompra());
                colId.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("compraId"));
                colFecha.setCellValueFactory(new PropertyValueFactory<Compra, Date>("fechaCompra"));
                colTotal.setCellValueFactory(new PropertyValueFactory<Compra, Double>("totalCompra"));
            }
        }
    }

    public void cargarLista() {
        tblCompra.setItems(listarCompras());
        colId.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("compraId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Compra, Date>("fechaCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra, Double>("totalCompra"));
        tblCompra.getSortOrder().add(colId);

    }

    public ObservableList<Compra> listarCompras() {
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
                compras.add(new Compra(compraId, fecha, total));

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
        return FXCollections.observableList(compras);
    }

    public Compra buscarCompra() {
        Compra compra = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");

                compra = new Compra(compraId, fecha, total);
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
        return compra;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
