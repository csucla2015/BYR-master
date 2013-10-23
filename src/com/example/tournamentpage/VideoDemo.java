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

package com.example.tournamentpage;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
public class VideoDemo extends Activity 
{
  private VideoView video;
  private MediaController ctlr;
  ImageButton myButton;
  ImageButton myButton1;
  @Override
  public void onCreate(Bundle icicle) 
  {
    super.onCreate(icicle);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    getWindow().setFormat(PixelFormat.TRANSLUCENT);
    setContentView(R.layout.main1);
    this.myButton = (ImageButton)super.findViewById(R.id.button);
    myButton.setOnClickListener(myButtonOnClickListener);
    this.myButton1 = (ImageButton)super.findViewById(R.id.button1);
    myButton1.setOnClickListener(myButtonOnClickListener);
    File clip=new File(Environment.getExternalStorageDirectory(),
                       "myvideo.mp4");
   if (clip.exists()) 
    {
      video=(VideoView)findViewById(R.id.video);
      video.setVideoPath(clip.getAbsolutePath());  
      ctlr=new MediaController(this);
      ctlr.setMediaPlayer(video);
      video.setMediaController(ctlr);
      video.requestFocus();
      video.start();
    }
  }
  private Button.OnClickListener myButtonOnClickListener
  = new Button.OnClickListener(){
	  public void onClick(View arg0) 
	  {  
		  switch (arg0.getId()) {
	         case R.id.button: 
		  video.stopPlayback();
		  break;
	         case R.id.button1: 
	        	 Intent intent = new Intent(VideoDemo.this,FinishedRecording.class);
	     		//intent.setData(Uri.fromFile(file));
	     		startActivity(intent);
	   		  //finish();
	   		  break;
		  }
  }
  };
  public void stop(View v) {
 
  }
}