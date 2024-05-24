/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.cristianlima.controller.ComprasAntiguasController;
import org.cristianlima.controller.FacturasAntiguasController;
import org.cristianlima.controller.MenuCargosController;
import org.cristianlima.controller.MenuCategoriaProductosController;
import org.cristianlima.controller.FormClientesController;
import org.cristianlima.controller.MenuClientesController;
import org.cristianlima.controller.MenuComprasController;
import org.cristianlima.controller.MenuPrincipalController;
import org.cristianlima.controller.MenuTicketSoporteController;
import org.cristianlima.controller.FormCargosController;
import org.cristianlima.controller.FormCategoriasController;
import org.cristianlima.controller.FormDistribuidoresController;
import org.cristianlima.controller.FormProductosController;
import org.cristianlima.controller.FormUserController;
import org.cristianlima.controller.LoginController;
import org.cristianlima.controller.MenuDistribuidoresController;
import org.cristianlima.controller.MenuEmpleadosController;
import org.cristianlima.controller.MenuFacturasController;
import org.cristianlima.controller.MenuProductosController;
import org.cristianlima.controller.MenuPromocionesController;

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
            FormClientesController formClientesView = (FormClientesController) switchScene("MenuAgregarClienteView.fxml",500,700);
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
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosView = (MenuCargosController) switchScene("MenuCargosView.fxml",1200,750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formCargoController(int op){
        try{
            FormCargosController formCargosView = (FormCargosController) switchScene("FormCargosView.fxml",500,500);
            formCargosView.setOp(op);
            formCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaView(){
        try{
            MenuCategoriaProductosController menuCategoria = (MenuCategoriaProductosController) switchScene("MenuCategoriaProductosView.fxml",1200,750);
            menuCategoria.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaController(int op){
        try{
            FormCategoriasController formCategoriaView = (FormCategoriasController) switchScene("FormCategoriasView.fxml",500,500);
            formCategoriaView.setOp(op);
            formCategoriaView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuComprasView(){
        try{
           MenuComprasController menuComprasView = (MenuComprasController) switchScene("MenuComprasView.fxml",1300,750);
           menuComprasView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuDistribuidoresView(){
        try{
            MenuDistribuidoresController menuDistribuidoresView = (MenuDistribuidoresController) switchScene("MenuDistribuidoresView.fxml",1200,750);
            menuDistribuidoresView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formDistribuidorControllerView(int op){
        try{
            FormDistribuidoresController formDistribuidoresView = (FormDistribuidoresController) switchScene("FormDistribuidoresView.fxml",500,700);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadosView(int op){
        try{
            MenuEmpleadosController menuEmpleadoView = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml",1400,750);
            menuEmpleadoView.setOp(op);
            menuEmpleadoView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductoView = (MenuProductosController)switchScene("MenuProductosView.fxml",1400,750);
            menuProductoView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formProductosControllerView(int op){
        try{
            FormProductosController formProductosView = (FormProductosController) switchScene("FormProductosView.fxml",600,850);
            formProductosView.setOp(op);
            formProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuPromocionesView(){
        try{
            MenuPromocionesController menuPromocionView = (MenuPromocionesController )switchScene("MenuPromocionesView.fxml",1300,750);
            menuPromocionView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuFacturasView(){
        try{
            MenuFacturasController menuFacturaView = (MenuFacturasController)switchScene("MenuFacturasView.fxml",1400,750);
            menuFacturaView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loginView(){
        try{
            LoginController loginView = (LoginController)switchScene("LoginView.fxml",450,800);
            loginView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formUserView(){
        try{
            FormUserController userView= (FormUserController)switchScene("FormUserView.fxml",450,800);
            userView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void comprasView(){
        try{
            ComprasAntiguasController comprasView = (ComprasAntiguasController) switchScene("ComprasAntiguasView.fxml",700,500);
            comprasView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void facturasView(){
        try{
            FacturasAntiguasController facturasView = (FacturasAntiguasController) switchScene("FacturasAntiguasView.fxml",820,500);
            facturasView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   
    public static void main(String[] args) {
        launch(args);
    }

}
