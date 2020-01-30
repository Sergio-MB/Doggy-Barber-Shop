/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class CitasUsuarioFragment extends Fragment {

    private Calendar calendar;
    private int MesActual;
    private int DiaActual;
    private int Year;
    private ListView mListView;
    private String user;
    private ArrayList<Cita> listacitas =new ArrayList<>();
    private ArrayList<String> listaKeys=new ArrayList<>();
  //  private ArrayAdapter adaptador;
    private CitaAdapter adapter;
    private SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         final RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_citasusuario, container, false);
        //USER
        String[] email=HomeActivity.userLogin.getEmail().split("@");
        user=email[0];

        calendar= Calendar.getInstance();
        DiaActual=(calendar.get(Calendar.DAY_OF_MONTH));//
        MesActual=(calendar.get(Calendar.MONTH)+1);//Enero=0
        Year=(calendar.get(Calendar.YEAR));

        mListView=(ListView)rl.findViewById(R.id.list_viewCitasUsuario);
        obtenerCitas();
        adapter=new CitaAdapter(rl.getContext(),listacitas);
       // adaptador=new ArrayAdapter(rl.getContext(),android.R.layout.simple_list_item_1,listacitas);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int fila, long arg3) {
                Intent intent=new Intent(getActivity(),EliminarCita.class);
                intent.putExtra("cita",  listacitas.get(fila));
                intent.putExtra("key",listaKeys.get(fila));
                startActivity(intent);
            }
        });


        return rl;

    }

    private void obtenerCitas(){
        Query query= FirebaseDatabase.getInstance().getReference("Citas");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cita citas = dataSnapshot.getValue(Cita.class);

                String[] fechas = citas.getFecha().toString().split("/");//Fecha de la cita
                int diaCita = Integer.parseInt(fechas[0]);
                int mesCita = Integer.parseInt(fechas[1]);
                int yearCita = Integer.parseInt(fechas[2]);

                if(citas.getNombreUsuario().equals(user)&&diaCita>=DiaActual&&mesCita>=MesActual&&yearCita>=Year) {
                   listaKeys.add(dataSnapshot.getKey());
                    listacitas.add(citas);
                    Log.e("asdasd", "Cita = " + listacitas.size()+"user: "+user);
                    // adaptador.notifyDataSetChanged();
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
