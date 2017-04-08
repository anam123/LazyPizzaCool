package com.sahasu.lazypizza;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Wallet extends Fragment {

    EditText email, amountSC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_wallet, container, false);
        return v;
    }
    public void walletButton()
    {
        Toast.makeText(this.getContext(), "SC Transferred successfully!", Toast.LENGTH_SHORT).show();
    }
}
