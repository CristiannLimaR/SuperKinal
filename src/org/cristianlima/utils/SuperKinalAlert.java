/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;

    private SuperKinalAlert() {
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){ //ALERTA DE CAMPOS PENDIENTES
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos pendientes");
            alert.setContentText("Algunos campos necesarios para el registro están pendientes");
            alert.showAndWait();
        }else if(code == 401){ //Alerta confirmacion
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación de registro");
            alert.setHeaderText("Confirmación de registro");
            alert.setContentText("El registro de ha creado con éxito!!!!!");
            alert.showAndWait();
        }else if(code == 402){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al eliminar o editar");
            alert.setHeaderText("Error al eliminar o editar");
            alert.setContentText("No puedes eliminar o editar este registro porque esta conectado a otra tabla ");
            alert.showAndWait();
        }else if(code  == 602){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario incorrecto");
            alert.setHeaderText("Usuario incorrecto");
            alert.setContentText("Verifique su usuario");
            alert.showAndWait();
        }else if(code  == 005){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña incorrecta");
            alert.setHeaderText("Contraseña incorrecto");
            alert.setContentText("Verifique su contraseña");
            alert.showAndWait();
        }
    }
        
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        if (code == 405) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de registro");
            alert.setHeaderText("Eliminacion de registro");
            alert.setContentText("¿Desea confirmar la eliminacion de registro?");
            action = alert.showAndWait();
        }else if(code == 106){ //Alerta confirmacion para edición de registros
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edición de registros");
            alert.setHeaderText("Edición de registros");
            alert.setContentText("¿Desea confirmar la edición del registro?");
            action = alert.showAndWait();
        }
        
    return action;
    }
}
