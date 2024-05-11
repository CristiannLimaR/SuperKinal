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
import java.sql.Time;
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
import org.cristianlima.model.Cliente;
import org.cristianlima.model.Compra;
import org.cristianlima.model.DetalleCompra;
import org.cristianlima.model.DetalleFactura;
import org.cristianlima.model.Empleado;
import org.cristianlima.model.Producto;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class MenuFacturasController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    TableView tblFacturas;

    @FXML
    TableColumn colId, colFecha, colHora, colProducto, colPrecios, colCliente, colEmpleado, colTotal;

    @FXML
    Button btnGuardar, btnListar, btnBuscar, btnVaciar, btnEditar, btnRegresar;

    @FXML
    TextField tfId, tfTotal, tfBuscar, tfHora;

    @FXML
    DatePicker dpFecha;

    @FXML
    ComboBox cmbCliente, cmbEmpleado, cmbProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCmbs();
        cargarLista();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            if (!cmbCliente.getSelectionModel().isEmpty() && !cmbEmpleado.getSelectionModel().isEmpty() && !cmbProducto.getSelectionModel().isEmpty()) {
                if (tfId.getText().isEmpty()) {
                    agregarFactura();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                } else {
                    agregarDetalle();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                }
            }else{
                SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
            }

            cargarLista();
        } else if (event.getSource() == btnBuscar) {
            tblFacturas.getItems().clear();
            if (tfBuscar.getText().isEmpty()) {
                cargarLista();
            } else {
                tblFacturas.setItems(buscarFactura());
                colId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("facturaId"));
                colFecha.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Date>("fecha"));
                colHora.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Time>("hora"));
                colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Producto"));
                colPrecios.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Precios"));
                colEmpleado.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Empleado"));
                colCliente.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Cliente"));
                colTotal.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("total"));
            }
        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnListar) {
            cargarLista();
        }
    }

    public void cargarCmbs() {
        cmbProducto.setItems(listarProductos());
        cmbCliente.setItems(listarClientes());
        cmbEmpleado.setItems(listarEmpleados());

    }

    public void cargarLista() {
        tblFacturas.setItems(listarFacturas());
        colId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Date>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Time>("hora"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Producto"));
        colPrecios.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Precios"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("Cliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("total"));
    }

    public void vaciarCampos() {
        tfId.clear();
        tfTotal.clear();
        dpFecha.setValue(null);
        tfHora.clear();
        cmbProducto.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
    }

    public void cargarDatosEditar() {
        DetalleFactura factura = (DetalleFactura) tblFacturas.getSelectionModel().getSelectedItem();
        if (factura != null) {
            tfId.setText(Integer.toString(factura.getFacturaId()));
            tfTotal.setText(Double.toString(factura.getTotal()));
            dpFecha.setValue(factura.getFecha().toLocalDate());
            tfHora.setText(factura.getHora().toString());
            cmbProducto.getSelectionModel().select(obtenerIndexProducto());
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            cmbEmpleado.getSelectionModel().select(obtenerIndexEmpleado());

        }
    }

    public int obtenerIndexProducto() {
        int index = 0;
        for (int i = 0; i < cmbProducto.getItems().size(); i++) {
            String productoCmb = cmbProducto.getItems().get(i).toString();
            String productoTbl = ((DetalleFactura) tblFacturas.getSelectionModel().getSelectedItem()).getProducto();
            if (productoCmb.equals(productoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexCliente() {
        int index = 0;
        for (int i = 0; i < cmbCliente.getItems().size(); i++) {
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            String clienteTbl = ((DetalleFactura) tblFacturas.getSelectionModel().getSelectedItem()).getCliente();
            if (clienteCmb.equals(clienteTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexEmpleado() {
        int index = 0;
        for (int i = 0; i < cmbEmpleado.getItems().size(); i++) {
            String empleadoCmb = cmbEmpleado.getItems().get(i).toString();
            String empleadoTbl = ((DetalleFactura) tblFacturas.getSelectionModel().getSelectedItem()).getEmpleado();
            if (empleadoCmb.equals(empleadoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ObservableList<DetalleFactura> listarFacturas() {
        ArrayList<DetalleFactura> facturas = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String producto = resultSet.getString("Producto");
                String precios = resultSet.getString("Precios");
                String empleado = resultSet.getString("Empleado");
                String cliente = resultSet.getString("Cliente");
                double total = resultSet.getDouble("total");
                facturas.add(new DetalleFactura(facturaId, fecha, hora, producto, precios, empleado, cliente, total));
            }
        } catch (Exception e) {
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
        return FXCollections.observableList(facturas);
    }

    public void agregarDetalle() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleFactura(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfId.getText()));
            statement.setInt(2, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

    public void agregarFactura() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFactura(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Cliente) cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(2, ((Empleado) cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.setInt(3, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

    public ObservableList<DetalleFactura> buscarFactura() {
        ArrayList<DetalleFactura> facturas = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDetalleFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String producto = resultSet.getString("Producto");
                String precios = resultSet.getString("Precios");
                String empleado = resultSet.getString("Empleado");
                String cliente = resultSet.getString("Cliente");
                double total = resultSet.getDouble("total");
                facturas.add(new DetalleFactura(facturaId, fecha, hora, producto, precios, empleado, cliente, total));
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
        return FXCollections.observableList(facturas);
    }

    public ObservableList<Cliente> listarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }

    public ObservableList<Empleado> listarEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int empleadoId = resultSet.getInt("empleadoId");
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time Entrada = resultSet.getTime("horaEntrada");
                Time Salida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("Cargo");
                String encargado = resultSet.getString("Encargado");

                empleados.add(new Empleado(empleadoId, nombre, apellido, sueldo, Entrada, Salida, cargo, encargado));
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
        return FXCollections.observableList(empleados);
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
