package com.example.tasgps1;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.model.value.GeoPointValue;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;

    private TextView mselectedDate;
    private TextView mselectedDate2;
    private TextView mselectedTime11;
    private TextView mselectedTime12;
    private TextView mselectedTime21;
    private TextView mselectedTime22;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener12;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener21;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener22;
    private TextView mselectedLoc;
    private TextInputLayout mselectedEmail;
    private TextInputLayout mselectedRad;
    private Button msavestuff ;
    private FirebaseFirestore mfirebaseFirestore;





    private int zyear1;
    private int zmonth1;
    private int zday1;
   // private int zyear2;
  //  private int zmonth2;
  //  private int zday2;




    private double xlatitude;
    private double xlongitude;

    private int zhour11;
    private int zminutes11;
    private int zhour12;
    private int zminutes12;
    private int zhour21;
    private int zminutes21;
    private int zhour22;
    private int zminutes22;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        getSupportActionBar().setTitle("Add Schedule");
        getSupportActionBar().setHomeButtonEnabled(true);

        mfirebaseFirestore=FirebaseFirestore.getInstance();
        mselectedDate=(TextView)findViewById(R.id.selectDate);
        //mselectedDate2=(TextView)findViewById(R.id.selectDate2);
        mselectedTime11=(TextView)findViewById(R.id.selectTime11);
        mselectedTime12=(TextView)findViewById(R.id.selectTime12);
        mselectedTime21=(TextView)findViewById(R.id.selectTime21);
        mselectedTime22=(TextView)findViewById(R.id.selectTime22);
        mselectedLoc=(TextView)findViewById(R.id.selectLoc);
        mselectedEmail=findViewById(R.id.selectUsername);
        mselectedRad=findViewById(R.id.selectRad);
        msavestuff=(Button)findViewById(R.id.savestuff);






        mselectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //final int year1=year;
                //final int month1=month+1;
                //final int date1=day;
                zyear1=year;
                zmonth1=month+1;
                zday1=day;

                String date = day + "/" + (month+1) + "/" + year;
                mselectedDate.setText(date);
            }
        };



       /* mselectedDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //final int year2=year
                //final int month2=month+1;
                //final int date2=day;
                zyear2=year;
                zmonth2=month+1;
                zday2=day;

                String date = month + "/" + day + "/" + year;
                mselectedDate2.setText(date);
            }
        };*/







        mselectedTime11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //final int hour11=hourOfDay;
                //final int minute11=minute;
                zhour11=hourOfDay;
                zminutes11=minute;
                String time=hourOfDay+":"+minute;
                mselectedTime11.setText(time);
            }
        };



        mselectedTime12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener12,
                        hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener12 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //final int hour12=hourOfDay;
                //final int minute12=minute;

                zhour12=hourOfDay;
                zminutes12=minute;
                String time=hourOfDay+":"+minute;
                mselectedTime12.setText(time);
            }
        };



        mselectedTime21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener21,
                        hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener21 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //final int hour21=hourOfDay;
                //final int minute21=minute;

                zhour21=hourOfDay;
                zminutes21=minute;
                String time=hourOfDay+":"+minute;
                mselectedTime21.setText(time);
            }
        };



        mselectedTime22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener22,
                        hour,minutes,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener22 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //final int hour22=hourOfDay;
                //final int minute22=minute;

                zhour22=hourOfDay;
                zminutes22=minute;
                String time=hourOfDay+":"+minute;
                mselectedTime22.setText(time);
            }
        };

        mselectedLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(AddSchedule.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });










        msavestuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String zzyear1=String.valueOf(zyear1);
                final String zzmonth1=String.valueOf(zmonth1);
                final String zzday1=String.valueOf(zday1);
                //final String zzyear2=String.valueOf(zyear2);
                // final  String zzmonth2=String.valueOf(zmonth2);
                // final String zzday2=String.valueOf(zday2);

                final GeoPoint geoPoint=new GeoPoint(xlatitude,xlongitude);


                final Date nndate11=new Date((zyear1-1900),zmonth1,zday1,zhour11,zminutes11);
                final Date nndate12=new Date((zyear1-1900),zmonth1,zday1,zhour12,zminutes12);
                final Date nndate21=new Date((zyear1-1900),zmonth1,zday1,zhour21,zminutes21);
                final Date nndate22=new Date((zyear1-1900),zmonth1,zday1,zhour22,zminutes22);


                final Timestamp timeStamp11=new Timestamp(nndate11);
                final Timestamp timeStamp12=new Timestamp(nndate12);
                final Timestamp timeStamp21=new Timestamp(nndate21);
                final Timestamp timeStamp22=new Timestamp(nndate22);

                final String xemail=mselectedEmail.getEditText().getText().toString();
                final String strxrad=mselectedRad.getEditText().getText().toString();
                final int xrad=Integer.parseInt(strxrad);



                Map<String,Object> map1=new HashMap<>();
                map1.put("radius",xrad);
                map1.put("origin",geoPoint);
                map1.put("inTime1",timeStamp11);
                map1.put("inTime2",timeStamp12);
                map1.put("outTime1",timeStamp21);
                map1.put("outTime2",timeStamp22);



                mfirebaseFirestore.collection("employees").document(xemail).collection("year").document(zzyear1).collection("month")
                       .document(zzmonth1).collection("date").document(zzday1).update(map1);


                Toast.makeText(AddSchedule.this, zzyear1, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data,this);
                StringBuilder stringBuilder =new StringBuilder();
                String  latitutde=String.valueOf(place.getLatLng().latitude);
                String  longitude=String.valueOf(place.getLatLng().longitude);

                xlatitude=Double.parseDouble(latitutde);
                xlongitude=Double.parseDouble(longitude);



                stringBuilder.append("Latitude:");
                stringBuilder.append(latitutde);
                stringBuilder.append("\n");
                stringBuilder.append("Longitude:");
                stringBuilder.append(longitude);
                mselectedLoc.setText(stringBuilder);

            }
        }
    }

}
