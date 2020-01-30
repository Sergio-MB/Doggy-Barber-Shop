/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegistrar;
    private EditText TxtCorreo,TxtPass,TxtPass2,TxtTlfnR,TxtNombre;
    private ProgressDialog progressDialog;
    private static final String TAG = "RegActivity";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference UsuariosBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        firebaseAuth=FirebaseAuth.getInstance();//AUTH
        UsuariosBD= FirebaseDatabase.getInstance().getReference("Usuarios");//BD

        TxtCorreo=(EditText)findViewById(R.id.InputCorreoR);
        TxtPass=(EditText)findViewById(R.id.InputPassR);
        TxtPass2=(EditText)findViewById(R.id.InputPass2R);
        TxtNombre=(EditText)findViewById(R.id.InputNombreR);
        TxtTlfnR=(EditText)findViewById(R.id.InputTlfnR);
        btnRegistrar=(Button)findViewById(R.id.ButtonReg);
        progressDialog=new ProgressDialog(this);
        //asociar boton al evento onclick
        btnRegistrar.setOnClickListener(RegActivity.this);

    }



    public void registrarUser(){
        final String correo=TxtCorreo.getText().toString().trim();
        String pass=TxtPass.getText().toString().trim();
        String pass2=TxtPass2.getText().toString().trim();
        final String Nombre=TxtNombre.getText().toString().trim();
        final String Telefono=TxtTlfnR.getText().toString().trim();
      //  Toast.makeText(this,Telefono,Toast.LENGTH_SHORT).show();
        if(correo.indexOf("@")==-1){
            Toast.makeText(this,"Inserte Correo @Sudomino.com",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass2)){
            Toast.makeText(this,"Campo Repetir Contraseña vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Nombre)){
            Toast.makeText(this,"Campo Nombre vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Telefono)){
            Toast.makeText(this,"Campo Telefono vacio",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this,"Campo Usuario vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Campo Contraseña vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pass.equals(pass2)){
            Toast.makeText(this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<6){
            Toast.makeText(this,"La contraseña debe contener 6 caracteres como mínimo",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando el registro...");
        progressDialog.show();

        //Guardo en la base de datos los otros datos

   // progressDialog.dismiss();
        //Firebase Para  Registro esa cuenta de correo con la contraseña
        firebaseAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    guardarDatosUser(correo,Nombre,Telefono);

                    Toast.makeText(RegActivity.this,"Se ha registrado el email: "+TxtCorreo.getText()+" correctamente",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){//existe el user
                        Toast.makeText(RegActivity.this,"Este usuario ya existe",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(RegActivity.this,"No se pudo registrar el usuario",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });

    }

    public void guardarDatosUser(String correo,String Nombre,String Telefono){

        String id =UsuariosBD.push().getKey();

        Usuarios usuarios= new Usuarios(Nombre,correo,Telefono);

        UsuariosBD.child(id).setValue(usuarios);
        Log.d(TAG,"Datos mandados al json" +UsuariosBD);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
                case R.id.ButtonReg:
                    registrarUser();
                    break;
        }
    }
}
