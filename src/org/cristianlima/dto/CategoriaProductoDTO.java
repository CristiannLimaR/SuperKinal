/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.dto;

import org.cristianlima.model.CategoriaProducto;

/**
 *
 * @author cristian
 */
public class CategoriaProductoDTO {
    private static CategoriaProductoDTO instance;
    private CategoriaProducto categoria;

    private CategoriaProductoDTO() {
    }
    
    public static CategoriaProductoDTO getCategoriaProductoDTO(){
        if(instance  == null){
            instance = new CategoriaProductoDTO();
        }
        return instance;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }
    
    
}
