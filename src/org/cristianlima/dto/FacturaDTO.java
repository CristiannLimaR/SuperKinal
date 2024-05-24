/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.dto;

import org.cristianlima.model.Factura;

/**
 *
 * @author cristian
 */
public class FacturaDTO {
    private static FacturaDTO instance;
    private Factura factura;
    
    private FacturaDTO(){
        
    }

    public static FacturaDTO getFacturaDTO() {
        if(instance == null){
            instance = new FacturaDTO();
        }
        return instance;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    
    
    
}
