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

public class leaderboard1 extends Activity {
	
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
        SharedPreferences pref = leaderboard1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	   	Editor editor = pref.edit();
	     	String link = pref.getString("tour", null);
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
        
        String wholetext = "1      "+ ids1 [0];
        
        TextView textview4 = (TextView) findViewById(R.id.textView4);
        textview4.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        textview4.setText(wholetext);
        
        TextView widget37 = (TextView) findViewById(R.id.widget37);
        widget37.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        widget37.setText(ids[0]);
        Log.v("this", ids3[0]);
        
        
        ImageView imageview2 = (ImageView) findViewById(R.id.imageView2);
        imageview2.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[0])));
		    }
		});
        
        wholetext = "2      "+ ids1 [1];
        TextView textview5 = (TextView) findViewById(R.id.textView5);
        textview5.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        textview5.setText(wholetext);
        
        TextView widget38 = (TextView) findViewById(R.id.widget38);
        widget38.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        widget38.setText(ids[1]);
        
        ImageView imageview3 = (ImageView) findViewById(R.id.imageView3);
        imageview3.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[1])));
		    }
		});
        
        wholetext = "3      "+ ids1 [2];
        
        TextView textview6 = (TextView) findViewById(R.id.textView6);
        textview6.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        textview6.setText(wholetext);
        
        TextView widget39 = (TextView) findViewById(R.id.widget39);
        widget39.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        widget39.setText(ids[2]);

        ImageView imageview4 = (ImageView) findViewById(R.id.imageView4);
        imageview4.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[2])));
		    }
		});
        
        TextView widget50 = (TextView) findViewById(R.id.widget50);
        widget50.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget50.setText("4      "+ ids1 [3] + "        " + ids[3]);
        widget50.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[3])));
		    }
		});
        
        TextView widget74 = (TextView) findViewById(R.id.widget74);
        widget74.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget74.setText("5      "+ ids1 [4] + "        " + ids[4]);
        widget74.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[4])));
		    }
		});
        
        TextView widget75 = (TextView) findViewById(R.id.widget75);
        widget75.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget75.setText("6      "+ ids1 [5] + "        " + ids[5]);
        widget75.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[5])));
		    }
		});
        
        TextView widget76 = (TextView) findViewById(R.id.widget76);
        widget76.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget76.setText("7      "+ ids1 [6] + "        " + ids[6]);
        widget76.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[6])));
		    }
		});
        
        TextView widget77 = (TextView) findViewById(R.id.widget77);
        widget77.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget77.setText("8      "+ ids1 [7] + "        " + ids[7]);
        widget77.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[7])));
		    }
		});
        
        TextView widget78 = (TextView) findViewById(R.id.widget78);
        widget78.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget78.setText("9      "+ ids1 [8] + "        " + ids[8]);
        widget78.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[8])));
		    }
		});
        
        TextView widget485 = (TextView) findViewById(R.id.widget485);
        widget485.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        widget485.setText("10      "+ ids1 [9] + "        " + ids[9]);
        widget485.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(ids3[9])));
		    }
		});
}
		
        
}

