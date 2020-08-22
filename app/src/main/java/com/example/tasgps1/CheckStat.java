package com.example.tasgps1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CheckStat extends AppCompatActivity {

    private TextInputLayout mselectUsername2;
    private TextView mtextView1;
    private  TextView mtextView2;
    private TextView mselectedDate3;
    private Button mcheckStat;
    private FirebaseFirestore mf;
    private DatePickerDialog.OnDateSetListener mDateSetListener3;

    private int zyear3;
    private int zmonth3;
    private int zday3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stat);

        getSupportActionBar().setTitle("Check Status");
        getSupportActionBar().setHomeButtonEnabled(true);

        mselectUsername2=findViewById(R.id.selectUsername2);
        mtextView1=(TextView)findViewById(R.id.temp21);
        mtextView2=(TextView)findViewById(R.id.temp22);
        mselectedDate3=(TextView) findViewById(R.id.selectDate3);
        mcheckStat=(Button) findViewById(R.id.checkStat);

        mf=FirebaseFirestore.getInstance();





        mselectedDate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CheckStat.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener3,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //final int year1=year;
                //final int month1=month+1;
                //final int date1=day;
                zyear3=year;
                zmonth3=month+1;
                zday3=day;
                String date = day + "/" + (month+1) + "/" + year;
                mselectedDate3.setText(date);

            }
        };


        mcheckStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUsername2=mselectUsername2.getEditText().getText().toString();
                final String zzyear3=String.valueOf(zyear3);
                final String zzmonth3=String.valueOf(zmonth3);
                final String zzday3=String.valueOf(zday3);

                mf.collection("employees").document(selectedUsername2).collection("year").document(zzyear3).collection("month")
                        .document(zzmonth3).collection("date").document(zzday3).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    Boolean bool1=documentSnapshot.getBoolean("inCheck");
                                    Boolean bool2=documentSnapshot.getBoolean("outCheck");
                                    if(bool1==true){
                                        mtextView1.setText("User has checked in successfully.");
                                    }
                                    else {
                                        mtextView1.setText("User has not yet checked in.");
                                    }
                                    if(bool2==true){
                                        mtextView2.setText("User has checked out successfully.");
                                    }
                                    else {
                                        mtextView2.setText("User has not yet checked out.");
                                    }

                                    Toast.makeText(CheckStat.this, "Success"+bool1+" "+bool2, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CheckStat.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
