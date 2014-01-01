package com.BeatYourRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;

import com.facebook.widget.*;
import java.util.*;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.LoginButton;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.facebook.widget.ProfilePictureView;


import android.app.Activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import android.widget.EditText;
import android.widget.ImageButton;


public class TournamentPage4 extends FragmentActivity {
	//////////////////////////////////////////////////////////////////////////////////
/////////////////FACEBOOK///////////////////////////	
	//////////////////////////////////////////////////////////////////////////////////

    private static final String PERMISSION = "publish_actions";
    private static final Location SEATTLE_LOCATION = new Location("") {
        {
            setLatitude(47.6097);
            setLongitude(-122.3331);
        }
    };

    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";

    private Button postStatusUpdateButton;
    private Button postPhotoButton;
    private Button pickFriendsButton;
    private Button pickPlaceButton;
    private LoginButton loginButton;
    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;
    private boolean canPresentShareDialog;

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };
	
	
	
	//////////////////////////////////////////////////////////////////////////////////
/////////////////FACEBOOK///////////////////////////	
//////////////////////////////////////////////////////////////////////////////////
	
	
	String login=""; String password=""; String test=""; String test2=""; String auth="";
    boolean bad=false;
   	String invalid="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
       // Session session = Session.getActiveSession();
        //session.closeAndClearTokenInformation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        File f4 = new File(
          		"/data/data/com.BeatYourRecord/shared_prefs/TourPref.xml");
          		if (f4.exists())
          		{	  
          			  Log.v("rest","rest");
          			  SharedPreferences pref17 = TournamentPage4.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
          			  Editor editor17 = pref17.edit();
          	          invalid = pref17.getString("invalid", null) ;
          			  Log.v("androids a bitch",invalid);
          			  if(invalid.equals("Invalid Login Credentials"))
          			  {
	          		        Session session = Session.getActiveSession();
	                  		session.closeAndClearTokenInformation();

	          				editor17.putString("invalid", "nope");
	          				editor17.commit();
          				 // finish();
          			  }	  
          			  
          		}
          	
		        //TextView tv = (TextView) findViewById(R.id.error);
		       // tv.setText(invalid);
        ImageButton ctr = (ImageButton) findViewById(R.id.imageButton1);
        //PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        		   ctr.setOnClickListener(new View.OnClickListener() {
        				
        				public void onClick(View v) {

        					iregistered();
        				//	Intent i12 = new Intent(TournamentPage4.this, ReadJson2.class);
        				//	startActivity(i12);
        					
        					
        				}
        			});
        		   
        		   
       //////////////////////////////////////////////////////
       /////////////////////FACEBOOK/////////////////////////
       //////////////////////////////////////////////////////
        	        uiHelper = new UiLifecycleHelper(this, callback);
        	        uiHelper.onCreate(savedInstanceState);

        	        if (savedInstanceState != null) {
        	            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
        	            pendingAction = PendingAction.valueOf(name);
        	        }


        	        loginButton = (LoginButton) findViewById(R.id.login_button);
        	        
        	        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
        	            @Override
        	            public void onUserInfoFetched(GraphUser user) {
        	                TournamentPage4.this.user = user;
        	                updateUI();
        	                // It's possible that we were waiting for this.user to be populated in order to post a
        	                // status update.
        	                handlePendingAction();
        	            }
        	        });

        	        

        	      


        	       
        	        // Listen for changes in the back stack so we know if a fragment got popped off because the user
        	        // clicked the back button.
        	        

        	        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
        	                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
    }
    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////FACEBOOK///////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

       updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException)) {
                new AlertDialog.Builder(TournamentPage4.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
            handlePendingAction();
        }
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());
        Log.v("Please",session.getAccessToken());;
      
        if (enableButtons && user != null) {
            Log.v("Email","" + user.asMap().get("email"));
            Log.v("First","" + user.getFirstName());
            Log.v("First","" + user.getLastName());

            Log.v("Username",user.getUsername());
            Log.v("Usersid",user.getId());
            
            
            DefaultHttpClient client = new DefaultHttpClient();
        	//log.v("here12","here");
        	HttpPost post = new HttpPost("http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/registrations/facebook");
        	JSONObject holder = new JSONObject();
        	JSONObject userObj = new JSONObject();
        	JSONObject userObj1 = new JSONObject();
        	JSONObject userObj2 = new JSONObject();
        	JSONObject userObj4 = new JSONObject();
        	JSONObject userObj5 = new JSONObject();
        	JSONObject userObj6 = new JSONObject();
        	JSONObject userObj7 = new JSONObject();
        	JSONObject userObj8 = new JSONObject();
        	JSONObject userObj9 = new JSONObject();
        	JSONObject userObj10 = new JSONObject();
        	String response = null;
        	JSONObject json = new JSONObject();
        	try {
        	    try {
        	        json.put("success", false);
        	        json.put("info", "Something went wrong. Retry!");
        	        // add the user email and password to
        	        // the params
        	        String k="";
        	        String k1="";
        	        String k2="";
        	        SharedPreferences pref6 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
                	Editor editor6 = pref6.edit();
                 editor6.putString("Username", user.getUsername()); 
                	editor6.commit();
        		        userObj.put("email", user.asMap().get("email"));
        		        k = userObj.toString();
        		        k=k.substring(0, k.length()-1);
        		        k+=",";
        		        userObj4.put("username", user.getUsername());
        		        k1 = userObj4.toString();
        		        k1=k1.substring(1, k1.length()-1);
        		        k1+=",";
        		        k+=k1;
        		        userObj5.put("provider", "facebook");
        		        k1 = userObj5.toString();
        		        k1=k1.substring(1, k1.length()-1);
        		        k1+=",";
        		        k+=k1;
        		        userObj6.put("fb_token", session.getAccessToken());
        		        k1 = userObj6.toString();
        		        k1=k1.substring(1, k1.length()-1);
        		        k1+=",";
        		        k+=k1;
        		        userObj7.put("first_name", user.getFirstName());
        		        k1 = userObj7.toString();
        		        k1=k1.substring(1, k1.length()-1);
        		        k1+=",";
        		        k+=k1;
        		        userObj8.put("last_name", user.getLastName());
        		        k1 = userObj8.toString();
        		        k1=k1.substring(1, k1.length()-1);
        		        k1+=",";
        		        k+=k1;
        		    
        	        userObj2.put("uid", user.getId());
        	        k1 = userObj2.toString();
        	        k1=k1.substring(1, k1.length());
        	        k+=k1;	       
        	        Log.v("holder",k);
        	        StringEntity se = new StringEntity(k);
        	        post.setEntity(se);
        	       

        	        // setup the request headers
        	        post.setHeader("Content-Type", "application/json");
        	        post.setHeader("Accept", "application/json");
        	      //  post.setHeader("Content-Type", "application/json");

        	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        	        response = client.execute(post, responseHandler);
        	        json = new JSONObject(response);
        	        String test6=json.getString("success");
        	        Log.v("Succes is", test6);
        	        String test = json.getJSONObject("data").getString("auth_token");
        	        JSONObject test2 = json.getJSONObject("data");
        	        String test3 = test2.getJSONObject("user").getString("_id");
			        Log.v("test2",test3);
			        Log.v("rest2","ree");
        	        SharedPreferences pref = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
                	Editor editor = pref.edit();
                	editor.putString("BYR_session", test); // Storing string
                	auth=test;
                	editor.commit();
        	        Log.v("test1authinsub",test);
        	        editor.putString("BYR_session", test); // Storing string
    	        	editor.commit();
    	        	SharedPreferences pref1 = TournamentPage4.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
    			   	Editor editor1 = pref1.edit();
	    	        editor1.putString("log", "no"); 
	    	        editor1.putString("user_id", test3); 

    	         editor1.commit();
    	        Log.v("test1",test);

        	    } 
        	    catch (HttpResponseException e) 
        	    {		
        	    	finish();
        	    	SharedPreferences pref7 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
	            	Editor editor7 = pref7.edit();
	            	editor7.putString("invalid", "Invalid Login Credentials"); 
	            	editor7.commit();
        	    	bad=true;
        	        e.printStackTrace();
        	        Log.e("ClientProtocol meet", "" + e);
        	        Intent intent = getIntent();
        	        overridePendingTransition(0, 0);
        	        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        	        finish();

        	        overridePendingTransition(0, 0);
        	        startActivity(intent);
        	        json.put("info", "Email and/or password are invalid. Retry!");
        	    } 
        	    catch (IOException e) 
        	    {
        	    	finish();

        	        e.printStackTrace();
        	        Log.e("IO", "" + e);
        	    }
        	} 
        	catch (JSONException e) 
        	{
    	    	finish();

        	    e.printStackTrace();
        	    Log.e("JSON", "" + e);
        	}

        	if(bad!=true && session.isOpened())
        	{	
    	    	finish();

        		session.closeAndClearTokenInformation();
        		Log.v("here","lol");
    	    	Intent i12 = new Intent(TournamentPage4.this, CarouselDemoActivity.class);
    			startActivity(i12);
        	}

        } else {
        
        }
    }

    @SuppressWarnings("incomplete-switch")
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case POST_PHOTO:
                postPhoto();
                break;
            case POST_STATUS_UPDATE:
                postStatusUpdate();
                break;
        }
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

    private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
        String title = null;
        String alertMessage = null;
        if (error == null) {
            title = getString(R.string.success);
            String id = result.cast(GraphObjectWithId.class).getId();
            Log.v("Message",message);
            alertMessage = getString(R.string.successfully_posted_post, message, id);
        } else {
            title = getString(R.string.error);
            alertMessage = error.getErrorMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void onClickPostStatusUpdate() {
        performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
    }

    private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
        return new FacebookDialog.ShareDialogBuilder(this)
                .setName("Hello Facebook")
                .setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
                .setLink("http://developers.facebook.com/android");
    }

    private void postStatusUpdate() {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = createShareDialogBuilder().build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else if (user != null && hasPublishPermission()) {
            final String message = getString(R.string.status_update, user.getFirstName(), (new Date().toString()));
            Request request = Request
                    .newStatusUpdateRequest(Session.getActiveSession(), message, place, tags, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            showPublishResult(message, response.getGraphObject(), response.getError());
                        }
                    });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_STATUS_UPDATE;
        }
    }

    private void onClickPostPhoto() {
        performPublish(PendingAction.POST_PHOTO, false);
    }

    private void postPhoto() {
        if (hasPublishPermission()) {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
            Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), image, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
                }
            });
            request.executeAsync();
        } else {
            pendingAction = PendingAction.POST_PHOTO;
        }
    }

    private void showPickerFragment(PickerFragment<?> fragment) {
        fragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> pickerFragment, FacebookException error) {
                String text = getString(R.string.exception, error.getMessage());
                Toast toast = Toast.makeText(TournamentPage4.this, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        controlsContainer.setVisibility(View.GONE);

        // We want the fragment fully created so we can use it immediately.
        fm.executePendingTransactions();

        fragment.loadData(false);
    }

    private void onClickPickFriends() {
        final FriendPickerFragment fragment = new FriendPickerFragment();

        setFriendPickerListeners(fragment);

        showPickerFragment(fragment);
    }

    private void setFriendPickerListeners(final FriendPickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onFriendPickerDone(fragment);
            }
        });
    }

    private void onFriendPickerDone(FriendPickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String results = "";

        List<GraphUser> selection = fragment.getSelection();
        tags = selection;
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
            }
            results = TextUtils.join(", ", names);
        } else {
            results = getString(R.string.no_friends_selected);
        }

        showAlert(getString(R.string.you_picked), results);
    }

    private void onPlacePickerDone(PlacePickerFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

        String result = "";

        GraphPlace selection = fragment.getSelection();
        if (selection != null) {
            result = selection.getName();
        } else {
            result = getString(R.string.no_place_selected);
        }

        place = selection;

        showAlert(getString(R.string.you_picked), result);
    }

    private void onClickPickPlace() {
        final PlacePickerFragment fragment = new PlacePickerFragment();
        fragment.setLocation(SEATTLE_LOCATION);
        fragment.setTitleText(getString(R.string.pick_seattle_place));

        setPlacePickerListeners(fragment);

        showPickerFragment(fragment);
    }

    private void setPlacePickerListeners(final PlacePickerFragment fragment) {
        fragment.setOnDoneButtonClickedListener(new PlacePickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                onPlacePickerDone(fragment);
            }
        });
        fragment.setOnSelectionChangedListener(new PlacePickerFragment.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(PickerFragment<?> pickerFragment) {
                if (fragment.getSelection() != null) {
                    onPlacePickerDone(fragment);
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void performPublish(PendingAction action, boolean allowNoSession) {
        Session session = Session.getActiveSession();
        if (session != null) {
            pendingAction = action;
            if (hasPublishPermission()) {
                // We can do the action right away.
                handlePendingAction();
                return;
            } else if (session.isOpened()) {
                // We need to get new permissions, then complete the action when we get called back.
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSION));
                return;
            }
        }

        if (allowNoSession) {
            pendingAction = action;
            handlePendingAction();
        }
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
    	        Log.v("holder",k);
    	        StringEntity se = new StringEntity(k);
    	        post.setEntity(se);

    	        // setup the request headers
    	        post.setHeader("Content-Type", "application/json");
    	        post.setHeader("Accept", "application/json");
    	      //  post.setHeader("Content-Type", "application/json");
    	        
    	        
    	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
    	        response = client.execute(post, responseHandler);
    	        json = new JSONObject(response);
    	        String test6=json.getString("success");
    	        Log.v("Succes is", test6);
    	        String test = json.getJSONObject("data").getString("auth_token");
    	        JSONObject test2 = json.getJSONObject("data");
    	        String test3 = test2.getJSONObject("user").getString("_id");
		        Log.v("test2",test3);
		        Log.v("rest2","ree");
    	        SharedPreferences pref = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
            	Editor editor = pref.edit();
                editor.putString("BYR_session", test); // Storing string
                auth=test;
            	editor.commit();
    	        Log.v("test1authinsub",test);
    	        editor.putString("BYR_session", test); // Storing string
	        	editor.commit();
	        	SharedPreferences pref1 = TournamentPage4.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			   	Editor editor1 = pref1.edit();
	        
	        editor1.putString("log", "no"); 
	        editor1.putString("user_id", test3); 
	         editor1.commit();
	        Log.v("test1",test);

    	    } 
    	    catch (HttpResponseException e) 
    	    {	
    	    	SharedPreferences pref7 = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
        	Editor editor7 = pref7.edit();
         editor7.putString("invalid", "Invalid Login Credentials"); 
        	editor7.commit();
    	    	bad=true;
    	        e.printStackTrace();
    	        Log.e("ClientProtocol meet", "" + e);
    	      
    	        if(str1.equals(str2)==false)
    	        {
    	        	 Toast.makeText(getApplicationContext(), 
    	                     "Password don't match", Toast.LENGTH_LONG).show();
    	        }
    	        else
    	        {	
    	   	 Toast.makeText(getApplicationContext(), 
                     "Username/Email is in use", Toast.LENGTH_LONG).show();
    	        }
    	        Intent intent = getIntent();
    	        overridePendingTransition(0, 0);
    	        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	        finish();

    	        overridePendingTransition(0, 0);
    	        startActivity(intent);
    	   
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
    	if(bad!=true)
    	{	
	    	Intent i12 = new Intent(TournamentPage4.this, CarouselDemoActivity.class);
			startActivity(i12);
    	}
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////FACEBOOK STARTS//////////////////////////////////////////
    
    

    
    
    
    
    
    
    
    
    
    
    
    
}