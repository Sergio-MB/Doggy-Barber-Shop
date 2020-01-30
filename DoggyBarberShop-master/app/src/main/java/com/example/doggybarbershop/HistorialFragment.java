/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;

public class HistorialFragment extends Fragment {

    private ArrayList<Cita> listacitas =new ArrayList<>();
    private ListView mListView;
    private CitaAdapter adapter;
    private String user;
    private Calendar calendar;
    private int MesActual;
    private int DiaActual;
    private int Year;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_historial, container, false);
        String[] email=HomeActivity.userLogin.getEmail().split("@");
        user=email[0];

        //Fechas
        calendar= Calendar.getInstance();
        DiaActual=(calendar.get(Calendar.DAY_OF_MONTH));//
        MesActual=(calendar.get(Calendar.MONTH)+1);//Enero=0
        Year=(calendar.get(Calendar.YEAR));

        mListView=(ListView)rl.findViewById(R.id.list_viewHistorial);

        obtenerCitas();
        adapter=new CitaAdapter(rl.getContext(),listacitas);
        mListView.setAdapter(adapter);

        return rl;
    }
    private void obtenerCitas() {
        Query query = FirebaseDatabase.getInstance().getReference("Citas");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cita citas = dataSnapshot.getValue(Cita.class);
                String[] fechas = citas.getFecha().toString().split("/");//Fecha de la cita
                int diaCita = Integer.parseInt(fechas[0]);
                int mesCita = Integer.parseInt(fechas[1]);
                int yearCita = Integer.parseInt(fechas[2]);
                if(citas.getNombreUsuario().equals(user)&&diaCita<=DiaActual&&mesCita<=MesActual&&yearCita<=Year) {
                    listacitas.add(citas);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
    }
}
