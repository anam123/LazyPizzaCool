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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public static String token;
    public static String expected;
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
                items.clear();
                for (DataSnapshot counter: dataSnapshot.getChildren()) {
                    HashMap<String,String> temp=new HashMap<String,String>();
                    temp.put("name",counter.getKey().toString());
                    temp.put("price",counter.child("price").getValue().toString());
                    temp.put("source",counter.child("source").getValue().toString());
                    items.add(i,temp);
                    i++;
                }
                itemsLoaded=true;
                if(isOld && GoogleLogin.loadedMain<2){
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
                    temp.put("src",counter.child("src").getValue().toString());
                    temp.put("expected",counter.child("expected").getValue().toString());
                    temp.put("deliveryboy",counter.child("deliveryboy").getValue().toString());
                    temp.put("deliveryboyphone",counter.child("deliveryboyphone").getValue().toString());
                    temp.put("destination",counter.child("destination").getValue().toString());
                    temp.put("item",counter.child("item").getValue().toString());
                    temp.put("phone",counter.child("phone").getValue().toString());
                    temp.put("price",counter.child("price").getValue().toString());
                    if(counter.child("timestamp").getValue()!=null)
                        temp.put("timestamp",counter.child("timestamp").getValue().toString());
                    market.add(i,temp);
                    i++;

                }
                marketLoaded=true;
                //Call method to reload the marketplace here
                if(isOld && GoogleLogin.loadedMain<2){
                    if(marketLoaded&&itemsLoaded)
                        GoogleLogin.gotoMain(loginContext);
                }else if(GoogleLogin.loadedMain>=2){
                    //@TODO Call Method to repopulate here
                    List<MarketInfo> data = new ArrayList<>();
                    if (com.sahasu.lazypizza.data.market!=null) {
                        int[] icons = {R.drawable.pizza, R.drawable.pizza, R.drawable.cheezydibble, R.drawable.cheezydibble,R.drawable.food,R.drawable.cake,R.drawable.cheetos,R.drawable.cake,R.drawable.cheezydibble};
                        for (int j = 0; j < com.sahasu.lazypizza.data.market.size(); j++) {
                            MarketInfo current = new MarketInfo();
                            current.img_id=icons[j%icons.length];
                            current.order_name = com.sahasu.lazypizza.data.market.get(j).get("item");
                            current.address = com.sahasu.lazypizza.data.market.get(j).get("destination");
                            current.cost = "Rs " + com.sahasu.lazypizza.data.market.get(j).get("price") + " + " + com.sahasu.lazypizza.data.market.get(j).get("SC") + " SC";
                            current.place_by = com.sahasu.lazypizza.data.market.get(j).get("email");
                            current.phone_no = com.sahasu.lazypizza.data.market.get(j).get("phone");
                            current.UID =  com.sahasu.lazypizza.data.market.get(j).get("UID");
                            current.src =  com.sahasu.lazypizza.data.market.get(j).get("src");
                            current.time_stamp = com.sahasu.lazypizza.data.market.get(j).get("timestamp");
                            //  Log.d("VERY EASY TO FIND TAG", com.sahasu.lazypizza.data.market.get(i).get("UID"));
                            data.add(current);
                        }
                        if(MarketAdaptor.mk!= null)
                            MarketAdaptor.mk.swapItems(data);
                    }

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
        public String item,SC,price,Remarks,destination,email,accepted,phone,deliveryboy,deliveryboyphone,timestamp,src,expected;
        public marketItem(String item,String SC,String price,String Remarks,String destination,String email,String accepted,
                          String phone,String deliveryboy,String deliveryboyphone, String src, String expected){
            this.item =item;
            this.SC = SC;
            this.src = src;
            this.expected = expected;
            this.Remarks = Remarks;
            this.price= price;
            this.destination =destination;
            this.email = email;
            this.accepted = accepted;
            this.phone = phone;
            this.deliveryboy = deliveryboy;
            this.deliveryboyphone = deliveryboyphone;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            this.timestamp=currentDateandTime;
        }
    }

    public static void setValues(String path, String item,String SC,String price,String Remarks,String destination,String email,String accepted,
                                 String phone,String deliveryboy,String deliveryboyphone,String src,String expected){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        marketItem current=new marketItem(item,SC,price,Remarks,destination,email,accepted,phone,deliveryboy,deliveryboyphone,src,expected);
        myRef.setValue(current);
    }
    public static void setValue(String path, String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.setValue(value);
    }

    public static void addToEscrow(final float amount){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.d("Very Important!!!!!", "Add to Escrow Called");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String senderSC = snapshot.child("users").child(emailToString(email)).child("SC").getValue().toString();
                String receiverSC;
                if(snapshot.child("escrow").child(emailToString(email)).getValue()==null) {
                    setValue("escrow/" + emailToString(email), "0");
                    receiverSC="0";
                }else {
                    receiverSC = snapshot.child("escrow").child(emailToString(email)).getValue().toString();
                }
                float senderSCBalance=Float.parseFloat(senderSC);
                float receiverSCBalance = Float.parseFloat(receiverSC);
                senderSCBalance-=amount;
                receiverSCBalance+=amount;
                setValue("escrow/"+emailToString(email),String.valueOf(receiverSCBalance));
                setValue("users/"+emailToString(email)+"/SC/",String.valueOf(senderSCBalance));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }
    public static void getFromEscrow(final float amount, final String receiverEmail, final String senderEmail){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String receiverSC = snapshot.child("users").child(emailToString(receiverEmail)).child("SC").getValue().toString();
                if(snapshot.child("escrow").child(emailToString(senderEmail)).getValue()==null){
                    Toast.makeText(Menu_PlaceOrder.context ,"Doesn't Exist", Toast.LENGTH_SHORT).show();;
                    return;
                }
                Log.d("Very Important!!!!!", String.valueOf(amount));
                String senderSC = snapshot.child("escrow").child(emailToString(senderEmail)).getValue().toString();
                float senderSCBalance=Float.parseFloat(senderSC);
                float receiverSCBalance = Float.parseFloat(receiverSC);
                senderSCBalance-=amount;
                receiverSCBalance+=amount;
                setValue("escrow/"+emailToString(senderEmail),String.valueOf(senderSCBalance));
                setValue("users/"+emailToString(receiverEmail)+"/SC/",String.valueOf(receiverSCBalance));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
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
                if(senderEmail.equals(receiverEmail)){
                    Wallet.incorrectEmail(0,"CAN'T SEND SC TO YOURSELF");
                    return;
                }
                if(snapshot.child(emailToString(receiverEmail)).child("SC").getValue()==null){
                    Wallet.incorrectEmail(0,"THIS USER DOES NOT EXIST");
                    return;
                }
                String senderSC = snapshot.child(emailToString(senderEmail)).child("SC").getValue().toString();
                String receiverSC = snapshot.child(emailToString(receiverEmail)).child("SC").getValue().toString();
                float senderSCBalance=Float.parseFloat(senderSC);
                float receiverSCBalance = Float.parseFloat(receiverSC);
                if(senderSCBalance<transferAmount){         //Not Enough Money
                    Wallet.incorrectEmail(0,"INSUFFICIENT FUNDS");
                    return;
                }else{
                    senderSCBalance-=transferAmount;
                    receiverSCBalance+=transferAmount;
                    Log.d(String.valueOf(senderSCBalance),"ff1");
                    setValue("users/"+emailToString(receiverEmail)+"/SC/",String.valueOf(receiverSCBalance));
                    setValue("users/"+emailToString(senderEmail)+"/SC/",String.valueOf(senderSCBalance));
                    Log.d(data.SC,"ff");
                }
                Wallet.incorrectEmail(1,"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }

    public static void addItem(String name, String price, String source){  //Adds a new item to the list of items
        setValue("items/"+name+"/",price);
    }
//    public static ArrayList<HashMap<String,String>> getItems(){         //Deprecated. Just use data.items
//        return items;
//    }

    public static void addToMarket(final String item, final String SC, final String price, final String Remarks, final String destination, final String source){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");
        Log.d("LOGGING OUTSIDE" , "ASFKLJASLFKJAKLJSFLKJASlk");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String currentSC = snapshot.child(emailToString(email)).child("SC").getValue().toString();
                float SCBalance=Float.parseFloat(currentSC);
                if(SCBalance<Float.parseFloat(SC)){         //Insufficient Balance
                    Toast.makeText(Menu_PlaceOrder.context ,"Not Enough Balance!", Toast.LENGTH_SHORT).show();;
                    return;
                }
                addToEscrow(Float.parseFloat(SC));
                String uid=String.valueOf(Math.round(Math.random()*10e10));
                setValues("marketplace/"+uid,item+" ",SC,price,Remarks+" ",destination+" ",emailToString(email),"0",phone+" "," "," ",source+ " ","");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
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
        Log.d("THE VALUE OF SC UID", "/"+SC+"/");
        getFromEscrow(Float.valueOf(SC),deliveryboyemail,email);
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
