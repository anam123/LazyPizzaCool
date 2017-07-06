package com.sahasu.lazypizza;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneNumber extends AppCompatActivity
{

    String phonenumber;

    TextView userNameText;
    Button saveBtn;
    EditText phoneTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        phonenumber = "";
        userNameText = (TextView)findViewById(R.id.txtview_helloUser);
        phoneTextBox = (EditText)findViewById(R.id.phoneNumber);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        final Typeface type=Typeface.createFromAsset(getAssets(),"Arcon-Regular.otf");


        phoneTextBox.setTypeface(type);
        if(data.name != null)
        {
            userNameText.setText("Hi, "+data.name+"!");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenumber = phoneTextBox.getText().toString();

                // SAVE THIS PHONE NUMBER IN THE DATABASE
                data.setValue("users/"+data.emailToString(data.email)+"/phone",phonenumber);

                //display toast
                Toast.makeText(PhoneNumber.this, "Your number has been saved.", Toast.LENGTH_SHORT).show();
                Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(mainIntent);
                // start mainActivity
            }
        });

    }
}
