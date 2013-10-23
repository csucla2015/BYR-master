package com.example.tournamentpage;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



import android.app.Activity;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class AndroidVideoCapture extends Activity implements SurfaceHolder.Callback{


MediaRecorder mediaRecorder;
SurfaceHolder surfaceHolder;
boolean recording;
ImageButton myButton;
File file;
ImageButton myButton1;
TextView tv ;
TextView tv1 ;
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      
       recording = false;
      
       mediaRecorder = new MediaRecorder();
       initMediaRecorder();
      
       setContentView(R.layout.main);
      
       SurfaceView myVideoView = (SurfaceView)findViewById(R.id.videoview);
       surfaceHolder = myVideoView.getHolder();
       surfaceHolder.addCallback(this);
       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
      this.tv = (TextView) findViewById(R.id.result);
      this.tv1 = (TextView) findViewById(R.id.result1);
       this.myButton = (ImageButton)super.findViewById(R.id.recordButton);
       myButton.setOnClickListener(myButtonOnClickListener);
       
   }
  
   private Button.OnClickListener myButtonOnClickListener
   = new Button.OnClickListener(){

 @Override
 public void onClick(View arg0) {
	 
	 
	 
	 
  // TODO Auto-generated method stub
  if(recording){
   mediaRecorder.stop();
   mediaRecorder.release();
   Intent intent = new Intent(AndroidVideoCapture.this,ReadJson.class);
	//intent.setData(Uri.fromFile(file));
	startActivity(intent);
   finish();
 
   
   
  }else{
  	  
	  
	    new CountDownTimer(11 * 1000, 1000) {
            int x = 10;

            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(Integer.toString(x));
                x--;

            }

            @Override
            public void onFinish() 
            {
            	myfunction();
            
            }
            
          
        }.start();	  
	  
	  
	  
	  
   

  }
 // myButton.setEnabled();
  //myButton.setVisibility(recording ? View.GONE : View.VISIBLE);
  //myButton1.setEnabled(true);
  //myButton1.setVisibility(recording ? View.VISIBLE : View.GONE);
 }

 };
  
@Override
public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
 // TODO Auto-generated method stub

}
@Override
public void surfaceCreated(SurfaceHolder arg0) {
 // TODO Auto-generated method stub
 prepareMediaRecorder();
}
@Override
public void surfaceDestroyed(SurfaceHolder arg0) {
	
}

private void initMediaRecorder(){
	mediaRecorder.reset();
 mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
       mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
       CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
       mediaRecorder.setProfile(camcorderProfile_HQ);
       mediaRecorder.setOutputFile("/sdcard/myvideo.mp4");
       mediaRecorder.setMaxDuration(9000); // Set max duration 60 sec.
       mediaRecorder.setMaxFileSize(50000000); // Set max file size 5M
      

       
}

private void prepareMediaRecorder(){
 mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
 try {
  mediaRecorder.prepare();
 } catch (IllegalStateException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 } catch (IOException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 }
}



void myfunction()
{
    new CountDownTimer(11 * 1000, 1000) {
        int x = 10;

        @Override
        public void onTick(long millisUntilFinished) {
            tv1.setText(Integer.toString(x));
            x--;

        }

        @Override
        public void onFinish() 
        {
        	myfunction();
        
        }
        
      
    }.start();	  
  
    tv.setText("");
    mediaRecorder.start();
    recording = true;
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {

       public void run() 
       {
 		mediaRecorder.stop();
 		mediaRecorder.release();
 		Intent intent = new Intent(AndroidVideoCapture.this,ReadJson.class);
 		//intent.setData(Uri.fromFile(file));
 		startActivity(intent);
     	  finish();

       }

    }, 11000);

}
}