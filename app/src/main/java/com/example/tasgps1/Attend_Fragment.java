package com.example.tasgps1;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Attend_Fragment extends Fragment  implements View.OnClickListener{

    private TextView mcurrentUsername;
    private Button mlogoutButton;
    private FirebaseAuth mfirebaseAuth;
    private FusedLocationProviderClient mclient;
    private TextView mtest1;
    private Button mtest2;
    private Button mtest4;
    private TextView mtest3;
    private FirebaseFirestore mfirestore;
    private double lat2;
    private double long2;
    private double xxlat2;
    private double xxlong2;
    private static double C =111320.00;
    private TextView mcheckStatus;


    private TextView mselectedDate100;
    private TextView mshoeResult;
    private DatePickerDialog.OnDateSetListener mDateSetListener100;
    private int zyear100;
    private int zmonth100;
    private int zday100;
    private Button mfinalShowResult;

    private Button mdot1;
    private Button mdot2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_attend,container,false);


        ((ProfileActivity) getActivity())
                .setActionBarTitle("My Attendance Schedule");

        mfirebaseAuth = FirebaseAuth.getInstance();
        if (mfirebaseAuth.getCurrentUser() == null) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }


        final FirebaseUser mfirebaseUser = mfirebaseAuth.getCurrentUser();


        mfirestore=FirebaseFirestore.getInstance();

        mcurrentUsername = (TextView) v.findViewById(R.id.currentUsername);
        mcurrentUsername.setText("Welcome  " + mfirebaseUser.getEmail());

        //mlogoutButton = (Button) v.findViewById(R.id.logoutButton);
        //mlogoutButton.setOnClickListener(this);


        //mtest1 = (TextView) v.findViewById(R.id.test1);
        mtest2 = (Button) v.findViewById(R.id.test2);
        //mtest3 = (TextView)v.findViewById(R.id.test3);
        mtest4 = (Button) v.findViewById(R.id.test4);
        mcheckStatus=(TextView)v.findViewById(R.id.checkStatus);

        mselectedDate100=(TextView)v.findViewById(R.id.selectDate100);
        mshoeResult=(TextView)v.findViewById(R.id.showresult);
        mfinalShowResult=(Button)v.findViewById(R.id.finalShowResult);


        mdot1=(Button)v.findViewById(R.id.dot1);
        mdot2=(Button)v.findViewById(R.id.dot2);


        requestPermission();


        mclient = LocationServices.getFusedLocationProviderClient(getActivity());



        final String emailCurrentUser=mfirebaseUser.getEmail();
        final Long timeCurrent = System.currentTimeMillis()/1000;
        Calendar calendar=Calendar.getInstance();
        final int yearCurrent=calendar.get(Calendar.YEAR);
        final int monthCurrent=calendar.get(Calendar.MONTH)+1;
        final int dateCurrent=calendar.get(Calendar.DATE);
        final String strYearCurrent=Integer.toString(yearCurrent);
        final String strMonthCurrent=Integer.toString(monthCurrent);
        final String strDateCurrent=Integer.toString(dateCurrent);


        mtest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mclient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                lat2 = latitude;
                                long2 = longitude;
                                // mtest1.setText("Latitude="+latitude+"Longitude"+longitude);
                            }
                        }
                    });


                    mfirestore.collection("employees").document(emailCurrentUser).collection("year").document(strYearCurrent).collection("month").document(strMonthCurrent).collection("date").document(strDateCurrent).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                try {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    double rad1 = documentSnapshot.getDouble("radius");
                                    double lat1 = documentSnapshot.getGeoPoint("origin").getLatitude();
                                    double long1 = documentSnapshot.getGeoPoint("origin").getLongitude();

                                    long time1 = documentSnapshot.getTimestamp("inTime1").getSeconds();
                                    long time2 = documentSnapshot.getTimestamp("inTime2").getSeconds();

                                    double temp1 = ((((lat2 - lat1) * (lat2 - lat1)) + ((long2 - long1) * (long2 - long1))) * C * C) - (rad1 * rad1);

                                    if (temp1 <= 0 && timeCurrent >= time1 && timeCurrent <= time2) {
                                        //mtest3.setText("Congrats");
                                        mdot1.setBackgroundColor(65280);
                                        mtest2.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                                        Map<String, Object> usermap = new HashMap<>();
                                        usermap.put("inCheck", true);
                                        mfirestore.collection("employees").document(emailCurrentUser).collection("year").document(strYearCurrent).collection("month").document(strMonthCurrent).collection("date").document(strDateCurrent).update(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getActivity(), "Checked In Successfully.", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        mtest3.setText("Sorry.Please Try Again.");
                                    }
                                }
                                catch (Exception e){
                                    Toast.makeText(getActivity(), "Your Schedule for today has not yet been added.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });


            }
        });


        mtest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
                mclient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){
                            double xxlatitude=location.getLatitude();
                            double xxlongitude=location.getLongitude();
                            xxlat2=xxlatitude;
                            xxlong2=xxlongitude;
                            //mtest1.setText("xxLatitude="+xxlatitude+"xxLongitude"+xxlongitude);
                        }
                    }
                });


                mfirestore.collection("employees").document(emailCurrentUser).collection("year").document(strYearCurrent).collection("month")
                        .document(strMonthCurrent).collection("date").document(strDateCurrent).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            try {


                                DocumentSnapshot documentSnapshot = task.getResult();
                                double xxrad1 = documentSnapshot.getDouble("radius");
                                double xxlat1 = documentSnapshot.getGeoPoint("origin").getLatitude();
                                double xxlong1 = documentSnapshot.getGeoPoint("origin").getLongitude();

                                long xxtime1 = documentSnapshot.getTimestamp("outTime1").getSeconds();
                                long xxtime2 = documentSnapshot.getTimestamp("outTime2").getSeconds();

                                double xxtemp1 = ((((xxlat2 - xxlat1) * (xxlat2 - xxlat1)) + ((xxlong2 - xxlong1) * (xxlong2 - xxlong1))) * C * C) - (xxrad1 * xxrad1);
                                if (xxtemp1 <= 0 && timeCurrent >= xxtime1 && timeCurrent <= xxtime2) {
                                    //mtest3.setText("Congrats again");
                                    mdot2.setBackgroundColor(65280);
                                    mtest4.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
                                    Map<String, Object> usermap = new HashMap<>();
                                    usermap.put("outCheck", true);
                                    mfirestore.collection("employees").document(emailCurrentUser).collection("year").document(strYearCurrent).collection("month")
                                            .document(strMonthCurrent).collection("date").document(strDateCurrent).update(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getActivity(), "Checked Out Successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    mtest3.setText("Sorry.Please Try Again.");

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getActivity(), "Your Schedule for today has not yet been added.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });


        mfirestore.collection("employees").document(emailCurrentUser).collection("year").document(strYearCurrent).collection("month")
                .document(strMonthCurrent).collection("date").document(strDateCurrent).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    Boolean bool1=documentSnapshot.getBoolean("inCheck");
                    Boolean bool2=documentSnapshot.getBoolean("outCheck");
                    if(bool1==true && bool2==true){
                        mcheckStatus.setText("Your marked present for today's attendance");
                    }
                    else{
                        mcheckStatus.setText("Your marked absent for today's attendance");
                    }

                }
            }
        });


        mselectedDate100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener100,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener100 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //final int year1=year;
                //final int month1=month+1;
                //final int date1=day;
                zyear100=year;
                zmonth100=month+1;
                zday100=day;
                String date = day + "/" + (month+1) + "/" + year;
                mselectedDate100.setText(date);

            }
        };


        mfinalShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUsername2=emailCurrentUser;
                final String zzyear3=String.valueOf(zyear100);
                final String zzmonth3=String.valueOf(zmonth100);
                final String zzday3=String.valueOf(zday100);

                mfirestore.collection("employees").document(selectedUsername2).collection("year").document(zzyear3).collection("month")
                        .document(zzmonth3).collection("date").document(zzday3).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            Boolean bool1=documentSnapshot.getBoolean("inCheck");
                            Boolean bool2=documentSnapshot.getBoolean("outCheck");
                            if(bool1==true && bool2==true){
                                mshoeResult.setText("You're marked present for above date");
                            }
                            else{
                                mshoeResult.setText("You're marked absent for above date");
                            }

                        }
                        else {
                            Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




        return v;

    }



    @Override
    public void onClick(View v) {
        if(v==mlogoutButton) {
            mfirebaseAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{ACCESS_FINE_LOCATION},1);
    }


}


