package com.example.arrayadapteritem;

// http://www.javacodegeeks.com/2013/09/android-listview-with-adapter-example.html

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class ArrayAdapterItem extends Activity {

    AlertDialog alertDialogStores;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.array_adapter_item, menu);
		return true;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_array_adap_item);

        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ibscan:
                        showPopUp();
                        break;
                    case R.id.ibadd:
                    	add_new_ib();
                    	break;
                }
            }
        };
        findViewById(R.id.ibscan).setOnClickListener(handler);
        findViewById(R.id.ibadd).setOnClickListener(handler);
    }

    void add_new_ib()
    {
    	//this.setContentView(R.layout.modify_ble);
    	Intent i = new Intent(this, AddNewIB.class);
    	startActivity(i);
    }
    
    public void showPopUp(){
    	Intent i = new Intent(this, ScanBle.class);
    	startActivity(i);

/*
        // add your items, this can be done programatically
        // your items can be from a database
        ObjectItem[] ObjectItemData = new ObjectItem[20];

        ObjectItemData[0] = new ObjectItem(91, "Mercury");
        ObjectItemData[1] = new ObjectItem(92, "Watson");
        ObjectItemData[2] = new ObjectItem(93, "Nissan");
        ObjectItemData[3] = new ObjectItem(94, "Puregold");
        ObjectItemData[4] = new ObjectItem(95, "SM");
        ObjectItemData[5] = new ObjectItem(96, "7 Eleven");
        ObjectItemData[6] = new ObjectItem(97, "Ministop");
        ObjectItemData[7] = new ObjectItem(98, "Fat Chicken");
        ObjectItemData[8] = new ObjectItem(99, "Master Siomai");
        ObjectItemData[9] = new ObjectItem(100, "Mang Inasal");
        ObjectItemData[10] = new ObjectItem(101, "Mercury 2");
        ObjectItemData[11] = new ObjectItem(102, "Watson 2");
        ObjectItemData[12] = new ObjectItem(103, "Nissan 2");
        ObjectItemData[13] = new ObjectItem(104, "Puregold 2");
        ObjectItemData[14] = new ObjectItem(105, "SM 2");
        ObjectItemData[15] = new ObjectItem(106, "7 Eleven 2");
        ObjectItemData[16] = new ObjectItem(107, "Ministop 2");
        ObjectItemData[17] = new ObjectItem(108, "Fat Chicken 2");
        ObjectItemData[18] = new ObjectItem(109, "Master Siomai 2");
        ObjectItemData[19] = new ObjectItem(110, "Mang Inasal 2");

        // our adapter instance
        ArrayAdapterItem_builder adapter = new ArrayAdapterItem_builder(this, R.layout.list_view_row_item, ObjectItemData);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(this);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());

        this.setContentView(listViewItems);

        // put the ListView in the pop up
        alertDialogStores = new AlertDialog.Builder(ArrayAdapterItem.this)
            .setView(listViewItems)
            .setTitle("Stores")
            .show();
        */
    }
}

