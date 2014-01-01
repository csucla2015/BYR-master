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

import com.BeatYourRecord.db.DbHelper;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Landing extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String login="";
	String output11="";
	String weather="";
	String store="";
	String name="";
	ImageButton next;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Bundle b = getIntent().getExtras();
		//login = b.getString("auth");
		//Log.v("Authhere",login);
	   // SharedPreferences pref = getApplicationContext().getSharedPreferences("BYRPref", 0); // 0 - for private mode
       //	Editor editor = pref.edit();
       	
      
       //	login = pref.getString("BYR_session", null);
       	Log.v("login using shared pregs",login);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing);
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
		  // SharedPreferences pref = getApplicationContext().getSharedPreferences("BYRPref", 0); // 0 - for private mode
	       //	Editor editor = pref.edit();
	       	
			
			//SharedPreferences pref = Landing.this.getSharedPreferences("TourPref", 0);
			//String category = pref.getString("CategoryName", null);
			
	       //	login = pref.getString("BYR_session", null);
	       	Log.v("login using shared pregs",login);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.landing);
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
			SharedPreferences pref = Landing.this.getSharedPreferences("TourPref", 0);
			String category = pref.getString("CategoryName", null);
			next = (ImageButton) findViewById(R.id.rules);
			//Button b2 = (Button) findViewById(R.id.button2);
			ImageView v1 = (ImageView) findViewById(R.id.widget124);
			if (category == "Beerpong")
			{
				v1.setImageDrawable(getResources().getDrawable(R.drawable.bn_pong));
			}
			
			else if (category == "Darts")
			{
				v1.setImageDrawable(getResources().getDrawable(R.drawable.bn_darts));
			}
			
			else if (category == "Freethrows")
			{
				v1.setImageDrawable(getResources().getDrawable(R.drawable.bn_basketball));
			}
			
			else if (category == "Quarters")
			{
				v1.setImageDrawable(getResources().getDrawable(R.drawable.bn_quarters));
			}
			
			else
			{
				v1.setImageDrawable(getResources().getDrawable(R.drawable.bn_basketball02));
			}
		next = (ImageButton) findViewById(R.id.rules);
		//Button b2 = (Button) findViewById(R.id.button2);
		ImageButton b3 = (ImageButton) findViewById(R.id.leaderboard);
		ImageButton b4 = (ImageButton) findViewById(R.id.record);
	     SharedPreferences pref17 = Landing.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			  Editor editor17 = pref17.edit();
			  String link = pref17.getString("tournamentname",null);
			  Log.v("name", link);
		      TextView textview1 = (TextView) findViewById(R.id.textView1);
		      textview1.setText(link);
		b4.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
				    showDialog(10);
				    Log.v("here","here");

					
					
				}
			});
		 /* b2.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					 Intent i1 = new Intent(Landing.this, ReadJson3.class);
						
						
						startActivity(i1);

					
					
				}
			});*/
		  b3.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					 Intent i1 = new Intent(Landing.this, LeaderBoard.class);
						
						
						startActivity(i1);
						
					
					
				}
			});
		  next.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					SharedPreferences pref = Landing.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
				   	Editor editor = pref.edit();
			     	String name = pref.getString("CategoryName", null);
			     	String name1="";
			     	for(int i=0;i<name.length();i++)
			     	{
			     		if(name.charAt(i)!=' ')
			     		name1+=String.valueOf(name.charAt(i));
			     	}
			     	name=name1;
					String link = "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/rules/"+name.toLowerCase()+"/summary";
					   Uri uriUrl = Uri.parse(link);
				        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				        startActivity(launchBrowser);
					       

					
					
				}
			});
		  findViewById(R.id.prizesupload).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_PICK);
		        intent.setType("video/*");

		        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
		            PackageManager.MATCH_DEFAULT_ONLY);
		        if (list.size() <= 0) {
		          return;
		        }
		        startActivityForResult(intent, 0);
		      }
		    });
		  	
		  
		  findViewById(R.id.text).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		          Intent i1 = new Intent(Landing.this, SendSMS.class);
					
		     	 
					startActivity(i1);
		      }
		    });
		
		
	}
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		    super.onActivityResult(requestCode, resultCode, data);
		//Changed by Meet
		    switch (requestCode) {
		    case 0:
		    case 1:
		      if (resultCode == RESULT_OK) {
		        Intent intent = new Intent(Landing.this, SubmitActivity.class);
				
				
				
		        intent.setData(data.getData());
		        intent.putExtra(DbHelper.YTD_DOMAIN, "string");
		        startActivityForResult(intent,3);

		      }
		      break;
		    }
		  }
	@Override
	  protected Dialog onCreateDialog(int id) {
	    final Dialog dialog = new Dialog(Landing.this);
	   
	    switch(id) {
	    case 11:
	        // Create out AlterDialog
	      /*  Builder builder = new AlertDialog.Builder(this);
	        dialog4.setContentView(R.layout.legal1);
	        builder.setMessage();
	        builder.setCancelable(true);
	      
	        AlertDialog dialog4 = builder.create();
	        dialog4.show();
	        return super.onCreateDialog(id);*/
	    	
	    	dialog.setContentView(R.layout.okbutton);

	        TextView legalText1 = (TextView) dialog.findViewById(R.id.legal);

	        legalText1.setText("Please Login using your email and password. If you have already registered, please click on sign in to proceed. ");

	        dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v)
	          {
	        	  dialog.cancel();
	          }
	    	
	        });
	    	break;
	    	
	    	
	        //break;
	    case 10:
	    	 //dialog.setTitle("Terms of Service");
	      dialog.setContentView(R.layout.legal1);
	      dialog.setTitle("How it Works");
	      TextView legalText = (TextView) dialog.findViewById(R.id.legal);
	      SharedPreferences pref = Landing.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
		   	Editor editor = pref.edit();
	     	 name = pref.getString("CategoryName", null);
	     	Log.v("namec",name);
	     	if(name.equals("Darts")==true)
		     	legalText.setText(Util.readFile(this, R.raw.darts).toString());
	     	else if(name.equals("Quarters"))
		     	legalText.setText(Util.readFile(this, R.raw.quarters).toString());
	     	else if(name.equals("Beerpong"))
		     	legalText.setText(Util.readFile(this, R.raw.beerpong).toString());
	     	else if(name.equals("Bestshots"))
		     	legalText.setText(Util.readFile(this, R.raw.bestshots).toString());
	     	
	     	else
	     	legalText.setText(Util.readFile(this, R.raw.legal1).toString());

	      dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	          dialog.cancel();
	          
	         SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
	        	Editor editor = pref.edit();
	        // editor.putString("log", "yes");
	          
	         Log.v("ehre in json3", " here in json3");
	          Intent i1 = new Intent(Landing.this, AndroidVideoCapture.class);
				
	 
				startActivity(i1);
	          ///////////////////////////////////////////////////////////////////////
	          File f = new File(
	          		"/data/data/com.BeatYourRecord/shared_prefs/Tester13.xml");
	          		if (f.exists())
	          		{	 
	          			Log.v("androids a bitch","das");
	          		}
	          		else
	          		{	
	          			Log.v("androids a bitch12","das");
	          		}	 
	           	

	        }
	      });
	      if(name.equals("Freethrows"))
		     { 
	      dialog.findViewById(R.id.agree1).setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 Intent i1 = new Intent(Landing.this, VideoDemo2.class);
						
		        	 
						startActivity(i1);
		          dialog.cancel();
		        }});
		     }
	      else
	    	  dialog.findViewById(R.id.agree1).setEnabled(false);
	      break;
	    case 12:
	    	 //dialog.setTitle("Terms of Service");
	      dialog.setContentView(R.layout.legal1);
	      dialog.setTitle(weather);
	      
	 
	      
	      TextView legalText2 = (TextView) dialog.findViewById(R.id.legal);

	      legalText2.setText(Util.readFile(this, R.raw.legal1).toString());

	      dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	          dialog.cancel();
	          
	         SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
	        	Editor editor = pref.edit();
	        // editor.putString("log", "yes");
	          
	         Log.v("ehre in json3", " here in json3");
	          Intent i1 = new Intent(Landing.this, AndroidVideoCapture.class);
				
	 
				startActivity(i1);
	          ///////////////////////////////////////////////////////////////////////
	          File f = new File(
	          		"/data/data/com.BeatYourRecord/shared_prefs/Tester13.xml");
	          		if (f.exists())
	          		{	 
	          			Log.v("androids a bitch","das");
	          		}
	          		else
	          		{	
	          			Log.v("androids a bitch12","das");
	          		}	 
	           	

	        }
	      });
	
	      Log.v("whatcat",name);
	     if(name.equals("Freethrows"))
	     { 
	      dialog.findViewById(R.id.agree1).setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	 Intent i1 = new Intent(Landing.this, VideoDemo2.class);
						
		        	 
						startActivity(i1);
		          dialog.cancel();
		        }});}
	     else
	    	 dialog.findViewById(R.id.agree1).setEnabled(false);
	      break;
	    }

	    return dialog;
	  }

	void differentthread() throws ClientProtocolException, IOException, JSONException
	{
		DefaultHttpClient client = new DefaultHttpClient();
		Log.v("here12","here");
		//finish();

		HttpGet get = new HttpGet("http://api.wunderground.com/api/4a0bffdd92bdd2ee/forecast/q/US/MA/Boston.json");
		String response = null;
		JSONObject json = new JSONObject();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		 response = client.execute(get,responseHandler);
	        json = new JSONObject(response);
	       
	        
	        JSONObject test = json.getJSONObject("forecast");
	        JSONObject test1 = test.getJSONObject("simpleforecast");
	        JSONArray test3 = test1.getJSONArray("forecastday");
	        JSONObject test4 = test3.getJSONObject(0);
	        String finalllys = test4.getString("conditions");
	        JSONObject test5 = test4.getJSONObject("date");
	        weather = test5.getString("pretty");
		   // showDialog(12);

	       // String test2 = test1.getString("date");
	       

	        Log.v("test",finalllys);
	}     
	
}