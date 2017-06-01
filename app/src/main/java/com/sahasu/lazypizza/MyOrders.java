package com.sahasu.lazypizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MyOrders extends AppCompatActivity {

    ListView simpleListView;
    // Array of strings...
    String[] orderTitle = {"If", "you", "are", "seeing", "this", "something is", "not", "right!"};
    String[] orderDescription = {"","","","","","","",""};
    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    ArrayList<Integer> index = new ArrayList<Integer>();
    ArrayList<Integer> index1 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        simpleListView=(ListView)findViewById(R.id.simpleListView);


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
        simpleAdapter = new SimpleAdapter(this,arrayList1,R.layout.activitymyorders_listview,from,to);//Create object and set the parameters for simpleAdapter
        if(simpleAdapter == null)
            Log.d("SIMPLE","Null simple adapter");
        if(simpleListView == null)
            Log.d("SIMPLE","Null simple List view");

        simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView

        //perform listView item click event
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),position+" is the position",Toast.LENGTH_LONG).show();//show the selected image in toast according to position

                // Open the order details activity from here and pass the details
                int marketIndex = index1.get(position);
                Intent orderDetails = new Intent(getApplicationContext(), com.sahasu.lazypizza.orderDetails.class);
                orderDetails.putExtra("Item",com.sahasu.lazypizza.data.market.get(marketIndex).get("item"));
                orderDetails.putExtra("UID",com.sahasu.lazypizza.data.market.get(marketIndex).get("UID"));
                orderDetails.putExtra("Price", com.sahasu.lazypizza.data.market.get(marketIndex).get("price"));
                orderDetails.putExtra("Destination", com.sahasu.lazypizza.data.market.get(marketIndex).get("destination"));
                orderDetails.putExtra("Accepted", com.sahasu.lazypizza.data.market.get(marketIndex).get("accepted"));
                orderDetails.putExtra("Time", com.sahasu.lazypizza.data.market.get(marketIndex).get("timestamp"));
                orderDetails.putExtra("DeliveryEmail", com.sahasu.lazypizza.data.market.get(marketIndex).get("deliveryboy"));
                orderDetails.putExtra("DeliveryPhone", com.sahasu.lazypizza.data.market.get(marketIndex).get("deliveryboyphone"));
                orderDetails.putExtra("SC", com.sahasu.lazypizza.data.market.get(marketIndex).get("SC"));
                startActivity(orderDetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
