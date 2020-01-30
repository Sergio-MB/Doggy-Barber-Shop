/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CitaAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Cita> datos;

    public CitaAdapter(Context context, ArrayList datos) {
        super(context, R.layout.cita_view, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.cita_view, null);

        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.


        TextView NombreUser=item.findViewById(R.id.TxTnombreUsuario);
        TextView pelu=item.findViewById(R.id.TxTpeluqueria);
        TextView raza=item.findViewById(R.id.TxTraza);
        TextView servicio=item.findViewById(R.id.TxTservicio);
        TextView fecha=item.findViewById(R.id.TxTFecha);

        NombreUser.setText("Nombre: " + datos.get(position).getNombreUsuario());
        pelu.setText("Peluqueria: " + datos.get(position).getPeluqueria());
        raza.setText("Raza: " + datos.get(position).getRaza());
        servicio.setText("Servicio: " + datos.get(position).getServicio());
        fecha.setText("Fecha: " + datos.get(position).getFecha());
//        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

}
