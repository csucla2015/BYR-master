/* Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.BeatYourRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeatYourRecord.Authorizer.AuthorizationListener;
import com.BeatYourRecord.db.DbHelper;


public class SubmitActivity extends Activity {
  private static final String LOG_TAG = SubmitActivity.class.getSimpleName();

  private static final String INITIAL_UPLOAD_URL =
      "http://uploads.gdata.youtube.com/resumable/feeds/api/users/default/uploads";
  private static final String DEFAULT_VIDEO_CATEGORY = "News";
  private static final String DEFAULT_VIDEO_TAGS = "mobile";

  private static final int DIALOG_LEGAL = 0;

  private static final int MAX_RETRIES = 5;
  private static final int BACKOFF = 4; // base of exponential backoff
double lat =0;
double lng = 0;
String permfilepath = "";
  private ProgressDialog dialog = null;
  private DbHelper dbHelper = null;
  private String ytdDomain = null;
  private String assignmentId = null;
  private Uri videoUri = null;
  private String clientLoginToken = null;
  private String youTubeName = null;
  private Date dateTaken = null;
  private Authorizer authorizer = null;
  private Location videoLocation = null;
  private String tags = null;
  private LocationListener locationListener = null;
  private LocationManager locationManager = null;
  private SharedPreferences preferences = null;
  private TextView domainHeader = null;
  // TODO - replace these counters with a state variable
  private double currentFileSize = 0;
  private double totalBytesUploaded = 0;
  private int numberOfRetries = 0;
String auth="";
String tourid="";
Uri needed;
String filepath1="";
String vidId="";
	String logout = "";
String filepath="";
  static class YouTubeAccountException extends Exception {
    public YouTubeAccountException(String msg) {
      super(msg);
    }
  }
 

  @Override
  public void onCreate(Bundle savedInstanceState) {
		boolean haveConnectedWifi=false;
		boolean haveConnectedMobile=false;
		 ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		               haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		  if (haveConnectedWifi==false ) {
			  showDialog(11);
		  }
    super.onCreate(savedInstanceState);
    
   // SharedPreferences pref = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   	//Editor editor = pref.edit();
     	//auth = pref.getString("BYR_session", null);
    /*SharedPreferences pref6 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   	Editor editor7 = pref6.edit();
		//this.setContentView(R.layout.submit);
     	String logoutme = pref6.getString("log", null);
     	//log.v("log",logoutme);
     */
    SharedPreferences pref1 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   	Editor editor1 = pref1.edit();
     	tourid = pref1.getString("id", null);
     	logout = pref1.getString("log", null) ;
     	filepath = pref1.getString("filepath", null);
     	//log.v("id",tourid);
     	//log.v("log",logout);
       
        		   Log.v("reached here first","reached here first");
    File f = new File(
    		"/data/data/com.BeatYourRecord/shared_prefs/Tester15.xml");
    		if (f.exists() && logout.equals("yes")==false)	
    		{	
    			
    			//log.v("yyy","yyy");
    			SharedPreferences pref = SubmitActivity.this.getSharedPreferences("Tester15", 0); // 0 - for private mode
    	   	Editor editor = pref.edit();
    			this.setContentView(R.layout.submit);
    	     	auth = pref.getString("BYR_session", null);

    		}
    		else
    		{	
    			//log.v("yyy","yyy1");
    			this.setContentView(R.layout.submit1);
    			 showDialog(10);
    		}	 
     	
   
       //	String checksession = pref.getString("BYR_session", null);
       //	//log.v("checksession", checksession);
        	
 
  
	////log.v("Authhere",auth);
    this.authorizer = new ClientLoginAuthorizer.ClientLoginAuthorizerFactory().getAuthorizer(this,
        ClientLoginAuthorizer.YOUTUBE_AUTH_TOKEN_TYPE);

    dbHelper = new DbHelper(this);
    dbHelper = dbHelper.open();

    Intent intent = this.getIntent();
    this.videoUri = intent.getData();
    SharedPreferences pref1223 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   	Editor editor1223 = pref1223.edit();
     filepath = pref1223.getString("filepath", null);
   // this.videoUri =
    		
    	//	Uri path = Uri.parse(filepath);
    		//File f1 = new File(filepath);  
            //Uri imageUri = Uri.fromFile(f1);
            //this.videoUri = imageUri;
    		//this.videoUri = path;
     
 
   Log.v("Reached here second","Reached here secord");
    //this.videoUri= Uri.fromFile(new File("/sdcard/Movies/com.BeatYourRecord/BYR_tournName_dateTim_20130724181901222.mp4"));
    //log.v("haha","haha");

   MediaScannerConnectionClient mediaScannerClient = new
		   MediaScannerConnectionClient() {
		    private MediaScannerConnection msc = null;
		    {
		        msc = new MediaScannerConnection(getApplicationContext(), this);
		        msc.connect();
		    }

		    public void onMediaScannerConnected(){
		        msc.scanFile(filepath, null);
		    }


		    public void onScanCompleted(String path, Uri uri) {
		        //This is where you get your content uri
		            Log.d("test3", uri.toString());
		            needed = uri;
		           // videoUri=needed;

		        msc.disconnect();
		    }
		   };
   
   
   
   
    String videoPath="";
    
	try {
		videoPath = getFilePathFromUri(this.videoUri);
		filepath1 = videoPath;
		Log.v("videoPath",videoPath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    File videoFile = new File(videoPath);
    

  
   
    this.ytdDomain = 	intent.getExtras().getString(DbHelper.YTD_DOMAIN);
    this.assignmentId=	intent.getExtras().getString(DbHelper.ASSIGNMENT_ID);

    
    this.domainHeader   = (TextView) this.findViewById(R.id.domainHeader);
    domainHeader.setText(SettingActivity.getYtdDomains(this).get(this.ytdDomain));

    this.preferences = this.getSharedPreferences(MainActivity.SHARED_PREF_NAME,
        Activity.MODE_PRIVATE);
    this.youTubeName = preferences.getString(DbHelper.YT_ACCOUNT, null);

    final Button submitButton = (Button) findViewById(R.id.submitButton);
    submitButton.setEnabled(false);

    submitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialog(DIALOG_LEGAL);
      }
    });
    addusertotournament();
    Button cancelButton = (Button) findViewById(R.id.cancelButton);
    cancelButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	   //SharedPreferences pref1 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
 		   	//Editor editor = pref1.edit();
 		    //editor.putString("filepath", permfilepath); 
             //editor.commit();
    	  Intent intent = new Intent(SubmitActivity.this,DetailsActivity.class);
	    	//	 intent.putExtra(DbHelper.YTD_DOMAIN, "TODO-appname.appspot.com");
	    		
	     
	     		//intent.setData(Uri.fromFile(file));	
	     		startActivity(intent);
	     		finish();
       // setResult(RESULT_CANCELED);
        //finish();
      }
    });

    Button forgotButton = (Button) findViewById(R.id.forgotButton);

    forgotButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
      	   //SharedPreferences pref1 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   		   	//Editor editor = pref1.edit();
   		    //editor.putString("filepath", permfilepath); 
               //editor.commit();
	    		
  	SharedPreferences pref155 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
    Editor editor155 = pref155.edit();

    editor155.putString("filepath", filepath1); 
    editor155.commit();
    Log.v("fielpathss",filepath1);
      	  Intent intent = new Intent(SubmitActivity.this,DetailsActivity.class);
  	    	//	 intent.putExtra(DbHelper.YTD_DOMAIN, "TODO-appname.appspot.com");

  	     		//intent.setData(Uri.fromFile(file));	
 		startActivity(intent);
 	//	finish();
         // setResult(RESULT_CANCELED);
          //finish();
        }
      });
    
    
    
    
    
    
    
    EditText titleEdit = (EditText) findViewById(R.id.submitTitle);
    titleEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable arg0) {
        enableSubmitIfReady();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }
    });
    EditText descriptionEdit = (EditText) findViewById(R.id.submitDescription);
    descriptionEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable arg0) {
        enableSubmitIfReady();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }
    });

    Cursor cursor = this.managedQuery(this.videoUri, null, null, null, null);

    if (cursor.getCount() == 0) {
      Log.d(LOG_TAG, "not a valid video uri");
      Toast.makeText(SubmitActivity.this, "not a valid video uri", Toast.LENGTH_LONG).show();
    } else {
      getVideoLocation();

      if (cursor.moveToFirst()) {

        long id = cursor.getLong(cursor.getColumnIndex(Video.VideoColumns._ID));
        this.dateTaken = new Date(cursor.getLong(cursor
            .getColumnIndex(Video.VideoColumns.DATE_TAKEN)));
        Log.v("here","here12");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        Configuration userConfig = new Configuration();
        Settings.System.getConfiguration(getContentResolver(), userConfig);
       /* Calendar cal = Calendar.getInstance(userConfig.locale);
        TimeZone tz = cal.getTimeZone();*/
        Log.v("here","here13");


       // dateFormat.setTimeZone(tz);

        TextView dateTakenView = (TextView) findViewById(R.id.dateCaptured);
        dateTakenView.setText("Date captured: " + dateFormat.format(dateTaken));

        ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
        ContentResolver crThumb = getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id,
            MediaStore.Video.Thumbnails.MICRO_KIND, options);
        thumbnail.setImageBitmap(curThumb);
      }
    }
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    final Dialog dialog = new Dialog(SubmitActivity.this);
   
    switch(id) {
    case 10:
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

        legalText1.setText("Please Login using your email and password.");

        dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v)
          {
        	  dialog.cancel();
          }
    	
        });
    	break;
    case 11:

    	
    	dialog.setContentView(R.layout.okbutton);
    	dialog.setTitle("Note");
        TextView legalText12 = (TextView) dialog.findViewById(R.id.legal);

        legalText12.setText("You are not connected to wifi. Upload times on 3g/4g can be long. To upload later click ok and then back");

        dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v)
          {
        	  dialog.cancel();
          }
    	
        });
    	break;
    	
    	
        //break;
    case DIALOG_LEGAL:
    	 //dialog.setTitle("Terms of Service");
      dialog.setContentView(R.layout.legal);

      TextView legalText = (TextView) dialog.findViewById(R.id.legal);

      legalText.setText(Util.readFile(this, R.raw.legal).toString());

      dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.cancel();
          ///////////////////////////////////////////////////////////////////////
          File f = new File(
          		"/data/data/com.BeatYourRecord/shared_prefs/Tester15.xml");
          		if (f.exists() && logout.equals("yes")==false)
          		{	 
          			Log.v("androids a bitch","das");
          		}
          		else
          		{	
          			iregistered();
          		}	 
           	

          getAuthTokenWithPermission(youTubeName);
        }
      });
      dialog.findViewById(R.id.notagree).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.cancel();
        }
      });

      break;
    }

    return dialog;
  }
