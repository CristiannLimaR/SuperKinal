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
import org.cristianlima.model.Cliente;
import org.cristianlima.model.Compra;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuComprasController implements Initializable {
    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnRegresar, btnAgregar, btnEditar, btnListar, btnEliminar, btnBuscar;

    @FXML
    TableView tblCompras;

    @FXML
    TableColumn colCompraId, colFecha, colTotal, colTelefono, colDireccion, colNit;

    @FXML
    TextField tfBuscar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception{
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            
        }else if(event.getSource() == btnListar){
            cargarLista();
        }else if(event.getSource() == btnEditar){
            
        }else if(event.getSource() == btnEliminar){
            int comId = ((Compra) tblCompras.getSelectionModel().getSelectedItem()).getCompraId();
            eliminarCompra(comId);
            cargarLista();
        }else if(event.getSource() == btnBuscar){
            tblCompras.getItems().clear();
            if(tfBuscar.getText().isEmpty()){
                cargarLista();
            } else{
                tblCompras.setItems(listarCompras());
            colCompraId.setCellValueFactory(new PropertyValueFactory<Compra,Integer>("compraId"));
            colFecha.setCellValueFactory(new PropertyValueFactory<Compra,String>("fechaCompra"));
            colTotal.setCellValueFactory(new PropertyValueFactory<Compra,String>("totalCompra"));
            }
        }
    }
    
    public void cargarLista(){
        tblCompras.setItems(listarCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compra,Integer>("compraId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Compra,String>("fechaCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra,String>("totalCompra"));
    }
    
    
    public ObservableList<Compra> listarCompras(){
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompras()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                int total = resultSet.getInt("totalCompra");
                
                compras.add(new Compra(compraId,fecha,total));
            }
        } catch (SQLException e) {
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
        return FXCollections.observableList(compras);
    }
    
    
    public void eliminarCompra(int comId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, comId);
            statement.execute();
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
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
    
    
    public Compra buscarCompra(){
        Compra compra = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                int total = resultSet.getInt("totalCompra");
                compra = (new Compra(compraId, fecha,total));
            }
        } catch (SQLException e) {
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
        return compra;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
