package com.BeatYourRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ReadJson2 extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String [] ids = new String[20];
	String [] ids1 = new String[20];
	String [] ids2 = new String[20];
	String tourid;
	Button [] buttons = new Button[50];
	LinearLayout ll;
	LayoutParams lp;
	String login="";
	ImageView image;
	String output11="";
	String store="";
	int i=0;
	int forfor=0;
	String name1;
	String timelimit1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
			//differentthread4();
			differentthread5();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			
			Log.v("Well crashed", "well crasehed");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	
	
	void differentthread4() throws ClientProtocolException, IOException, JSONException
	{
	
		HttpGet get = new HttpGet("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments.json");

		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		String result = EntityUtils.toString(response.getEntity()); 
		//Log.v("Res",result);
		int ctr=0;
		i=0;
		for(;;)
		{	
		int index = result.indexOf("active");
		int index1 = result.indexOf("date_end");
		int index2 = result.indexOf("name");
		String quotes = Character.toString ((char) 34);
		ctr++;
		//Log.v("index",String.valueOf(index));
		if(index!=-1)
		{	
			String test = result.substring(index-27,index-3);
			//Log.v("test22",test);
			ids[i]=test;

		}
		if(index1!=-1)
		{	
			String test = result.substring(index1+11,index1+21);
			//Log.v("test22",test);
			ids1[i]=test;

		}
		if(index2!=-1)
		{	
			String test = result.substring(index2+6,index2+54);
			char c = quotes.charAt(0);
			test=test.substring(1);
			int myind = test.indexOf(c);
			test=test.substring(0,myind);
			//Log.v("myind",String.valueOf(myind));
			//Log.v("test22",test);
			ids2[i]=test;


		}
		result=result.substring(index+300);
		if(index==-1)
			break;
			i++;
		}
		forfor=0;
		for(;;)
		{	
			ctr--;
			if(ctr==0)
				break;
	        buttons[i] = new Button(this);
			Button myButton = new Button(this);
			buttons[i].setBackgroundResource(R.drawable.btn_black);
			buttons[i].setMaxLines(5);
			buttons[i].setTextSize(17);
			buttons[i].setText(ids2[forfor]+"\nEnd Date : "+ids1[forfor]);

		//myButton.setText(ids[forfor]);
			forfor++;
		
			buttons[i].setTextColor(Color.WHITE); 
			
			 ll = (LinearLayout)findViewById(R.id.meet);
			 lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			//ll.addView(myButton, lp);


			 ll.addView(buttons[i], lp);
			 //anotherfunction(buttons[i],forfor-1);
		}
///////////////////////////////////////////////////////////////////////////////////////////

			for(int i1=0;i1<i;i1++)
				Log.v("ids",ids[i1]);
	}

	void anotherfunction(Button b1, final ImageView image, final String forfor1,final String name, final String timelimit,final Boolean isprivate)
	{
		
			b1.setOnClickListener(new View.OnClickListener() {		
			public void onClick(View v) {
				 ll = (LinearLayout)findViewById(R.id.meet);
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				//ll.addView(myButton, lp);
				lp.gravity=Gravity.CENTER;
				
				addiamge(image);
			
		        
			        
			        /*
					if(isprivate==true)
					{	
						tourid=forfor1;
						name1=name;
						timelimit1=timelimit;
						Log.v("wegood","wegood");
					    showDialog(10);

					}	
					else
					{
				   Log.v("for",String.valueOf(forfor1));
				   Log.v("for",String.valueOf(name));
				   SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
		           Editor editor = pref.edit();
		           //Log.v("dsadsa","http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/"+ids[forfor1]+".json");
		           
		           editor.putString("tour", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/"+forfor1+"/retrieve"); // Storing string
		           editor.putString("id", forfor1); // Storing string
		           editor.putString("tournamentname", name);
		           editor.putString("time", timelimit);

		        // editor.putString("log", "yes"); 
		           editor.commit();
		           String print = pref.getString("tour",null);
		        	
		        	Log.v("Act123",print);
				    Intent i = new Intent(ReadJson2.this, Landing.class);
					startActivity(i);	
					}*/
			}
		});	
		
	}
	void addiamge(ImageView image)
	{
		 ll = (LinearLayout)findViewById(R.id.meet);
		 lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.addView(image);
	   // image.getLayoutParams().height = 150;
	    //image.getLayoutParams().width = 250;*/

	}
	void differentthread5()throws ClientProtocolException, IOException, JSONException
	{
		SharedPreferences pref17 = ReadJson2.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
		  Editor editor17 = pref17.edit();
		  String link = pref17.getString("CategoryLink",null);
		  Log.v("link",link);
		 DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
	        HttpGet httpget = new HttpGet(link);
	        // Depends on your web service
	        httpget.setHeader("Content-type", "application/json");
	        httpget.setHeader("Accept","application/json");	

	        InputStream inputStream = null;
	        String someresult = null;
	        try {
	            HttpResponse response = httpclient.execute(httpget);           
	            HttpEntity entity = response.getEntity();

	            inputStream = entity.getContent();
	            // json is UTF-8 by default
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
	            StringBuilder sb = new StringBuilder();

	            String line = null;
	            while ((line = reader.readLine()) != null)
	            {
	                sb.append(line + "\n");
	            }
	            someresult = sb.toString();
	        } catch (Exception e) { 
	            // Oops
	        }
	        finally {
	            try
	            {
	            	if(inputStream != null)inputStream.close();
	            }
	            catch(Exception squish)
	            {
	            	
	            }
	        }
	        


	        try {
				JSONObject jobject1 = new JSONObject(someresult);
				JSONObject jobject2 = jobject1.getJSONObject("data");
				JSONArray jarray = jobject2.getJSONArray("tournaments");
			
				for (int i=0; i < jarray.length(); i++)
				{
					JSONObject oneObject = jarray.getJSONObject(i);
			        //Log.v("oneObjectsItem", oneObject.toString());
			        String aJsonString = oneObject.getString("_id");
			        String time = oneObject.getString("time_limit");

			        String aJsonString1 = oneObject.getString("name");
			        String aJsonString2 = oneObject.getString("date_end");
			        Boolean isprivate = oneObject.getBoolean("private");
			        Log.v("isprivate",String.valueOf(isprivate));
			        aJsonString2 = aJsonString2.substring(0,10);
			        System.out.print("tesT"+aJsonString+"tesT1"+aJsonString1+"tesT2"+aJsonString2);
					Log.v("here",aJsonString+" "+aJsonString1+" "+aJsonString2);
					


					
					
					
					buttons[i] = new Button(this);

					Button myButton = new Button(this);
					buttons[i].setBackgroundResource(R.drawable.btn_black);
					buttons[i].setMaxLines(5);
					buttons[i].setTextSize(17);
					buttons[i].setText(aJsonString1+"\nEnd Date : "+aJsonString2);

					//myButton.setText(ids[forfor]);
				
					buttons[i].setTextColor(Color.WHITE);
					 ll = (LinearLayout)findViewById(R.id.meet);
					 lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					//ll.addView(myButton, lp);
					lp.gravity=Gravity.CENTER;
					

					image = new ImageView(this);
			        image.setBackgroundResource(R.drawable.firstsecondthird);
			        image.setLayoutParams(lp);
			        

					//ll.addView(image);
					ll.addView(buttons[i], lp);
				      image.getLayoutParams().height = 150;
				        image.getLayoutParams().width = 250;
				        
				        
					anotherfunction(buttons[i],image, aJsonString,aJsonString1,time,isprivate);
	
     //  JSONArray oneObject = jarray.getJSONArray(i);
				}
				
			
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    
	        
	}
	
	////////////////////FOR PRIVATE////////////////////////////
	@Override
	  protected Dialog onCreateDialog(int id) {
	    final Dialog dialog = new Dialog(ReadJson2.this);
	   
	    switch(id) {
	
	    case 10:
	    	 //dialog.setTitle("Terms of Service");
	      dialog.setContentView(R.layout.legal2);
	      dialog.setTitle("Password");
	      //TextView legalText = (TextView) dialog.findViewById(R.id.legal);
	      dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {

		      EditText e2 = (EditText) dialog.findViewById(R.id.editText1);
		      String str = e2.getText().toString();
		      Log.v("str",str);
		      SharedPreferences pref = ReadJson2.this.getSharedPreferences("Tester15", 0); // 0 - for private mode
	  	   	  Editor editor = pref.edit();
	  	      String auth = pref.getString("BYR_session", null);
	      
	  	      DefaultHttpClient client = new DefaultHttpClient();
		    	Log.v("here1233","here");
		    	Log.v("authtoke",auth);
		    	Log.v("ayth","http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/"+tourid+"/join?auth_token="+auth);
		    	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/"+tourid+"/join/private?auth_token="+auth);
		    	JSONObject holder = new JSONObject();
		    	JSONObject userObj1 = new JSONObject();
		    	JSONObject userObj2 = new JSONObject();
		    	String response;
		    	JSONObject json = new JSONObject();

		    	try {
		    	    try {
		    	     
		    	        json.put("success", false);
		    	        json.put("info", "Something went wrong. Retry!");
		    
		    	        String k="";
		    	        String k1="";
		    	        String k2="";
		    	       
		    		        userObj1.put("password", str);
		    		        k1 += userObj1.toString();
		    		        k1=k1.substring(1, k1.length());
		    		        
		    	      
		    	        k1 = "{" + k1;
		    	        Log.v("holder",k1);
		    	        StringEntity se = new StringEntity(k1);
		    	    post.setEntity(se);
		    	       
	    	        // setup the request headers
	    	     

			        // setup the request headers
			        post.setHeader("Content-Type", "application/json");
			        post.setHeader("Accept", "application/json");
			      //  post.setHeader("Content-Type", "application/json");
			        Log.v("test1","wearegere");
			        ResponseHandler<String> responseHandler = new BasicResponseHandler();
			        response = client.execute(post, responseHandler);
			        Log.v("response",response);
			        json = new JSONObject(response);
			        SharedPreferences pref1 = ReadJson2.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			  	   	  Editor editor1 = pref1.edit();
			        editor1.putString("tour", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/"+tourid+"/retrieve"); // Storing string
			           editor1.putString("id", tourid); // Storing string
			           editor1.putString("tournamentname", name1);
			           editor1.putString("time", timelimit1);

			           editor1.commit();
			          // String print = pref.getString("tour",null);
			        	//Log.v("Act1234id",tourid);
			        	//Log.v("Name",name1);

			        	//Log.v("Act1234",print);
					    Intent i = new Intent(ReadJson2.this, Landing.class);
						startActivity(i);	
				    
				    
	    	  

	    	    } 
	    	    catch (HttpResponseException e) 
	    	    {
	    	    	showDialog(10);
	    	        e.printStackTrace();
	    	        Log.e("ClientProtocol", "" + e);
	    	        json.put("info", "Email and/or password are invalid. Retry!");
	    	    } 
	    	    catch (IOException e) 
	    	    {
	    	        e.printStackTrace();
	    	        Log.e("IO", "" + e);
	    	    }
	    	} 
	    	catch (JSONException e) 
	    	{
	    	    e.printStackTrace();
	    	    Log.e("JSON", "" + e);
	    	}
	      }
				});
	      break;
	    }

	    return dialog;
	  }
	}
	
	
