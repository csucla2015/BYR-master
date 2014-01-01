package com.BeatYourRecord;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TournamentPage2 extends Activity {
	
    String login="";
    String password="";
    String test="";
    String test2="";

   
   	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        SharedPreferences pref11 = TournamentPage2.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
       	Editor editor11 = pref11.edit();
         	String invalid = pref11.getString("invalid", null);
      //  SharedPreferences pref = TournamentPage.this.getSharedPreferences("MyPref469", 0); // 0 - for private mode
      // 	Editor editor = pref.edit();
       //	editor.putString("BYR_session", "e7vr4VAtroysvfkzDGJt"); // Storing string
       //	editor.commit();
       //	String checksession = pref.getString("BYR_session", null);
      // 	boolean cont = pref.contains("BYR_session");
       //	Log.v("checksession", checksession);
            TextView ctr5 = (TextView) findViewById(R.id.textView1);
            ctr5.setText(invalid);

        ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
      //  Button b = (Button) findViewById(R.id.submitButton);
        //Drawable defaultDrawable = findViewById(R.id.button1).getBackground();
        //defaultDrawable=b.getDrawable();
        TextView ctr = (TextView) findViewById(R.id.link_to_register);
        //PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        		   ctr.setOnClickListener(new View.OnClickListener() {
        				
        				public void onClick(View v) {
        					// Switching to Register screen
        					Intent i12 = new Intent(getApplicationContext(), TournamentPage1.class);
        					startActivity(i12);
        					
        					
        				}
        			});
        //d.setColorFilter(filter);
        			
     
      b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 EditText e2 = (EditText) findViewById(R.id.editText1);
		         String  m_login = e2.getText().toString();
		        Log.v("Value1",m_login);
		        EditText e3 = (EditText) findViewById(R.id.editText2);
		        String m_password = e3.getText().toString();
		        Log.v("Value1",m_password);
				DefaultHttpClient client = new DefaultHttpClient();
				Log.v("here12","here");
				HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/sessions");
				JSONObject holder = new JSONObject();
				JSONObject userObj = new JSONObject();
				JSONObject userObj1 = new JSONObject();
				JSONObject userObj2 = new JSONObject();
				String response = null;
				JSONObject json = new JSONObject();
				 SharedPreferences pref6 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
		        	Editor editor6 = pref6.edit();
		         editor6.putString("Username", m_login); 
		        	editor6.commit();
				try {
				    try {
				        // setup the returned values in case
				        // something goes wrong
				        json.put("success", false);
				        json.put("info", "Something went wrong. Retry!");
				        // add the user email and password to
				        // the params
				        String k="";
				        String k1="";
				        String k2="";
				        
				            
					        userObj.put("login", m_login);
					        holder.put("user", userObj);
					        k = holder.toString();
					        k=k.substring(0, k.length()-2);
					        k+=",";
					        userObj1.put("password", m_password);
					        k1 += userObj1.toString();
					        k1=k1.substring(1, k1.length());
					        
					        k+=k1;
					        k+="}";
					        
				      
				       
					        
				        Log.v("holder",k);
				        StringEntity se = new StringEntity(k);
				        post.setEntity(se);

				        // setup the request headers
				        post.setHeader("Content-Type", "application/json");
				        post.setHeader("Accept", "application/json");
				      //  post.setHeader("Content-Type", "application/json");
				        Log.v("test1","wearegere");
				        ResponseHandler<String> responseHandler = new BasicResponseHandler();
				        response = client.execute(post, responseHandler);
				        Log.v("response",response);
				        json = new JSONObject(response);
				       test2 = json.getString("success");
				        Log.v("dsadasdsa",test2);
				     
				         test = json.getJSONObject("data").getString("auth_token");
				         SharedPreferences pref = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
				        	Editor editor = pref.edit();
				         editor.putString("BYR_session", test); // Storing string
				        	editor.commit();
				        	SharedPreferences pref1 = TournamentPage2.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
						   	Editor editor1 = pref1.edit();
				        // editor.putString("tour", "http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
				         //editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
				        editor1.putString("log", "no"); 
				         editor1.commit();
				        Log.v("test1",test);

				    } 
				    catch (HttpResponseException e) 
				    {
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
				
				// Switching to Register screen
				 if(test2=="false")
			        {
			        	Intent i1 = new Intent(TournamentPage2.this, TournamentPage.class);
					//get.abort();
				
					startActivity(i1);
					finish();
			        }
				 Log.v("here",test2);
				Intent i = new Intent(TournamentPage2.this, HomePage.class);
				//get.abort();
			
				startActivity(i);
				
			}
		});
    }
}