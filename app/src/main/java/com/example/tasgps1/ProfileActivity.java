package com.example.tasgps1;

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
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView memail;

    private FirebaseAuth mfirebaseAuth40;
    ArrayList<Uri> arrayListapkFilepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_niew);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Profile_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }



        mfirebaseAuth40 = FirebaseAuth.getInstance();
        //memail=(TextView)findViewById(R.id.email);
       // final FirebaseUser firebaseUser60=mfirebaseAuth40.getCurrentUser();
       // memail.setText(firebaseUser60.getEmail());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_attend:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Attend_Fragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profile_Fragment()).commit();
                break;
            case R.id.nav_share:


               /* arrayListapkFilepath = new ArrayList<Uri>();

                try {
                    shareAPK(getPackageName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("application/vnd.android.package-archive");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
                        arrayListapkFilepath);
                startActivity(Intent.createChooser(intent, "Share " +
                        arrayListapkFilepath.size() + " Files Via"));*/
                Toast.makeText(this, "Functionality not yet added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                mfirebaseAuth40.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Successfully Logged Out.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
        }
    }


   /* public void shareAPK(String bundle_id) throws IOException {
        File f1;
        File f2 = null;

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
        int z = 0;
        for (Object object : pkgAppsList) {

            ResolveInfo info = (ResolveInfo) object;
            if (info.activityInfo.packageName.equals(bundle_id)) {

                f1 = new File(info.activityInfo.applicationInfo.publicSourceDir);

                Log.v("file--",
                        " " + f1.getName().toString() + "----" + info.loadLabel(getPackageManager()));
                try {

                    String file_name = info.loadLabel(getPackageManager()).toString();
                    Log.d("file_name--", " " + file_name);

                    f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Folder");
                    f2.mkdirs();
                    f2 = new File(f2.getPath() + "/" + file_name + ".apk");
                    f2.createNewFile();

                    InputStream in = new FileInputStream(f1);

                    OutputStream out = new FileOutputStream(f2);

                    // byte[] buf = new byte[1024];
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    System.out.println("File copied.");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage() + " in the specified directory.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        arrayListapkFilepath.add(Uri.fromFile(new File(f2.getAbsolutePath())));

    }*/
    @Override
    public void onClick(View v) {

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
