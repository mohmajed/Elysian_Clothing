package com.example.elysian_clothing;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account_fragment extends Fragment {
    TextView email;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= firebaseAuth.getCurrentUser();

    public Account_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v;
         v= inflater.inflate(R.layout.fragment_account_fragment, container, false);
         email=(TextView)v.findViewById(R.id.email);
         email.setText(user.getEmail());


        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Button btnLogOut = (Button) getView().findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(getActivity(), login.class);
                startActivity(I);
            }
        });*/
    }

    public void updt(View view){
        EditText edt= (EditText) view.findViewById(R.id.newpass);
        String newPass = edt.getText().toString();
        user.updatePassword(newPass);
        Toast.makeText(getActivity(), "Password Updated Successfully", Toast.LENGTH_LONG).show();
    }

    public void changePass(View view){
        TextView txt= (TextView) view.findViewById(R.id.txt);
        txt.setVisibility(View.VISIBLE);
        EditText edt= (EditText) view.findViewById(R.id.newpass);
        edt.setVisibility(View.VISIBLE);
        Button btn= (Button) view.findViewById(R.id.Updtpass);
        btn.setVisibility(View.VISIBLE);
    }

    public void log_out(View view){

        //Toast.makeText(getActivity(),user.getEmail(),Toast.LENGTH_LONG).show();
        FirebaseAuth.getInstance().signOut();

        //updateUI(null);
        Toast.makeText(getActivity(),user.getEmail(),Toast.LENGTH_LONG).show();
        Intent I = new Intent(getActivity(), login.class);

        startActivity(I);

    }
    public void buyNow(View view){
        Toast.makeText(getActivity(), "Thank You For Using Our Service! :)", Toast.LENGTH_LONG).show();
    }
    /*public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(getActivity(),"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), login.class));
        }else {
            Toast.makeText(getActivity(),"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }*/
}
