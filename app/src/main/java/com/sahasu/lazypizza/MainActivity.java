package com.sahasu.lazypizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView textView;
    private BottomBar bottomBar;
    private boolean exit = false;
    String ordername;
    String cnt;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
        com.sahasu.lazypizza.PrefManager prefManager;
        prefManager = new com.sahasu.lazypizza.PrefManager(this);
        prefManager.setPressedHowToUse(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment frag = new MarketMain();
                if (tabId == R.id.tab_calls) {
//                    Intent market = new Intent(getApplicationContext(),menuFood.class);
//                    startActivity(market);
                    frag = new MarketMain();
                } else if (tabId == R.id.tab_groups) {
                    // The tab with id R.id.tab_groups was selected,
                    // change your content accordingly.
                    frag = new MenuMain();
                } else if (tabId == R.id.tab_chats) {
                    // The tab with id R.id.tab_chats was selected,
                    // change your content accordingly.
                    frag = new Wallet();
                }
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, frag);
                fragmentTransaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
                finish();
            }
//            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back
// again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {


            try {

                FileInputStream fileIn = openFileInput("cart.txt");
                InputStreamReader InputRead = new InputStreamReader(fileIn);

                char[] inputBuffer = new char[10000];
                String s = "";
                int charRead;

                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    // char to string conversion
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }
                InputRead.close();
                System.out.print(s);
                final String[] arr0 = s.split("\n");
                final String[] arr=new String[arr0.length];

                final String src;
                String src1="";
                final String order="";
                count=0;
                ordername="";


                for (int i = 0; i < arr0.length; i++)
                {

                    String[] arr1=arr0[i].split(",");
                    arr[i]=arr1[0];
                    src1=arr1[1];
                    System.out.println(src1);
                }
                for (int i = 0; i < arr0.length; i++)
                {

                    String[] arr1=arr0[i].split(",");

                        arr[i]=arr1[0];

                    String[] first=arr[i].split("----");
                    ordername=ordername+first[0]+" | ";
                    count=count+Integer.parseInt(first[1]);
                    src1=arr1[1];
                    System.out.println(src1);
                }
                src=src1;

                System.out.println("ordername "+ordername);
                System.out.println("price "+count);
                cnt=Integer.toString(count);

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cart");
                builder.setIcon(R.drawable.ic_launcher);

// add a checkbox list

                boolean[] checked= new boolean[arr.length];
                for(int i=0;i<checked.length;i++)
                {
                    checked[i]=false;
                }

                final ArrayList<Integer> selected=new ArrayList<>();
                builder.setMultiChoiceItems(arr, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (isChecked) {
                            selected.add(++which);
                        } else if (selected.contains(which)) {
                            selected.remove(Integer.valueOf(++which));
                        }

                        System.out.println(selected);


                    }
                });

// add OK and Cancel buttons
                builder.setPositiveButton("PLACE ORDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // user clicked OK
                final Intent intent = new Intent(MainActivity.this, Menu_PlaceOrder.class);

                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                alert.setTitle("Enter Location");

                final EditText input_loc = new EditText(getApplicationContext());
                alert.setView(input_loc.getRootView());
                //alert.setView(input_loc);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String loc = input_loc.getText().toString();
                        intent.putExtra("address", loc);
//                    MenuInfo info;
//                    info = menuData.get(getAdapterPosition());
//                    intent.putExtra("orderName", info.getOrder_name());
//                    intent.putExtra("cost", info.getCost());
//                    v.getContext().startActivity(intent);
                        final AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                        alert2.setTitle("Enter Super Coins");

                        final EditText input2 = new EditText(getApplicationContext());
                        input2.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        alert2.setView(input2.getRootView());

                        alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String sc = input2.getText().toString();
                                intent.putExtra("scs", sc);
                                final AlertDialog.Builder alert3 = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                                alert3.setTitle("Enter Remarks");

                                final EditText input3 = new EditText(getApplicationContext());
                                input3.setRawInputType(InputType.TYPE_CLASS_TEXT);
                                alert3.setView(input3.getRootView());
                                alert3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String remarks = input3.getText().toString();

                                        intent.putExtra("orderName", ordername);
                                        intent.putExtra("cost", cnt);
                                        intent.putExtra("source", src);
                                        intent.putExtra("remarks", remarks);
                                       MainActivity.this.startActivity(intent);
                                    }
                                });

                                alert3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                                });
                                alert3.show();

                            }

                        });


                        alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
                        alert2.show();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                    }
                });
                builder.setNegativeButton("REMOVE SELECTED",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<String> a=new ArrayList<>(Arrays.asList(arr));
                        ArrayList<String> b=new ArrayList<>(a);
                        for (int i = 0; i < selected.size(); i++) {
                            int select = selected.get(i);

                            a.remove(b.get(select-1));



                        }

                        System.out.println("size: "+ a.size());

                        try {
                            FileOutputStream fileout=getApplicationContext().openFileOutput("cart.txt", getApplicationContext().MODE_PRIVATE);
                            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

                            if(a.size()==0)
                            {
                                getApplicationContext().deleteFile("cart.txt");
                            }
                            for(int i=0;i<a.size();i++)
                            {
                                outputWriter.write(a.get(i)+"\n");
                            }
                            outputWriter.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // user clicked OK
                    }
                });

// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();



            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            Intent myprofile = new Intent(getApplicationContext(), ProfilePage.class);
            startActivity(myprofile);
        } else if (id == R.id.balance) {
            Intent mybalance = new Intent(getApplicationContext(), MyBalance.class);
            startActivity(mybalance);
        } else if (id == R.id.orders) {
            Intent order = new Intent(getApplicationContext(), MyOrders.class);
            startActivity(order);
        }
        else if (id==R.id.delivery){

            Intent curr = new Intent(getApplicationContext(), Current_Delivery.class);
            startActivity(curr);
        }

        else if (id == R.id.use) {

            com.sahasu.lazypizza.PrefManager prefManager;
            prefManager = new com.sahasu.lazypizza.PrefManager(this);
            prefManager.setFirstTimeLaunch(true);
            prefManager.setPressedHowToUse(true);

            Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcome);
        } else if (id == R.id.logout) {
            GoogleLogin.logout();
            Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}