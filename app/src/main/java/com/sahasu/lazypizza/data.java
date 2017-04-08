package com.sahasu.lazypizza;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by sarmad on 07-04-2017.
 */
public class data {

    public static String name;
    public static String email;
    public static Uri PhotoURL;
    public static String SC;
    public static String phone;
    public static ArrayList<HashMap<String,String>> market; //count,data key, data value
    public static Context loginContext;
    public static boolean isOld=false;

    public static void initialize(){
        market=new ArrayList<HashMap<String, String>>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("marketplace/");
        ValueEventListener mp = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot counter: dataSnapshot.getChildren()) {
                    HashMap<String,String> temp=new HashMap<String,String>();
                    temp.put("UID",counter.getValue().toString());
                    temp.put("email",stringToEmail(counter.child("email").getValue().toString()));
                    temp.put("Remarks",counter.child("Remarks").getValue().toString());
                    temp.put("accepted",counter.child("accepted").getValue().toString());
                    temp.put("SC",counter.child("SC").getValue().toString());
                    temp.put("deliveryboy",counter.child("deliveryboy").getValue().toString());
                    temp.put("deliveryboyphone",counter.child("deliveryboyphone").getValue().toString());
                    temp.put("destination",counter.child("destination").getValue().toString());
                    temp.put("item",counter.child("item").getValue().toString());
                    temp.put("phone",counter.child("phone").getValue().toString());
                    temp.put("price",counter.child("price").getValue().toString());
                    market.add(i,temp);
                    i++;

                }
                //Call method to reload the marketplace here
                if(isOld)
                    GoogleLogin.gotoMain(loginContext);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addValueEventListener(mp);
    }

    public static void createNewUser(Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+emailToString(email)+"/");
        myRef.child("SC").setValue("5");
        data.SC="5";
        //Go to the phone number activity here
        //This is how you store the number
        Intent phoneIntent=new Intent(context,PhoneNumber.class);
        phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(phoneIntent);
    }

    public static void setValue(String path, String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.setValue(value);
    }
    static boolean flag=false;
    //Transfer SC from one account to the other
    public static void transferSC(final String senderEmail, final String receiverEmail, final float transferAmount){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String senderSC = snapshot.child(emailToString(senderEmail)).child("SC").getValue().toString();
                String receiverSC = snapshot.child(emailToString(senderEmail)).child("SC").getValue().toString();
                float senderSCBalance=Float.parseFloat(senderSC);
                float receiverSCBalance = Float.parseFloat(receiverSC);
                if(senderSCBalance<transferAmount){         //Not Enough Money
                    flag=true;
                    return;
                }else{
                    senderSCBalance-=transferAmount;
                    receiverSCBalance+=transferAmount;
                    setValue("users/"+emailToString(receiverEmail)+"/SC/",String.valueOf(receiverSCBalance));
                    setValue("users/"+emailToString(senderEmail)+"/SC/",String.valueOf(senderSCBalance));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }

    public static void addItem(String name, String price){  //Adds a new item to the list of items
        setValue("items/"+name+"/",price);
    }

    public static void addToMarket(String item, String SC, String price, String Remarks, String destination){
        String uid=String.valueOf(Math.round(Math.random()*10e10));
        setValue("marketplace/"+uid+"/item/",item+" ");
        setValue("marketplace/"+uid+"/SC/",SC+" ");
        setValue("marketplace/"+uid+"/price/",price+" ");
        setValue("marketplace/"+uid+"/Remarks/",Remarks+" ");
        setValue("marketplace/"+uid+"/destination/",destination+" ");
        setValue("marketplace/"+uid+"/email/", emailToString(email)+" ");
        setValue("marketplace/"+uid+"/accepted/","0");
        setValue("marketplace/"+uid+"/phone/",phone+" ");
        setValue("marketplace/"+uid+"/deliveryboy/"," ");
        setValue("marketplace/"+uid+"/deliveryboyphone/"," ");
    }

    public static void acceptItemFromMarket(String UID){
        setValue("marketplace/"+UID+"/accepted","1");
        setValue("marketplace/"+UID+"/deliveryboy/",emailToString(email)+" ");
        setValue("marketplace/"+UID+"/deliveryboyphone/",phone+" ");
    }

    public static void orderCompleted(String UID, String email, String deliveryboyemail, String SC){
        transferSC(email,deliveryboyemail,Float.valueOf(SC));
        if(!flag) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("marketplace/" + UID + "/");
            myRef.removeValue();
        }else{
            flag=false;
        }
    }

    public static String emailToString(String email){
        String s=email.replaceAll("\\.",",");
        return s;
    }
    public static String stringToEmail(String string){
        String s=string.replaceAll(",","\\.");
        return s;
    }

}
