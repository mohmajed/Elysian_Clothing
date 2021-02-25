package com.example.elysian_clothing;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {

    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mref;
    FirebaseRecyclerAdapter <Datas, ViewHolder>firebaseRecyclerAdapter;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user= firebaseAuth.getCurrentUser();
    ArrayList<Datas>datasArrayList;
    FirebaseRecyclerOptions<Datas> options;
    public Home_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home_fragment, container, false);

        datasArrayList=new ArrayList<Datas>();

        mrecyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref= mfirebaseDatabase.getReference().child("Datas");
        mref.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Datas>()
                        .setQuery(mref, Datas.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Datas, ViewHolder>(options) {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.row, parent, false);
                return new ViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Datas model) {
                holder.setDetails(getActivity().getApplicationContext(),model.getName(),model.getPrice(),model.getImgid());
            }

        };


        mrecyclerView.setAdapter(firebaseRecyclerAdapter);

        return v;
    }

}
