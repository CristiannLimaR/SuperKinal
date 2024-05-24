/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.sql.Blob;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianlima.dao.Conexion;
import org.cristianlima.model.Producto;
import org.cristianlima.model.Promocion;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class MenuPromocionesController implements Initializable {

    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TableView tblPromociones;

    @FXML
    TableColumn colId, colPrecio, colDescripcion, colInicio, colFinal, colProducto;

    @FXML
    Button btnRegresar, btnGuardar, btnVaciar, btnEliminar, btnBuscar;

    @FXML
    TextField tfId, tfPrecio, tfBuscar;

    @FXML
    TextArea taDescripcion;

    @FXML
    ComboBox cmbProducto;

    @FXML
    DatePicker dpInicio, dpFinal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProducto.setItems(listarProductos());
        cargarLista();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            if (tfId.getText().isEmpty()) {
                if (!tfPrecio.getText().isEmpty() && !taDescripcion.getText().isEmpty() && dpInicio.getValue() != null && dpFinal.getValue() != null) {
                    agregarPromocion();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    cargarLista();
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }

            } else {
                if (!tfPrecio.getText().isEmpty() && !taDescripcion.getText().isEmpty() && dpInicio.getValue() != null && dpFinal.getValue() != null) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarPromocion();
                        cargarLista();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }

            }
        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnEliminar) {
            int promId = ((Promocion) tblPromociones.getSelectionModel().getSelectedItem()).getPromocionId();
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                eliminarPromocion(promId);
                cargarLista();

            }
        } else if (event.getSource() == btnBuscar) {
            tblPromociones.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblPromociones.getItems().add(buscarPromocion());
                colId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
                colPrecio.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioPromocion"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("Descripcion"));
                colInicio.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaInicio"));
                colFinal.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaFinalizacion"));
                colProducto.setCellValueFactory(new PropertyValueFactory<Promocion, String>("Producto"));
            }
        }
    }

    public void cargarLista() {
        tblPromociones.setItems(listarPromociones());
        colId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioPromocion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("Descripcion"));
        colInicio.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaInicio"));
        colFinal.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promocion, String>("Producto"));
        tblPromociones.getSortOrder().add(colId);
    }

    public void cargarDatosEditar() {
        cmbProducto.setItems(listarProductos());
        Promocion promocion = (Promocion) tblPromociones.getSelectionModel().getSelectedItem();
        if (promocion != null) {
            tfId.setText(Integer.toString(promocion.getPromocionId()));
            tfPrecio.setText(Double.toString(promocion.getPrecioPromocion()));
            taDescripcion.setText(promocion.getDescripcion());
            dpInicio.setValue(promocion.getFechaInicio().toLocalDate());
            dpFinal.setValue(promocion.getFechaFinalizacion().toLocalDate());
            cmbProducto.getSelectionModel().select(obtenerIndexProducto());
        }
    }

    public void vaciarCampos() {
        tfId.clear();
        tfPrecio.clear();
        taDescripcion.clear();
        dpInicio.setValue(null);
        dpFinal.setValue(null);
        cmbProducto.getSelectionModel().clearSelection();
    }

    public int obtenerIndexProducto() {
        int index = 0;
        for (int i = 0; i < cmbProducto.getItems().size(); i++) {
            String productoCmb = cmbProducto.getItems().get(i).toString();
            String productoTbl = ((Promocion) tblPromociones.getSelectionModel().getSelectedItem()).getProducto();
            if (productoCmb.equals(productoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ObservableList<Promocion> listarPromociones() {
        ArrayList<Promocion> promociones = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("promocionId");
                double precio = resultSet.getDouble("precioPromocion");
                String descripcion = resultSet.getString("Descripcion");
                Date inicio = resultSet.getDate("fechaInicio");
                Date fin = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("Producto");
                promociones.add(new Promocion(Id, precio, descripcion, inicio, fin, producto));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        return FXCollections.observableList(promociones);
    }

    public void agregarPromocion() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromocion(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setDouble(1, Double.parseDouble(tfPrecio.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setDate(3, Date.valueOf(dpInicio.getValue()));
            statement.setDate(4, Date.valueOf(dpFinal.getValue()));
            statement.setInt(5, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
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

    public void editarPromocion() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromocion(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setDouble(2, Double.parseDouble(tfPrecio.getText()));
            statement.setString(3, taDescripcion.getText());
            statement.setDate(4, Date.valueOf(dpInicio.getValue()));
            statement.setDate(5, Date.valueOf(dpFinal.getValue()));
            statement.setInt(6, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();

        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
            e.printStackTrace();
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

    public void eliminarPromocion(int promId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, promId);
            statement.execute();

        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertaInfo(402);
            e.printStackTrace();
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

    public Promocion buscarPromocion() {
        Promocion promocion = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("promocionId");
                double precio = resultSet.getDouble("precioPromocion");
                String descripcion = resultSet.getString("Descripcion");
                Date inicio = resultSet.getDate("fechaInicio");
                Date fin = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("Producto");
                promocion = (new Promocion(Id, precio, descripcion, inicio, fin, producto));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        return promocion;
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
