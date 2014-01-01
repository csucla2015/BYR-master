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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.BeatYourRecord.db.Assignment;
import com.BeatYourRecord.db.DbHelper;

public class DetailsActivity1 extends Activity {
  private static final String LOG_TAG = DetailsActivity1.class.getSimpleName();
  private static final int CAPTURE_RETURN = 1;
  private static final int GALLERY_RETURN = 2;
  private static final int SUBMIT_RETURN = 3;
  private DbHelper dbHelper = null;
  private String ytdDomain = null;
  private String assignmentId = null;
  private TextView domainHeader = null;
String auth="";
String filepath="";
  @Override
  public void onCreate(Bundle savedInstanceState) {
	  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse
    		   ("file://" + Environment.getExternalStorageDirectory())));
	 // SharedPreferences pref = getApplicationContext().getSharedPreferences("BYRPref", 0); // 0 - for private mode
     //	Editor editor = pref.edit();
  
	  SharedPreferences pref1 = DetailsActivity1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	   	Editor editor1 = pref1.edit();
	     	filepath = pref1.getString("filepath", null);
	     	//logout = pref1.getString("log", null) ;
		
		Log.v("Authhere3",auth);

		Log.v("Authhere3",filepath);

    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.details);
    VideoView myVideoView = (VideoView)findViewById(R.id.myvideoview);
    myVideoView.setVideoPath(filepath);
    myVideoView.setMediaController(new MediaController(this));
    myVideoView.requestFocus();
    myVideoView.start();
   
    dbHelper = new DbHelper(this);
    dbHelper = dbHelper.open();
    Button b1 = (Button) findViewById(R.id.captureButton1);

    Intent intent = getIntent();
    this.ytdDomain = intent.getStringExtra(DbHelper.YTD_DOMAIN);
    this.assignmentId = intent.getStringExtra(DbHelper.ASSIGNMENT_ID);

    this.domainHeader = (TextView) this.findViewById(R.id.domainHeader);
    domainHeader.setText(SettingActivity.getYtdDomains(this).get(this.ytdDomain));

    if (intent.hasExtra("notificationId")) {
      NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      nm.cancel(intent.getIntExtra("notificationId", 0));
    }

    if (!Util.isNullOrEmpty(ytdDomain) && !Util.isNullOrEmpty(assignmentId)) {
      Log.d(LOG_TAG, "ytdDomain=" + ytdDomain + " id=" + assignmentId);
      displayAssignmentDetails();
    } else {
      displayAnonymousAssignment();
    }

    findViewById(R.id.captureButton1).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
      		Intent i1 = new Intent(DetailsActivity1.this, VideoDemo.class);
  			Bundle b = new Bundle();
  			
  			b.putString("filepath", filepath);
  			i1.putExtras(b);
  			startActivity(i1);
  			
        }
      });
    findViewById(R.id.captureButton).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    		Intent i1 = new Intent(DetailsActivity1.this, AndroidVideoCapture.class);
			Bundle b = new Bundle();
			
			
			i1.putExtras(b);
			startActivity(i1);
			finish();
      }
    });
    findViewById(R.id.captureButton2).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
      		Intent i1 = new Intent(DetailsActivity1.this, Landing.class);
  			
  			startActivity(i1);
  			finish();
        }
      });
    findViewById(R.id.galleryButton).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("video/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() <= 0) {
          Log.d(LOG_TAG, String.valueOf(list.get(0)));
          return;
        }
        startActivityForResult(intent, GALLERY_RETURN);
      }
    });
  }

  public void displayAssignmentDetails() {
    Assignment assignment = dbHelper.getAssignment(this.ytdDomain, this.assignmentId);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm aaa");
    Configuration userConfig = new Configuration();
    Settings.System.getConfiguration(getContentResolver(), userConfig);
    Calendar cal = Calendar.getInstance(userConfig.locale);
    TimeZone tz = cal.getTimeZone();

    TextView assignmentCreated = (TextView) findViewById(R.id.assignmentCreated);
    assignmentCreated.setText("Created:  " + dateFormat.format(assignment.getCreated()));

    TextView assignmentUpdated = (TextView) findViewById(R.id.assignmentUpdated);
    assignmentUpdated.setText("Updated: " + dateFormat.format(assignment.getUpdated()));

    TextView assignmentDescription = (TextView) findViewById(R.id.assignmentDescription);
    assignmentDescription.setText(assignment.getDescription());
  }

  public void displayAnonymousAssignment() {
    TextView assignmentDescription = (TextView) findViewById(R.id.assignmentDescription);
    assignmentDescription.setText("Upload your " + SettingActivity.getYtdDomains(this).get(this.ytdDomain)
        + " news videos");
    assignmentDescription.setTypeface(null, Typeface.BOLD);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
//Changed by Meet
    switch (requestCode) {
    case CAPTURE_RETURN:
    case GALLERY_RETURN:
      if (resultCode == RESULT_OK) {
        Intent intent = new Intent(this, SubmitActivity.class);
		

		
        intent.setData(data.getData());
        intent.putExtra(DbHelper.YTD_DOMAIN, this.ytdDomain);
        if (!Util.isNullOrEmpty(this.assignmentId)) {
          intent.putExtra(DbHelper.ASSIGNMENT_ID, this.assignmentId);
        }
        startActivityForResult(intent, SUBMIT_RETURN);
      }
      break;
    case SUBMIT_RETURN:
      if (resultCode == RESULT_OK) {
        Toast.makeText(DetailsActivity1.this, "thank you!", Toast.LENGTH_LONG).show();
      } else {
        // Toast.makeText(DetailsActivity1.this, "submit failed or cancelled",
        // Toast.LENGTH_LONG).show();
      }
      break;
    }
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    dbHelper.close();
  }
}