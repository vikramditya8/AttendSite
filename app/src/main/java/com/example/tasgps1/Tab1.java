package com.example.tasgps1;

/* Created By Adittya Raj
  email:raj21adittya@gmail.com
  This is a personal project(Github Repository)
  Public Distribution without permission is strictly denied.
 */

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment implements View.OnClickListener {



    private TextInputLayout mloginPageUsername2;
    private TextInputLayout mloginPagePassword2;
    private Button mloginPageButton2;
    private TextView mloginPageTextView2;
    private ProgressDialog mProgressDialog2;
    private FirebaseAuth mFireBaseAuth;
    private TextView mforgotPass;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
        View v= inflater.inflate(R.layout.fragment_tab1, container, false);


        mFireBaseAuth=FirebaseAuth.getInstance();

        mloginPageUsername2= v.findViewById(R.id.loginPageUsername2);
        mloginPagePassword2=v.findViewById(R.id.loginPagePassword2);
        mloginPageButton2=(Button) v.findViewById(R.id.loginPageButton2);
        mloginPageTextView2=(TextView) v.findViewById(R.id.loginPageTextView2);
        mforgotPass=(TextView)v.findViewById(R.id.forgotPass);

        mProgressDialog2=new ProgressDialog(getActivity());

        mloginPageButton2.setOnClickListener(this);
        mloginPageTextView2.setOnClickListener(this);
        mforgotPass.setOnClickListener(this);
        mFireBaseAuth=FirebaseAuth.getInstance();



        if(mFireBaseAuth.getCurrentUser()!=null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),ProfileActivity.class));
        }

        return v;
    }



    private void forgotPassowrd(){
        if(mloginPageUsername2.getEditText().getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please Enter your email in above Email field", Toast.LENGTH_SHORT).show();
        }

        else{
            mFireBaseAuth.sendPasswordResetEmail(mloginPageUsername2.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    }

    private void loginUser(){
        String sloginPageUsername2=mloginPageUsername2.getEditText().getText().toString().trim();
        String sloginPagePassword2=mloginPagePassword2.getEditText().getText().toString().trim();


        if(TextUtils.isEmpty(sloginPageUsername2))
            Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(sloginPagePassword2))
            Toast.makeText(getActivity(), "Please enter passworf", Toast.LENGTH_SHORT).show();


        mProgressDialog2.setMessage("Logging In User....");
        mProgressDialog2.show();

        mFireBaseAuth.signInWithEmailAndPassword(sloginPageUsername2,sloginPagePassword2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog2.dismiss();
                        if(task.isSuccessful()) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity().getApplicationContext(),ProfileActivity.class));
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
            getActivity().finish();
            startActivity(new Intent(getActivity(), OriginActivity.class));
        }
        if(v==mforgotPass){
            forgotPassowrd();
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
