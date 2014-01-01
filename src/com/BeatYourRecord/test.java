package com.BeatYourRecord;

import java.io.File;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class test extends Activity {
    ImageButton imageItem;
    int i=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("hre","hre");
        setContentView(R.layout.test);
        imageItem  = (ImageButton) findViewById(R.id.imageView1);
        imageItem.setImageResource(R.drawable.basketball);

        final TypedArray puppyResourcesTypedArray = getResources().obtainTypedArray(R.array.puppies_array);
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
	            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));
	            i++;
	            if(i==puppyResourcesTypedArray.length()-1)
	            	i=0;

				
			}
		});
        imageItem.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i1 = new Intent(test.this, ReadJson2.class);	
    			startActivity(i1);
    			

				
			}
		});
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
	            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));
	            i--;
	            if(i==-1)
	            	i=puppyResourcesTypedArray.length()-1;

				
			}
		});


          		
    }}	