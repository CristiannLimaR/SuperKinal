/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.cristianlima.dao.Conexion;
import org.cristianlima.dto.ProductoDTO;
import org.cristianlima.model.CategoriaProducto;
import org.cristianlima.model.Distribuidor;
import org.cristianlima.model.Producto;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class FormProductosController implements Initializable {

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private Main stage;
    private int op;
    private List<File> files = null;
    InputStream img;

    @FXML
    Button btnGuardar, btnSalir;

    @FXML
    TextField tfId, tfProducto, tfDescripcion, tfStock, tfPrecioU, tfPrecioM, tfPrecioC;

    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;

    @FXML
    ImageView imgCargar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCmbs();
        if (ProductoDTO.getProductoDTO().getProducto() != null) {
            cargarDatos(ProductoDTO.getProductoDTO().getProducto());
        }

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnSalir) {
            stage.menuProductosView();
            ProductoDTO.getProductoDTO().setProducto(null);
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfProducto.getText().isEmpty() && !tfDescripcion.getText().isEmpty() && !tfStock.getText().isEmpty() && !tfPrecioU.getText().isEmpty() && !tfPrecioM.getText().isEmpty() && !tfPrecioC.getText().isEmpty()
                        && cmbDistribuidor.getSelectionModel().getSelectedItem() != null && cmbCategoria.getSelectionModel().getSelectedItem() != null) {
                    agregarProducto();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuProductosView();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfProducto.requestFocus();
                    return;

                }
            } else if (op == 2) {
                if (!tfProducto.getText().isEmpty() && !tfDescripcion.getText().isEmpty() && !tfStock.getText().isEmpty() && !tfPrecioU.getText().isEmpty() && !tfPrecioM.getText().isEmpty() && !tfPrecioC.getText().isEmpty()
                        && cmbDistribuidor.getSelectionModel().getSelectedItem() != null && cmbCategoria.getSelectionModel().getSelectedItem() != null) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarProducto();
                        ProductoDTO.getProductoDTO().setProducto(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                        stage.menuProductosView();
                    }

                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    ProductoDTO.getProductoDTO().setProducto(null);
                    tfProducto.requestFocus();
                }
            }
        }
    }

    public Image mostrarImagen(Blob blob) {
        Image imagen = null;
        if (blob != null) {
            try {
                img = blob.getBinaryStream();
                imagen = new Image(img);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return imagen;
    }

    public void cargarDatos(Producto producto) {
        tfId.setText(Integer.toString(producto.getProductoId()));
        tfProducto.setText(producto.getNombreProducto());
        tfDescripcion.setText(producto.getDescripcionProducto());
        tfStock.setText(Integer.toString(producto.getCantidadStock()));
        tfPrecioU.setText(Double.toString(producto.getPrecioVentaUnitario()));
        tfPrecioM.setText(Double.toString(producto.getPrecioVentaMayor()));
        tfPrecioC.setText(Double.toString(producto.getPrecioCompra()));
        cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor(producto.getDistribuidor()));
        cmbCategoria.getSelectionModel().select(obtenerIndexCategoria(producto.getCategoria()));
        imgCargar.setImage(mostrarImagen(producto.getImagenProducto()));
    }

    public void cargarCmbs() {
        cmbDistribuidor.setItems(listarDistribuidores());
        cmbCategoria.setItems(listarCategorias());
    }

    public int obtenerIndexDistribuidor(String distribuidor) {
        int index = 0;
        for (int i = 0; i < cmbDistribuidor.getItems().size(); i++) {
            String disCmb = cmbDistribuidor.getItems().get(i).toString();

            if (disCmb.equals(distribuidor)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexCategoria(String categoria) {
        int index = 0;
        for (int i = 0; i < cmbCategoria.getItems().size(); i++) {
            String catCmb = cmbCategoria.getItems().get(i).toString();
            if (catCmb.equals(categoria)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @FXML
    public void handleOnDrag(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void handleOnDrop(DragEvent event) {
        try {
            files = event.getDragboard().getFiles();
            img = new FileInputStream(files.get(0));
            Image image = new Image(img);
            imgCargar.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfProducto.getText());
            statement.setString(2, tfDescripcion.getText());
            statement.setInt(3, Integer.parseInt(tfStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfPrecioU.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioM.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioC.getText()));
            if (imgCargar.getImage() == null) {
                statement.setBinaryStream(7, null);
            } else {
                InputStream img = new FileInputStream(files.get(0));
                statement.setBinaryStream(7, img);
            }
            statement.setInt(8, ((Distribuidor) cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProducto) cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();

        } catch (Exception e) {
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

    public void editarProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_editarProducto(?,?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setString(2, tfProducto.getText());
            statement.setString(3, tfDescripcion.getText());
            statement.setInt(4, Integer.parseInt(tfStock.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioU.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioM.getText()));
            statement.setDouble(7, Double.parseDouble(tfPrecioC.getText()));
            if (imgCargar.getImage() == null) {
                statement.setBinaryStream(8, null);
            } else {
                img = new FileInputStream(files.get(0));
                statement.setBinaryStream(8, img);
            }
            statement.setInt(9, ((Distribuidor) cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProducto) cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();

        } catch (Exception e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
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

                distribuidores.add(new Distribuidor(Id, nombre, direccion, nit, telefono, web));
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
