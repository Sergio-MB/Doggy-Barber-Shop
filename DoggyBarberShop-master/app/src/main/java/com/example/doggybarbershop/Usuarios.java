/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

public  class Usuarios {
    private String Nombre ;
    private String Correo;
    private String Telefono;

    public Usuarios(){

    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public Usuarios(String Nom, String Corr, String Tlfn){
        this.Nombre=Nom;
        this.Correo=Corr;
        this.Telefono=Tlfn;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getTelefono() {
        return Telefono;
    }
}
