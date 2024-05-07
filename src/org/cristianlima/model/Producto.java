/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Blob;

/**
 *
 * @author informatica
 */
public class Producto {
    private int productoId;
    private String nombreProducto;
    private String descripcionProducto;
    private int cantidadStock;
    private double precioVentaUnitario;
    private double precioVentaMayor;
    private double precioCompra;
    private Blob imagemProducto;
    private int distribuidorId;
    private int categoriaProductosId;

    public Producto() {
    }

    public Producto(int productoId, String nombreProducto, String descripcionProducto, int cantidadStock, double precioVentaUnitario, double precioVentaMayor, double precioCompra, int distribuidorId, int categoriaProductosId) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadStock = cantidadStock;
        this.precioVentaUnitario = precioVentaUnitario;
        this.precioVentaMayor = precioVentaMayor;
        this.precioCompra = precioCompra;
        this.distribuidorId = distribuidorId;
        this.categoriaProductosId = categoriaProductosId;
    }

    
    public Producto(int productoId, String nombreProducto, String descripcionProducto, int cantidadStock, double precioVentaUnitario, double precioVentaMayor, double precioCompra, Blob imagemProducto, int distribuidorId, int categoriaProductosId) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadStock = cantidadStock;
        this.precioVentaUnitario = precioVentaUnitario;
        this.precioVentaMayor = precioVentaMayor;
        this.precioCompra = precioCompra;
        this.imagemProducto = imagemProducto;
        this.distribuidorId = distribuidorId;
        this.categoriaProductosId = categoriaProductosId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public double getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public void setPrecioVentaUnitario(double precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public double getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(double precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Blob getImagemProducto() {
        return imagemProducto;
    }

    public void setImagemProducto(Blob imagemProducto) {
        this.imagemProducto = imagemProducto;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public int getCategoriaProductosId() {
        return categoriaProductosId;
    }

    public void setCategoriaProductosId(int categoriaProductosId) {
        this.categoriaProductosId = categoriaProductosId;
    }

    @Override
    public String toString() {
        return "Producto{" + "productoId=" + productoId + ", nombreProducto=" + nombreProducto + ", descripcionProducto=" + descripcionProducto + ", cantidadStock=" + cantidadStock + ", precioVentaUnitario=" + precioVentaUnitario + ", precioVentaMayor=" + precioVentaMayor + ", precioCompra=" + precioCompra + ", imagemProducto=" + imagemProducto + ", distribuidorId=" + distribuidorId + ", categoriaProductosId=" + categoriaProductosId + '}';
    }
    
    
    
    
}
