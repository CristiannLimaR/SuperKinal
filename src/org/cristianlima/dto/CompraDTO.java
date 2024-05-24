/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.dto;

import org.cristianlima.model.Compra;

/**
 *
 * @author cristian
 */
public class CompraDTO {
    private static CompraDTO instance;
    private Compra compra;
    
    private CompraDTO() {
    }
    
    public static CompraDTO getCompraDTO(){
        if(instance == null){
            instance = new CompraDTO();
        }
       return instance; 
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
