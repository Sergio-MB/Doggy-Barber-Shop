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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Calendar;

public class WeekFragment extends Fragment {
    private Calendar calendar;
    private String MesActual;
    private String DiaActual;
    private String Year;
    private String FechaActual;
    private ListView mListView;
    //Adaptador de listas
    //private FirebaseListAdapter adapter;
    private ArrayList<Cita> listacitas =new ArrayList<>();
    //  private ArrayAdapter adaptador;
    private CitaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        calendar= Calendar.getInstance();
        DiaActual=Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));//
        MesActual=Integer.toString(calendar.get(Calendar.MONTH)+1);//Enero=0
        Year=Integer.toString(calendar.get(Calendar.YEAR));
        /*Creo un objeto relative layout para poder acceder a los datos del layout que quiera*/
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_week, container, false);
        mListView=(ListView)rl.findViewById(R.id.list_viewW);

        obtenerCitas();
        adapter=new CitaAdapter(rl.getContext(),listacitas);

        //Obtengo la referencia del nodo que quiero consultar
//        Query query= FirebaseDatabase.getInstance().getReference("Citas");
//
//        //Creo las opciones
//        FirebaseListOptions<Cita> options=new FirebaseListOptions.Builder<Cita>()
//                .setLayout(R.layout.cita_view)
//                .setQuery(query,Cita.class)
//                .build();
//        //Creo el adaptador con las opciones ya creadas anteriormente
//        adapter=new FirebaseListAdapter(options) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
//                //el find busca en usuarios_view que le indique en las opciones
//
//                TextView NombreUser=v.findViewById(R.id.TxTnombreUsuario);
//                TextView pelu=v.findViewById(R.id.TxTpeluqueria);
//                TextView raza=v.findViewById(R.id.TxTraza);
//                TextView servicio=v.findViewById(R.id.TxTservicio);
//                TextView fecha=v.findViewById(R.id.TxTFecha);
//
//                //Pillo datos del user y los muestro
//                Cita cita=(Cita)model;
//                //Comparo que sea el mismo mes
//
//                String [] fechas=cita.getFecha().toString().split("/");
//                String diaCita=fechas[0];
//                String mesCita=fechas[1];
//                String yearCita=fechas[2];
//                // String diaCita=cita.getFecha().toString().substring(0,2);
//                // String mesCita=cita.getFecha().toString().substring(3,5);
//                // String yearCita=cita.getFecha().toString().substring(6,10);
//                // Log.e("TIEMPO HOY ","Cita es: fecha "+fechas[0]+"Dia actual: "+DiaActual+"mes cita: "+mesCita+"año cita: "+yearCita+
//                //      "mes actual: "+MesActual+"año actual: "+Year);
//                int diaCitaInt=Integer.parseInt(diaCita);
//                int diaActualI=Integer.parseInt(DiaActual);
//              //  Log.e("TIEMPO HOY ","Cita es: "+Integer.parseInt(diaCita)+"Dia actual: "+DiaActual);
//                if(diaActualI<=diaCitaInt&&(diaCitaInt-diaActualI)<=7 &&yearCita.equals(Year)&&mesCita.equals(MesActual)) {
//                    NombreUser.setText("Nombre: " + cita.getNombreUsuario().toString());
//                    pelu.setText("Peluqueria: " + cita.getPeluqueria().toString());
//                    raza.setText("Raza: " + cita.getRaza().toString());
//                    servicio.setText("Servicio: " + cita.getServicio().toString());
//                    fecha.setText("Fecha: " + cita.getFecha().toString());
//                }
//            }
//        };

        mListView.setAdapter(adapter);
        return rl;
        // return inflater.inflate(R.layout.fragment_month, null);
    }
    private void obtenerCitas() {
        Query query = FirebaseDatabase.getInstance().getReference("Citas");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cita citas = dataSnapshot.getValue(Cita.class);
                String[] fechas = citas.getFecha().toString().split("/");
                String diaCita = fechas[0];
                String mesCita = fechas[1];
                String yearCita = fechas[2];
                int diaCitaInt=Integer.parseInt(diaCita);
                int diaActualI=Integer.parseInt(DiaActual);
                if(diaActualI<=diaCitaInt&&(diaCitaInt-diaActualI)<=7 &&yearCita.equals(Year)&&mesCita.equals(MesActual)) {
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

//    @Override
//    public void onStart(){
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    public void onStop(){
//        super.onStop();
//        adapter.stopListening();
//    }
}
