package com.BeatYourRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class create1 extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	List<Cookie> cookies;
    	String [] ids = new String[20];
    	String [] ids1 = new String[20];
    	String [] ids2 = new String[20];
    	Button [] buttons = new Button[50];

    	String login="";
    	String output11="";
    	String store="";

        ImageButton imageItem;
        int forfor=0;
        int count = 0;
        
        super.onCreate(savedInstanceState);
        Log.v("hre","hre");
        setContentView(R.layout.json);
        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httpget = new HttpGet("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/categories");
        // Depends on your web service
        httpget.setHeader("Content-type", "application/json");
        httpget.setHeader("Accept","application/json");	

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httpget);           
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
            Log.v("result", result);
        } catch (Exception e) { 
            // Oops
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        
        try {
			JSONObject jObject1 = new JSONObject(result);
			JSONObject jObject = jObject1.getJSONObject("data");

			JSONArray jArray = jObject.getJSONArray("categories");
			for (int i=0; i < jArray.length(); i++)
			{
			    try {
			        JSONObject oneObject = jArray.getJSONObject(i);
			        // Pulling items from the array
			        String oneObjectsItem = oneObject.getString("name");
			        Log.v("oneObjectsItem", oneObjectsItem);
			        ids[count] = oneObjectsItem;
			        count++;
			    } catch (JSONException e) {
			        // Oops
			    }
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
}
                 	

}
        
        /*
        try {
			differentthread4();
			
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
                 		
/*
void differentthread4() throws ClientProtocolException, IOException, JSONException
{

	HttpGet get = new HttpGet("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/categories");

	HttpClient client = new DefaultHttpClient();
	HttpResponse response = client.execute(get);
	String result = EntityUtils.toString(response.getEntity()); 
	Log.v("Res",result);
	int ctr=0;
	/*i=0;
	for(;;)
	{	
	int index = result.indexOf("active");
	int index1 = result.indexOf("date_end");
	int index2 = result.indexOf("name");
	String quotes = Character.toString ((char) 34);
	ctr++;
	Log.v("index",String.valueOf(index));
	if(index!=-1)
	{	
		String test = result.substring(index-27,index-3);
		Log.v("test22",test);
		ids[i]=test;

	}
	if(index1!=-1)
	{	
		String test = result.substring(index1+11,index1+21);
		Log.v("test22",test);
		ids1[i]=test;

	}
	if(index2!=-1)
	{	
		String test = result.substring(index2+6,index2+54);
		char c = quotes.charAt(0);
		test=test.substring(1);
		int myind = test.indexOf(c);
		test=test.substring(0,myind);
		Log.v("myind",String.valueOf(myind));
		Log.v("test22",test);
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
	LinearLayout ll = (LinearLayout)findViewById(R.id.meet);
	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	//ll.addView(myButton, lp);
	ll.addView(buttons[i], lp);
	anotherfunction(buttons[i],forfor-1);
	}
///////////////////////////////////////////////////////////////////////////////////////////

	for(int i1=0;i1<i;i1++)
		Log.v("ids",ids[i1]);
}*/

/*void anotherfunction(Button b1,final int forfor1)
{
		b1.setOnClickListener(new View.OnClickListener() {		
		public void onClick(View v) {
			   Log.v("for",String.valueOf(forfor1));
			   SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
	           Editor editor = pref.edit();
	           Log.v("dsadsa","http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/"+ids[forfor1]+".json");
	           editor.putString("tour", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/tournaments/"+ids[forfor1]+".json"); // Storing string
	           editor.putString("id", ids[forfor1]); // Storing string
	        // editor.putString("log", "yes"); 
	           editor.commit();
	           String print = pref.getString("tour",null);
	        	
	        	Log.v("Act1",print);
			    Intent i = new Intent(create.this, Landing.class);
				startActivity(i);		
		}
	});	
	
}
}
*/