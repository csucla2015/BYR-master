package com.BeatYourRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ReadJson4 extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String login="";
	String output11="";
	String store="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Bundle b = getIntent().getExtras();
		//login = b.getString("auth");
		//Log.v("Authhere",login);
		boolean haveConnectedWifi=false;
		boolean haveConnectedMobile=false;
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		               haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		  if (haveConnectedWifi==false &&  haveConnectedMobile==false) {
			  Toast.makeText(getApplicationContext(), 
                      "Please connect to the internet to continue", Toast.LENGTH_LONG).show();
		  }
       //	login = pref.getString("BYR_session", null);
       	Log.v("login using shared pregs",login);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json);
		
		//HttpClient httpclient = new DefaultHttpClient();
	
		try {
			Button tr1 = (Button) findViewById(R.id.button7);
			Button tr2 = (Button) findViewById(R.id.button6);
			differentthread1("http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json",tr2);
			Log.v("Sent","sent");
			differentthread1("http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments.json",tr1);
			differentthread();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			Button tr1 = (Button) findViewById(R.id.button7);
			Button tr2 = (Button) findViewById(R.id.button6);

			//get.abort();
			tr2.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					
					    			 
					    			SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
						        	Editor editor = pref.edit();
						         editor.putString("tour", "http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
						         editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
						        // editor.putString("log", "yes"); 
						         editor.commit();
						        	String print = pref.getString("tour",null);
						        	
						        	Log.v("Act1",print);
								Intent i = new Intent(ReadJson4.this, Landing.class);
									startActivity(i);
									

					    		
					    		
				}
			});	
	
			    		
		tr1.setOnClickListener(new View.OnClickListener() {
			    			
			    			public void onClick(View v) {
			    				
			    				SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
					        	Editor editor = pref.edit();
					         editor.putString("tour", "http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/520192dd1a59327bfe000002.json"); // Storing string
					         editor.putString("id", "520192dd1a59327bfe000002"); // Storing string

					        	editor.commit();
					        	String print = pref.getString("tour",null);
					        	
					        	Log.v("Act1",print);
				    			Intent i = new Intent(ReadJson4.this, Landing.class);
								startActivity(i);
			    				    	
			    			}
			    		});		
			    
			Log.v("Well crashed", "well crasehed");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	public void differentthread() throws ClientProtocolException, IOException, JSONException
	{
		DefaultHttpClient client = new DefaultHttpClient();
		Log.v("here12","here");
		HttpGet get = new HttpGet("http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments.json");
		String response = null;
		JSONObject json = new JSONObject();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

		 response = client.execute(get,responseHandler);
		 
	        json = new JSONObject(response);
	        String test = json.getString("name");


		 response = client.execute(get,responseHandler);
		//Button b = (Button) findViewById(R.id.btnLogin);
		Button tr1 = (Button) findViewById(R.id.button7);
		Button tr2 = (Button) findViewById(R.id.button6);

		//get.abort();
		tr1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				   File f = new File(
				    		"/data/data/com.google.ytd/shared_prefs/Tester12.xml");
				    		if (f.exists())
				    		{	 
							Intent i = new Intent(ReadJson4.this, ReadJson3.class);
								startActivity(i);

				    		}
				    		else
				    		{	
				    			Intent i = new Intent(ReadJson4.this, ReadJson3.class);
								startActivity(i);
				    		}
			}
		});	


		
		
		
		
	
		
		    		
		    		
	tr2.setOnClickListener(new View.OnClickListener() {
		    			
		    			public void onClick(View v) {
		    				
		    				   File f = new File(
		    				    		"/data/data/com.google.ytd/shared_prefs/Tester12.xml");
		    				    		if (f.exists())
		    				    		{	 
		    							Intent i = new Intent(ReadJson4.this, ReadJson3.class);
		    								startActivity(i);

		    				    		}
		    				    		else
		    				    		{	
		    				    			Intent i = new Intent(ReadJson4.this, ReadJson3.class);
		    								startActivity(i);
		    				    		}
		    			}
		    		});		
		    
		    
	
	}	
		
	void differentthread1(String url,Button tr2) throws ClientProtocolException, IOException, JSONException
	{
		DefaultHttpClient client = new DefaultHttpClient();
		Log.v("here12","here");

		HttpGet get = new HttpGet(url);
		String response = null;
		JSONObject json = new JSONObject();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

		 response = client.execute(get,responseHandler);
		 
	        json = new JSONObject(response);
	       
	        
	        String test = json.getString("date_id");
	        String name = json.getString("name");

	        tr2.setText(name+"\nMost Baskets in 1 minute\nPrize : $200 \nEnd Date : "+test.substring(0,10));

	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	       Log.v("test123",test);
///////////////////////////////////////////////////////////////////////////////////////////
	      
		
		
		
		
		
		
	}
		

	}
	
	
