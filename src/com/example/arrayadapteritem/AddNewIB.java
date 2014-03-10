package com.example.arrayadapteritem;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewIB  extends Activity {
	private static String tag = "arrayadapter";
	EditText uuid_view;
	EditText major_num_view;
	EditText minor_num_view;
	String uuid;
	int major_num;
	int minor_num;
	static AddNewIB ib;
	static private class ConfirmPackage extends DialogFragment {
		AddNewIB ib;
		public ConfirmPackage()
		{
			this.ib = AddNewIB.ib;
		}
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("")                                                                                                            
                   .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                	   public void onClick(DialogInterface dialog, int id) {
                		   try {
                		        HttpLib lib = new HttpLib(new HashMap<String, String>(){
                		        	{put("id",ib.uuid+"-"+String.valueOf(ib.major_num)+"-"+String.valueOf(ib.minor_num));
                		        	 put("content", "Registered");}});
                		        String info = lib.POST();
                		    } catch (Exception e) {
                		    	Log.i("asdf", e.toString());
                		    }
                           getActivity().finish();
                       }
                   })
                   .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_ble);
		ib = this;
		uuid_view = (EditText) findViewById(R.id.uuid);
		major_num_view = (EditText) findViewById(R.id.major_number);
		minor_num_view = (EditText) findViewById(R.id.minor_number);
		Button send = (Button)	findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				uuid = uuid_view.getText().toString();
				major_num = Integer.parseInt(major_num_view.getText().toString());
				minor_num = Integer.parseInt(minor_num_view.getText().toString());
				if(uuid.length() != 32) {
					Log.i(tag, "uuid length: " + uuid.length());
					uuid_view.setText("");
				} else if (major_num < 0 || major_num > 16635) {
					major_num_view.setText("");
				} else if (minor_num < 0 || minor_num > 16635) {
					minor_num_view.setText("");
				} else {
					ConfirmPackage cp = new ConfirmPackage();
					cp.show(getFragmentManager(), "uuid");
				}
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.array_adapter_item, menu);
		return true;
	}
}
