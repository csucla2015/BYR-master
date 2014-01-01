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
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TournamentPage1 extends Activity {
	 String test="";
    @Override
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
        TextView meet = (TextView) findViewById(R.id.terms_text);
        meet.setText(Html.fromHtml("By registering I agree to Beat Your Record's " +
                "<a href='http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/Home/TermsAndConditions/Page1'>Terms and conditions</a>" + " , " + "<a href='http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/Home/PrivacyPolicy/Page1'>Privacy Policy</a>"+" and "+"<a href='http://www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com/Home/PrivacyPolicy/Page1'>Rules</a>"));
        meet.setClickable(true);
        meet.setMovementMethod(LinkMovementMethod.getInstance());
      //  Button b = (Button) findViewById(R.id.submitButton);
        //Drawable defaultDrawable = findViewById(R.id.button1).getBackground();
        //defaultDrawable=b.getDrawable();
        
        //PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        
        //d.setColorFilter(filter);
     
       
     TextView ctr = (TextView) findViewById(R.id.link_to_register);
     ctr.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i1 = new Intent(getApplicationContext(), TournamentPage.class);
				startActivity(i1);
				
				
			}
		});

      b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 EditText e2 = (EditText) findViewById(R.id.editText9);
		         String str = e2.getText().toString();
		        Log.v("Value1",str);
		        EditText e3 = (EditText) findViewById(R.id.editText8);
		         String str1 = e3.getText().toString();
		        Log.v("Value1",str1);
		        EditText e4 = (EditText) findViewById(R.id.editText0);
		         String str2 = e4.getText().toString();
		        Log.v("Value1",str2);
		        
		        
		        
		        DefaultHttpClient client = new DefaultHttpClient();
				Log.v("here12","here");
				HttpPost post = new HttpPost("http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/api/v1/registrations/");
				JSONObject holder = new JSONObject();
				JSONObject userObj = new JSONObject();
				JSONObject userObj1 = new JSONObject();
				JSONObject userObj2 = new JSONObject();
				String response = null;
				JSONObject json = new JSONObject();

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
				        
					        userObj.put("email", str);
					        k = userObj.toString();
					        k=k.substring(0, k.length()-1);
					        k+=",";
					        userObj1.put("password", str1);
					        k1 += userObj1.toString();
					        k1=k1.substring(1, k1.length()-1);
					        k1+=",";
					        k+=k1;
				        userObj2.put("password_confirmation", str2);
				        k1 = userObj2.toString();
				        k1=k1.substring(1, k1.length());
				        k+=k1;
				       
				       
				      
				       
				       
				        Log.v("holder",k);
				        StringEntity se = new StringEntity(k);
				        post.setEntity(se);

				        // setup the request headers
				        post.setHeader("Content-Type", "application/json");
				        post.setHeader("Accept", "application/json");
				      //  post.setHeader("Content-Type", "application/json");

				        ResponseHandler<String> responseHandler = new BasicResponseHandler();
				        response = client.execute(post, responseHandler);
				        json = new JSONObject(response);
				        test = json.getJSONObject("data").getString("auth_token");
				        Log.v("test1",test);
				        JSONObject test2 = json.getJSONObject("data");
	        	        String test3 = test2.getJSONObject("user").getString("_id");
				        Log.v("test2",test3);
				        Log.v("rest2","ree");

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
				Intent i = new Intent(TournamentPage1.this, ReadJson2.class);
				//get.abort();
				Bundle b = new Bundle();
			
				b.putString("auth", test);
				
				i.putExtras(b);
				startActivity(i);
				
			}
		});
    }
}

















