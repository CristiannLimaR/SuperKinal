package org.cristianlima.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cristianlima.dao.Conexion;
import org.cristianlima.model.Cliente;
import org.cristianlima.model.Factura;
import org.cristianlima.model.TicketSoporte;
import org.cristianlima.system.Main;
import org.cristianlima.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class MenuTicketSoporteController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    ComboBox cmbEstatus, cmbClientes, cmbFacturas;

    @FXML
    Button btnRegresar, btnGuardar, btnVaciar;

    @FXML
    TextField tfTicketId;
    @FXML
    TextArea taDescripcion;

    @FXML
    TableView tblTickets;
    @FXML
    TableColumn colTicketId, colDescripcion, colEstatus, colCliente, colFactura;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            if (tfTicketId.getText().isEmpty()) {
                if (!taDescripcion.getText().isEmpty() && !cmbFacturas.getSelectionModel().isEmpty() && !cmbClientes.getSelectionModel().isEmpty()) {
                    agregarTicket();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    cargarDatos();
                    
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }

            } else {
                if (!taDescripcion.getText().isEmpty() && !cmbFacturas.getSelectionModel().isEmpty() && !cmbClientes.getSelectionModel().isEmpty()) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarTicket();
                        cargarDatos();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }
            }

        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        }
    }

    public void vaciarCampos() {
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        cmbFacturas.getSelectionModel().clearSelection();
    }

    //Cargar datps a el tableView
    public void cargarDatos() {
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("Cliente"));
        colFactura.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("Factura"));

        tblTickets.getSortOrder().add(colTicketId);
    }

    public void cargarDatosEditar() {
        TicketSoporte ts = (TicketSoporte) tblTickets.getSelectionModel().getSelectedItem();
        if (ts != null) {
            tfTicketId.setText((Integer.toString(ts.getTicketSoporteId())));
            taDescripcion.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbClientes.getSelectionModel().select(obtenerIndexClientes());
            cmbFacturas.getSelectionModel().select(obtenerIndexFacturas());
        }
    }

    //cargar el comboBox de Clientes
    public int obtenerIndexClientes() {
        int index = 0;
        for (int i = 0; i <= cmbClientes.getItems().size(); i++) {
            String clienteCmb = cmbClientes.getItems().get(i).toString();
            String clienteTbl = ((TicketSoporte) tblTickets.getSelectionModel().getSelectedItem()).getCliente();

            if (clienteCmb.equals(clienteTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexFacturas() {
        int index = 0;
        for (int i = 0; i <= cmbFacturas.getItems().size(); i++) {
            String facturaCmb = cmbFacturas.getItems().get(i).toString();
            String facturaTbl = ((TicketSoporte) tblTickets.getSelectionModel().getSelectedItem()).getFactura();

            if (facturaCmb.equals(facturaTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    // Cargar datos a el comboBox estatus
    public void cargarCmbEstatus() {
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCmbEstatus();
        cmbClientes.setItems(listarClientes());
        cmbFacturas.setItems(listarFacturas());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public ObservableList<TicketSoporte> listarTickets() {
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoporte()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                String factura = resultSet.getString("factura");

                tickets.add(new TicketSoporte(ticketId, descripcion, estatus, cliente, factura));
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
        return FXCollections.observableList(tickets);
    }

    public void agregarTicket() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoporte(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente) cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, 1);
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

    public void editarTicket() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, cmbEstatus.getSelectionModel().getSelectedItem().toString());
            statement.setInt(4, ((Cliente) cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Factura) cmbFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
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

    // consulta sp_listarClientes para llenar el comboBox clientes
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

    public ObservableList<Factura> listarFacturas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String empleado = resultSet.getString("Empleado");
                String cliente = resultSet.getString("Cliente");
                Double total = resultSet.getDouble("total");
                facturas.add(new Factura(facturaId, fecha, hora, empleado, cliente, total));

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
        return FXCollections.observableList(facturas);
    }

}
