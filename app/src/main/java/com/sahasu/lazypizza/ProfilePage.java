package com.sahasu.lazypizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {

    TextView profileMail, profilePhone, profileBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileMail = (TextView) findViewById(R.id.profilemail);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileBalance = (TextView) findViewById(R.id.profileBalance);

        profileMail.setText(data.email);
        profilePhone.setText(data.phone);
        profileBalance.setText(data.SC);

    }
}
