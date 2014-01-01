package com.BeatYourRecord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaderBoard extends Activity {
	
	List<Cookie> cookies;
	String result="";
	String [] ids = new String[20];
	String [] ids1 = new String[20];
	String [] ids2 = new String[20];
	String [] ids3 = new String[20];
	Button [] buttons = new Button[50];

	String login="";
	String output11="";
	String store="";
	int count = 0;
	int count1 = 0;
	int count2 = 0;
	int count3 = 0;
    ImageButton imageItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("hre","hre");
        setContentView(R.layout.leader_test);
        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
 	   SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
       Editor editor = pref.edit();
       //Log.v("dsadsa","http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/"+ids[forfor1]+".json");
       
    
    // editor.putString("log", "yes"); 
       editor.commit();
       String print = pref.getString("tour",null);
        HttpGet httpget = new HttpGet(print);
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
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        Log.v("test1",someresult);
        try {
			JSONObject jobject1 = new JSONObject(someresult);
			JSONObject jobject2 = jobject1.getJSONObject("data");
			JSONObject jobject3 = jobject2.getJSONObject("tournament");
			JSONArray jarray = jobject3.getJSONArray("top_10");
			Log.v("here",String.valueOf(jarray.length()));
			for (int i=0; i < jarray.length(); i++)
			{
			    try {
					Log.v("ere","ere");
			        JSONArray oneObject = jarray.getJSONArray(i);
			       // JSONObject oneObject = oneObject1.getJSONObject(i);
			        // Pulling items from the array
			        Log.v("rerere","rere");
			        Log.v("oneObjectsItem", oneObject.toString());
			        String[] s =  oneObject.toString().split(",");
			        Log.v("finally",s[0].substring(1,s[0].length()));
			        Log.v("finally",s[1].substring(1,s[1].length()-1));
			        Log.v("finally",s[2].substring(1,s[2].length()-2));
			        ids1[i] = s[1].substring(1,s[1].length()-1);
			        ids[i] = s[0].substring(1,s[0].length());
			        ids2[i] = s[2].substring(1,s[2].length()-2);
			        if (s[3].length() > 20)
			        	ids3[i] = s[3].substring(1, 6) + s[3].substring(7, 8) + s[3].substring(9, 25) + s[3].substring(26, s[3].length() - 2);
			        else
			        	ids3[i] = s[3].substring(1, s[3].length() - 2);
			        count++;
			    } catch (JSONException e) {
			        //Log.v("rerere","rere");

			    }
			}
			
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        for(int ii=0;ii<count;ii++)
        {
        	Log.v("re"+String.valueOf(ii),ids[ii]);
        }
        for(int ii=0;ii<count;ii++)
        {
        	Log.v("re1"+String.valueOf(ii),ids1[ii]);
        }
        for(int ii=0;ii<count;ii++)
        {
        	Log.v("re2"+String.valueOf(ii),ids2[ii]);
        }
        
        for(int ii=0;ii<count;ii++)
        {
        	Log.v("re3"+String.valueOf(ii),ids3[ii]);
        }
        /*
        for(int i=0;i<ids1.length;i++)
        {
        	System.out.print(ids1[i]);
        }
        for(int i=0;i<ids2.length;i++)
        {
        	System.out.print(ids2[i]);
        }*/
        
        String wholetext = ids1 [0];
        
        SharedPreferences pref17 = LeaderBoard.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
		Editor editor17 = pref17.edit();
		String link = pref17.getString("tournamentname",null);
	    TextView textview02 = (TextView) findViewById(R.id.textView1);
	    textview02.setText(link);
        
        TextView textview4 = (TextView) findViewById(R.id.textView4);
        textview4.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        textview4.setText(wholetext);
        
        TextView widget37 = (TextView) findViewById(R.id.widget37);
        widget37.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        widget37.setText(ids[0]);
        Log.v("this", ids3[0]);
        
        
        ImageView imageview2 = (ImageView) findViewById(R.id.imageView2);
        imageview2.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[0])));
		    }
		});
        
        wholetext = ids1 [1];
        TextView textview5 = (TextView) findViewById(R.id.textView5);
        textview5.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        textview5.setText(wholetext);
        
        TextView widget38 = (TextView) findViewById(R.id.widget38);
        widget38.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        widget38.setText(ids[1]);
        
        ImageView imageview3 = (ImageView) findViewById(R.id.imageView3);
        imageview3.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[1])));
		    }
		});
        
        wholetext = ids1 [2];
        
        TextView textview6 = (TextView) findViewById(R.id.textView6);
        textview6.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        textview6.setText(wholetext);
        
        TextView widget39 = (TextView) findViewById(R.id.widget39);
        widget39.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        widget39.setText(ids[2]);

        ImageView imageview4 = (ImageView) findViewById(R.id.imageView4);
        imageview4.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[2])));
		    }
		});
        
        TextView widget50 = (TextView) findViewById(R.id.widget50);
        widget50.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget50.setText(ids1 [3]);
        
        TextView textView9 = (TextView) findViewById(R.id.textView9);
        textView9.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView9.setText(ids [3]);
        
        ImageView imageview8 = (ImageView) findViewById(R.id.imageView8);
        
        imageview8.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[3])));
		    }
		});
        
        TextView widget74 = (TextView) findViewById(R.id.widget74);
        widget74.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget74.setText(ids1 [4]);
        
        TextView textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView10.setText(ids [4]);
        
        ImageView imageview9 = (ImageView) findViewById(R.id.imageView9);
        
        imageview9.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[4])));
		    }
		});
        
        TextView widget75 = (TextView) findViewById(R.id.widget75);
        widget75.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget75.setText(ids1 [5]);
        
        TextView textView11 = (TextView) findViewById(R.id.textView11);
        textView11.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView11.setText(ids [5]);
        
        ImageView imageview10 = (ImageView) findViewById(R.id.imageView10);
        
        imageview10.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[5])));
		    }
		});        
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView8.setText(ids1 [6]);
        
        TextView textView12 = (TextView) findViewById(R.id.textView12);
        textView12.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView12.setText(ids [6]);
        
        ImageView imageview11 = (ImageView) findViewById(R.id.imageView11);
        
        imageview11.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[6])));
		    }
		});
        
        TextView widget78 = (TextView) findViewById(R.id.widget78);
        widget78.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget78.setText(ids1 [7]);
        
        TextView textView13 = (TextView) findViewById(R.id.textView13);
        textView13.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView13.setText(ids [7]);
        
        ImageView imageview12 = (ImageView) findViewById(R.id.imageView12);
        
        imageview12.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[7])));
		    }
		});
        
        TextView textView01 = (TextView) findViewById(R.id.TextView01);
        textView01.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView01.setText(ids1 [8]);
        
        TextView textView14 = (TextView) findViewById(R.id.textView14);
        textView14.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView14.setText(ids [8]);
        
        ImageView imageview13 = (ImageView) findViewById(R.id.imageView13);
        
        imageview13.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[8])));
		    }
		});
        
        TextView widget485 = (TextView) findViewById(R.id.widget485);
        widget485.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget485.setText(ids1 [9]);
        
        TextView textView15 = (TextView) findViewById(R.id.textView15);
        textView15.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView15.setText(ids [9]);
        
        ImageView imageview14 = (ImageView) findViewById(R.id.imageView14);
        
        imageview14.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[9])));
		    }
		});
        
        
        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
        Editor editor2 = pref2.edit();
        String authtoke = pref2.getString("BYR_session", null);
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
        Editor editor1 = pref1.edit();
        String id = pref1.getString("id", null);
        
        String authTokenLink = "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/users/profile?auth_token="+authtoke;
        
        DefaultHttpClient httpclient1 = new DefaultHttpClient(new BasicHttpParams());
        //HttpGet httpget1 = new HttpGet(authTokenLink);
        HttpGet httpget1 = new HttpGet(authTokenLink);
        // Depends on your web service
        httpget.setHeader("Content-type", "application/json");
        httpget.setHeader("Accept","application/json");	
        InputStream inputStream1 = null;
        String someresult1 = null;
        try {
            HttpResponse response = httpclient1.execute(httpget1);           
            HttpEntity entity = response.getEntity();
            inputStream1 = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream1, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            someresult1 = sb.toString();
        } catch (Exception e) { 
            // Oops
        }
        finally {
            try{if(inputStream1 != null)inputStream1.close();}catch(Exception squish){}
        }
        
        int maximumScore = 0;
        
        Log.v("test123;",someresult1);
        try {
			JSONObject jobject1 = new JSONObject(someresult1);
			JSONObject jobject2 = jobject1.getJSONObject("profile");
			JSONArray jarray = jobject2.getJSONArray("entries");
			Log.v("hereasdf",String.valueOf(jarray.length()));
			for (int i=0; i < jarray.length(); i++)
			{
				try
				{
					JSONObject oneObject = jarray.getJSONObject(i);
					int score = oneObject.getInt("score");
					String tournamentID = oneObject.getString("tournament_id");
					
					Log.v("score is", String.valueOf(score));
					Log.v("id is", id);
					Log.v("tournament ID is", tournamentID);
					if (tournamentID.equals(id)==true && maximumScore < score)
					{
						Log.v("heremax","max");
						maximumScore = score;
					}
			    }
				
				catch (JSONException e) {
			        //Log.v("rerere","rere");
			    }
			}
			
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.v("this is the maximum score", String.valueOf(maximumScore));
        TextView textview112 = (TextView) findViewById(R.id.textView17);
	    textview112.setText(String.valueOf(maximumScore));
	    ImageView imageview22 = (ImageView) findViewById(R.id.imageView22);
        imageview22.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[0])));
		    }
		});
        
        
}
		
        
}
