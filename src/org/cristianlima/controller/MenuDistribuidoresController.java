/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */zzbfsevq
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Connection;
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
import org.cristianlima.dto.ClienteDTO;
import org.cristianlima.model.Cliente;
import org.cristianlima.model.Distribuidor;
import org.cristianlima.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuDistribuidoresController implements Initializable {
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnRegresar, btnAgregar, btnEditar, btnListar, btnEliminar, btnBuscar;

    @FXML
    TableView tblDistribuidores;

    @FXML
    TableColumn colDistribuidorId, colNombre, colWeb, colTelefono, colDireccion, colNit;

    @FXML
    TextField tfBuscar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.formClienteController(1);
        } else if (event.getSource() == btnListar){
            cargarLista();
        }else if (event.getSource() == btnEditar) {
            ClienteDTO.getClienteDTO().setCliente((Cliente) tblDistribuidores.getSelectionModel().getSelectedItem());
            stage.formClienteController(2);
        } else if (event.getSource() == btnEliminar) {
            int cliId = ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getClienteId();
            eliminarCliente(cliId);
            cargarLista();
        } else if (event.getSource() == btnBuscar) {
            tblClientes.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblClientes.getItems().add(buscarCliente());
                colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
                colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
            }
        }

    }

    public void cargarLista() {
        tblDistribuidores.setItems(listarDistribuidores());
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidor, Integer>("distribuidorId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("nombreDistribuidor"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("web"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("telefonoDistribuidor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("direccionDistribuidor"));
        colNit.setCellValueFactory(new PropertyValueFactory<Distribuidor, String>("nitDistribuidor"));
    }

    public ObservableList<Distribuidor> listarDistribuidores() {
        ArrayList<Distribuidor> distribuidores = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("distribuidorId");
                String nombre = resultSet.getString("nombreDistribuidor");
                String direccion = resultSet.getString("direccionDistribuidor");
                String nit = resultSet.getString("nitDistribuidor");
                String telefono = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                
                distribuidores.add(new Distribuidor(Id,nombre,direccion,nit,telefono,web));
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
        return FXCollections.observableList(distribuidores);
    }

    

    public void eliminarCliente(int cliId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, cliId);
            statement.execute();
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
    }

    public Cliente buscarCliente() {
        Cliente cliente = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                cliente = (new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return cliente;
    }
    
    
}
