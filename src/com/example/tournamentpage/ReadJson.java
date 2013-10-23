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
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReadJson extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json);
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
	        String test = "http://www.beatyourrecord.com/Services/Tournaments/GetTournaments/?sessionId="+result;

		
			try {
				
				CookieStore cookieStore = new BasicCookieStore();
				cookieStore.addCookie(cookies.get(0));
				cookieStore.addCookie(cookies.get(1));

				HttpContext localContext = new BasicHttpContext();
				localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			    HttpClient client = new DefaultHttpClient();  
			    final TextView[] myTextViews = new TextView[10];
		        HttpGet get = new HttpGet(test);
		        
		        HttpResponse responseGet = client.execute(get,localContext);  
		        HttpEntity resEntityGet = responseGet.getEntity();
		        Log.v("HERE2", test);
		        
			    output11 = EntityUtils.toString(resEntityGet);
			
			
			    store=output11;
			    int pos=0;
			    pos=output11.indexOf("expulsion above");
			    String trial = output11.substring(pos, pos+180);
			    pos=trial.indexOf("Id");
			    trial=trial.substring(pos+4,pos+9);
			    final int id = Integer.parseInt(trial);
				//TextView view2212 = (TextView) findViewById(R.id.result);
			//	view2212.setText("Game Name1:" + trial);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    pos=0;	
		        pos = output11.indexOf("GameName");
		        output11 = output11.substring(pos+10);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(0,pos);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		        TextView view112 = (TextView) findViewById(R.id.result);
		        view112.setText("Game Name : " + output11);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		        view112.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// Switching to Register screen
						Intent i = new Intent(ReadJson.this, ReadJson1.class);
						//get.abort();
						Bundle b = new Bundle();
						b.putInt("id", id); //Your id
						b.putString("login", "julia");
						b.putString("password", "1111");//Your id
						i.putExtras(b);
						startActivity(i);
						finish();
					}
				});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				output11=store;
			    pos = output11.indexOf("GameCategory");
		        output11 = output11.substring(pos+14);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(0,pos);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				TextView view12 = (TextView) findViewById(R.id.result4);
				view12.setText("Game Category : " + output11);
				
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		        view12.setOnClickListener(new View.OnClickListener() {
					
							public void onClick(View v) {
								// Switching to Register screen
								Intent i = new Intent(ReadJson.this, ReadJson1.class);
								//get.abort();
								Bundle b = new Bundle();
								b.putInt("id", id); //Your id
								b.putString("login", "julia");
								b.putString("password", "1111");//Your id
								i.putExtras(b);
								startActivity(i);
								finish();
							}
						});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
				output11=store;
			    pos = output11.indexOf("Cost");
		        output11 = output11.substring(pos+5);
		        pos = output11.indexOf(':');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf(',');
		        output11 = output11.substring(0,pos);
		       
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				TextView view13 = (TextView) findViewById(R.id.result5);
				view13.setText("Cost : " + output11);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    view13.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// Switching to Register screen
						Intent i = new Intent(ReadJson.this, ReadJson1.class);
						//get.abort();
						Bundle b = new Bundle();
						b.putInt("id", id); //Your id
						b.putString("login", "julia");
						b.putString("password", "1111");//Your id
						i.putExtras(b);
						startActivity(i);
						finish();
					}
				});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			   // Log.v("store",store);
			    output11=store;
			    pos=output11.indexOf("expulsion above");
			    output11 = output11.substring(pos+59);
			    pos=output11.indexOf("expulsion above");
			    trial = output11.substring(pos);
			    pos=trial.indexOf("Id");
			    final String trial1=trial.substring(pos+4,pos+9);
			   // TextView view231 = (TextView) findViewById(R.id.result7);
			//	view231.setText("Game Name : " + trial1);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////			    
			    store=store.substring(350);
			    output11=store;
				pos = output11.indexOf("GameName");
		        output11 = output11.substring(pos+10);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(0,pos);
		      
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				TextView view21 = (TextView) findViewById(R.id.result7);
				view21.setText("Game Name : " + output11);
	        view21.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// Switching to Register screen
					Intent i = new Intent(ReadJson.this, ReadJson1.class);
					//get.abort();
					Bundle b = new Bundle();
					b.putInt("id", Integer.parseInt(trial1)); //Your id
					b.putString("login", "julia");
					b.putString("password", "1111");//Your id
					i.putExtras(b);
					startActivity(i);
					finish();
				}
			});
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				output11=store;
			    pos = output11.indexOf("GameCategory");
		        output11 = output11.substring(pos+14);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf('"');
		        output11 = output11.substring(0,pos);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				TextView view22 = (TextView) findViewById(R.id.result8);
				view22.setText("Game Category : " + output11);
				   view22.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View v) {
							// Switching to Register screen
							Intent i = new Intent(ReadJson.this, ReadJson1.class);
							//get.abort();
							Bundle b = new Bundle();
							b.putInt("key", 1); //Your id
							b.putInt("id", Integer.parseInt(trial1)); //Your id
							b.putString("login", "julia");
							b.putString("password", "1111");//Your id
							i.putExtras(b);
							startActivity(i);
							finish();
						}
					});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
						
						
				
				output11=store;
			    pos = output11.indexOf("Cost");
		        output11 = output11.substring(pos+5);
		        pos = output11.indexOf(':');
		        output11 = output11.substring(pos+1);
		        pos = output11.indexOf(',');
		        output11 = output11.substring(0,pos);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

				TextView view23 = (TextView) findViewById(R.id.result9);
				view23.setText("Cost : " + output11);
			    store=store.substring(pos);
			    
			    
			    
			    
			    view23.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// Switching to Register screen
						Intent i = new Intent(ReadJson.this, ReadJson1.class);
						//get.abort();
						Bundle b = new Bundle();
						b.putInt("id", Integer.parseInt(trial1)); //Your id
						b.putString("login", "julia");
						b.putString("password", "1111");//Your id
						i.putExtras(b);
						startActivity(i);
						finish();
					}
				});
				
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					
			    
			    
			    
			    
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			
			//String test = "http://www.beatyourrecord.com/Services/Tournaments/GetTournaments/?sessionId="+output11;
			
		
		
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		catch (Exception e) {
		    e.printStackTrace();
		}		
		}
}