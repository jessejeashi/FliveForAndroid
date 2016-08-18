package com.iems5722.group13;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Created by Administrator on 2016/2/3.
 */
public class ItemsAdapter extends ArrayAdapter<Item>{

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing user JI Xi
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.roomitemlist, parent, false);

        // Lookup view for data population
        if (convertView != null){
            TextView textMsg = (TextView) convertView.findViewById(R.id.cname);
            TextView textTime = (TextView) convertView.findViewById(R.id.topic);
            TextView textUname = (TextView) convertView.findViewById(R.id.uname);
            // Populate the data into the template view using the data object
            textMsg.setText(item.cname);
            textTime.setText(item.topic);
            textUname.setText(item.uname);
        }
        // Return the completed view to render on screen
        return convertView;
    }



}
