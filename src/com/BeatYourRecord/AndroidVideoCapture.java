package com.BeatYourRecord;


import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;




import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AndroidVideoCapture extends Activity implements SurfaceHolder.Callback 
{
	 private PowerManager.WakeLock wl;

	File dir;
	MediaPlayer mp1;
	MediaPlayer mp2;
	MediaPlayer mp3;
	public static final String LOGTAG = "VIDEOCAPTURE";
	private MediaRecorder recorder;
	private SurfaceHolder holder;
	private CamcorderProfile camcorderProfile;
	private Camera camera;	
	boolean recording = false;
	boolean used5= false;
	boolean usecamera = true;
	boolean previewRunning = false;
	TextView tv2 ;
	boolean isclicked=false;
	ImageButton myButton;
	TextView tv ;
	String auth="";
	String filepath="";
	TextView tv1 ;
	long time=0;
	boolean used = false;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		SharedPreferences pref1 = AndroidVideoCapture.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	   	Editor editor22 = pref1.edit();
	   	char time1 = pref1.getString("time", null).charAt(0);
	   	time = Long.parseLong(String.valueOf(time1));
	   	Log.v("time",String.valueOf(time));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
		setContentView(R.layout.main2);
		SurfaceView cameraView = (SurfaceView) findViewById(R.id.CameraView);
		holder = cameraView.getHolder();
		 PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
         wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNjfdhotDimScreen");
         wl.acquire();
 //End of onCreate
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		recording=false;
		  SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref469", 0); // 0 - for private mode
	       	Editor editor = pref.edit();
	       	
	      
	      // 	auth = pref.getString("BYR_session", null);

		 this.tv = (TextView) findViewById(R.id.result);
	      this.tv1 = (TextView) findViewById(R.id.result1);
	      this.tv2 = (TextView) findViewById(R.id.result2);

			 tv2.setText("Click the red button to start your 5 second countdown. Once you hear a beep your recording will start.");
	       this.myButton = (ImageButton)super.findViewById(R.id.recordButton);
	       File k;
			dir = new File(Environment.getExternalStorageDirectory() +"/BeatYourRecord/");
			if(!dir.exists())
				dir.mkdirs();
		       myButton.setOnClickListener(myButtonOnClickListener);


	}
	   private Button.OnClickListener myButtonOnClickListener
	   = new Button.OnClickListener(){
	public void onClick(View v) 
	{
		myButton.setEnabled(false);
		if (recording) 
		{
			//Log.v("test","test");
			//mp3.stop();
			isclicked=true;
			//mp3.release();
			SharedPreferences pref1 = AndroidVideoCapture.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
		   	Editor editor = pref1.edit();
		   	int time = String.valueOf(pref1.getString("time", null)).charAt(0);
		    editor.putString("filepath", filepath); 
            editor.commit();
        // editor.putString("tour", "http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
         //editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
          
	 		Intent intent = new Intent(AndroidVideoCapture.this,DetailsActivity.class);
	    	//	 intent.putExtra(DbHelper.YTD_DOMAIN, "TODO-appname.appspot.com");
	    		//Bundle b = new Bundle();
	    		
	    	//	b.putString("filepath", filepath);
	    	//	intent.putExtras(b);
	     
	     		//intent.setData(Uri.fromFile(file));	
	     		startActivity(intent);
	     		
	         	  finish();
			
		} 
		else 
		{	
			tv2.setText("");
			Log.v("ere","rere");
            used=true;
            
			 new CountDownTimer(6 * 1000, 1000) {
		            int x = 5;
		            
		            @Override
		            public void onTick(long millisUntilFinished) {
		            	
		                tv.setText(" "+Integer.toString(x));
		                x--;
		            if(x==4)
		            {
		            	mp1=MediaPlayer.create(getBaseContext(), R.raw.countdown);  
	                mp1.start();
		            }
		         
		            }

		            @Override
		            public void onFinish() 
		            {
		            	/*((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_ALARM,true);
		            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_DTMF,true);
		            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC,true);
		            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING,true);
		            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM,true);
		            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_VOICE_CALL,true);*/
		            	//mp2=MediaPlayer.create(getBaseContext(), R.raw.beep);  
		                //mp2.start();
		                myButton.setEnabled(true);
			            	myfunction();
			            	Log.v("gotcha","gotcha");
			            
		            	Log.v("work,","work");
		            }
		            
		          
		        }.start();	  

		}
		
	}
	   };
	private void prepareRecorder() 
	{
        recorder = new MediaRecorder();
		recorder.setPreviewDisplay(holder.getSurface());	
		if (usecamera) 
		{
			camera.unlock();
			recorder.setCamera(camera);
		}
		
		recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		recorder.setMaxDuration(60000);
		recorder.setProfile(camcorderProfile);

		// This is all very sloppy
		if (camcorderProfile.fileFormat == MediaRecorder.OutputFormat.THREE_GPP) 
		{
        	try 
        	{
				File newFile = File.createTempFile("Meet", ".3gp", Environment.getExternalStorageDirectory());

        		File temp  = new File(dir.getAbsolutePath(), new SimpleDateFormat(
		                "'BYR_tournName_dateTim_'yyyyMMddHHmmss'.3gp'").format(new Date()));
				filepath = temp.getAbsolutePath();

				recorder.setOutputFile(filepath);
			} 
        	catch (IOException e) 
        	{
				Log.v(LOGTAG,"Couldn't create file");
				e.printStackTrace();
				finish();
			}
		}
		else if (camcorderProfile.fileFormat == MediaRecorder.OutputFormat.MPEG_4) 
		{
        	try 
        	{
        		Log.v("uses this","uses this");
				File newFile = File.createTempFile("Meet", ".mp4", Environment.getExternalStorageDirectory());
				File temp  = new File(dir.getAbsolutePath(), new SimpleDateFormat(
		                "'BYR_tournName_dateTim_'yyyyMMddHHmmss'.mp4'").format(new Date()));
				filepath = temp.getAbsolutePath();
				recorder.setOutputFile(filepath);
				//recorder.setOutputFile(Environment.getExternalStorageDirectory() +"dir.getAbsolutePath()Meet.mp4");

			} 
        	catch (IOException e) 
        	{
				Log.v(LOGTAG,"Couldn't create file");
				e.printStackTrace();
				finish();
			}
		} 
		else 
		{
        	try 
        	{
				File newFile = File.createTempFile("Meet", ".mp4", Environment.getExternalStorageDirectory());

        		File temp  = new File(dir.getAbsolutePath(), new SimpleDateFormat(
		                "'BYR_tournName_dateTim_'yyyyMMddHHmmss'.mp4'").format(new Date()));
				filepath = temp.getAbsolutePath();

				recorder.setOutputFile(filepath);
				//recorder.setOutputFile(Environment.getExternalStorageDirectory() +"/BeatYourRecord/Meet.mp4");

			} 
        	catch (IOException e) 
        	{
				Log.v(LOGTAG,"Couldn't create file");
				e.printStackTrace();
				finish();
        	}
		}
		//recorder.setMaxDuration(50000); // 50 seconds
		//recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
		
		try 
		{
			recorder.prepare();
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
			finish();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			finish();
		}
	}
		public void surfaceCreated(SurfaceHolder holder) 
	{
		Log.v(LOGTAG, "surfaceCreated");	
		if (usecamera) 
		{
			camera = Camera.open();	
			try 
			{
				camera.setPreviewDisplay(holder);
				camera.startPreview();
				previewRunning = true;
			}
			catch (IOException e) 
			{
				Log.e(LOGTAG,e.getMessage());
				e.printStackTrace();
			}	
		}		
		
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		Log.v(LOGTAG, "surfaceChanged");

		if (!recording && usecamera) 
		{
			if (previewRunning)
			{
				camera.stopPreview();
			}

			try 
			{
				//Camera.Parameters p = camera.getParameters();

				 //p.setPreviewSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
			     //p.setPreviewFrameRate(camcorderProfile.videoFrameRate);
			     Camera.Parameters parameters = camera.getParameters();  
			     List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();  
			     Camera.Size cs = sizes.get(0);  
			     parameters.setPreviewSize(cs.width, cs.height);  
			     camera.setParameters(parameters);
				//camera.setParameters(p);
				
				camera.setPreviewDisplay(holder);
				camera.startPreview();
				previewRunning = true;
			}
			catch (IOException e) 
			{
				Log.e(LOGTAG,e.getMessage());
				e.printStackTrace();
			}	
			
			prepareRecorder();	
		}
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		wl.acquire();
		Log.v(LOGTAG, "surfaceDestroyed");
		
		if(used==true)
		{
		mp1.stop();
		mp1.release();
		//mp2.stop();
		//mp2.release();
		mp3.stop();
		mp3.release();
		}
		if (recording) {
			recorder.stop();
			
			recording = false;
		}
		recorder.release();
		if (usecamera) {
			previewRunning = false;
			//camera.lock();
			 camera.stopPreview();
		     camera.setPreviewCallback(null);
			 camera.release();
		}
		finish();
	}
	
	void myfunction()
	{
		
		//mp2=MediaPlayer.create(getBaseContext(), R.raw.beep);  
	    //mp2.start();
	    recorder.start();
	   /* ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_ALARM,false);
	    ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_DTMF,false);
	    ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC,false);
	    ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING,false);
	    ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM,false);
	    ((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_VOICE_CALL,false);*/
	    recording = true;

    
    	mp3=MediaPlayer.create(getBaseContext(), R.raw.countdown);  
    	
	    new CountDownTimer(((time*10) +1) * 1000, 1000) {
	        int x = (int) ((time*10)-1);

	        @Override
	        public void onTick(long millisUntilFinished) {
	            tv1.setText(Integer.toString(x));
	            x--;
	            if(x==4 && isclicked==false)
	            {

                    mp3.start();

	            }

	        }

	        @Override
	        public void onFinish() 
	        {
	            if( isclicked==false)
	            {
	        	/*((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_ALARM,true);
            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_DTMF,true);
            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_MUSIC,true);
            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_RING,true);
            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM,true);
            	((AudioManager)AndroidVideoCapture.this.getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_VOICE_CALL,true);*/
	        	used5=true;
	        	//mp3.stop();
	    		//mp3.release();
	        	//final MediaPlayer mp4=MediaPlayer.create(getBaseContext(), R.raw.beep);  
                //mp4.start();
	        	//recorder.stop();
	     		//recorder.release();
	     		//camera.release();
	     		  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse
	     	    		   ("file://" + Environment.getExternalStorageDirectory())));
	     	       Log.v("here33","here33");
	     	      SharedPreferences pref1 = AndroidVideoCapture.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
	  		   	Editor editor = pref1.edit();
	  		    editor.putString("filepath", filepath); 
	              editor.commit();
	     		Intent intent = new Intent(AndroidVideoCapture.this,DetailsActivity.class);
	    	//	 intent.putExtra(DbHelper.YTD_DOMAIN, "TODO-appname.appspot.com");
	    		
	     
	     		//intent.setData(Uri.fromFile(file));	
	     		startActivity(intent);
	     		
	         	 // finish();
	            }
	        }
	        
	      
	    }.start();	  
	    tv.setText("");
	    Timer timer = new Timer();
	 

		}
	
	
}
