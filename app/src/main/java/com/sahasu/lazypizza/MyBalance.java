package com.sahasu.lazypizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyBalance extends AppCompatActivity {

    TextView balanceSc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_balance);

        balanceSc = (TextView)findViewById(R.id.balanceSc);
        balanceSc.setText(data.SC);


    }
}
