/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author cristian
 */
public class Promocion {

    private int promocionId;
    private double precioPromocion;
    private String descripcionPromocion;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private int productoId;
    private String producto;
    
    

    public Promocion() {
    }

    public Promocion(int promocionId, double precioPromocion, String descripcionPromocion, Date fechaInicio, Date fechaFinalizacion, int productoId) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.productoId = productoId;
    }

    public Promocion(int promocionId, double precioPromocion, String descripcionPromocion, Date fechaInicio, Date fechaFinalizacion, String producto) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.producto = producto;
    }

    

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public String getDescripcion() {
        return descripcionPromocion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcionPromocion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
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

    public void setProducto(String producto) {
        this.producto = producto;
    }

}
