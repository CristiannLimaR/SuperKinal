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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cristianlima.dao.Conexion;
import org.cristianlima.model.Usuario;
import org.cristianlima.system.Main;
import org.cristianlima.utils.PasswordUtils;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class LoginController implements Initializable {

    private Main stage;
    private int op = 0;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TextField tfUser;

    @FXML
    PasswordField tfPassword;

    @FXML
    Button btnIniciar, btnRegistrar;

    @FXML
    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btnIniciar) {
            Usuario user = buscarUsuario();
            if (op == 0) {
                if (user != null) {
                    if (PasswordUtils.getInstance().checkPassword(tfPassword.getText(), user.getContrasenia())) {
                        SuperKinalAlert.getInstance().alertaSaludo(user.getUsuario());
                        if (user.getNivelAccesoId() == 1) {
                            btnRegistrar.setDisable(false);
                            btnIniciar.setText("Ir al menu");
                            op = 1;
                        } else if (user.getNivelAccesoId() == 2) {
                            stage.menuPrincipalView();
                        }

                    } else {
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(005);
                    }

                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(602);
                }   
            } else {
                stage.menuPrincipalView();
            }

        } else if (event.getSource() == btnRegistrar) {
            stage.formUserView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Usuario buscarUsuario() {
        Usuario usuario = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarUsuario(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUser.getText());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int usuarioId = resultSet.getInt("usuarioId");
                String user = resultSet.getString("usuario");
                String contra = resultSet.getString("pass");
                int nivel = resultSet.getInt("nivelAccesoId");
                int empleado = resultSet.getInt("empleadoId");

                usuario = new Usuario(usuarioId, user, contra, nivel, empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return usuario;
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
