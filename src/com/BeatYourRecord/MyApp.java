package com.BeatYourRecord;

import android.app.Application;

	class MyApp extends Application {

		   String myState="test";

		  public String getState(){
		    return myState;
		  }
		  public void setState(String s){
		    myState = s;
		  }
		}


