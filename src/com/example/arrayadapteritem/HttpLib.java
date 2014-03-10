package com.example.arrayadapteritem;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class HttpLib {
	private static String ip = "128.97.93.163";
	private static String port = "80";
	private  conn con;
	private httpcon h;
	private static String info = null;
	private static String tag = "arrayadpater";
	
	public HttpLib(HashMap<String, String> header) {
		Log.i(tag, "ip:" + ip);
		con = new conn("http://" + ip + ":" + port, header);
		h = new httpcon();
	}

	private static class conn {
		private String url;
		public String method;
		private HashMap<String, String> headers;
		
		public conn(String url, HashMap<String, String> header)
		{
			this.url = new String(url);
			if (header != null)
				this.headers = new HashMap<String, String>(header);
			else {
				Log.i(tag, "header is null");
				this.headers = null;
			}
		}
	}

	private static class httpcon extends AsyncTask<conn, Integer, Long>
	{
		private HttpURLConnection con;

		private void init(String _url, String method) throws Exception
		{
			con = (HttpURLConnection) new URL(_url).openConnection();
			con.setRequestMethod(method);
		}
		
		@Override
		protected Long doInBackground(conn... params) {
			try {
				init(params[0].url, params[0].method);
				if (params[0].method.equals("GET")) {
					Log.i(tag, "url" + params[0].url + " get");
					HttpLib.info = receivePacket(params[0].headers);
				} else {
					Log.i(tag, "url" + params[0].url + " post");
					HttpLib.info = sendpacket(params[0].headers);
				}
			} catch (Exception e) {
				Log.i(tag, "Exception received" + e.toString());
			}
			return 0l;
		}

		public String receivePacket(HashMap<String, String> headers) throws Exception
		{
			for(Map.Entry<String, String> entry : headers.entrySet())
				con.setRequestProperty(entry.getKey(), entry.getValue());
			Log.i(tag, "receivePacket");
			con.setDoInput(true);
			Log.i(tag, "response: " + con.getResponseCode());
			
			for (Map.Entry<String, List<String>> entry : con.getHeaderFields().entrySet()) {
				if (entry.getKey() != null) {
					Log.i(tag, entry.getKey() + ":" + entry.getValue());
					if (entry.getKey().equals(new String("info"))) {
//						Boolean is = new Boolean(entry.getKey().contentEquals("info"));
						Log.i(tag, "info value:" + entry.getValue());
						return new String(entry.getValue().get(0).toString());
					}
				}
			}
			return null;
		}

		public String sendpacket(HashMap<String, String> headers) throws Exception
		{
			for(Map.Entry<String, String> entry : headers.entrySet())
				con.setRequestProperty(entry.getKey(), entry.getValue());
			Log.i(tag, "sendPacket");
			con.setDoInput(true);
			con.setDoOutput(true);
			Log.i(tag, "response: " + con.getResponseCode());
			
			for (Map.Entry<String, List<String>> entry : con.getHeaderFields().entrySet()) {
				if (entry.getKey() != null) {
					Log.i(tag, entry.getKey() + ":" + entry.getValue());
					if (entry.getKey().equals(new String("info"))) {
//						Boolean is = new Boolean(entry.getKey().contentEquals("info"));
						Log.i(tag, "info value:" + entry.getValue());
						return new String(entry.getValue().get(0).toString());
					}
				}
			}
			return null;
		}
	}
	
	public String POST() throws Exception
	{
		Log.i(tag, "POST");
		con.method = "POST";
		h.execute(con);
		return info;
	}
	public String GET() throws Exception
	{
		Log.i(tag, "GET");
		con.method = "GET";
		h.execute(con);
		return info;
	}
}
