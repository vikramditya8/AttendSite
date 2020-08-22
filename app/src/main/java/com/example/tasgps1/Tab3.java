package com.example.tasgps1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment implements View.OnClickListener {



    private TextInputLayout mloginPageUsername;
    private TextInputLayout mloginPagePassword;
    private Button mloginPageButton;
    private TextView mloginPageTextView1;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFireBaseAuth;
    private FirebaseFirestore mfirebasefirestore;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_tab3, container, false);



        mProgressDialog=new ProgressDialog(getActivity());


        mloginPageUsername= v.findViewById(R.id.loginPageUsername);
        mloginPagePassword=v.findViewById(R.id.loginPagePassword);
        mloginPageButton=(Button) v.findViewById(R.id.loginPageButton);
        mloginPageTextView1=(TextView) v.findViewById(R.id.loginPageTextView1);
        mFireBaseAuth=FirebaseAuth.getInstance();
        mloginPageButton.setOnClickListener(this);
        mloginPageTextView1.setOnClickListener(this);
        mfirebasefirestore=FirebaseFirestore.getInstance();


        if(mFireBaseAuth.getCurrentUser()!=null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),ProfileActivity.class));
        }



        return v;
    }


    private void registerUser(){
        final String sloginPageUsername=mloginPageUsername.getEditText().getText().toString().trim();
        final String sloginPagePassword=mloginPagePassword.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(sloginPageUsername))
            Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(sloginPagePassword))
            Toast.makeText(getActivity(), "Please enter passworf", Toast.LENGTH_SHORT).show();


        mProgressDialog.setMessage("Registering User....");
        mProgressDialog.show();


        mFireBaseAuth.createUserWithEmailAndPassword(sloginPageUsername,sloginPagePassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Map<String,Object> note=new HashMap<>();
                        note.put("inCheck",false);
                        note.put("outCheck",false);

                        for (int i=2019;i<=2023;i++){
                            for (int j=1;j<=12;j++){
                                int k;
                                if(j==1||j==3||j==5||j==7||j==8||j==10||j==12){
                                    k=31;
                                }
                                else if(j==2){
                                    if(((i % 4 == 0) && (i % 100!= 0)) || (i%400 == 0)){
                                        k=29;
                                    }
                                    else{
                                        k=28;
                                    }
                                }
                                else{
                                    k=30;
                                }

                                for (int l=1;l<=k;l++){
                                    mfirebasefirestore.collection("employees").document(sloginPageUsername).collection("year").document(Integer.toString(i))
                                            .collection("month").document(Integer.toString(j)).collection("date").document(Integer.toString(l)).set(note);
                                }
                            }
                        }
                        mProgressDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Registered Succussfully", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity().getApplicationContext(),ProfileActivity.class));
                        }
                        else
                            Toast.makeText(getActivity(), "Could not register", Toast.LENGTH_SHORT).show();
                    }
                });




    }

    @Override
    public void onClick(View v) {
        if (v==mloginPageButton) {
            registerUser();
        }
        if(v==mloginPageTextView1) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
