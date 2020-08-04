package com.example.tasgps1;
/* Created By Adittya Raj
  email:raj21adittya@gmail.com
  This is a personal project(Github Repository)
  Public Distribution without permission is strictly denied.
 */

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer2;
    private FirebaseAuth mfirebaseAuth41;
    private TextView memail;

    ArrayList<Uri> arrayListapkFilepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        mfirebaseAuth41=FirebaseAuth.getInstance();



        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        drawer2=findViewById(R.id.drawer_layout2);
        NavigationView navigationView=findViewById(R.id.nav_niew2);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer2,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer2.addDrawerListener(toggle);
        toggle.syncState();

       if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                    new Profile2_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile2);
        }





       // memail=(TextView)findViewById(R.id.email2);
        //FirebaseUser firebaseUser60=mfirebaseAuth41.getCurrentUser();
        //memail.setText(firebaseUser60.getEmail());


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_attend2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                        new Attend2_Fragment()).commit();
                break;
            case R.id.nav_profile2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                        new Profile2_Fragment()).commit();
                break;
            case R.id.nav_share2:

                Toast.makeText(this, "Functionality not yet added", Toast.LENGTH_SHORT).show();



                break;
            case R.id.nav_logout2:
                mfirebaseAuth41.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Successfully Logged Out.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer2.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer2.isDrawerOpen(GravityCompat.START)){
            drawer2.closeDrawer(GravityCompat.START);
        }
        else {
        }
    }

    @Override
    public void onClick(View v) {

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
