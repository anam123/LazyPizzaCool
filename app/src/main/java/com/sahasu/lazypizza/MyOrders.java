package com.sahasu.lazypizza;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MyOrders extends Fragment {

    ListView simpleListView;
    // Array of strings...
    String[] orderTitle = {"If", "you", "are", "seeing", "this", "something is", "not", "right!"};
    String[] orderDescription = {"","","","","","","",""};
    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    ArrayList<Integer> index = new ArrayList<Integer>();
    ArrayList<Integer> index1 = new ArrayList<Integer>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Orders");

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        final View v = inflater.inflate(R.layout.activity_my_orders, container, false);
        simpleListView=(ListView)v.findViewById(R.id.simpleListView);

        for (int i = 0; i < com.sahasu.lazypizza.data.market.size(); i++) {
            if(com.sahasu.lazypizza.data.market.get(i).get("accepted").equals("1"))
            {
                if(com.sahasu.lazypizza.data.market.get(i).get("email").equals(data.email))
                {
                    HashMap<String,String> hashMap=new HashMap<String, String>();//create a hashmap to store the data in key value pair
                    hashMap.put("name",com.sahasu.lazypizza.data.market.get(i).get("item"));
                    String hour = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(0,2);
                    String minutes = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(2,4);
                    String ts;
//                    if(Integer.parseInt(hour) < 12)
//                        ts = hour + ":" + minutes + " am";
//                    else
//                        ts = hour + ":" + minutes + " pm";
                    ts=com.sahasu.lazypizza.data.market.get(i).get("timestamp");
                    hashMap.put("desc",ts);
                    arrayList.add(hashMap);
                    index.add(i);
                }
            }
            else
            {
                if(com.sahasu.lazypizza.data.market.get(i).get("email").equals(data.email))
                {
                    HashMap<String,String> hashMap=new HashMap<String, String>();//create a hashmap to store the data in key value pair
                    hashMap.put("name",com.sahasu.lazypizza.data.market.get(i).get("item"));
                    String hour = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(0,2);
                    String minutes = com.sahasu.lazypizza.data.market.get(i).get("timestamp").split("_")[1].substring(2,4);
                    String ts;
//                    if(Integer.parseInt(hour) < 12)
//                        ts = hour + ":" + minutes + " am";
//                    else
//                        ts = hour + ":" + minutes + " pm";
                    ts=com.sahasu.lazypizza.data.market.get(i).get("timestamp");
                    Log.d(ts,"Dd");
                    hashMap.put("desc",ts);
                    arrayList.add(hashMap);
                    index.add(i);
                }
            }
        }
        Log.d(index.toString(),"laa");
        ArrayList<HashMap<String,String>> arrayList1=new ArrayList<>(arrayList);
        Collections.sort(arrayList1, new Comparator<HashMap<String,String>>() {
            DateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");
            @Override
            public int compare(HashMap<String,String> o1, HashMap<String,String> o2) {
                try {
                    return f.parse(o1.get("desc")).compareTo(f.parse(o2.get("desc")));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        Collections.reverse(arrayList1);
        for(int i=0;i<arrayList1.size();i++)
        {
            Log.d(arrayList1.get(i).get("desc")+" "+arrayList1.get(i).get("name"),"das");
        }


        for(int i=0;i<arrayList1.size();i++)
        {
            int j=arrayList.indexOf(arrayList1.get(i));
            Log.d(Integer.toString(j),"Dda");
            int k=index.get(j);
            Log.d(Integer.toString(k),"Dda");
            index1.add(k);


        }

        for(int i=0;i<arrayList1.size();i++)
        {
            String hour=arrayList1.get(i).get("desc").split("_")[1].substring(0,2);
            String minutes=arrayList1.get(i).get("desc").split("_")[1].substring(2,4);
            String day=arrayList1.get(i).get("desc").split("_")[0];

            String ts;
            if(Integer.parseInt(hour) < 12)
                        ts = hour + ":" + minutes + " am";
                    else
                        ts = hour + ":" + minutes + " pm";

            arrayList1.get(i).put("desc",ts+ " | " + day);



        }
        /*for (int i=0;i<orderTitle.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("name",orderTitle[i]);
            hashMap.put("desc",orderDescription[i]+"");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }*/
        String[] from={"name","desc"}; //string array
        int[] to={R.id.foodTitle, R.id.foodDesc}; //int array of views id's
        SimpleAdapter simpleAdapter = null;

        TextView tv1=(TextView)v.findViewById(R.id.foodDesc);
        TextView tv2=(TextView)v.findViewById(R.id.foodTitle);
        simpleAdapter = new SimpleAdapter(v.getContext(),arrayList1,R.layout.activitymyorders_listview,from,to);//Create object and set the parameters for simpleAdapter
        if(simpleAdapter == null)
            Log.d("SIMPLE","Null simple adapter");
        if(simpleListView == null)
            Log.d("SIMPLE","Null simple List view");

        simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView



        //perform listView item click event
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(v.getContext(),position+" is the position",Toast.LENGTH_LONG).show();//show the selected image in toast according to position

                // Open the order details activity from here and pass the details
                int marketIndex = index1.get(position);
                Intent orderDetails = new Intent(v.getContext(), com.sahasu.lazypizza.orderDetails.class);
                orderDetails.putExtra("Item",com.sahasu.lazypizza.data.market.get(marketIndex).get("item"));
                orderDetails.putExtra("UID",com.sahasu.lazypizza.data.market.get(marketIndex).get("UID"));
                orderDetails.putExtra("Price", com.sahasu.lazypizza.data.market.get(marketIndex).get("price"));
                orderDetails.putExtra("Destination", com.sahasu.lazypizza.data.market.get(marketIndex).get("destination"));
                orderDetails.putExtra("Accepted", com.sahasu.lazypizza.data.market.get(marketIndex).get("accepted"));
                orderDetails.putExtra("Time", com.sahasu.lazypizza.data.market.get(marketIndex).get("timestamp"));
                orderDetails.putExtra("DeliveryEmail", com.sahasu.lazypizza.data.market.get(marketIndex).get("deliveryboy"));
                orderDetails.putExtra("Expectedtime", com.sahasu.lazypizza.data.market.get(marketIndex).get("exp_timestamp"));
                orderDetails.putExtra("exp", com.sahasu.lazypizza.data.market.get(marketIndex).get("expected"));
                orderDetails.putExtra("DeliveryPhone", com.sahasu.lazypizza.data.market.get(marketIndex).get("deliveryboyphone"));
                orderDetails.putExtra("SC", com.sahasu.lazypizza.data.market.get(marketIndex).get("SC"));
                startActivity(orderDetails);
            }
        });





        return v;

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
