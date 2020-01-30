/*
 *@author David Pastor Pérez
 *@author Sergio Muñumer Blázquez
 **/
package com.example.doggybarbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = null;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);


        /*Barra inferior*/
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_day:
                        fragment = new DayFragment();

                        Toast.makeText(AdminActivity.this, "Citas de Hoy", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_week:
                        fragment = new WeekFragment();
                        Toast.makeText(AdminActivity.this, "Citas de esta Semana", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_month:
                        fragment = new MonthFragment();
                        Toast.makeText(AdminActivity.this, "Citas del Mes", Toast.LENGTH_SHORT).show();
                        break;
                }
                return loadFragment(fragment);
            }
        });

        /*TOOLBAR*/
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





    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();//fragment creado en admin_layout
            return true;
        }
        return false;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_cita:
                startActivity(new Intent(this, AddCitaActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int title;
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_1:
                title = R.string.menu_1;
                bottomNavigationView.setVisibility(View.VISIBLE);
               // fragment = HomeContentFragment.newInstance(getString(title));//TEngo que llamar al fragment del dia
                fragment=new DayFragment();
                break;
            case R.id.nav_2:
                title = R.string.menuAdmin_1;
                bottomNavigationView.setVisibility(View.GONE);
                fragment = new AddPeluqueriaFragment();
                break;
//            case R.id.nav_3:
//                title = R.string.menu_3;
//                fragment = HomeContentFragment.newInstance(getString(title));
//                break;
            case R.id.nav_4:
                title = R.string.menu_4;
                FirebaseAuth.getInstance().signOut();
                Intent i1 = new Intent(getApplication(), MainActivity.class);
                startActivity(i1);
                fragment = HomeContentFragment.newInstance(getString(title));
                break;
//            case R.id.nav_5:
//                title = R.string.menu_5;
//                fragment = HomeContentFragment.newInstance(getString(title));
//                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }

     //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
//        loadFragment(fragment);
        setTitle(getString(title));

        drawerLayout.closeDrawer(GravityCompat.START);

        return  loadFragment(fragment);
    }
}

