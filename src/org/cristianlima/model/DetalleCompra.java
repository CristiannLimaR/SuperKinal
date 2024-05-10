/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Date;

/**
 *
 * @author cristian
 */
public class DetalleCompra extends Compra {
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private String Producto;
    

    public DetalleCompra() {
    }

    public DetalleCompra(int detalleCompraId, int cantidadCompra, int productoId, int compraId, Date fechaCompra, double totalCompra) {
        super(compraId, fechaCompra, totalCompra);
        this.detalleCompraId = detalleCompraId;
        this.cantidadCompra = cantidadCompra;
        this.productoId = productoId;
    }

    public DetalleCompra(int detalleCompraId, int cantidadCompra, String Producto, int compraId, Date fechaCompra, double totalCompra) {
        super(compraId, fechaCompra, totalCompra);
        this.detalleCompraId = detalleCompraId;
        this.cantidadCompra = cantidadCompra;
        this.Producto = Producto;
    }

    public DetalleCompra(int compraId,int cantidadCompra, String Producto,Date fechaCompra, double totalCompra) {
        super(compraId, fechaCompra, totalCompra);
        this.cantidadCompra = cantidadCompra;
        this.Producto = Producto;
    }


    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String Producto) {
        this.Producto = Producto;
    }
    
    
}
