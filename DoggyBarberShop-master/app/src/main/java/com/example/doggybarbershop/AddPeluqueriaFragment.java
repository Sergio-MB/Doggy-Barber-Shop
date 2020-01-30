/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddPeluqueriaFragment   extends Fragment {
    private EditText ETXTNombre;
    private EditText ETXTLugar;
    private Button btnConfirmar;
    private final ArrayList<String> listaPeluquerias = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference PeluqueriaBD;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.fragment_add_peluqueria, container, false);
        firebaseAuth=FirebaseAuth.getInstance();//AUTH
        PeluqueriaBD= FirebaseDatabase.getInstance().getReference("Peluquerias");//BD

        ETXTNombre=rl.findViewById(R.id.eTxtNombre);
        ETXTLugar=rl.findViewById(R.id.eTxtLugar);
        btnConfirmar=rl.findViewById(R.id.BtnGuardarPelu);
        DescargaPeluquerias(PeluqueriaBD);
//        for (String object: listaPeluquerias) {
//            Log.e("DATOS Recuperados","nombre: " +object);
//        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Lugar=ETXTLugar.getText().toString();
                String Nombre=ETXTNombre.getText().toString();
                if(TextUtils.isEmpty(Nombre)||TextUtils.isEmpty(Lugar)){
                    Toast.makeText(rl.getContext(),"Complete todos los campos ",Toast.LENGTH_SHORT).show();
                }else{
                if(!listaPeluquerias.contains(Nombre.toLowerCase())) {
                    String id = PeluqueriaBD.push().getKey();
                    Peluqueria peluqueria = new Peluqueria(Lugar, Nombre);
                    PeluqueriaBD.child(id).setValue(peluqueria);
                    Log.d("DATOS GUARDADOS", "Datos mandados al json" + PeluqueriaBD);
                    Toast.makeText(rl.getContext(),"La peluqueria "+Nombre+" se ha guardado correctamente ",Toast.LENGTH_SHORT).show();
                    //Mandar activity principal
                    Intent intent = new Intent(getContext(), AdminActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(rl.getContext(),"La peluqueria "+Nombre+" Ya existe ",Toast.LENGTH_SHORT).show();
                }
            }
            }
        });

        return rl;

    }
    private void DescargaPeluquerias(DatabaseReference PeluqueriaBD) {

        PeluqueriaBD.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Peluqueria peluqueria = dataSnapshot.getValue(Peluqueria.class);
                listaPeluquerias.add(peluqueria.getNombre().toLowerCase());
                Log.e("Child", "add usuarios name = " + peluqueria.getNombre());

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

