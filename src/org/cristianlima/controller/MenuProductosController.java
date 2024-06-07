/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Blob;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.ProductoDTO;
import org.cristianlima.model.Producto;
import org.cristianlima.report.GenerarReporte;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {

    private Main stage;
    private Stage Imagen;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnRegresar, btnAgregar, btnEditar, btnListar, btnEliminar, btnBuscar, btnImagen;

    @FXML
    TableView tblProductos;

    @FXML
    TableColumn colId, colProducto, colDescripcion, colStock, colPrecioU, colPrecioM, colPrecioC, colDistribuidor, colCategoria;

    @FXML
    TextField tfBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        try {
            if (event.getSource() == btnRegresar) {
                stage.menuPrincipalView();
            } else if (event.getSource() == btnAgregar) {
                stage.formProductosControllerView(1);
                cargarLista();
            } else if (event.getSource() == btnListar) {
                GenerarReporte.getInstance().generarProductos();
            } else if (event.getSource() == btnEditar) {
                ProductoDTO.getProductoDTO().setProducto((Producto) tblProductos.getSelectionModel().getSelectedItem());
                stage.formProductosControllerView(2);
            } else if (event.getSource() == btnEliminar) {
                int proId = ((Producto) tblProductos.getSelectionModel().getSelectedItem()).getProductoId();
                if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                    eliminarProducto(proId);
                    cargarLista();

                }
            } else if (event.getSource() == btnImagen) {
                ProductoDTO.getProductoDTO().setProducto((Producto) tblProductos.getSelectionModel().getSelectedItem());
                mostrarImagen();
            } else if (event.getSource() == btnBuscar) {
                tblProductos.getItems().clear();
                if (tfBuscar.getText().isEmpty()) {
                    cargarLista();
                } else {
                    tblProductos.getItems().add(buscarProducto());
                    colId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
                    colProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
                    colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
                    colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
                    colPrecioU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
                    colPrecioM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
                    colPrecioC.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
                    colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("Distribuidor"));
                    colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("Categoria"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarImagen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/cristianlima/view/CargarImagenView.fxml"));
            Imagen = new Stage();
            Imagen.setTitle("Imagen");
            Imagen.setScene(new Scene(root));
            Imagen.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarLista() {
        tblProductos.setItems(listarProductos());
        colId.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("productoId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("descripcionProducto"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidadStock"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaUnitario"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioVentaMayor"));
        colPrecioC.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precioCompra"));
        colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String>("Distribuidor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("Categoria"));
        tblProductos.getSortOrder().add(colId);
    }

    public ObservableList<Producto> listarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int stock = resultSet.getInt("cantidadStock");
                double precioU = resultSet.getDouble("precioVentaUnitario");
                double precioM = resultSet.getDouble("precioVentaMayor");
                double precioC = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidor");
                String categoria = resultSet.getString("Categoria");

                productos.add(new Producto(Id, nombre, descripcion, stock, precioU, precioM, precioC, imagen, distribuidor, categoria));
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
        return FXCollections.observableList(productos);
    }

    public void eliminarProducto(int proId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, proId);
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
                System.out.println(e.getMessage());
            }
        }
    }

    public Producto buscarProducto() {
        Producto producto = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int Id = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int stock = resultSet.getInt("cantidadStock");
                double precioU = resultSet.getDouble("precioVentaUnitario");
                double precioM = resultSet.getDouble("precioVentaMayor");
                double precioC = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidor");
                String categoria = resultSet.getString("Categoria");
                producto = new Producto(Id, nombre, descripcion, stock, precioU, precioM, precioC, imagen, distribuidor, categoria);
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
        return producto;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
