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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import org.cristianlima.dao.Conexion;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class ProductosMasVendidosController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    Button btnRegresar;
    
    @FXML
    BarChart bcProductos;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarGrafica();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        }
    }
    
    public void llenarGrafica(){
        XYChart.Series grafica = new XYChart.Series();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_calcularVentas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                String producto = resultSet.getString("producto");
                int ventas = resultSet.getInt("Cantidad");
                grafica.getData().add(new XYChart.Data(producto,ventas));
            }
            
            bcProductos.getData().add(grafica);
        }catch(SQLException e){
            e.printStackTrace();
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
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
