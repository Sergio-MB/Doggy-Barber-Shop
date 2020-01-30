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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Paso4Fragment extends Fragment {
    private TextView txtView;
    private Button btnConfirmar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference CitasBD;
    private String user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_paso4, container, false);
        Bundle datosRecuperados = getArguments();
//        if (datosRecuperados == null) {
//            // No hay datos, manejar excepción
//            return;
//        }
        String[] email=HomeActivity.userLogin.getEmail().split("@");
        user=email[0];
        firebaseAuth=FirebaseAuth.getInstance();//AUTH
        CitasBD= FirebaseDatabase.getInstance().getReference("Citas");//BD

        txtView=rl.findViewById(R.id.TextViewCitaCompleta);
        btnConfirmar=rl.findViewById(R.id.ButtonAceptaCita);

        final Peluqueria peluqueria= (Peluqueria)datosRecuperados.getSerializable("peluqueria");
        final String hora = datosRecuperados.getString("hora");
        final String fecha = datosRecuperados.getString("fecha");
        final String raza = datosRecuperados.getString("raza");
        final String servicio = datosRecuperados.getString("servicio");
        final String otros = datosRecuperados.getString("otros");
        txtView.setText("\n\tDatos de la cita: \n\n\t\tPeluqueria: "+peluqueria.getNombre()+"\n"+"\t\tDirección: "+peluqueria.getLugar()+"\n"+"\t\tRaza: "+raza+"\n"+"\t\tServicio: "+servicio+"\n"
                +"\t\tOtros datos: "+otros+"\n"+"\t\tFecha: "+fecha+"\n"+"\t\tHora: "+hora+"\n\n ¿Desea confirmar la cita?");

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatosCita(user,peluqueria.getNombre(),fecha,hora,raza,servicio,otros);

                //Mandar activity principal
                Intent intent=new Intent(getContext(),HomeActivity.class);
                startActivity(intent);
//               Fragment fragment = new Paso5Fragment();
//
//
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.home_content, fragment)
//                            .addToBackStack(null)
//                            .commit();


            }

        });


        return rl;
    }



    public void guardarDatosCita(String nombreUser,String nombrePeluqueria,String fecha,String hora,String raza,String servicio,String otros){

        String id =CitasBD.push().getKey();
        Cita cita= new Cita( nombreUser,nombrePeluqueria,raza,servicio,fecha,hora,otros);
        CitasBD.child(id).setValue(cita);
        Log.d("DATOS GUARDADOS","Datos mandados al json" +CitasBD);
    }

}
