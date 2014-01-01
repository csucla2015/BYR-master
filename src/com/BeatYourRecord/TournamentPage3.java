package com.BeatYourRecord;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class TournamentPage3 extends Activity {
	
    String login="";
    String password="";
    String test="";
    String test2="";
    String logout="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("hre","hre");
        File f4 = new File(
          		"/data/data/com.BeatYourRecord/shared_prefs/TourPref.xml");
          		if (f4.exists())
          		{	
          			
          			SharedPreferences pref17 = TournamentPage3.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
          			  Editor editor17 = pref17.edit();
          			  String page = pref17.getString("page",null);
          			  Log.v("know this",page);
          			  if(page.charAt(0)=='y')
          			  {	  
          	          logout = pref17.getString("log", null) ;
          			  Log.v("androids a bitch",logout);
          			  }
          	        
          		}
          		else
          		{	
          			Log.v("androids a bitch","das");
          		}	
                Log.v("hre","hre1");

        File f = new File(
          		"/data/data/com.BeatYourRecord/shared_prefs/Tester15.xml");
  				if (f.exists()==false )
          		{	 
          			Log.v("androids a bitch2","das");

          		}
          		else
          		{	
          			if(logout.equals("yes")==false)
          			{
          				Intent i1 = new Intent(TournamentPage3.this, HomePage1.class);	
            			startActivity(i1);
            			finish();
          			}	
          			
          			Log.v("androids a bitch2","das");
          		}	
		        setContentView(R.layout.homepage3);
		        SharedPreferences pref555 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
		    	Editor editor555 = pref555.edit();
		    	editor555.putString("page", "no");
		        editor555.putString("invalid", "");
		        editor555.commit();
      			Log.v("androids a bitch","das");

        
        
        TextView meet = (TextView) findViewById(R.id.terms_text);
        meet.setText(Html.fromHtml("By clicking play now or register I agree to Beat Your Record's " +
        		 "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/toc'><font color='#009900'>Terms and Conditions</font></a>" + " , " + "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/privacy'>Privacy Policy</a>"+" , "+"<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/detail_rules'>Rules</a>"+" , "+	"<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/contest_rules'> Contest Rules</a>"+" and "+ 		"<a href='http://www.youtube.com/static?template=terms'>YouTube's Terms and Conditions</a>"));
        meet.setClickable(true);
        meet.setMovementMethod(LinkMovementMethod.getInstance());
        Button login = (Button) findViewById(R.id.button2);
        Button how = (Button) findViewById(R.id.button4);
        Button register = (Button) findViewById(R.id.button1);
        
        login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 SharedPreferences pref177 = TournamentPage3.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	 			  Editor editor177 = pref177.edit();
	 	
	 			 editor177.putString("page", "yes") ;
	 	           editor177.putString("log", "yes") ;
	 	           editor177.commit();
				Intent i1 = new Intent(TournamentPage3.this, TournamentPage.class);
				
				
				startActivity(i1);
				
			}
		});
        how.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 SharedPreferences pref177 = TournamentPage3.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	 			  Editor editor177 = pref177.edit();
	 			 editor177.putString("page", "yes") ;
				Intent i1 = new Intent(TournamentPage3.this, VideoDemo1.class);
				startActivity(i1);
				
			}
		});
        register.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			 SharedPreferences pref177 = TournamentPage3.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
 			  Editor editor177 = pref177.edit();
 			 editor177.putString("page", "yes") ;
 	           editor177.putString("log", "yes") ;
 	          

 	           editor177.commit();
			Intent i1 = new Intent(TournamentPage3.this, TournamentPage4.class);
			
			
			startActivity(i1);
			
		}
	});
      
    }
}