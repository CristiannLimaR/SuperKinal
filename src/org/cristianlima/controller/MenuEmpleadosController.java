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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuEmpleadosController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    ComboBox cmbCargo, cmbEncargado;
    
    @FXML
    TextField tfId, tfNombre,tfApellido,tfSueldo,tfEntrada,tfSalida;
            
    @FXML
    Button btnGuardar, btnVaciar,btnEliminar;
            
    @FXML
    TableView tblEmpleados;
            
    @FXML
    TableColumn colId,colNombre,colApellido,colSueldo,colEntrada,colSalida,colCargo,colEncargado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
