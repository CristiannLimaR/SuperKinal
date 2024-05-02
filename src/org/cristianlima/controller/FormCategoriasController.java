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
import javafx.scene.control.TextField;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.CategoriaProductoDTO;
import org.cristianlima.model.CategoriaProducto;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class FormCategoriasController implements Initializable {

    private Main stage;
    private int op;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    TextField tfId, tfCategoria, tfDescripcion;

    @FXML
    Button btnSalir, btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoria() != null){
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoria());
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSalir) {
            stage.menuCategoriaView();
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoria(null);
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfCategoria.getText().isEmpty() && !tfDescripcion.getText().isEmpty()) {
                    agregarCategoria();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuCategoriaView();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    return;
                }
            } else if (op == 2) {
                if (!tfCategoria.getText().isEmpty() && !tfDescripcion.getText().isEmpty()) {
                    editarCategoria();
                    CategoriaProductoDTO.getCategoriaProductoDTO().setCategoria(null);
                    stage.menuCategoriaView();
                }
            } else {
                SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
            }
        }
    }
    
    public void cargarDatos(CategoriaProducto categoria){
        tfId.setText(Integer.toString(categoria.getCategoriaProductosId()));
        tfCategoria.setText(categoria.getNombreCategoria());
        tfDescripcion.setText(categoria.getDescripcionCategoria());
    }

    public void agregarCategoria() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProductos(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCategoria.getText());
            statement.setString(2, tfDescripcion.getText());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
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

    public void editarCategoria() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setString(2, tfCategoria.getText());
            statement.setString(3, tfDescripcion.getText());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
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
