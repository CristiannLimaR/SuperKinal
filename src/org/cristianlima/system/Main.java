/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.system;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.cristianlima.controller.formClientesController;
import org.cristianlima.controller.MenuClientesController;
import org.cristianlima.controller.MenuPrincipalController;
import org.cristianlima.controller.MenuTicketSoporteController;

/**
 *
 * @author cristian
 */
public class Main extends Application {

    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/cristianlima/view/";

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("SuperKinal");
        menuPrincipalView();
        stage.show();

    }

    public Initializable switchScene(String fxmlName,int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        Image icon = new Image("/org/cristianlima/image/icon.png");
        scene = new Scene((AnchorPane)loader.load(file),width, height);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml",1000,675);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesView.fxml",1200,750);
            menuClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClienteController(int op){
        try{
            formClientesController formClientesView = (formClientesController) switchScene("MenuAgregarClienteView.fxml",500,700);
            formClientesView.setOp(op);
            formClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml",1200,750);
            menuTicketView.setStage(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
