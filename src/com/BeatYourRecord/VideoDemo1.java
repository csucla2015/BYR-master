/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package com.BeatYourRecord;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
public class VideoDemo1 extends Activity 
{
  private VideoView video;
  private MediaController ctlr;
  ImageButton myButton;
  ImageButton myButton1;
  String auth="";
  String filepath="";
  @Override
  public void onCreate(Bundle icicle) 
  {
	  //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref469", 0); // 0 - for private mode
     //	Editor editor = pref.edit();
     	
    
     
    super.onCreate(icicle);
    setContentView(R.layout.main5);
    VideoView myVideoView = (VideoView)findViewById(R.id.video);
    String UrlPath="android.resource://"+getPackageName()+"/"+R.raw.byr;
    myVideoView.setVideoURI(Uri.parse(UrlPath));
   // videocontainer.start();
   // myVideoView.setVideoPath(filepath);
    myVideoView.setMediaController(new MediaController(this));
    myVideoView.requestFocus();
    myVideoView.start();
    Button b1 = (Button) findViewById(R.id.button1);

 b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent i1 = new Intent(VideoDemo1.this, CarouselDemoActivity.class);
			
		
			startActivity(i1);
			//finish();
			
		}
	});
      }
  

}










