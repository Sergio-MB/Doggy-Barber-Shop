/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextView TxtRegistrar;
    private EditText TxtUser;
    private EditText TxtPass;
    private Button btnLogIn;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.e("MAIN PRUEBA",HomeActivity.userLogin.getEmail());
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        final String userSaved = prefs.getString("user", "null");
        final String passSaved = prefs.getString("pass", "null");
        Log.e("tag","valores:"+userSaved);
        if(!userSaved.equals("null") && !passSaved.equals("null")){

            firebaseAuth.signInWithEmailAndPassword(userSaved,passSaved).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        //INTENT PARA CUANDO LOGEAS  Correo: admin@admin.com CONTRASEÑA PARA EL ADMINISTRADOR : 123456
                        if(userSaved.equals("admin@admin.com")){
                            Toast.makeText(MainActivity.this,"Bienvenido Don Admin ",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplication(),AdminActivity.class);
                            // intent.putExtra(AdminActivity.usuario,user);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this,"Bienvenido: "+userSaved,Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplication(),HomeActivity.class);
                            // intent.putExtra(AdminActivity.usuario,user);
                            startActivity(intent);
                        }
                        // Intent intent=new Intent(getApplication(),Main2Activity.class);
                        // intent.putExtra(Main2Activity.usuario,user);
                        //  startActivity(intent);

                    }else{
                   /* if(task.getException() instanceof FirebaseAuthUserCollisionException){//existe el user
                        Toast.makeText(MainActivity.this,"Este usuario ya existe",Toast.LENGTH_SHORT).show();
                    }*/
                        Toast.makeText(MainActivity.this,"No se pudo iniciar sesión con este usuario",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });


        }


        TxtUser=(EditText)findViewById(R.id.InputCorreo);
        TxtPass=(EditText)findViewById(R.id.InputPass);
      //  btnRegistrar=(Button)findViewById(R.id.ButtonReg);
        TxtRegistrar=(TextView)findViewById(R.id.InputTxtReg);
        btnLogIn=(Button) findViewById(R.id.ButtonLogIn);
        progressDialog=new ProgressDialog(this);
        //asociar boton al evento onclick
       // btnRegistrar.setOnClickListener(this);
        TxtRegistrar.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }




    private void iniciarSesion() {
        final String user=TxtUser.getText().toString().trim();
        final String pass=TxtPass.getText().toString().trim();

        if(TextUtils.isEmpty(user)){
            Toast.makeText(this,"Campo Usuario vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Campo Contraseña vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //INTENT PARA CUANDO LOGEAS
                    if(user.equals("admin@admin.com")){
                        Toast.makeText(MainActivity.this,"Bienvenido Don Admin: ",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplication(),AdminActivity.class);
                        // intent.putExtra(AdminActivity.usuario,user);
                          startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Bienvenido: "+TxtUser.getText(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplication(),HomeActivity.class);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user", user);
                        editor.putString("pass", pass);
                        editor.commit();
                        // intent.putExtra(AdminActivity.usuario,user);
                        startActivity(intent);
                    }
                    // Intent intent=new Intent(getApplication(),Main2Activity.class);
                    // intent.putExtra(Main2Activity.usuario,user);
                  //  startActivity(intent);

                }else{
                   /* if(task.getException() instanceof FirebaseAuthUserCollisionException){//existe el user
                        Toast.makeText(MainActivity.this,"Este usuario ya existe",Toast.LENGTH_SHORT).show();
                    }*/
                    Toast.makeText(MainActivity.this,"No se pudo iniciar sesión con este usuario",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        //    case R.id.ButtonReg:
         //       registrarUser();
         //       break;
            case R.id.ButtonLogIn:
                iniciarSesion();
                break;
            case R.id.InputTxtReg :
                Intent intent=new Intent(getApplication(),RegActivity.class);
                //intent.putExtra(Main2Activity.usuario,user);
                startActivity(intent);
                break;

        }
    }
}
