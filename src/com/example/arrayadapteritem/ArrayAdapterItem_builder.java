package com.example.arrayadapteritem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterItem_builder extends ArrayAdapter<ObjectItem>{

    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectItem> data = null;

    public ArrayAdapterItem_builder(Context mContext, int layoutResourceId, ArrayList<ObjectItem> data) {
        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post 
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout. 
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        ObjectItem objectItem = data.get(position);

        Log.i("ad", "position: " + position);
        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.uuid_text);
        textViewItem.setText(objectItem.uuid);
        textViewItem = (TextView) convertView.findViewById(R.id.major_num);
        textViewItem.setText(String.valueOf(objectItem.major_number));
        textViewItem = (TextView) convertView.findViewById(R.id.minor_num);
        textViewItem.setText(String.valueOf(objectItem.minor_number));
        textViewItem = (TextView) convertView.findViewById(R.id.rssi_value);
        textViewItem.setText("rssi: " + objectItem.rssi);
//        textViewItem.setTag(objectItem.itemId);

        return convertView;

    }
}