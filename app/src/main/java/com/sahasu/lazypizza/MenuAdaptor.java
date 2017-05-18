package com.sahasu.lazypizza;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.MyHolder> implements Filterable{

    private List<MenuInfo> menuData;
    private List<MenuInfo> menuData1;
    private LayoutInflater inflater;

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

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
                            Log.d(constraint.toString().toLowerCase(),"vf");
                            Log.d(data,"vf");
                            FilteredArrList.add(new MenuInfo(menuData.get(i).getOrder_name(),menuData.get(i).getCost(),menuData.get(i).getSource(),menuData.get(i).getUID(),menuData.get(i).getImage_id()));
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

    public MenuAdaptor(List<MenuInfo> menuData, Context c)
    {
        this.inflater = LayoutInflater.from(c);
        this.menuData = menuData;
        this.menuData1 = menuData;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menuitem,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MenuInfo menu= menuData1.get(position);
        holder.title.setText(menu.getOrder_name());
        holder.cost.setText(menu.getCost());
        holder.icon.setBackgroundResource(menu.getImage_id());
    }




    @Override
    public int getItemCount() {
        return menuData1.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private LinearLayout icon;
        private TextView cost;
        private TextView placeOrder;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.menuOrderName);
            icon = (LinearLayout) itemView.findViewById(R.id.menuIcon);
            cost = (TextView) itemView.findViewById(R.id.menuAddress);
            placeOrder = (TextView) itemView.findViewById(R.id.menuPlaceOrder);

            placeOrder.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {

            Toast.makeText(itemView.getContext(), "Clicked button at position : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(itemView.getContext(), Menu_PlaceOrder.class);

            final AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
            alert.setTitle("Enter Location");

            final EditText input_loc = new EditText(itemView.getContext());
            alert.setView(input_loc.getRootView());
            //alert.setView(input_loc);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String loc=input_loc.getText().toString();
                    intent.putExtra("address",loc);
//                    MenuInfo info;
//                    info = menuData.get(getAdapterPosition());
//                    intent.putExtra("orderName", info.getOrder_name());
//                    intent.putExtra("cost", info.getCost());
//                    v.getContext().startActivity(intent);
                    final AlertDialog.Builder alert2 = new AlertDialog.Builder(itemView.getContext());
                    alert2.setTitle("Enter Super Coins");

                    final EditText input2 = new EditText (itemView.getContext());
                    input2.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                    alert2.setView(input2.getRootView());

                    alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                                String sc=input2.getText().toString();
                                intent.putExtra("scs",sc);
                                final AlertDialog.Builder alert3 = new AlertDialog.Builder(itemView.getContext());
                                alert3.setTitle("Enter Remarks");

                                final EditText input3 = new EditText (itemView.getContext());
                                input3.setRawInputType(InputType.TYPE_CLASS_TEXT);
                                alert3.setView(input3.getRootView());
                                alert3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String remarks = input3.getText().toString();
                                        MenuInfo info;
                                        info = menuData1.get(getAdapterPosition());
                                        intent.putExtra("orderName", info.getOrder_name());
                                        intent.putExtra("cost", info.getCost());
                                        intent.putExtra("source", info.getSource());
                                        intent.putExtra("remarks", remarks);
                                        v.getContext().startActivity(intent);
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




    }

}
