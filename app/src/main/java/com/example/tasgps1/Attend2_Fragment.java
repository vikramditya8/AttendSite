package com.example.tasgps1;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Attend2_Fragment extends Fragment implements View.OnClickListener{
    private Button maddSchd;
    private Button mcheckStat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_attend2_,container,false);


        ((AdminActivity) getActivity())
                .setActionBarTitle("Manage Attendance Schedule");

        maddSchd=v.findViewById(R.id.addSchd);
        maddSchd.setOnClickListener(this);
        mcheckStat=v.findViewById(R.id.checkStat);
        mcheckStat.setOnClickListener(this);
        mcheckStat.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==maddSchd){
            startActivity(new Intent(getActivity(),AddSchedule.class));
        }
        else if (v==mcheckStat){
            startActivity(new Intent(getActivity(),CheckStat.class));
        }

    }
}


