/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.cristianlima.dto.ProductoDTO;
import org.cristianlima.model.Producto;

/**
 * FXML Controller class
 *
 * @author cristian
 */
public class CargarImagenController implements Initializable {
    
    @FXML
    TextField tfId, tfProducto;
    @FXML
    ImageView imgProducto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (ProductoDTO.getProductoDTO().getProducto() != null) {
            cargarDatos(ProductoDTO.getProductoDTO().getProducto());
            ProductoDTO.getProductoDTO().setProducto(null);
        }
    }    
    
    
    
    
    public void cargarDatos(Producto producto){
        imgProducto.setImage(mostrarImagen(producto.getImagenProducto()));
        tfId.setText(Integer.toString(producto.getProductoId()));
        tfProducto.setText(producto.getNombreProducto());
    }
    
    public Image mostrarImagen(Blob blob) {
        Image imagen = null;
        try {
            InputStream file = blob.getBinaryStream();
            imagen = new Image(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagen;
    }
}
