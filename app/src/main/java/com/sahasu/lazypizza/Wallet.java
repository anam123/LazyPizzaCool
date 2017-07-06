package com.sahasu.lazypizza;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

public class Wallet extends Fragment {

    static EditText Email, amountSC;
    Button transferMoney;
    static Context context;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wallet, container, false);
        Email = (EditText) v.findViewById(R.id.email);
        amountSC = (EditText) v.findViewById(R.id.amountSc);
        tv = (TextView) v.findViewById(R.id.tvv);
        transferMoney = (Button) v.findViewById(R.id.walletButton);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"Arcon-Regular.otf");
        transferMoney.setTypeface(type);
        Email.setTypeface(type);
        amountSC.setTypeface(type);
        tv.setTypeface(type);

        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), Email.getText().toString(), Toast.LENGTH_SHORT).show();

                if(Email.getText().toString().equals("") || amountSC.getText().toString().equals(""))
                {
                    Toast.makeText(v.getContext() ,"Both Fields Mandatory!", Toast.LENGTH_SHORT).show();
                }
                else {

                    data.transferSC(data.email, Email.getText().toString(), Float.parseFloat(amountSC.getText().toString()), 1);
                    Email.setText("");
                    amountSC.setText("");
                    Email.setHint("Enter Email Address");
                    amountSC.setHint("Enter Amount (in SCs)");
                    context = v.getContext();
                }
            }
        });
        return v;
    }

    public static void incorrectEmail(int i, String error)
    {
        if(i ==0)
        {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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
