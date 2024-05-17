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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianlima.dao.Conexion;
import org.cristianlima.model.Cargo;
import org.cristianlima.model.Empleado;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuEmpleadosController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    ComboBox cmbCargo, cmbEncargado;

    @FXML
    TextField tfId, tfNombre, tfApellido, tfSueldo, tfEntrada, tfSalida, tfBuscar;

    @FXML
    Button btnGuardar, btnVaciar, btnEliminar, btnRegresar, btnBuscar;

    @FXML
    TableView tblEmpleados;

    @FXML
    TableColumn colId, colNombre, colApellido, colSueldo, colEntrada, colSalida, colCargo, colEncargado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cargarCmbs();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            if(op == 2){
                stage.formUserView();
            }else{
                stage.menuPrincipalView();
            }
        } else if (event.getSource() == btnGuardar) {
            if (tfId.getText().isEmpty()) {
                if (!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfSueldo.getText().isEmpty() && !tfEntrada.getText().isEmpty() && !tfSalida.getText().isEmpty() && !cmbCargo.getSelectionModel().isEmpty()) {
                    agregarEmpleado();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    cargarDatos();
                    if(op == 2){
                        stage.formUserView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);

                }

            } else {
                if (!tfNombre.getText().isEmpty() && !tfApellido.getText().isEmpty() && !tfSueldo.getText().isEmpty() && !tfEntrada.getText().isEmpty() && !tfSalida.getText().isEmpty() && !cmbCargo.getSelectionModel().isEmpty()) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarEmpleado();
                        cargarDatos();
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    }

                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);

                }
            }

        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnEliminar) {
            int empId = ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId();
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
            eliminarEmpleado(empId);
            cargarDatos();
              
            }
        } else if (event.getSource() == btnBuscar) {
            tblEmpleados.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarDatos();
            } else {
                tblEmpleados.getItems().add(buscarEmpleado());
                colId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
                colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
                colEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaEntrada"));
                colSalida.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaSalida"));
                colCargo.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Cargo"));
                colEncargado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Encargado"));
            }
        }
    }

    public void cargarEditar() {
        cargarCmbs();
        Empleado empleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            tfId.setText(Integer.toString(empleado.getEmpleadoId()));
            tfNombre.setText(empleado.getNombreEmpleado());
            tfApellido.setText(empleado.getApellidoEmpleado());
            tfSueldo.setText(Double.toString(empleado.getSueldo()));
            tfEntrada.setText(empleado.getHoraEntrada().toString());
            tfSalida.setText(empleado.getHoraSalida().toString());
            cmbCargo.getSelectionModel().select(obtenerIndexCargo());
            cmbEncargado.getSelectionModel().select(obtenerIndexEncargado());
        }
    }

    public void cargarDatos() {
        tblEmpleados.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaSalida"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Cargo"));
        colEncargado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Encargado"));

        tblEmpleados.getSortOrder().add(colId);
    }

    public void vaciarCampos() {
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfSueldo.clear();
        tfEntrada.clear();
        tfSalida.clear();
        cmbCargo.getSelectionModel().clearSelection();
        cmbEncargado.getSelectionModel().clearSelection();
    }

    public void cargarCmbs() {
        cmbCargo.setItems(listarCargos());
        cmbEncargado.setItems(listarEmpleados());
    }

    public int obtenerIndexCargo() {
        int index = 0;
        for (int i = 0; i < cmbCargo.getItems().size(); i++) {
            String cargoCmb = cmbCargo.getItems().get(i).toString();
            String cargoTbl = ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getCargo();
            if (cargoCmb.equals(cargoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexEncargado() {
        int index = 0;
        for (int i = 0; i < cmbEncargado.getItems().size(); i++) {
            String encargadoCmb = cmbEncargado.getItems().get(i).toString();
            String encargadoTbl = ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getEncargado();
            if (encargadoCmb.equals(encargadoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ObservableList<Empleado> listarEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int empleadoId = resultSet.getInt("empleadoId");
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time Entrada = resultSet.getTime("horaEntrada");
                Time Salida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("Cargo");
                String encargado = resultSet.getString("Encargado");

                empleados.add(new Empleado(empleadoId, nombre, apellido, sueldo, Entrada, Salida, cargo, encargado));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(empleados);
    }

    public void agregarEmpleado() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfSueldo.getText());
            statement.setString(4, tfEntrada.getText());
            statement.setString(5, tfSalida.getText());
            statement.setInt(6, ((Cargo) cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            if ((cmbEncargado.getSelectionModel().getSelectedItem()) == null) {
                statement.setNull(7, 0);
            } else {
                statement.setInt(7, ((Empleado) cmbEncargado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            }
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void editarEmpleado() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_editarEmpleado(?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfSueldo.getText());
            statement.setString(5, tfEntrada.getText());
            statement.setString(6, tfSalida.getText());
            statement.setInt(7, ((Cargo) cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(8, ((Empleado) cmbEncargado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();

        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public void eliminarEmpleado(int empId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.execute();
        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Empleado buscarEmpleado() {
        Empleado empleado = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int empleadoId = resultSet.getInt("empleadoId");
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time Entrada = resultSet.getTime("horaEntrada");
                Time Salida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("Cargo");
                String encargado = resultSet.getString("Encargado");
                empleado = new Empleado(empleadoId, nombre, apellido, sueldo, Entrada, Salida, cargo, encargado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return empleado;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    

}