public void iregistered()
{
	 EditText e2 = (EditText) findViewById(R.id.editText9);
     String str = e2.getText().toString();
    //log.v("Value1",str);
    EditText e3 = (EditText) findViewById(R.id.editText8);
     String str1 = e3.getText().toString();
    //log.v("Value1",str1);
    EditText e4 = (EditText) findViewById(R.id.editText0);
     String str2 = e4.getText().toString();
     EditText e5 = (EditText) findViewById(R.id.editText14);
     String str3 = e5.getText().toString();
    //log.v("Value1",str2);
    
    
    
    DefaultHttpClient client = new DefaultHttpClient();
	//log.v("here12","here");
	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/registrations/");
	JSONObject holder = new JSONObject();
	JSONObject userObj = new JSONObject();
	JSONObject userObj1 = new JSONObject();
	JSONObject userObj2 = new JSONObject();
	JSONObject userObj4 = new JSONObject();

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
	        SharedPreferences pref6 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
        	Editor editor6 = pref6.edit();
         editor6.putString("Username", str3); 
        	editor6.commit();
		        userObj.put("email", str);
		        k = userObj.toString();
		        k=k.substring(0, k.length()-1);
		        k+=",";
		        userObj4.put("username", str3);
		        k1 = userObj4.toString();
		        k1=k1.substring(1, k1.length()-1);
		        k1+=",";
		        k+=k1;
		        userObj1.put("password", str1);
		        k1 = userObj1.toString();
		        k1=k1.substring(1, k1.length()-1);
		        k1+=",";
		        k+=k1;
	        userObj2.put("password_confirmation", str2);
	        k1 = userObj2.toString();
	        k1=k1.substring(1, k1.length());
	        k+=k1;	       
	        //log.v("holder",k);
	        StringEntity se = new StringEntity(k);
	        post.setEntity(se);

	        // setup the request headers
	        post.setHeader("Content-Type", "application/json");
	        post.setHeader("Accept", "application/json");
	      //  post.setHeader("Content-Type", "application/json");

	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        response = client.execute(post, responseHandler);
	        json = new JSONObject(response);
	        String test = json.getJSONObject("data").getString("auth_token");
	        SharedPreferences pref = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
        	Editor editor = pref.edit();
         editor.putString("BYR_session", test); // Storing string
         auth=test;
        	editor.commit();
	        Log.v("test1authinsub",test);

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
}
  @Override
  public void onRestart() {
    super.onRestart();
    hideKeyboard(this.getCurrentFocus());
  }

  private void requestDummyFocus() {
    //this.findViewById(R.id.dummy).requestFocus();
  }

  private void hideKeyboard(View currentFocusView) {
    if (currentFocusView instanceof EditText) {
      InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
      requestDummyFocus();
    }
  }

  public String getTitleText() {
    EditText titleEdit = (EditText) findViewById(R.id.submitTitle);
    return sanitize(titleEdit.getText().toString());
  }

  public String getDescriptionText() {
    EditText descriptionEdit = (EditText) findViewById(R.id.submitDescription);
    return sanitize(descriptionEdit.getText().toString());
  }

  private String sanitize(String text) {
    return text.replaceAll("&", "&amp;");
  }

  public String getTagsText() {
    EditText tagsEdit = (EditText) findViewById(R.id.submitTags);
    return sanitize(tagsEdit.getText().toString());
  }

  public void enableSubmitIfReady() {
    Button submit = (Button) findViewById(R.id.submitButton);
    boolean isReady = getTitleText().length() > 0 ;

    if (isReady) {
      submit.setEnabled(true);
    } else {
      submit.setEnabled(false);
    }
  }

  @Override
  public void onDestroy() {
	  Intent intent123 = new Intent(SubmitActivity.this, HomePage1.class);
	  SharedPreferences pref1 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	   	Editor editor = pref1.edit();
  // editor.putString("tour", "http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
   //editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
  editor.putString("log", "no"); 
   editor.commit();
	
	 // Bundle b = new Bundle();
		
	//	b.putString("auth", auth);
		
		
  	 startActivity(intent123);
			finish();
    super.onDestroy();
    dbHelper.close();
    if (this.locationListener != null) {
      this.locationManager.removeUpdates(locationListener);
    }
  }

  public void upload(Uri videoUri) {
    this.dialog = new ProgressDialog(this);
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog.setMessage("uploading ...");
    dialog.setCancelable(false);
    dialog.show();

    Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        dialog.dismiss();
        String videoId = msg.getData().getString("videoId");
        Log.v("Video",videoId);
        String youtubelink = "http://www.youtube.com/watch?v=" + videoId;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
        Editor editor = pref.edit();
        
        editor.putString("myyoutubelink",youtubelink);
        editor.commit();
        DefaultHttpClient client = new DefaultHttpClient();
      	Log.v("here1233","here");
      	Log.v("authtoke",auth);
      	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/entries?auth_token="+auth);
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
      	        
      	            //log.v("auth",auth);
      		        userObj.put("score", getTitleText());
      		        SharedPreferences pref11 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
      	 		   	Editor editor11 = pref11.edit();
      	 		    editor11.putString("score", getTitleText()); 
      	             editor11.commit();
      		        ////////////////////////
      		        holder.put("entry", userObj);
      		        k = holder.toString();
      		        k=k.substring(0, k.length()-2);
      		        k+=",";
      		        userObj1.put("tournament_id", tourid);
      		        k1 += userObj1.toString();
      		        k1=k1.substring(1, k1.length()-1);
      		        
      		        k+=k1;
      		        k+=",";
      		        userObj2.put("yt_video", youtubelink);
      		        k1 = userObj2.toString();
      		        k1=k1.substring(1,k1.length());
      		        k+=k1;
      		    /*    double arr[] = new double[2];
      		        arr[0]=lat;
      		        arr[1]=lng;
      		      JSONObject Array1 = new JSONObject();
      		    Array1.put("geoloc", arr);
      		    k1=Array1.toString();
      		  k1=k1.substring(1, k1.length());
  		        
  		        k+=k1;
  		        
      		      userObj2.put("geoloc", String.valueOf(lat) +","+ String.valueOf(lng));
    		        k1 += userObj2.toString();
    		        k1=k1.substring(1, k1.length());
    		        
    		        k+=k1;*/
      		        k+="}";
      		        
      	      
      	       
      		        
      	        //log.v("holder",k);
      	        StringEntity se = new StringEntity(k);
      	        post.setEntity(se);
      	    
      	       
      	        // setup the request headers
      	        post.setHeader("Content-Type", "application/json");
      	        post.setHeader("Accept", "application/json");
      	      //  post.setHeader("Content-Type", "application/json");
      	        //log.v("test1","wearegere");
      	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
      	        response = client.execute(post, responseHandler);
      	        json = new JSONObject(response);
      	        // test = json.getJSONObject("data").getString("auth_token");
      	      //  //log.v("test1",test);

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
      	
      	
        if (!Util.isNullOrEmpty(videoId)) {
          currentFileSize = 0;
          totalBytesUploaded = 0;
          Intent result = new Intent();
          result.putExtra("videoId", videoId);
          Log.v("tired",videoId);
          setResult(RESULT_OK, result);
          finish();
        } else {
          String error = msg.getData().getString("error");
          if (!Util.isNullOrEmpty(error)) {
            Toast.makeText(SubmitActivity.this, error, Toast.LENGTH_LONG).show();
          }
        }
      }
    };

    asyncUpload(videoUri, handler);
  }

  public void asyncUpload(final Uri uri, final Handler handler) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        msg.setData(bundle);

        String videoId = null;
        int submitCount=0;
        try {
          while (submitCount<=MAX_RETRIES && videoId == null) {
            try {
              submitCount++;
              videoId = startUpload(uri);
              //log.v("please",videoId);
              assert videoId!=null;
            } catch (Internal500ResumeException e500) { // TODO - this should not really happen
              if (submitCount<MAX_RETRIES) {
                Log.w(LOG_TAG, e500.getMessage());
                Log.d(LOG_TAG, String.format("Upload retry :%d.",submitCount));
              } else {
                Log.d(LOG_TAG, "Giving up");
                Log.e(LOG_TAG, e500.getMessage());
                throw new IOException(e500.getMessage());
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
          return;
        } catch (YouTubeAccountException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
          return;
        } catch (SAXException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
        } catch (ParserConfigurationException e) {
          e.printStackTrace();  
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
        }

        bundle.putString("videoId", videoId);
        handler.sendMessage(msg);
      }
    }).start();
  }

  private File getFileFromUri(Uri uri) throws IOException {
    Cursor cursor = managedQuery(uri, null, null, null, null);
    if (cursor.getCount() == 0) {
      throw new IOException(String.format("cannot find data from %s", uri.toString()));
    } else {
      cursor.moveToFirst();
    }

    String filePath = cursor.getString(cursor.getColumnIndex(Video.VideoColumns.DATA));
    //log.v("filePath",filePath);
    permfilepath = filePath;

    File file = new File(filePath);
    cursor.close();
    return file;
  }

  private String getFilePathFromUri(Uri uri) throws IOException {
    Cursor cursor = managedQuery(uri, null, null, null, null);
    if (cursor.getCount() == 0) {
      throw new IOException(String.format("cannot find data from %s", uri.toString()));
    } else {
      cursor.moveToFirst();
    }

    String filePath = cursor.getString(cursor.getColumnIndex(Video.VideoColumns.DATA));
    permfilepath = filePath;

    cursor.close();
    return filePath;
  }

  private String startUpload(Uri uri) throws IOException, YouTubeAccountException, SAXException, ParserConfigurationException, Internal500ResumeException {
    File file = getFileFromUri(uri);

    if (this.clientLoginToken == null) {
      // The stored gmail account is not linked to YouTube
      throw new YouTubeAccountException(this.youTubeName + " is not linked to a YouTube account.");
    }

    String uploadUrl = uploadMetaData(file.getAbsolutePath(), true);

    Log.d(LOG_TAG, "uploadUrl=" + uploadUrl);
    Log.d(LOG_TAG, String.format("Client token : %s ",this.clientLoginToken));
      

    this.currentFileSize = file.length();
    this.totalBytesUploaded = 0;
    this.numberOfRetries = 0;

    int uploadChunk = 1024 * 1024 * 3; // 3MB

    int start = 0;
    int end = -1;

    String videoId = null;
    double fileSize = this.currentFileSize;
    while (fileSize > 0) {
      if (fileSize - uploadChunk > 0) {
        end = start + uploadChunk - 1;
      } else {
        end = start + (int) fileSize - 1;
      }
      Log.d(LOG_TAG, String.format("start=%s end=%s total=%s", start, end, file.length()));
      try {
        videoId = gdataUpload(file, uploadUrl, start, end);
        fileSize -= uploadChunk;
        start = end + 1;
        this.numberOfRetries = 0; // clear this counter as we had a succesfull upload
      } catch (IOException e) {
        Log.d(LOG_TAG,"Error during upload : " + e.getMessage());
        ResumeInfo resumeInfo = null;
        do {
          if (!shouldResume()) {
            Log.d(LOG_TAG, String.format("Giving up uploading '%s'.", uploadUrl));
            throw e;
          }
          try {
            resumeInfo = resumeFileUpload(uploadUrl);
          } catch (IOException re) {
            // ignore
            Log.d(LOG_TAG, String.format("Failed retry attempt of : %s due to: '%s'.", uploadUrl, re.getMessage()));
          }
        } while (resumeInfo == null);
        Log.d(LOG_TAG, String.format("Resuming stalled upload to: %s.", uploadUrl));
        if (resumeInfo.videoId != null) { // upload actually complted despite the exception
          videoId = resumeInfo.videoId;
          Log.d(LOG_TAG, String.format("No need to resume video ID '%s'.", videoId));          
          break;
        } else {
          int nextByteToUpload = resumeInfo.nextByteToUpload;
          Log.d(LOG_TAG, String.format("Next byte to upload is '%d'.", nextByteToUpload));
          this.totalBytesUploaded = nextByteToUpload; // possibly rolling back the previosuly saved value
          fileSize = this.currentFileSize - nextByteToUpload;
          start = nextByteToUpload;
        }
      }
    }

    if (videoId != null) {
      return videoId;
    }

    return null;
  }
  private File initFile() {
	  File k;
      File dir = new File(
              Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), this
                      .getClass().getPackage().getName());
      if (!dir.exists() && !dir.mkdirs()) {
          k = null;
      } else {
          k = new File(dir.getAbsolutePath(), new SimpleDateFormat(
                  "'BYR_tournName_dateTim_'yyyyMMddHHmmss'.mp4'").format(new Date()));
      }
      return k;
  }
  private String uploadMetaData(String filePath, boolean retry) throws IOException {
    String uploadUrl = INITIAL_UPLOAD_URL;

    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    urlConnection.setRequestMethod("POST");
    urlConnection.setDoOutput(true);
    urlConnection.setRequestProperty("Content-Type", "application/atom+xml");
    urlConnection.setRequestProperty("Slug", filePath);
    String atomData;
    int pos = filePath.indexOf("BYR");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
    String currentDateandTime = sdf.format(new Date());
    SharedPreferences pref1 = SubmitActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
   	Editor editor1 = pref1.edit();
     	String username = pref1.getString("Username", null);
    String title = "BeatYourRecord Free Throws " + currentDateandTime + " Score : "+getTitleText() +" User : "+username;
    		//this.initFile().getAbsolutePath();
    
    String description = "Beat Your Record Free Throw Tournament. User : "+username+ ". Go to" + " www.ec2-54-212-221-3.us-west-2.compute.amazonaws.com" +" to take part in our other tournaments";
    String category = DEFAULT_VIDEO_CATEGORY;
    this.tags = DEFAULT_VIDEO_TAGS;

    if (!Util.isNullOrEmpty(this.getTagsText())) {
      this.tags = this.getTagsText();
    }

    if (this.videoLocation == null) {
      String template = Util.readFile(this, R.raw.gdata).toString();
      atomData = String.format(template, title, description, category, this.tags);
    } else {
      String template = Util.readFile(this, R.raw.gdata_geo).toString();
      atomData = String.format(template, title, description, category, this.tags,
          videoLocation.getLatitude(), videoLocation.getLongitude());
    }

    OutputStreamWriter outStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
    outStreamWriter.write(atomData);
    outStreamWriter.close();

    int responseCode = urlConnection.getResponseCode();
    if (responseCode < 200 || responseCode >= 300) {
      // The response code is 40X
      if ((responseCode + "").startsWith("4") && retry) {
        Log.d(LOG_TAG, "retrying to fetch auth token for " + youTubeName);
        this.clientLoginToken = authorizer.getFreshAuthToken(youTubeName, clientLoginToken);
        // Try again with fresh token
        return uploadMetaData(filePath, false);
      } else {
        throw new IOException(String.format("response code='%s' (code %d)" + " for %s",
            urlConnection.getResponseMessage(), responseCode, urlConnection.getURL()));
      }
    }

    return urlConnection.getHeaderField("Location");
  }

  private String gdataUpload(File file, String uploadUrl, int start, int end) throws IOException {
    int chunk = end - start + 1;
    int bufferSize = 1024;
    byte[] buffer = new byte[bufferSize];
    FileInputStream fileStream = new FileInputStream(file);
    
    
    
    
    
    
    
    
    
    
    
    
    

    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    // some mobile proxies do not support PUT, using X-HTTP-Method-Override to get around this problem
    if (isFirstRequest()) {
      Log.d(LOG_TAG, String.format("Uploaded %d bytes so far, using POST method.", (int)totalBytesUploaded));
      urlConnection.setRequestMethod("POST");
    } else {
      urlConnection.setRequestMethod("POST");
      urlConnection.setRequestProperty("X-HTTP-Method-Override", "PUT");
      Log.d(LOG_TAG, String.format("Uploaded %d bytes so far, using POST with X-HTTP-Method-Override PUT method.",
        (int)totalBytesUploaded));
    }
    urlConnection.setDoOutput(true);
    urlConnection.setFixedLengthStreamingMode(chunk);
    urlConnection.setRequestProperty("Content-Type", "video/3gpp");
    urlConnection.setRequestProperty("Content-Range", String.format("bytes %d-%d/%d", start, end,
        file.length()));
    Log.d(LOG_TAG, urlConnection.getRequestProperty("Content-Range"));
//////may be
    //log.v("id man id",urlConnection.getRequestProperty("Content-Range"));
    OutputStream outStreamWriter = urlConnection.getOutputStream();

    fileStream.skip(start);

    int bytesRead;
    int totalRead = 0;
    while ((bytesRead = fileStream.read(buffer, 0, bufferSize)) != -1) {
      outStreamWriter.write(buffer, 0, bytesRead);
      totalRead += bytesRead;
      this.totalBytesUploaded += bytesRead;

      double percent = (totalBytesUploaded / currentFileSize) * 99;

      /*
      Log.d(LOG_TAG, String.format(
      "fileSize=%f totalBytesUploaded=%f percent=%f", currentFileSize,
      totalBytesUploaded, percent));
      */

      dialog.setProgress((int) percent);

      if (totalRead == (end - start + 1)) {
        break;
      }
    }

    outStreamWriter.close();

    int responseCode = urlConnection.getResponseCode();

    Log.d(LOG_TAG, "responseCode=" + responseCode);
    Log.d(LOG_TAG, "responseMessage=" + urlConnection.getResponseMessage());

    try {
      if (responseCode == 201) {
        String videoId = parseVideoId(urlConnection.getInputStream());
//log.v("Video ID", videoId);
vidId=videoId;
        String latLng = null;
        if (this.videoLocation != null) {
          latLng = String.format("lat=%f lng=%f", this.videoLocation.getLatitude(),
              this.videoLocation.getLongitude());
        }

        submitToYtdDomain(this.ytdDomain, this.assignmentId, videoId,
            this.youTubeName, SubmitActivity.this.clientLoginToken, getTitleText(),
            getDescriptionText(), this.dateTaken, latLng, this.tags);
        dialog.setProgress(100);
        //log.v("10video id",videoId);
        return videoId;
      } else if (responseCode == 200) {
        Set<String> keySet = urlConnection.getHeaderFields().keySet();
        String keys = urlConnection.getHeaderFields().keySet().toString();
        Log.d(LOG_TAG, String.format("Headers keys %s.", keys));
        //////////////may be
        for (String key : keySet) {
          Log.d(LOG_TAG, String.format("Header key %s value %s.", key, urlConnection.getHeaderField(key)));          
        }
        Log.w(LOG_TAG, "Received 200 response during resumable uploading");
        throw new IOException(String.format("Unexpected response code : responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage()));
      } else {
        if ((responseCode + "").startsWith("5")) {
          String error = String.format("responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage());
          Log.w(LOG_TAG, error);
          // TODO - this exception will trigger retry mechanism to kick in
          // TODO - even though it should not, consider introducing a new type so
          // TODO - resume does not kick in upon 5xx
          throw new IOException(error);
        } else if (responseCode == 308) {
          // OK, the chunk completed succesfully 
          Log.d(LOG_TAG, String.format("responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage()));
        } else {
          // TODO - this case is not handled properly yet
          Log.w(LOG_TAG, String.format("Unexpected return code : %d %s while uploading :%s", responseCode,
            urlConnection.getResponseMessage(), uploadUrl));
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }

    return null;
  }

  public boolean isFirstRequest() {
    return totalBytesUploaded==0;
  }

  private ResumeInfo resumeFileUpload(String uploadUrl) throws IOException, ParserConfigurationException, SAXException, Internal500ResumeException {
    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    urlConnection.setRequestProperty("Content-Range", "bytes */*");
    urlConnection.setRequestMethod("POST");
    urlConnection.setRequestProperty("X-HTTP-Method-Override", "PUT");
    urlConnection.setFixedLengthStreamingMode(0);

    HttpURLConnection.setFollowRedirects(false);

    urlConnection.connect();
    int responseCode = urlConnection.getResponseCode();

    if (responseCode >= 300 && responseCode < 400) {
      int nextByteToUpload;
      String range = urlConnection.getHeaderField("Range");
      if (range == null) {
        Log.d(LOG_TAG, String.format("PUT to %s did not return 'Range' header.", uploadUrl));
        nextByteToUpload = 0;
      } else {
        Log.d(LOG_TAG, String.format("Range header is '%s'.", range));
        String[] parts = range.split("-");
        if (parts.length > 1) {
          nextByteToUpload = Integer.parseInt(parts[1]) + 1;
        } else {
          nextByteToUpload = 0;
        }
      }
      return new ResumeInfo(nextByteToUpload);
    } else if (responseCode >= 200 && responseCode < 300) {
      return new ResumeInfo(parseVideoId(urlConnection.getInputStream()));
    } else if (responseCode == 500) {
      // TODO this is a workaround for current problems with resuming uploads while switching transport (Wifi->EDGE)
      throw new Internal500ResumeException(String.format("Unexpected response for PUT to %s: %s " +
      		"(code %d)", uploadUrl, urlConnection.getResponseMessage(), responseCode));
    } else {
      throw new IOException(String.format("Unexpected response for PUT to %s: %s " +
      		"(code %d)", uploadUrl, urlConnection.getResponseMessage(), responseCode));
    }
  }


  private boolean shouldResume() {
    this.numberOfRetries++;
    if (this.numberOfRetries>MAX_RETRIES) {
      return false;
    }
    try {
      int sleepSeconds = (int) Math.pow(BACKOFF, this.numberOfRetries);
      Log.d(LOG_TAG,String.format("Zzzzz for : %d sec.", sleepSeconds));
      Thread.currentThread().sleep(sleepSeconds * 1000);
      Log.d(LOG_TAG,String.format("Zzzzz for : %d sec done.", sleepSeconds));      
    } catch (InterruptedException se) {
      se.printStackTrace();
      return false;
    }
    return true;
  }

  private String parseVideoId(InputStream atomDataStream) throws ParserConfigurationException,
      SAXException, IOException {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(atomDataStream);

    NodeList nodes = doc.getElementsByTagNameNS("*", "*");
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      String nodeName = node.getNodeName();
      if (nodeName != null && nodeName.equals("yt:videoid")) {
        return node.getFirstChild().getNodeValue();
      }
    }
    return null;
  }

  private HttpURLConnection getGDataUrlConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("Authorization", String.format("GoogleLogin auth=\"%s\"",
        clientLoginToken));
    connection.setRequestProperty("GData-Version", "2");
    connection.setRequestProperty("X-GData-Client", this.getString(R.string.client_id));
    connection.setRequestProperty("X-GData-Key", String.format("key=%s", this.getString(R.string.dev_key)));
    return connection;
  }

  private void getAuthTokenWithPermission(String accountName) {
    this.authorizer.fetchAuthToken(accountName, this, new AuthorizationListener<String>() {
      @Override
      public void onCanceled() {
      }

      @Override
      public void onError(Exception e) {
      }

      @Override
      public void onSuccess(String result) {
        SubmitActivity.this.clientLoginToken = result;
        upload(SubmitActivity.this.videoUri);
      }});
  }
  void addusertotournament()
  {
	  Log.v("tester","tester");
	  DefaultHttpClient client = new DefaultHttpClient();
    	Log.v("here1233","here");
    	Log.v("authtoke",auth);
    	Log.v("ayth","http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/join?auth_token="+auth);
    	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/"+tourid+"join?auth_token="+auth);
    	JSONObject holder = new JSONObject();
    	JSONObject userObj1 = new JSONObject();
    	JSONObject userObj2 = new JSONObject();
    	HttpResponse response = null;
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
    	        
    	            //log.v("auth",auth);
    		      
    		        userObj1.put("tourn_id", tourid);
    		        k1 += userObj1.toString();
    		        k1=k1.substring(1, k1.length());
    		        
    	      
    	        k1 = "{" + k1;
    	        Log.v("holder",k1);
    	        StringEntity se = new StringEntity(k1);
    	    post.setEntity(se);
    	       
    	        // setup the request headers
    	        post.setHeader("Content-Type", "application/json");
    	        post.setHeader("Accept", "application/json");
    	      //  post.setHeader("Content-Type", "application/json");
    	        //log.v("test1","wearegere");
    	       // ResponseHandler<String> responseHandler = new BasicResponseHandler();
    	        response = client.execute(post);
    	       // json = new JSONObject(response);
    	        // test = json.getJSONObject("data").getString("auth_token");
    	      //  //log.v("test1",test);

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
  }

  private void getVideoLocation() {
    this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    criteria.setPowerRequirement(Criteria.POWER_HIGH);
    criteria.setAltitudeRequired(false);
    criteria.setBearingRequired(false);
    criteria.setSpeedRequired(false);
    criteria.setCostAllowed(true);

    String provider = locationManager.getBestProvider(criteria, true);

    this.locationListener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        if (location != null) {
          SubmitActivity.this.videoLocation = location;
           lat = location.getLatitude();
          lng = location.getLongitude();
          Log.d(LOG_TAG, "lat=" + lat);
          Log.d(LOG_TAG, "lng=" + lng);

          TextView locationText = (TextView) findViewById(R.id.locationLabel);
          locationText.setText("Geo Location: " + String.format("lat=%.2f lng=%.2f", lat, lng));
          locationManager.removeUpdates(this);
        } else {
          Log.d(LOG_TAG, "location is null");
        }
      }

      @Override
      public void onProviderDisabled(String provider) {
      }

      @Override
      public void onProviderEnabled(String provider) {
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

    };
    
    if (provider != null) {
      locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }
  }

  public void submitToYtdDomain(String ytdDomain, String assignmentId, String videoId,
      String youTubeName, String clientLoginToken, String title, String description,
      Date dateTaken, String videoLocation, String tags) {

    JSONObject payload = new JSONObject();
    try {
    	
        
       
    	
    	
    	
    	
    	
    	
    	
      payload.put("method", "NEW_MOBILE_VIDEO_SUBMISSION");
      JSONObject params = new JSONObject();
      Log.v("params videoId",videoId);
      vidId = videoId;
      params.put("videoId", videoId);
      params.put("youTubeName", youTubeName);
      params.put("clientLoginToken", clientLoginToken);
      params.put("title", title);
      params.put("description", description);
      params.put("videoDate", dateTaken.toString());
      params.put("tags", tags);

      if (videoLocation != null) {
        params.put("videoLocation", videoLocation);
      }

      if (assignmentId != null) {
        params.put("assignmentId", assignmentId);
      }

      payload.put("params", params);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    String jsonRpcUrl = "http://" + ytdDomain + "/jsonrpc";
    String json = Util.makeJsonRpcCall(jsonRpcUrl, payload);

    if (json != null) {
      try {
        JSONObject jsonObj = new JSONObject(json);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  class ResumeInfo {
    int nextByteToUpload;
    String videoId;
    ResumeInfo(int nextByteToUpload) {
      this.nextByteToUpload = nextByteToUpload;
    }
    ResumeInfo(String videoId) {
      this.videoId = videoId;
     Log.v("whats",videoId);
    }
  }

  /**
   * Need this for now to trigger entire upload transaction retry
   */
  class Internal500ResumeException extends Exception {
    Internal500ResumeException(String message) {
      super(message);
    }
  }
  
}  
