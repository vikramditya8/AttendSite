package com.example.tasgps1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;

public class Profile_Fragment extends Fragment {
    private TextView mresultUsername;
    private FirebaseAuth mfirebaseAuth50;
    private Button mforgot;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_profile,container,false);

        ((ProfileActivity) getActivity())
                .setActionBarTitle("My Profile");

        mresultUsername=(TextView)v.findViewById(R.id.resultUsername);
        mforgot=(Button)v.findViewById(R.id.forgot);
        mfirebaseAuth50 = FirebaseAuth.getInstance();
        final FirebaseUser mfirebaseUser50 = mfirebaseAuth50.getCurrentUser();
        final String emailCurrentUser50=mfirebaseUser50.getEmail();

        mresultUsername.setText(emailCurrentUser50);



        mforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfirebaseAuth50.sendPasswordResetEmail(emailCurrentUser50).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Password has been sent to email", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return v;
    }
}


