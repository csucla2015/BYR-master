package com.example.tournamentpage;

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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReadJson1 extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json1);
		Bundle b = getIntent().getExtras();
		String login = b.getString("login");
		String password = b.getString("password");
		int id = b.getInt("id");
		//HttpClient httpclient = new DefaultHttpClient();
		String result="";
		String output11="";
		String store="";
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 
		    HttpPost httpGet = new HttpPost("http://www.beatyourrecord.com/Services/Tournaments/Login/");//	"http://www.beatyourrecord.com/Services/Tournaments/Login?login=julia&password=1111");
		try {
		   
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Login", "julia"));
            params.add(new BasicNameValuePair("password", "1111"));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            httpGet.setEntity(ent);
		    HttpResponse httpResponse = httpClient.execute(httpGet);
		    HttpEntity httpEntity = httpResponse.getEntity();
		    String output = EntityUtils.toString(httpEntity);
		
			result=output;
			result=result.substring(30);
			int k = result.indexOf('"');
			result = result.substring(k+1);
			k=result.indexOf('"');
			result=result.substring(0,k);
		    System.out.println("Initial set of cookies:");
	        cookies = httpClient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }
	        String test = "http://www.beatyourrecord.com/Services/Tournaments/GetTournament/?tournamentId="+String.valueOf(id)+"&sessionId="+result;

		
			try {
				
				CookieStore cookieStore = new BasicCookieStore();
				cookieStore.addCookie(cookies.get(0));
				cookieStore.addCookie(cookies.get(1));

				HttpContext localContext = new BasicHttpContext();
				localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			    HttpClient client = new DefaultHttpClient();  
			 //   final TextView[] myTextViews = new TextView[10];
		        HttpGet get = new HttpGet(test);
		        
		        HttpResponse responseGet = client.execute(get,localContext);  
		        HttpEntity resEntityGet = responseGet.getEntity();
		        Log.v("HERE2", test);
		        
			    output11 = EntityUtils.toString(resEntityGet);
			
			
			    store=output11;
			    int pos=0;
		
		        pos = output11.indexOf("Title");
		        output11 = output11.substring(pos+7);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(0,pos);

				TextView view112 = (TextView) findViewById(R.id.result);
				view112.setText("Game Name : " + output11);
		    
				output11=store;
			    pos = output11.indexOf("Description");
		        int pos1 = output11.indexOf('/');
		        output11 = output11.substring(pos+27,pos1);
		        String output12="";
		        for(int i=0;i<output11.length();i++)
		        {
		        	char c =output11.charAt(i);
		        	
		        	if((c >='a' && c<='z') || (c>='A' && c <='Z') || (c>='0' && c<='9') || c==' ' || c==',')
		        	{
		        		Log.v("String",String.valueOf(c));
		        	
		        		output12+=String.valueOf(c);
		        	}
		        	else
		        		break;
		        	Log.v("String",output12);
		        }
		        TextView view12 = (TextView) findViewById(R.id.result4);
				view12.setText("Description : " + output12);
	
		        output11=output12;
		        //int i=0;
		     /*   String description="";
		        for(;;)
		        {
		        	if(!Character.isLetterOrDigit(output11.charAt(i))=true && output11.charAt(i)!=' ')
		        		break;
		        	i++;
		        	description+=Sring.valueOf(output11[i]);
		        }
		        */
		        Log.v("HERE3",String.valueOf(id));
		       // pos = output11.indexOf('.');
		       // output11 = output11.substring(0,pos);
				
			    store=store.substring(pos+450);

	        /*view21.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// Switching to Register screen
					Intent i = new Intent(ReadJson1.this, ReadJson.class);
					//get.abort();
				/*	Bundle b = new Bundle();
					b.putInt("key", 1); //Your id
					b.putString("login", "justinpelton");
					b.putString("password", "justin");//Your id
					i.putExtras(b);
					startActivity(i);
					finish();
				}
			});*/
			    store=store.substring(pos);
	
			}
			catch (Exception e) 
			{
			    e.printStackTrace();
			}

		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
		 Button registerScreen = (Button) findViewById(R.id.btnLogin);
	        
	        // Listening to register new account link
	        registerScreen.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// Switching to Register screen
					Intent i = new Intent(getApplicationContext(), AndroidVideoCapture.class);
					startActivity(i);
				}
			});
		}
}