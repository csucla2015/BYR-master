package com.BeatYourRecord;

import java.io.BufferedReader;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReadJson3 extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String login="";
	String output11="";
	String store="";
	Button next;
	String link ="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Bundle b = getIntent().getExtras();
		//login = b.getString("auth");
		//Log.v("Authhere",login);
	  //  SharedPreferences pref = getApplicationContext().getSharedPreferences("BYRPref", 0); 
      // 	Editor editor = pref.edit();
        SharedPreferences pref = ReadJson3.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	   	Editor editor = pref.edit();
	     	link = pref.getString("tour", null);
	     	Log.v("link",link);
      
       //	login = pref.getString("BYR_session", null);
       	Log.v("login using shared pregs",login);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tournamentdetails);
		Button totalhigh2 = (Button) findViewById(R.id.button7);
		Button totalhigh3 = (Button) findViewById(R.id.button8);
		
		next = (Button) findViewById(R.id.button1);
		try {
			differentthread();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  next.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent i1 = new Intent(ReadJson3.this, AndroidVideoCapture.class);
					startActivity(i1);
					
				}
			});

	}
	
	void differentthread() throws ClientProtocolException, IOException, JSONException
	{
		DefaultHttpClient client = new DefaultHttpClient();
		Log.v("here12","here");

		HttpGet get = new HttpGet(link);
		String response = null;
		JSONObject json = new JSONObject();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		 response = client.execute(get,responseHandler);
	        json = new JSONObject(response);
	        JSONArray arr = json.getJSONArray("score_cum");
	       
	        
	        String test = json.getString("score_cum");
	        String arr1[]= new String[3];
	        stringparser(arr1,test);
			Button totalhigh = (Button) findViewById(R.id.button7);
			String c1  =Character.toString ((char) 34);
			char c =c1.charAt(0);
			Log.v("c",c1);
			for(int i=0;i<arr1.length;i++)
			{
				String t = arr1[i];
				int t1 =  t.indexOf(",");
				String t2 = t.substring(0,t1) +" "+ t.substring(t1+2,t.length()-1);
				t1 = t.indexOf("@");
				if(t1>0)
				t2=t2.substring(0,t1-1);
				arr1[i]=t2;
			}
			totalhigh.setText("          Most Cumulative Free Throws\n                      " +
					"" +
					"" +
					"1st   = "+arr1[0]+"\n                      2nd = "+arr1[1]+"\n                      3rd  = "+arr1[2]);
			
			
			arr = json.getJSONArray("score_high");
	       
	        
	        test = json.getString("score_high");
			  Log.v("testlo",test);

	        stringparser(arr1,test);
			Button totalhigh1 = (Button) findViewById(R.id.button6);
			for(int i=0;i<arr1.length;i++)
			{
				String t = arr1[i];
				int t1 =  t.indexOf(",");
				String t2 = t.substring(0,t1) +" "+ t.substring(t1+2,t.length()-1);
				t1 = t.indexOf("@");
				if(t1>0)
				t2=t2.substring(0,t1-1);
				arr1[i]=t2;
			}
			totalhigh1.setText("          Most Free Throws in a Minute \n                      1st   = "+arr1[0]+"\n                      2nd = "+arr1[1]+"\n                      3rd  = "+arr1[2]);
			
			 test = json.getString("score_today");
			 
			//  test=test.substring(14,test.length()-1);
			  Log.v("testlo",test);
		       stringparser(arr1,test);
				Button totalhigh2 = (Button) findViewById(R.id.button8);
				for(int i=0;i<arr1.length;i++)
				{
					String t = arr1[i];
					int t1 =  t.indexOf(",");
					String t2 = t.substring(0,t1) +" "+ t.substring(t1+2,t.length()-1);
					t1 = t.indexOf("@");
					if(t1>0)
					t2=t2.substring(0,t1-1);
					arr1[i]=t2;
				}
				totalhigh2.setText("               Most Free Throws Daily \n                     1st   = "+arr1[0]+"\n                     2nd = "+arr1[1]+"\n                     3rd  = "+arr1[2]);
///////////////////////////////////////////////////////////////////////////////////////////
	      
		
		
		
		
		
		
	}
	void stringparser(String arr1[], String test1)
	{
		String test = test1;
		  int index = test.indexOf("[");
	        int index1 = test.indexOf("]");		
	        String highest = test.substring(index+2,index1);
	        Log.v("Score cum",highest);
	        arr1[0]=highest;
	        test=test.substring(index1);
	        index1=test.indexOf(",");
	        test=test.substring(index1+1);
	        index = test.indexOf("[");
	        index1 = test.indexOf("]");		
	        highest = test.substring(index+1,index1);
	        arr1[1]=highest;
	        Log.v("Score cum",highest);
	        test=test.substring(index1);
	        index1=test.indexOf(",");
	        test=test.substring(index1+1);
	        index = test.indexOf("[");
	        index1 = test.indexOf("]");		
	        highest = test.substring(index+1,index1);
	        Log.v("Score cum",highest);
	        arr1[2]=highest;
	}
}