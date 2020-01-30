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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Paso2Fragment extends Fragment {
    private CalendarView calendario;
    private  ArrayList<String> Horario=new ArrayList<>();
    private   List<Cita> ListaDeCitas;
    private  ArrayList<String> HorarioOcupado=new ArrayList<>();
    private ArrayList<String> HorarioDisponible=new ArrayList<>();
    private ListView listViewHorario;
    private  String fechaSelect;
    private  Peluqueria peluqueria;
    private  ArrayAdapter adaptador;
    private int diaActual;
    private int mesActual;
    private int yearActual;
    private boolean seleccionCorrecta=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_paso2, container, false);
        //Recupero datos del fragment 1
        Bundle datosRecuperados = getArguments();
        peluqueria=(Peluqueria)datosRecuperados.getSerializable("peluqueria");
        //fecha actual
        Calendar calendar;
        calendar=Calendar.getInstance();
        diaActual=(calendar.get(Calendar.DAY_OF_MONTH));
        mesActual=(calendar.get(Calendar.MONTH)+1);
        yearActual=(calendar.get(Calendar.YEAR));
       // fechaSelect=diaActual+"/"+mesActual+"/"+yearActual;
        fechaSelect=null;
        //Log.e("FECHA",fechaSelect);
        rellenaHorario(Horario);//Horario tienda
        HorarioDisponible=new ArrayList<>(Horario);
        recuperaCitas();
        calendario=rl.findViewById(R.id.calendarioView);
        listViewHorario=rl.findViewById(R.id.list_viewHorario);


       //El calendario solo muestra del mes actual en adelante
        calendar.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
        long date = calendar.getTime().getTime();
        calendario.setMinDate(date);


         adaptador=new ArrayAdapter(rl.getContext(),android.R.layout.simple_list_item_1, HorarioDisponible);
        listViewHorario.setAdapter(adaptador);
       // citasDiaSeleccion(fechaSelect);
        listViewHorario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int fila, long arg3) {
            if(fechaSelect!=null) {
                Log.v("TAG", "CLICKED  " + fila + "arrayList: " + Horario.get(fila));
                if (seleccionCorrecta) {
                    Fragment fragment = new Paso3Fragment();
                    Bundle datos = new Bundle();
                    datos.putSerializable("peluqueria", peluqueria);
                    datos.putString("fecha", fechaSelect);
                    datos.putString("hora", Horario.get(fila));
                    fragment.setArguments(datos);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_content, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }else {
                Toast.makeText(rl.getContext(),"Seleccione una fecha",Toast.LENGTH_SHORT).show();

            }
            }

        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                 fechaSelect=dayOfMonth+"/"+month+"/"+year;
                 if(dayOfMonth<diaActual&&mesActual==month){
                     seleccionCorrecta=false;
                     Toast.makeText(rl.getContext(),"Seleccione un dia posterior o igual al dia actual",Toast.LENGTH_SHORT).show();
                 }else {
                     seleccionCorrecta=true;
                     citasDiaSeleccion(fechaSelect);
                 }
               // Log.e("Paso2",fechaSelect);
            }
        });

        return rl;
    }

    private void rellenaHorario(ArrayList Horario){
        Horario.add("10:00");
        Horario.add("10:30");
        Horario.add("11:00");
        Horario.add("11:30");
        Horario.add("12:00");
        Horario.add("12:30");
        Horario.add("13:00");
        Horario.add("13:30");
        Horario.add("14:00");

        Horario.add("16:00");
        Horario.add("16:30");
        Horario.add("17:00");
        Horario.add("17:30");
        Horario.add("18:00");
        Horario.add("18:30");
        Horario.add("19:00");
        Horario.add("19:30");
    }
    //Acumulo todas las citas de ese dia lo meto en un arrayList y comparo con el arraylist de el horario
    private void citasDiaSeleccion(String Fecha){
       if(HorarioOcupado.size()>0) HorarioOcupado.clear();//Limpio

        //El adaptador se pobla con referencia para poder modificar tengo que vaciar y rellenar la misma
         if(HorarioDisponible.size()<Horario.size()){
             HorarioDisponible.clear();
             HorarioDisponible.addAll(Horario);
             adaptador.notifyDataSetChanged();
         }


        adaptador.notifyDataSetChanged();
        for(Cita list: ListaDeCitas){
            if(list.getFecha().equals(fechaSelect)&&list.getPeluqueria().equals(peluqueria.getNombre())) {
                HorarioOcupado.add(list.getHora());
                Log.e(TAG, "Fecha = " + list.getFecha() + " Hora: " + list.getHora() +"Peluqueria: "+list.getPeluqueria());
            }
        }
      //  Log.e(TAG, "Tamaño antes = " + HorarioDisponible.size());
        for(String hora: HorarioOcupado){
            if(HorarioDisponible.contains(hora)){
                HorarioDisponible.remove(hora);
              //  Log.e(TAG, "Hora eliminada = " + hora);
            }
        }
       // Log.e(TAG, "Tamaño Despues = " + HorarioDisponible.size());
       adaptador.notifyDataSetChanged();

    }

    private void recuperaCitas(){
        Query query= FirebaseDatabase.getInstance().getReference("Citas");
        ListaDeCitas = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cita citas = dataSnapshot.getValue(Cita.class);
                ListaDeCitas.add(citas);
               // Log.e(TAG,"Cita = " + citas.getFecha()+HorarioOcupado.size());

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
