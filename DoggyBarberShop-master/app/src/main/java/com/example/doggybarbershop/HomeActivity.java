/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

  private DrawerLayout drawerLayout;
  public static FirebaseUser userLogin;
  private SharedPreferences prefs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    userLogin= FirebaseAuth.getInstance().getCurrentUser();

    
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.vacio,
            R.string.vacio);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    MenuItem menuItem = navigationView.getMenu().getItem(0);
    onNavigationItemSelected(menuItem);
    menuItem.setChecked(true);

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(HomeActivity.this, getString(R.string.title_click),
                Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int title;
    Fragment fragment;
    switch (menuItem.getItemId()) {
      case R.id.nav_1:
        title = R.string.menu_1;
        fragment = new CitasUsuarioFragment();
        break;
      case R.id.nav_2:
        title = R.string.menu_2;
        fragment = new Paso1Fragment();
        break;
      case R.id.nav_3:
        title = R.string.menu_3;
        fragment = new HistorialFragment();
        break;
      case R.id.nav_4:
        title = R.string.menu_4;
        prefs = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user", "null");
        editor.putString("pass", "null");
        editor.commit();
        FirebaseAuth.getInstance().signOut();

        Intent i1 = new Intent(getApplication(), MainActivity.class);
        startActivity(i1);
        //fragment=null;
        fragment = HomeContentFragment.newInstance(getString(title));
        break;
//      case R.id.nav_5:
//        title = R.string.menu_5;
//        fragment = HomeContentFragment.newInstance(getString(title));
//        break;
      default:
        throw new IllegalArgumentException("menu option not implemented!!");
    }


    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

    setTitle(getString(title));

    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }

  @Override
  public void onDrawerSlide(@NonNull View view, float v) {
    //cambio en la posición del drawer
  }

  @Override
  public void onDrawerOpened(@NonNull View view) {
    //el drawer se ha abierto completamente

  }

  @Override
  public void onDrawerClosed(@NonNull View view) {
    //el drawer se ha cerrado completamente
  }

  @Override
  public void onDrawerStateChanged(int i) {
    //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.add_cita:
       Fragment fragment = new Paso1Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

        setTitle("Reservar cita");
        return true;
      default:
        return false;
    }
  }

}
