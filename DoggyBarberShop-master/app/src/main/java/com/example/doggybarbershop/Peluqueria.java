/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import java.io.Serializable;

public class Peluqueria implements Serializable {
    private String Lugar;
    private String Nombre;

    public Peluqueria(){

    }

    public Peluqueria(String lugar, String nombre) {
        Lugar = lugar;
        Nombre = nombre;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
