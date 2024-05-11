/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.CargoDTO;
import org.cristianlima.dto.ClienteDTO;
import org.cristianlima.model.Cargo;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class FormCargosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfId, tfCargo,tfDescripcion;
    
    @FXML
    Button btnSalir, btnGuardar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if(CargoDTO.getCargoDTO().getCargo() != null){
                cargarDatos(CargoDTO.getCargoDTO().getCargo());
            }
    }    
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnSalir){
            stage.menuCargosView();
            ClienteDTO.getClienteDTO().setCliente(null);
        }else if(event.getSource() == btnGuardar){
           if(op == 1){
               if(!tfCargo.getText().isEmpty() && !tfDescripcion.getText().isEmpty() ){
                   agregarCargo();
                   SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                   stage.menuCargosView();
               }else{
                   SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                   return;
               }
           }else if(op == 2){
               if(!tfCargo.getText().isEmpty() && !tfDescripcion.getText().isEmpty() ){
                   if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                       editarCargo();
                       CargoDTO.getCargoDTO().setCargo(null);
                       stage.menuCargosView();
                   }
                   
               }else {
                   SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
               }
           }
        }
    }
    
    public void cargarDatos(Cargo cargo){
        tfId.setText(Integer.toString(cargo.getCargoId()));
        tfCargo.setText(cargo.getNombreCargo());
        tfDescripcion.setText(cargo.getDescripcionCargo());
    }
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCargo.getText());
            statement.setString(2, tfDescripcion.getText());
            statement.execute();
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
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
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargo(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfId.getText()));
            statement.setString(2, tfCargo.getText());
            statement.setString(3, tfDescripcion.getText());
            statement.execute();
            
        }catch(SQLException e){
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
            e.printStackTrace();
        }finally {
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
