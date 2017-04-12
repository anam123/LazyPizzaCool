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
import java.util.Map;
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
    public static ArrayList<HashMap<String,String>> items;
    public static Context loginContext;
    public static boolean isOld=false;
    public static boolean marketLoaded=false,itemsLoaded=false;

    public static void initialize(){
        market=new ArrayList<HashMap<String, String>>();
        items = new ArrayList<HashMap<String, String>>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("marketplace/");
        DatabaseReference myRef2 = database.getReference("items/");
        ValueEventListener mp2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot counter: dataSnapshot.getChildren()) {
                    HashMap<String,String> temp=new HashMap<String,String>();
                    temp.put("name",counter.getKey().toString());
                    temp.put("price",counter.child("price").getValue().toString());
                    items.add(i,temp);
                    i++;
                }
                itemsLoaded=true;
                if(isOld){
                    if(marketLoaded&&itemsLoaded)
                        GoogleLogin.gotoMain(loginContext);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef2.addListenerForSingleValueEvent(mp2);
        ValueEventListener mp = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                market.clear();
                for (DataSnapshot counter: dataSnapshot.getChildren()) {
                    HashMap<String,String> temp=new HashMap<String,String>();
                    temp.put("UID",counter.getKey().toString());
                  //  Log.d("VERY EASY TO FIND TAG", counter.getKey().toString());
                    //Toast.makeText(loginContext, counter.getValue().toString(), Toast.LENGTH_SHORT);
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
                marketLoaded=true;
                //Call method to reload the marketplace here
                if(isOld){
                    if(marketLoaded&&itemsLoaded)
                        GoogleLogin.gotoMain(loginContext);
                }
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

    public static class marketItem{
        public String item,SC,price,Remarks,destination,email,accepted,phone,deliveryboy,deliveryboyphone;
        public marketItem(String item,String SC,String price,String Remarks,String destination,String email,String accepted,
                          String phone,String deliveryboy,String deliveryboyphone){
            this.item =item;
            this.SC = SC;
            this.Remarks = Remarks;
            this.price= price;
            this.destination =destination;
            this.email = email;
            this.accepted = accepted;
            this.phone = phone;
            this.deliveryboy = deliveryboy;
            this.deliveryboyphone = deliveryboyphone;
        }
    }

    public static void setValues(String path, String item,String SC,String price,String Remarks,String destination,String email,String accepted,
                                 String phone,String deliveryboy,String deliveryboyphone){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        marketItem current=new marketItem(item,SC,price,Remarks,destination,email,accepted,phone,deliveryboy,deliveryboyphone);
        myRef.setValue(current);
    }
    public static void setValue(String path, String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.setValue(value);
    }

    //Transfer SC from one account to the other
    public static void transferSC(final String senderEmail, final String receiverEmail, final float transferAmount){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");
        Log.d("LOGGING OUTSIDE" , "ASFKLJASLFKJAKLJSFLKJASlk");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("FIND HERE DATA","/"+emailToString(receiverEmail)+"/");
                if(snapshot.child(emailToString(receiverEmail)).child("SC").getValue()==null){
                    Wallet.incorrectEmail(0);
                    return;
                }
                String senderSC = snapshot.child(emailToString(senderEmail)).child("SC").getValue().toString();
                String receiverSC = snapshot.child(emailToString(receiverEmail)).child("SC").getValue().toString();
                float senderSCBalance=Float.parseFloat(senderSC);
                float receiverSCBalance = Float.parseFloat(receiverSC);
                if(senderSCBalance<transferAmount){         //Not Enough Money
                    return;
                }else{
                    senderSCBalance-=transferAmount;
                    receiverSCBalance+=transferAmount;
                    setValue("users/"+emailToString(receiverEmail)+"/SC/",String.valueOf(receiverSCBalance));
                    setValue("users/"+emailToString(senderEmail)+"/SC/",String.valueOf(senderSCBalance));
                }
                Wallet.incorrectEmail(1);
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
//    public static ArrayList<HashMap<String,String>> getItems(){         //Deprecated. Just use data.items
//        return items;
//    }

    public static void addToMarket(String item, String SC, String price, String Remarks, String destination){
        String uid=String.valueOf(Math.round(Math.random()*10e10));
        setValues("marketplace/"+uid,item+" ",SC,price+" ",Remarks+" ",destination+" ",emailToString(email),"0",phone+" "," "," ");
//        setValue("marketplace/"+uid+"/item/",item+" ");
//        setValue("marketplace/"+uid+"/SC/",SC+" ");
//        setValue("marketplace/"+uid+"/price/",price+" ");
//        setValue("marketplace/"+uid+"/Remarks/",Remarks+" ");
//        setValue("marketplace/"+uid+"/destination/",destination+" ");
//        setValue("marketplace/"+uid+"/email/", emailToString(email)+" ");
//        setValue("marketplace/"+uid+"/accepted/","0");
//        setValue("marketplace/"+uid+"/phone/",phone+" ");
//        setValue("marketplace/"+uid+"/deliveryboy/"," ");
//        setValue("marketplace/"+uid+"/deliveryboyphone/"," ");
    }

    public static void acceptItemFromMarket(String UID){
        setValue("marketplace/"+UID+"/accepted","1");
        setValue("marketplace/"+UID+"/deliveryboy/",emailToString(email)+" ");
        setValue("marketplace/"+UID+"/deliveryboyphone/",phone+" ");
    }

    public static void orderCompleted(String UID, String email, String deliveryboyemail, String SC){
        transferSC(email,deliveryboyemail,Float.valueOf(SC));
        Log.d("THE VALUE OF TEST UID", "/"+UID+"/");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("THE VALUE OF UID", "/"+UID+"/");
        DatabaseReference myRef = database.getReference("marketplace/" + UID + "/");
        myRef.removeValue();

    }

    public static String emailToString(String email){
        String s=email.replaceAll("\\.",",").replaceAll(" ","");
        return s;
    }
    public static String stringToEmail(String string){
        String s=string.replaceAll(",","\\.").replaceAll(" ","");
        return s;
    }

}
