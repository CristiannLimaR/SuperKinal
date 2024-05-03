/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.cristianlima.system.Main;
/**
 *
 * @author cristian
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    @FXML
    MenuItem btnMenuClientes, btnMenuTicketSoporte, btnCargos,btnCategoria,btnCompras,btnDistribuidores;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception{
        if(event.getSource() == btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource() == btnMenuTicketSoporte){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnCategoria){
            stage.menuCategoriaView();
        }else if(event.getSource() == btnCompras){
            stage.menuComprasView();
        }else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidoresView();
        }
    }
    
    
}
