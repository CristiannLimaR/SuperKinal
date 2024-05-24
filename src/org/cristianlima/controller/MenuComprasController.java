/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.CompraDTO;
import org.cristianlima.model.Compra;
import org.cristianlima.model.DetalleCompra;
import org.cristianlima.model.Producto;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuComprasController implements Initializable {

    private Main stage;

    private static Connection conexion = null;
    private static CallableStatement statement = null;
    private static ResultSet resultSet = null;
    private int nuevaCompra;

    @FXML
    Button btnRegresar, btnGuardar, btnNueva, btnListar, btnCompras;
    @FXML
    TableView tblCompras;

    @FXML
    TableColumn colId, colFecha, colProducto, colCantidad, colTotal;

    @FXML
    TextField tfBuscar, tfCantidad, tfId, tfTotal;
    @FXML
    DatePicker dpFecha;
    @FXML
    ComboBox cmbProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (CompraDTO.getCompraDTO().getCompra() != null) {
            nuevaCompra = CompraDTO.getCompraDTO().getCompra().getCompraId();
            cargarLista();
            cargarDatosAntiguos();
        }
        cmbProducto.setItems(listarProductos());
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {

        if (event.getSource() == btnRegresar) {
            CompraDTO.getCompraDTO().setCompra(null);
            stage.menuPrincipalView();
            
        } else if (event.getSource() == btnGuardar) {
            if (tfId.getText().isEmpty()) {
                if (!cmbProducto.getSelectionModel().isEmpty() && !tfCantidad.getText().isEmpty()) {
                    agregarCompra();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    cargarLista();
                    cargarDatos();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);

                }
            } else {
                if (!cmbProducto.getSelectionModel().isEmpty() && !tfCantidad.getText().isEmpty()) {
                    agregarDetalle();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    cargarLista();
                    cargarDatos();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }
            }
        } else if (event.getSource() == btnNueva) {
            vaciarCampos();
            tblCompras.getItems().clear();
        } else if (event.getSource() == btnCompras) {
            stage.comprasView();

        }
    }

    public void cargarLista() {
        tblCompras.setItems(listarCompras(nuevaCompra));
        colId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("compraId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Date>("fechaCompra"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("Producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidadCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra, String>("totalCompra"));
        tblCompras.getSortOrder().add(colId);
    }

    public void cargarDatos() {
        DetalleCompra compra = (DetalleCompra) tblCompras.getItems().get(0);
        if (compra != null) {
            tfId.setText(Integer.toString(compra.getCompraId()));
            tfTotal.setText(Double.toString(compra.getTotalCompra()));
            dpFecha.setValue(compra.getFechaCompra().toLocalDate());
            tfCantidad.setText(Integer.toString(compra.getCantidadCompra()));
        }
    }

    public void cargarDatosAntiguos() {
        tfId.setText(Integer.toString(CompraDTO.getCompraDTO().getCompra().getCompraId()));
        tfTotal.setText(Double.toString(CompraDTO.getCompraDTO().getCompra().getTotalCompra()));
        dpFecha.setValue(CompraDTO.getCompraDTO().getCompra().getFechaCompra().toLocalDate());
    }

    public void vaciarCampos() {
        tfId.clear();
        tfTotal.clear();
        dpFecha.setValue(null);
        cmbProducto.getSelectionModel().clearSelection();
        tfCantidad.clear();
    }

    public ObservableList<DetalleCompra> listarCompras(int compraId) {
        ArrayList<DetalleCompra> compras = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleCompras(?)";
            statement = conexion.prepareCall(sql);
            statement.setInt(1, compraId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                double total = resultSet.getDouble("totalCompra");
                String producto = resultSet.getString("Producto");
                int cantidad = resultSet.getInt("cantidadCompra");
                compras.add(new DetalleCompra(id, cantidad, producto, fecha, total));
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

    public void agregarDetalle() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleCompra(?,?,?)";
            statement = conexion.prepareCall(sql);
            statement.setInt(1, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(2, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3, Integer.parseInt(tfId.getText()));
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

    public void agregarCompra() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompra(?,?,?)";
            statement = conexion.prepareCall(sql);
            statement.setInt(1, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(2, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            statement.execute();

            nuevaCompra = statement.getInt(3);

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

    public ObservableList<Producto> listarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProductos()";
            statement = conexion.prepareCall(sql);
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
