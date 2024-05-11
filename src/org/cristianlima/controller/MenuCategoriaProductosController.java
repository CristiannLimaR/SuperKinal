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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class MenuCategoriaProductosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblCategorias;

    @FXML
    TableColumn colCategoriaId, colCategoria, colDescripcion;

    @FXML
    Button btnRegresar, btnAgregar, btnEditar, btnListar, btnEliminar, btnBuscar;

    @FXML
    TextField tfBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();

    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnListar) {
            cargarLista();
        } else if (event.getSource() == btnAgregar) {
            stage.formCategoriaController(1);
        } else if (event.getSource() == btnEditar) {
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoria((CategoriaProducto) tblCategorias.getSelectionModel().getSelectedItem());
            stage.formCategoriaController(2);
        } else if (event.getSource() == btnEliminar) {
            int catId = ((CategoriaProducto) tblCategorias.getSelectionModel().getSelectedItem()).getCategoriaProductosId();
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                eliminarCategoria(catId);
                cargarLista();
            }
        } else if (event.getSource() == btnBuscar) {
            tblCategorias.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblCategorias.setItems(listarCategorias());
                colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, Integer>("categoriaId"));
                colCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("nombreCategoria"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("descripcionCategoria"));
            }
        }
    }

    public void cargarLista() {
        tblCategorias.setItems(listarCategorias());
        colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, Integer>("categoriaProductosId"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("nombreCategoria"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<CategoriaProducto, String>("descripcionCategoria"));

    }

    public ObservableList<CategoriaProducto> listarCategorias() {
        ArrayList<CategoriaProducto> categorias = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int catPId = resultSet.getInt("categoriaProductosId");
                String nombreCat = resultSet.getString("nombreCategoria");
                String desCat = resultSet.getString("descripcionCategoria");
                categorias.add(new CategoriaProducto(catPId, nombreCat, desCat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return FXCollections.observableList(categorias);

    }

    public void eliminarCategoria(int catId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, catId);
            statement.execute();
        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
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
                SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
                System.out.println(e.getMessage());
            }
        }
    }

    public CategoriaProducto buscarCategoria() {
        CategoriaProducto categoria = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int catPId = resultSet.getInt("categoriaProductoId");
                String nombreCat = resultSet.getString("nombreCategoria");
                String desCat = resultSet.getString("dscripcionCategoria");
                categoria = (new CategoriaProducto(catPId, nombreCat, desCat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return categoria;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
