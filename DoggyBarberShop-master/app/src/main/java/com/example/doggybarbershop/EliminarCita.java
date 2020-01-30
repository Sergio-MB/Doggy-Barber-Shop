/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EliminarCita extends AppCompatActivity {
    private TextView txtView;
    private Button btnEliminar;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cita);
        txtView=findViewById(R.id.TextViewCitaCompletaEliminar);
        btnEliminar=findViewById(R.id.ButtonEliminaCita);
        databaseReference= FirebaseDatabase.getInstance().getReference("Citas");
        final Cita cita= (Cita)getIntent().getSerializableExtra("cita");
        final String key=getIntent().getStringExtra("key");
        Log.e("ELIMINA CITA",cita.getNombreUsuario()+key);
       // btnEliminar.setOnClickListener(EliminarCita.this);
        txtView.setText("\n Datos de la cita: \n\n\t\tPeluqueria: "+cita.getPeluqueria()+"\n"+"\t\tRaza: "+cita.getRaza()+"\n"+"\t\tServicio: "+cita.getServicio()+"\n"
                +"\t\tOtros datos: "+cita.getOtros()+"\n"+"\t\tFecha: "+cita.getFecha()+"\n"+"\t\tHora: "+cita.getHora()+"\n\n ¿Desea eliminar esta cita?");

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(key).removeValue();
                //Mandar activity principal
                Intent intent=new Intent(EliminarCita.this,HomeActivity.class);
                startActivity(intent);
            }

        });

    }

private void EliminarCita(){

}
}
