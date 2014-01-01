package com.BeatYourRecord;

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
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HomePage extends Activity {
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String login="";
	String output11="";
	String store="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Bundle b = getIntent().getExtras();
		//login = b.getString("auth");
		//Log.v("Authhere",login);
	 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		TextView meet = (TextView) findViewById(R.id.terms_text);
        meet.setText(Html.fromHtml("By clicking play now I agree to Beat Your Record's " +
        		 "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/toc'><font color='#009900'>Terms and Conditions</font></a>" + " , " + "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/privacy'>Privacy Policy</a>"+" , "+"<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/detail_rules'>Rules</a>"+" , "+	"<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/contest_rules'> Contest Rules</a>"+" and "+ 		"<a href='http://www.youtube.com/static?template=terms'>YouTube's Terms and Conditions</a>"));
        meet.setClickable(true);
        meet.setMovementMethod(LinkMovementMethod.getInstance());
        Button b = (Button) findViewById(R.id.button1);
        Button b1 = (Button) findViewById(R.id.button2);
        
 b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i1 = new Intent(HomePage.this, test.class);
				
			
				startActivity(i1);
				//finish();
				
			}
		});
		 
 b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent i1 = new Intent(HomePage.this, VideoDemo1.class);
			
		
			startActivity(i1);
			//finish();
			
		}
	});
	}
	
	 protected Dialog onCreateDialog(int id) {
		    switch (id) {
		    case 10:
		      // Create out AlterDialog
		      Builder builder = new AlertDialog.Builder(this);
		      builder.setMessage("By clicking 'Submit,' you are representing that this video does not violate YouTube’s Terms of Service located at http://www.youtube.com/t/terms and that you own all rights to the copyrights in this video or have authorization to upload it. ");
		      builder.setCancelable(true);
		    
		      AlertDialog dialog = builder.create();
		      dialog.show();
		    }
		    return super.onCreateDialog(id);
		  }

	
}