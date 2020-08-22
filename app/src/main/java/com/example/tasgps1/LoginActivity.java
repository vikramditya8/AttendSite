package com.example.tasgps1;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mloginPageUsername2;
    private TextInputLayout mloginPagePassword2;
    private Button mloginPageButton2;
    private TextView mloginPageTextView2;
    private ProgressDialog mProgressDialog2;
    private FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("AttendSite");
        mFireBaseAuth=FirebaseAuth.getInstance();

        mloginPageUsername2= findViewById(R.id.loginPageUsername2);
        mloginPagePassword2=findViewById(R.id.loginPagePassword2);
        mloginPageButton2=(Button) findViewById(R.id.loginPageButton2);
        mloginPageTextView2=(TextView) findViewById(R.id.loginPageTextView2);

        mProgressDialog2=new ProgressDialog(this);

        mloginPageButton2.setOnClickListener(this);
        mloginPageTextView2.setOnClickListener(this);
        mFireBaseAuth=FirebaseAuth.getInstance();



        if(mFireBaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

    }


    private void loginUser(){
        String sloginPageUsername2=mloginPageUsername2.getEditText().getText().toString().trim();
        String sloginPagePassword2=mloginPagePassword2.getEditText().getText().toString().trim();


        if(TextUtils.isEmpty(sloginPageUsername2))
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(sloginPagePassword2))
            Toast.makeText(this, "Please enter passworf", Toast.LENGTH_SHORT).show();


        mProgressDialog2.setMessage("Logging In User....");
        mProgressDialog2.show();

        mFireBaseAuth.signInWithEmailAndPassword(sloginPageUsername2,sloginPagePassword2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog2.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==mloginPageButton2) {
            loginUser();
        }
        if(v==mloginPageTextView2) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
