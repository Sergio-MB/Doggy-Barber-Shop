/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;

public class Paso1Fragment extends Fragment {
    private Calendar calendar;
    private String MesActual;
    private String Year;
    private ListView mListView;
    private ArrayList<Peluqueria> listaPelu;
    //Adaptador de listas
    private FirebaseListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listaPelu=new ArrayList<Peluqueria>();
        /*Creo un objeto relative layout para poder acceder a los datos del layout que quiera*/
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_paso1, container, false);
        mListView=(ListView)rl.findViewById(R.id.list_viewPaso1);
        //Obtengo la referencia del nodo que quiero consultar
        Query query= FirebaseDatabase.getInstance().getReference("Peluquerias");

        //Creo las opciones
        FirebaseListOptions<Peluqueria> options=new FirebaseListOptions.Builder<Peluqueria>()
                .setLayout(R.layout.peluqueria_view)
                .setQuery(query,Peluqueria.class)
                .build();
        //Creo el adaptador con las opciones ya creadas anteriormente
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView Lugar=v.findViewById(R.id.TxTLugar);
                TextView Nombre=v.findViewById(R.id.TxTNombrePelu);

                Peluqueria peluqueria=(Peluqueria)model;

                Nombre.setText("Nombre Peluqueria: " + peluqueria.getNombre().toString());
                Lugar.setText("Lugar : " + peluqueria.getLugar().toString());
              if(!listaPelu.contains(peluqueria)) listaPelu.add(peluqueria);

            }
        };

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int fila, long arg3) {

                Log.v("TAG", "CLICKED  " + fila+"arrayList: "+ listaPelu.get(fila));
                //Envio datos al paso 2
                Bundle datos=new Bundle();
                Fragment fragment=new Paso2Fragment();
                datos.putSerializable("peluqueria",listaPelu.get(fila));

                fragment.setArguments(datos);//TENGO QUE PASAR OBJETO PELUQUERIA
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_content,fragment)
                        .addToBackStack(null)
                        .commit();
            }

        });

        mListView.setAdapter(adapter);
        return rl;
        // return inflater.inflate(R.layout.fragment_month, null);
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
