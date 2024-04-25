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
import org.cristianlima.dto.ClienteDTO;
import org.cristianlima.model.Cliente;
import org.cristianlima.system.Main;

/**
 *
 * @author cristian
 */
public class formClientesController implements Initializable {

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private Main stage;
    private int op;
    @FXML
    Button btnGuardar, btnSalir;

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfDireccion, tfNit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ClienteDTO.getClienteDTO().getCliente() != null) {
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }

    public void cargarDatos(Cliente cliente) {
        tfId.setText(Integer.toString(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        tfNit.setText(cliente.getNit());
    }

    public void agregarCliente() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_agregarCliente(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfDireccion.getText());
            statement.setString(5, tfNit.getText());
            statement.execute();
        } catch (SQLException e) {
            e.getMessage();
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

    public void editarCliente() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_editarCliente(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfTelefono.getText());
            statement.setString(5, tfDireccion.getText());
            statement.setString(6, tfNit.getText());
            statement.execute();

        } catch (SQLException e) {
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

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (op == 1) {
                agregarCliente();
                stage.menuClientesView();
            } else if (op == 2) {
                editarCliente();
                ClienteDTO.getClienteDTO().setCliente(null);
                
                stage.menuClientesView();
            }
        } else if (event.getSource() == btnSalir) {
            stage.menuClientesView();
            ClienteDTO.getClienteDTO().setCliente(null);
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
