/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Paso3Fragment extends Fragment  {
    private EditText editTextRaza;
    private Spinner spinnerServicio;
    private EditText textoOtros;
    private Button btnSiguiente;
    private String fecha;
    private String hora;
    private Peluqueria peluqueria;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_paso3, container, false);

        editTextRaza=(EditText)rl.findViewById(R.id.editTextRaza);
        spinnerServicio=(Spinner)rl.findViewById(R.id.spinnerServicio);
        textoOtros=(EditText)rl.findViewById(R.id.editTextOtros);
        btnSiguiente=(Button)rl.findViewById(R.id.buttonNext);
        textoOtros.setLines(4);
        Bundle datosRecuperados = getArguments();
        peluqueria=(Peluqueria)datosRecuperados.getSerializable("peluqueria");
        fecha=datosRecuperados.getString("fecha");
        hora=datosRecuperados.getString("hora");

        Query query= FirebaseDatabase.getInstance().getReference("Servicios");
        final List<String> listaServicios = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Servicios servicios = dataSnapshot.getValue(Servicios.class);
                listaServicios.add(servicios.getTipo());
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rl.getContext(),android.R.layout.simple_spinner_item,listaServicios);
                // dataAdapter.setDropDownViewResource(R.layout.spinner_style_mod);
                spinnerServicio.setAdapter(dataAdapter);
                //  Log.e("CHILEDADDED","add TIPO name = " + servicios.getTipo());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            //    Servicios servicios = dataSnapshot.getValue(Servicios.class);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String raza, servicio, otros;


                raza = editTextRaza.getText().toString();
                servicio = spinnerServicio.getSelectedItem().toString();
                otros = textoOtros.getText().toString();
                //  Log.e("BOTONN","raza: "+raza+" servicio: "+servicio+" otros: "+otros);
                //Creo un Bundle para mandar datos entre fragments
                if (raza.isEmpty()||servicio.isEmpty()) {
                        Toast.makeText(rl.getContext(),"Completa todos los campos",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle datos = new Bundle();
                    datos.putSerializable("peluqueria", peluqueria);
                    datos.putString("fecha", fecha);
                    datos.putString("hora", hora);
                    datos.putString("raza", raza);
                    datos.putString("servicio", servicio);
                    datos.putString("otros", otros);
                    Fragment fragment = new Paso4Fragment();

                    fragment.setArguments(datos);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_content, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }

        });

        //EN EL ONCLICK DEL BUTTON
//        String raza,servicio,otros;
//        raza=editTextRaza.getText().toString();
//        servicio=spinnerServicio.getSelectedItem().toString();
//        otros=textoOtros.getText().toString();



        return rl;
    }



}
