/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import java.io.Serializable;

public class Cita implements Serializable {
    private String NombreUsuario;
    private String Peluqueria;
    private String Raza;
    private String Servicio;
    private String Fecha;
    private String Hora;
    private String Otros;
    public Cita(String Nom,String Pelu,String Raza,String Serv,String Fecha,String Hora,String Otros){
       this.NombreUsuario=Nom;
       this.Peluqueria=Pelu;
       this.Raza=Raza;
       this.Servicio=Serv;
       this.Fecha=Fecha;
       this.Hora=Hora;
       this.Otros=Otros;
    }
    public Cita(){

    }

    public String getOtros() {
        return Otros;
    }

    public void setOtros(String otros) {
        Otros = otros;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getPeluqueria() {
        return Peluqueria;
    }

    public void setPeluqueria(String peluqueria) {
        Peluqueria = peluqueria;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String raza) {
        Raza = raza;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
