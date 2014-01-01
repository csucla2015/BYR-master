package com.BeatYourRecord;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CarouselDemoActivity extends Activity {
    ImageButton imageItem;

    /**
     * Define the number of items visible when the carousel is first shown.
     */
    private static final float INITIAL_ITEMS_COUNT = 3.5F;

    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set title
        setTitle("test");

        // Set content layout
        setContentView(R.layout.activity_carousel_demo);

        // Get reference to carousel container
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
    }
  
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);

        // Get the array of puppy resources
        final TypedArray puppyResourcesTypedArray = getResources().obtainTypedArray(R.array.puppies_array);
        findViewById(R.id.button11).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		          Intent i1 = new Intent(CarouselDemoActivity.this, create.class);
					
		     	 
					startActivity(i1);
		      }
		    });
        // Populate the carousel with items
        int j=0;
        for (int i = puppyResourcesTypedArray.length()-1 ; i >=0 ; --i) {
            // Create new ImageView
            imageItem = new ImageButton(this);
            imageItem.setClickable(true);
            imageItem.setTag("coolyo");
           newfunction(imageItem,i);
                // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));
            // Set the size of the image view to the previously computed value
            //imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }
    }
 
    void setimage(ImageButton imageItem)
    {
        imageItem.setImageResource(R.drawable.freethrows);
     } 
    void newfunction(ImageButton imageItem,final int i)
    {
        Log.v("rer",String.valueOf(i));
        System.out.print("i " + i);
        imageItem.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    	        	if(i==0)
    	        	{
    	        		 SharedPreferences pref2 = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
    	        	        Editor editor2 = pref2.edit();
    	        	        String authtoke = pref2.getString("BYR_session", null);
    	        		 SharedPreferences pref177 = CarouselDemoActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    		 			  Editor editor177 = pref177.edit();
    		 			 editor177.putString("CategoryLink", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/categories/Speedpong?auth_token=gX1LnsuxZkSM5kMzLQGX") ;
    		 			 editor177.putString("CategoryName", "Beerpong") ;

    		 			 editor177.commit();

    		 			 Intent i1 = new Intent(CarouselDemoActivity.this, ReadJson2.class);	
        			startActivity(i1);}
    	        	if(i==1)
    	        	{
    	        		 SharedPreferences pref177 = CarouselDemoActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    		 			  Editor editor177 = pref177.edit();
    		 			 editor177.putString("CategoryLink", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/categories/Bestshots?auth_token=gX1LnsuxZkSM5kMzLQGX") ;
    		 			 editor177.putString("CategoryName", "Bestshots") ;

    		 			 editor177.commit();

    		 			 Intent i1 = new Intent(CarouselDemoActivity.this, ReadJson2.class);	
        			startActivity(i1);}
    	        	if(i==2)
    	        	{
    	        		 SharedPreferences pref177 = CarouselDemoActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    		 			  Editor editor177 = pref177.edit();
    		 			 editor177.putString("CategoryLink", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/categories/Darts?auth_token=gX1LnsuxZkSM5kMzLQGX") ;
    		 			 editor177.putString("CategoryName", "Darts") ;
  
    		 			 editor177.commit();

    		 			 Intent i1 = new Intent(CarouselDemoActivity.this, ReadJson2.class);	
        			startActivity(i1);}
    	        	if(i==3)
    	        	{
    	        		 SharedPreferences pref177 = CarouselDemoActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    		 			  Editor editor177 = pref177.edit();
    		 			 editor177.putString("CategoryLink", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/categories/Quarters?auth_token=gX1LnsuxZkSM5kMzLQGX") ;
    		 			 editor177.putString("CategoryName", "Quarters") ;
   
    		 			 editor177.commit();

    				Intent i1 = new Intent(CarouselDemoActivity.this, ReadJson2.class);	
        			startActivity(i1);}
    	        	
    	           	if(i==4)
    	        	{
    	        		 SharedPreferences pref177 = CarouselDemoActivity.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    		 			  Editor editor177 = pref177.edit();
    		 			 editor177.putString("CategoryLink", "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/tournaments/categories/Freethrows?auth_token=gX1LnsuxZkSM5kMzLQGX") ;
    		 			 editor177.putString("CategoryName", "Freethrows") ;
   
    		 			 editor177.commit();

    				Intent i1 = new Intent(CarouselDemoActivity.this, ReadJson2.class);	
        			startActivity(i1);}

    				
    			}
    		});
    
    }
    
}
