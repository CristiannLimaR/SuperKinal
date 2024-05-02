/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Date;

/**
 *
 * @author informatica
 */
public class Compra {
    private int compraId;
    private Date fechaCompra;
    private int totalCompra;

    public Compra() {
    }

    public Compra(int compraId, Date fechaCompra, int totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }
    
    

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(int totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Compras{" + "compraId=" + compraId + ", fechaCompra=" + fechaCompra + ", totalCompra=" + totalCompra + '}';
    }
    
    
    
}
