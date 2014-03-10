package com.example.arrayadapteritem;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Here you can control what to do next when the user selects an item
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	
    	Context context = view.getContext();

        TextView textViewItem = ((TextView) view.findViewById(R.id.uuid_text));
        String uuid = textViewItem.getText().toString();
        
        textViewItem = (TextView) view.findViewById(R.id.major_num);
        String major_num = textViewItem.getText().toString();

        textViewItem = (TextView) view.findViewById(R.id.minor_num);
        String minor_num = textViewItem.getText().toString();

        final String id_val = uuid+"-"+major_num+"-"+minor_num;
	    try {
	        HttpLib lib = new HttpLib(new HashMap<String, String>(){{put("id", id_val);}});
	        String info = lib.GET();
	        // just toast it
	        Toast.makeText(context, ((info != null)?info:"null"), Toast.LENGTH_SHORT).show();
	    } catch (Exception e) {
	    	Log.i("asdf", e.toString());
	    }
    }

}