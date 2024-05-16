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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.cristianlima.dao.Conexion;
import org.cristianlima.model.Empleado;
import org.cristianlima.model.NivelAcceso;
import org.cristianlima.system.Main;
import org.cristianlima.utils.PasswordUtils;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormUserController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfUser, tfPassword;
    
    @FXML
    ComboBox cmbEmpleado, cmbAcceso;
    
    @FXML
    Button btnRegistrar, btnEmpleado;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegistrar){
            agregarUsuario();
            stage.loginView();
        }else if(event.getSource() == btnEmpleado){
           stage.menuEmpleadosView(2);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEmpleado.setItems(listarEmpleados());
        cmbAcceso.setItems(listarNivelAcceso());
    }    
    
    public void agregarUsuario(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarUsuario(?,?,?,?)";
            statement  = conexion.prepareStatement(sql);
            statement.setString(1, tfUser.getText());
            statement.setString(2,PasswordUtils.getInstance().encryptedPassword(tfPassword.getText()));
            statement.setInt(3,((NivelAcceso) cmbAcceso.getSelectionModel().getSelectedItem()).getNivelAccesoId());
            statement.setInt(4, ((Empleado) cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
             try {
                if (conexion != null) {
                    conexion.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ObservableList<NivelAcceso> listarNivelAcceso(){
        ArrayList<NivelAcceso> niveles = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarNivelAcceso()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                String nivelAcceso = resultSet.getString("nivelAcceso");
                
                niveles.add(new NivelAcceso(nivelAccesoId,nivelAcceso));
            }
        }catch(SQLException e){
            
        }finally{
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
        
        return FXCollections.observableList(niveles);
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
    
    
    
}
