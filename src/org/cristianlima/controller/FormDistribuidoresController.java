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
import org.cristianlima.dto.DistribuidorDTO;
import org.cristianlima.model.Distribuidor;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormDistribuidoresController implements Initializable {

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private Main stage;
    private int op;
    @FXML
    Button btnGuardar, btnSalir;

    @FXML
    TextField tfId, tfNombre, tfWeb, tfTelefono, tfDireccion, tfNit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSalir) {
            stage.menuDistribuidoresView();
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombre.getText().isEmpty() && !tfDireccion.getText().isEmpty() && !tfTelefono.getText().isEmpty() && !tfNit.getText().isEmpty()) {
                    agregarDistribuidor();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuDistribuidoresView();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;

                }
            } else if (op == 2) {
                if (!tfNombre.getText().isEmpty() && !tfDireccion.getText().isEmpty() && !tfTelefono.getText().isEmpty() && !tfNit.getText().isEmpty()) {
                    editarDistribuidor();
                    DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                    stage.menuDistribuidoresView();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }
            }
        }
    }

    public void cargarDatos(Distribuidor distribuidor) {
        tfId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombre.setText(distribuidor.getNombreDistribuidor());
        tfDireccion.setText(distribuidor.getDireccionDistribuidor());
        tfTelefono.setText(distribuidor.getTelefonoDistribuidor());
        tfNit.setText(distribuidor.getNitDistribuidor());
        tfWeb.setText(distribuidor.getWeb());

    }

    public void agregarDistribuidor() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidor(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfDireccion.getText());
            statement.setString(3, tfNit.getText());
            statement.setString(4, tfTelefono.getText());
            if (tfWeb.getText().isEmpty()) {
                statement.setString(5, null);
            } else {
                statement.setString(5, tfWeb.getText());

            }
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

    public void editarDistribuidor() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidor(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfDireccion.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfTelefono.getText());
            statement.setString(6, tfWeb.getText());
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
