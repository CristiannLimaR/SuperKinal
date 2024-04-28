/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.cristianlima.dto.CargoDTO;
import org.cristianlima.model.Cargo;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class MenuCargosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TableView tblCargos;
    @FXML
    TableColumn colCargoId, colCargo, colDescripcion;
    @FXML
    Button btnRegresar, btnAgregar, btnEditar, btnListar, btnEliminar, btnBuscar;
    @FXML
    TextField tfBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.formCargoController(1);
        } else if (event.getSource() == btnListar) {
            cargarLista();
        } else if (event.getSource() == btnEditar) {
            CargoDTO.getCargoDTO().setCargo((Cargo) tblCargos.getSelectionModel().getSelectedItem());
            stage.formCargoController(2);
        } else if (event.getSource() == btnEliminar) {
            int carId = ((Cargo) tblCargos.getSelectionModel().getSelectedItem()).getCargoId();
            eliminarCargo(carId);
            cargarLista();
        } else if (event.getSource() == btnBuscar) {
            tblCargos.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblCargos.getItems().add(buscarCargo());
                colCargoId.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("CargoId"));
                colCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory<Cargo, String>("DescripcionCargo"));
            }
        }

    }

    public void cargarLista() {
        tblCargos.setItems(listarCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("CargoId"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Cargo, String>("DescripcionCargo"));
    }

    public ObservableList<Cargo> listarCargos() {
        ArrayList<Cargo> cargos = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
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
        return FXCollections.observableList(cargos);
    }

    public void eliminarCargo(int carId) {

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
            statement.execute();

        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
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
                System.out.println(e.getMessage());
                
            }
        }
    }

    public Cargo buscarCargo() {
        Cargo cargo = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcion = resultSet.getString("descripcionCargo");
                cargo = new Cargo(cargoId, nombreCargo, descripcion);
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
        return cargo;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
