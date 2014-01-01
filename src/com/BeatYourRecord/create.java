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
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class create extends Activity 
{
    ImageButton imageItem;
    int i=0;
    String k="";
    String k1="";
    String auth="";
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.v("hre","hre");
        setContentView(R.layout.create);
        ImageButton ctr = (ImageButton) findViewById(R.id.imageButton1);
        //PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        		   ctr.setOnClickListener(new View.OnClickListener() 
        		   {	
        				public void onClick(View v) 
        				{
        				    EditText e2 = (EditText) findViewById(R.id.editText0);
        			        String description = e2.getText().toString();
        			        EditText e23 = (EditText) findViewById(R.id.editText1);
        			        String name = e23.getText().toString();
        			        DefaultHttpClient client = new DefaultHttpClient();
        			    	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/suggestion");
        			    	JSONObject holder = new JSONObject();
        			    	JSONObject userObj = new JSONObject();
        			    	JSONObject userObj1 = new JSONObject();
        			    	JSONObject userObj2 = new JSONObject();
        			    	JSONObject userObj4 = new JSONObject();
        			    	String response = null;
        			    	JSONObject json = new JSONObject();
        			    	try {
        			    	    try {
        			    		        userObj.put("title", name);
        			    		        k = userObj.toString();
        			    		        k=k.substring(0, k.length()-1);
        			    		        k+=",";
        			    		       
        			    	        userObj2.put("content", description);
        			    	        k1 = userObj2.toString();
        			    	        k1=k1.substring(1, k1.length());
        			    	        k+=k1;	       
        			    	        Log.v("holder",k);
        			    	        StringEntity se = new StringEntity(k);
        			    	        post.setEntity(se);
        			    	        post.setHeader("Content-Type", "application/json");
        			    	        post.setHeader("Accept", "application/json");      			    	               			    	        
        			    	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        			    	        response = client.execute(post, responseHandler);
        			    	        json = new JSONObject(response);
        			    	        Toast toast = Toast.makeText(create.this, "Thanks for the game suggestion. We'll check it out!", Toast.LENGTH_SHORT);
        			                   toast.show();
        			                   Intent i1 = new Intent(create.this, CarouselDemoActivity.class);
        			                   startActivity(i1);
        			    	    } 
        			    	    catch (HttpResponseException e) 
        			    	    {	
        			    	    	
        			    	        e.printStackTrace();
        			    	        Log.e("ClientProtocol meet", "" + e);
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
  
					
  		     	 
					
          		
        		   }}