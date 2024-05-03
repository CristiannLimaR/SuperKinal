/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cristianlima.model;

import java.sql.Time;

/**
 *
 * @author informatica
 */
public class Empleado {

    private int empleadoId;
    private String nombreEmpleado;
    private String apelligoEmpleado;
    private Double sueldo;
    private Time horaEntrada;
    private Time horaSalida;
    private String cargo;
    private int cargoId;
    private String encargado;
    private int encargadoId;

    public Empleado(int empleadoId, String nombreEmpleado, String apelligoEmpleado, Double sueldo, Time horaEntrada, Time horaSalida, String cargo, String encargado) {
        this.empleadoId = empleadoId;
        this.nombreEmpleado = nombreEmpleado;
        this.apelligoEmpleado = apelligoEmpleado;
        this.sueldo = sueldo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.cargo = cargo;
        this.encargado = encargado;
    }

    public Empleado(int empleadoId, String nombreEmpleado, String apelligoEmpleado, Double sueldo, Time horaEntrada, Time horaSalida, int cargoId, int encargadoId) {
        this.empleadoId = empleadoId;
        this.nombreEmpleado = nombreEmpleado;
        this.apelligoEmpleado = apelligoEmpleado;
        this.sueldo = sueldo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.cargoId = cargoId;
        this.encargadoId = encargadoId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApelligoEmpleado() {
        return apelligoEmpleado;
    }

    public void setApelligoEmpleado(String apelligoEmpleado) {
        this.apelligoEmpleado = apelligoEmpleado;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    @Override
    public String toString() {
        return "Empleado{" + "empleadoId=" + empleadoId + ", nombreEmpleado=" + nombreEmpleado + ", apelligoEmpleado=" + apelligoEmpleado + ", sueldo=" + sueldo + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", cargo=" + cargo + ", cargoId=" + cargoId + ", encargado=" + encargado + ", encargadoId=" + encargadoId + '}';
    }
    
    

}
