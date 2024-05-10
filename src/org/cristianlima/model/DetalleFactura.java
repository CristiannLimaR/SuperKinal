/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author cristian
 */
public class DetalleFactura extends Factura{
    private int detalleFacturaId;
    private int productoId;
    private String producto;
    private String precios;

    public DetalleFactura(int productoId, String Producto, int facturaId, Date fecha, Time hora, int clienteId, int empleadoId, double total) {
        super(facturaId, fecha, hora, clienteId, empleadoId, total);
        this.productoId = productoId;
    }

    public DetalleFactura(int detalleFacturaId ,int productoId, int facturaId, Date fecha, Time hora, int clienteId, int empleadoId) {
        super(facturaId, fecha, hora, clienteId, empleadoId);
        this.detalleFacturaId = detalleFacturaId;
        this.productoId = productoId;
    }

    public DetalleFactura(int detalleFacturaId, String Producto, int facturaId, Date fecha, Time hora, String cliente, String empleado, double total) {
        super(facturaId, fecha, hora, cliente, empleado, total);
        this.detalleFacturaId = detalleFacturaId;
        this.producto = producto;
    }

    public DetalleFactura(int detalleFacturaId, String Producto, int facturaId, Date fecha, Time hora, String cliente, String empleado) {
        super(facturaId, fecha, hora, cliente, empleado);
        this.detalleFacturaId = detalleFacturaId;
        this.producto = producto;
    }

    public DetalleFactura(int facturaId, Date fecha, Time hora, String producto, String precios, String empleado, String cliente, double total) {
        super(facturaId,fecha,hora,cliente,empleado,total);
        this.precios = precios;
        this.producto = producto;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String Producto) {
        this.producto = producto;
    }

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }
    
    
    
    
    
    
    
    
    
    
}
