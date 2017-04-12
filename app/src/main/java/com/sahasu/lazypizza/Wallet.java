package com.sahasu.lazypizza;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Wallet extends Fragment {

    static EditText Email, amountSC;
    Button transferMoney;
    static Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wallet, container, false);
        Email = (EditText) v.findViewById(R.id.email);
        amountSC = (EditText) v.findViewById(R.id.amountSc);
        transferMoney = (Button) v.findViewById(R.id.walletButton);

        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), Email.getText().toString(), Toast.LENGTH_SHORT).show();
                data.transferSC(data.email,Email.getText().toString(),Float.parseFloat(amountSC.getText().toString()));
                Email.setText("");
                amountSC.setText("");
                Email.setHint("Enter Email Address");
                amountSC.setHint("Enter Amount (in SCs)");
                context = v.getContext();
            }
        });
        return v;
    }

    public static void incorrectEmail(int i)
    {
        if(i ==0)
        {
            Toast.makeText(context, "THIS USER DOES NOT EXIST", Toast.LENGTH_SHORT).show();
            Email.setText("");
            amountSC.setText("");
            Email.setHint("Enter Email Address");
            amountSC.setHint("Enter Amount (in SCs)");
        }
        else
        {
            Toast.makeText(context ,"Amount Successfuly Transfered!", Toast.LENGTH_SHORT).show();
        }

    }


}
