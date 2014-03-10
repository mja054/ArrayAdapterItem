package com.example.arrayadapteritem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothClass.Device.Major;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class ScanBle extends Activity {
	private int REQUEST_ENABLE_BT = 1;
	private static String tag = "arrayadpater";
	private ArrayAdapterItem_builder adapter;
	private ArrayList<ObjectItem> ObjectItemData;
	ArrayList<BluetoothDevice> mdevices = new ArrayList<BluetoothDevice>();
    private BluetoothAdapter mBluetoothAdapter;
    private Handler handler = new Handler();
    
	
	private void init_adapter()
	{
		ObjectItemData = new ArrayList<ObjectItem>();

		adapter = new ArrayAdapterItem_builder(this, R.layout.list_view_row_item, ObjectItemData);
	    // create a new ListView, set the adapter and item click listener      
		ListView listViewItems = new ListView(this);
	    listViewItems.setAdapter(adapter);
	    listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());
	
	    this.setContentView(listViewItems);
	}
	
	private void init_ble()
	{
		final BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = mBluetoothManager.getAdapter();

		//Enable Bluetooth
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
 			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);
		init_adapter();
		init_ble();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.array_adapter_item, menu);
		return true;
	}
	
	private BluetoothAdapter.LeScanCallback mLeScanCallback =
			new BluetoothAdapter.LeScanCallback() {	

		private ObjectItem parse_ibmsg(final byte[] ibmsg)
		{
			int start_str = 12; // start of uuid
			int indexUUID = 6;
			int indexMajor;
			int indexMinor;
			int minor_number = 0;
			int major_number = 0;
			byte[] b;
			StringBuilder sb = new StringBuilder();
			
			for (byte i : ibmsg)
				sb.append(String.format("%02x", i));

			start_str = sb.toString().indexOf("000215") + 6;
			indexUUID = start_str/2;
			indexMajor = indexUUID + 16;
			indexMinor = indexMajor + 2;
			
			String uuid = new String(sb.subSequence(start_str, start_str+32).toString());

			b = Arrays.copyOfRange(ibmsg, indexMajor, indexMajor + 2);
			int n = b[0];
			major_number = (n << 8) + b[1];

			b = Arrays.copyOfRange(ibmsg, indexMinor, indexMinor+2);
			n = b[0];
			minor_number = (n << 8) + b[1];

			return new ObjectItem(uuid, major_number, minor_number, 0);
		}
		
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] ibmsg) {
			Boolean found = false;
			ObjectItem o = parse_ibmsg(ibmsg);

			int i = 0;
			while(i < ObjectItemData.size()) {
				ObjectItem tmp = ObjectItemData.get(i);
				if ((tmp.uuid.equals(o.uuid)) && (tmp.major_number == o.major_number) &&
						(tmp.minor_number == o.minor_number)) {
//					ObjectItemData.remove(i);
					tmp.rssi = rssi;
					found = true;
					break;
				} 
				i++;
			}
			if (found == false) {
				ObjectItem oi = new ObjectItem(o.uuid, o.major_number, o.minor_number, rssi);
				ObjectItemData.add(oi);
			}
/*			adapter.clear();
			adapter.notifyDataSetChanged();
*/
		}
	};

	@Override
	protected void onPause()
	{
		super.onPause();
		// Ble stop scan
		mBluetoothAdapter.stopLeScan(mLeScanCallback);
	}
	
	private void setTimer()
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run()
			{
				adapter.notifyDataSetChanged();
				setTimer();
			}
		}, 1000);
	}
	
	@Override
	protected void onResume()
	{
		super.onRestart();
		// Start ble scan
		//Enable Bluetooth
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
 			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}

		setTimer();
		mBluetoothAdapter.startLeScan(mLeScanCallback);
	}
}
