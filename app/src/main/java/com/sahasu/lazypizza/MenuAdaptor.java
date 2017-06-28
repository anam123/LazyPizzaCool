package com.sahasu.lazypizza;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.os.ParcelFileDescriptor.MODE_APPEND;
import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;


public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.MyHolder> implements Filterable {

    private List<MenuInfo> menuData;
    private List<MenuInfo> menuData1;
    FileOutputStream fileout;
    private LayoutInflater inflater;

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                menuData1 = (ArrayList<MenuInfo>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<MenuInfo> FilteredArrList = new ArrayList<MenuInfo>();

                if (menuData == null) {
                    menuData = new ArrayList<MenuInfo>(menuData1); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = menuData.size();
                    results.values = menuData;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < menuData.size(); i++) {
                        String data = menuData.get(i).order_name;

                        if (data.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            Log.d(constraint.toString().toLowerCase(), "vf");
                            Log.d(data, "vf");
                            FilteredArrList.add(new MenuInfo(menuData.get(i).getOrder_name(), menuData.get(i).getCost(), menuData.get(i).getSource(), menuData.get(i).getUID()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    System.out.println(results.count);
                    results.values = FilteredArrList;
                    System.out.println(results.values);
                }
                return results;
            }
        };
    }

    public MenuAdaptor(List<MenuInfo> menuData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.menuData = menuData;
        this.menuData1 = menuData;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menuitem, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MenuInfo menu = menuData1.get(position);
        holder.title.setText(menu.getOrder_name());
        holder.cost.setText(menu.getCost());

    }


    @Override
    public int getItemCount() {
        return menuData1.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;

        private TextView cost;
        private TextView placeOrder;
        private Button addcart;



        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.menuOrderName);

            cost = (TextView) itemView.findViewById(R.id.menuAddress);
            placeOrder = (TextView) itemView.findViewById(R.id.menuPlaceOrder);

            placeOrder.setOnClickListener(this);


        }


        @Override
        public void onClick(final View v) {


            if (v == placeOrder) {



                MenuInfo info;
                String file = "mydata";
                info = menuData1.get(getAdapterPosition());
                String name=info.getOrder_name();
                String cost=info.getCost();
                String source=info.getSource();
                String wtf;


                String s="";
                String src1="";
                int lines;
                try {

                    FileInputStream fileIn = itemView.getContext().openFileInput("cart.txt");
                    InputStreamReader InputRead = new InputStreamReader(fileIn);

                    char[] inputBuffer = new char[10000];

                    int charRead;

                    while ((charRead = InputRead.read(inputBuffer)) > 0) {
                        // char to string conversion
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        s += readstring;
                    }
                    InputRead.close();
                }
                catch(IOException e)
                {

                }
                    System.out.println("as: "+s);
                    final String[] arr0 = s.split("\n");
                for (int i = 0; i < arr0.length; i++)
                {

                    System.out.println("arr0 "+arr0[i]);
                    if(arr0[i].equals(""))
                    {

                    }
                    else {
                        String[] arr1 = arr0[i].split(",");
                        src1 = arr1[1];
                        System.out.println("src: " + src1);
                    }
                }
                lines=arr0.length;
                System.out.println("lines: "+lines);




                if(lines<=2 && ( source.equals(src1) || src1.equals(""))) {
                    try {
                        fileout = itemView.getContext().openFileOutput("cart.txt", itemView.getContext().MODE_APPEND);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                        wtf = name + "----" + cost + "," + source + "\n";
                        System.out.println("wtf :" + wtf);
                        outputWriter.write(wtf);
                        outputWriter.close();

                        //display file saved message
                        Toast.makeText(itemView.getContext(), "Added to cart!",
                                Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    if(lines>2) {
                        Toast.makeText(v.getContext(), "Cart Reached Max Limit, i.e 3!", Toast.LENGTH_SHORT).show();
                    }
                    else if(!source.equals(src1))
                    {
                        Toast.makeText(v.getContext(), "Order from one shop at a time.", Toast.LENGTH_SHORT).show();
                    }

                }


            }


        }

    }
}
