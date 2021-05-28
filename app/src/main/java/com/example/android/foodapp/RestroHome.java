package com.example.android.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.foodapp.ui.home.AddDishFragment;
import com.example.android.foodapp.ui.home.ViewDishesFragment;
import com.example.android.foodapp.ui.home.ViewOrderFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RestroHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restro_home);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.my_drawer_layout2);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ViewDishesFragment()).commit();
        actionBarDrawerToggle = new ActionBarDrawerToggle(RestroHome.this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {


            if (item.getItemId() == R.id.action_logout) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(RestroHome.this, "You have logged out!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RestroHome.this, StartActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dishes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ViewDishesFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ViewOrderFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_adddishes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddDishFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            default:


        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}