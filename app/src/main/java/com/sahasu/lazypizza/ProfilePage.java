package com.sahasu.lazypizza;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ProfilePage  extends Fragment {

    TextView profileMail, profilePhone, profileBalance, profileName,bala,numa,emaila;
    Button btn4;
    String scs;
    String phone;
    String mail;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile_page);
//
//        profileName = (TextView) findViewById(R.id.profileName);
//        profileMail = (TextView) findViewById(R.id.profilemail);
//        profilePhone = (TextView) findViewById(R.id.profilePhone);
//        profileBalance = (TextView) findViewById(R.id.profileBalance);
//        Log.d(data.SC,"sc");
//        profileMail.setText(data.email);
//        profilePhone.setText(data.phone);
//        profileBalance.setText(data.SC);
//        profileName.setText(data.name);
//    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Profile");

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.activity_profile_page, container, false);

        profileName = (TextView) v.findViewById(R.id.profileName);
        profileMail = (TextView) v.findViewById(R.id.profilemail);
        profilePhone = (TextView) v.findViewById(R.id.profilePhone);
        profileBalance = (TextView) v.findViewById(R.id.profileBalance);
        bala = (TextView) v.findViewById(R.id.bal);
        emaila = (TextView) v.findViewById(R.id.email);
        numa = (TextView) v.findViewById(R.id.num);
        btn4=(Button)v.findViewById(R.id.button4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");


        final Typeface type=Typeface.createFromAsset(v.getContext().getAssets(),"Arcon-Regular.otf");
        profileName.setTypeface(type);
        profileBalance.setTypeface(type);
        profileMail.setTypeface(type);
        profilePhone.setTypeface(type);
        bala.setTypeface(type);
        emaila.setTypeface(type);
        numa.setTypeface(type);



        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent=new Intent(v.getContext(),PhoneNumber.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(mainIntent);

            }
        });


        ValueEventListener mp2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot counter: dataSnapshot.getChildren()) {

                    if(data.email.equals(data.stringToEmail(counter.getKey().toString())))
                    {
                        scs=counter.child("SC").getValue().toString();
                        phone=counter.child("phone").getValue().toString();
                        mail=counter.getKey().toString();



                    }

                }
                profileBalance.setText(scs);
                profilePhone.setText(phone);
                profileMail.setText(mail);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(mp2);
        Log.d(scs,"s1c");



        profileName.setText(data.name);

        return v;

    }
}
