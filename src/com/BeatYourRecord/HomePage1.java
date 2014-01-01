package com.BeatYourRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
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


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage1 extends FragmentActivity {
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
            //onSessionStateChange(session, state, exception);
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
	
	
	/** Called when the activity is first created. */
	List<Cookie> cookies;
	String result="";
	String login="";
	String output11="";
	String email="";
	String store="";
	String test="";
	String test2="";
	final int CONTACT_PICKER_RESULT = 1001;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Bundle b = getIntent().getExtras();
		//login = b.getString("auth");
		//Log.v("Authhere",login);
	 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage1);
		TextView meet = (TextView) findViewById(R.id.terms_text);
        meet.setText(Html.fromHtml("By clicking play now I agree to Beat Your Record's " +
         		 "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/toc'><font color='#009900'>Terms and Conditions</font></a>" + " , " + "<a href='http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/privacy'>Privacy Policy</a> "+ "and "+ 		"<a href='http://www.youtube.com/static?template=terms'>YouTube's Terms and Conditions</a>"));
        meet.setClickable(true);
        meet.setMovementMethod(LinkMovementMethod.getInstance());
        Button b = (Button) findViewById(R.id.button1);
        Button b1 = (Button) findViewById(R.id.button2);
        Button b2 = (Button) findViewById(R.id.button3);
        Button b3 = (Button) findViewById(R.id.button4);
        try {
			differentthread();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i1 = new Intent(HomePage1.this, CarouselDemoActivity.class);
				SharedPreferences pref1 = HomePage1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			   	Editor editor = pref1.edit();
	        // editor.putString("tour", "http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
	         //editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
	        editor.putString("log", "no"); 
	         editor.commit();
			
				startActivity(i1);
				//finish();
				
			}
		});
		 
 b1.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent i1 = new Intent(HomePage1.this, VideoDemo1.class);
	        Session session = Session.getActiveSession();
    		session.closeAndClearTokenInformation();
			startActivity(i1);
			//finish();
			
		}
	});
 b2.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
	        Session session = Session.getActiveSession();
    		session.closeAndClearTokenInformation();
			SharedPreferences pref1 = HomePage1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
		   	Editor editor = pref1.edit();
        // editor.putString("tour", "http://ec2-54-244-107-102.us-west-2.compute.amazonaws.com/tournaments/520192c41a5932f6a6000001.json"); // Storing string
         //editor.putString("id", "520192c41a5932f6a6000001"); // Storing string
        editor.putString("log", "yes");
        editor.putString("page", "yes");
         editor.commit();
			Intent i1 = new Intent(HomePage1.this, TournamentPage3.class);
			
		
			startActivity(i1);
			//finish();
			
		}
	}); 
 
 
 b3.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
	        Session session = Session.getActiveSession();
    		session.closeAndClearTokenInformation();
			 SharedPreferences pref = HomePage1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			Editor editor = pref.edit();
		  	String score = pref.getString("score", null);
		  	//Log.v("my score",score);
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
		            Contacts.CONTENT_URI);  
		    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

		    
		    
			
		}
	}); 

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
         HomePage1.this.user = user;
         updateUI();
         // It's possible that we were waiting for this.user to be populated in order to post a
         // status update.
         handlePendingAction();
     }
 });

 



 postStatusUpdateButton = (Button) findViewById(R.id.postStatusUpdateButton);
 postStatusUpdateButton.setEnabled(false);
 postStatusUpdateButton.setOnClickListener(new View.OnClickListener() {
     public void onClick(View view) {
         onClickPostStatusUpdate();
     }
 });

 
 controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);

 final FragmentManager fm = getSupportFragmentManager();
 Fragment fragment = fm.findFragmentById(R.id.fragment_container);
 if (fragment != null) {
     // If we're being re-created and have a fragment, we need to a) hide the main UI controls and
     // b) hook up its listeners again.
     controlsContainer.setVisibility(View.GONE);
     if (fragment instanceof FriendPickerFragment) {
         setFriendPickerListeners((FriendPickerFragment) fragment);
     } else if (fragment instanceof PlacePickerFragment) {
         setPlacePickerListeners((PlacePickerFragment) fragment);
     }
 }

 // Listen for changes in the back stack so we know if a fragment got popped off because the user
 // clicked the back button.
 fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
     @Override
     public void onBackStackChanged() {
         if (fm.getBackStackEntryCount() == 0) {
             // We need to re-show our UI.
             controlsContainer.setVisibility(View.VISIBLE);
         }
     }
 });


 // Listen for changes in the back stack so we know if a fragment got popped off because the user
 // clicked the back button.
 

 canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
         FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
	}
	
	
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

	  /*  @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	    }*/

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
	                new AlertDialog.Builder(HomePage1.this)
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
	        postStatusUpdateButton.setEnabled(enableButtons );
	       
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
	            //Log.v("Message",message);
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
	    	 SharedPreferences pref = getApplicationContext().getSharedPreferences("TourPref", 0); // 0 - for private mode
	           Editor editor = pref.edit();
			  	String youtubelink = pref.getString("myyoutubelink", null);

	        return new FacebookDialog.ShareDialogBuilder(this)
	        	
	                .setName("My Video Using Beat Your Record")
	                .setDescription("Beat my record if you can")
	                .setLink(youtubelink);
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
/*
	    private void postStatusUpdate() {
	        if (canPresentShareDialog) {
	        	Log.v("here","hre");
	            final String message = "You Score so many shots @Meet";
	            Request request = Request
	                    .newStatusUpdateRequest(Session.getActiveSession(), message, place, tags, new Request.Callback() {
	                        @Override
	                        public void onCompleted(Response response) {
	                            showPublishResult(message, response.getGraphObject(), response.getError());
	                        }
	                    });
	            request.executeAsync();
	            //FacebookDialog shareDialog = createShareDialogBuilder().build();
	            
	            //uiHelper.trackPendingDialogCall(shareDialog.present());
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
*/
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
	                Toast toast = Toast.makeText(HomePage1.this, text, Toast.LENGTH_SHORT);
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
	    
	 protected Dialog onCreateDialog(int id) {
		    switch (id) {
		    case 10:
		      // Create out AlterDialog
		      /*Builder builder = new AlertDialog.Builder(this);
		     // builder.setMessage("By clicking 'Submit,' you are representing that this video does not violate YouTube’s Terms of Service located at http://www.youtube.com/t/terms and that you own all rights to the copyrights in this video or have authorization to upload it. ");
		      builder.setMessage("You have an invite from xyz");

		      builder.setCancelable(true);
		    
		      AlertDialog dialog = builder.create();
		      dialog.show();*/
		    }
		    return super.onCreateDialog(id);
		  }

	 @SuppressWarnings("deprecation")
	 private void sendSMS(String phoneNumber, String message)
	 {        
	     //Log.v("phoneNumber",phoneNumber);
	     //Log.v("MEssage",message);
	               
	     SmsManager sms = SmsManager.getDefault();
	     sms.sendTextMessage(phoneNumber, null, message, null, null);

	     //sms.sendTextMessage(phoneNumber, null, message, pi, null);        
	 }    

	 public void doLaunchContactPicker(View view) {  
	     Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
	             Contacts.CONTENT_URI);  
	     startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);  
	 }  
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		 super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	     if (resultCode == RESULT_OK) {  
	         switch (requestCode) {  
	         case CONTACT_PICKER_RESULT:  
	             Cursor cursor = null;  
	             try {  
	                 Uri result = data.getData();  
	                 //Log.v("lol", "Got a contact result: "   + result.toString());  
	                 // get the contact id from the Uri  
	                 String id = result.getLastPathSegment();  
	                 // query for everything email
	                 
	                 
	                 
	                 
	                 cursor = getContentResolver().query(Phone.CONTENT_URI,  
	                         null, Phone.CONTACT_ID + "=?", new String[] { id },  
	                         null);  
	                 int emailIdx = cursor.getColumnIndex(Phone.DATA);  
	                 // let's just get the first email  
	                 if (cursor.moveToFirst()) {  
	                     email = cursor.getString(emailIdx);  
	                     //Log.v("lol", "Got email: " + email);  
	                     Button myButton = new Button(this);
	                     myButton.setText("Invite : " +email);

	                     LinearLayout ll = (LinearLayout)findViewById(R.id.meet);
	                     LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	                     ll.addView(myButton, lp);
	                     anotherfunction(myButton,email);
	                    
	                 } else {  
	                     Log.w("lol", "No results");  
	                 }  
	             } catch (Exception e) {  
	                 Log.e("lol", "Failed to get email data", e);  
	             } finally {  
	                 if (cursor != null) {  
	                     cursor.close();  
	                 }  
	                 //EditText emailEntry = (EditText) findViewById(R.id.invite_email);  
	                 //emailEntry.setText(email);  
	                 if (email.length() == 0) {  
	                     Toast.makeText(this, "No email found for contact.",  
	                             Toast.LENGTH_LONG).show();  
	                 }  
	             }  
	             break;  
	         }  
	     } else {  
	         Log.w("lol", "Warning: activity result not ok");  
	     }  
	 }  
	 void anotherfunction(Button myButton,final String email)
	 {
	 	 myButton.setOnClickListener(new View.OnClickListener() {		
	 			public void onClick(View v) {
	 				sendSMS(email, "Hi You got a message!");

	 			}
	 		});	
	 	
	 }
	 void differentthread() throws ClientProtocolException, IOException, JSONException
	 {
		 SharedPreferences pref = HomePage1.this.getSharedPreferences("TourPref", 0); // 0 - for private mode
			Editor editor = pref.edit();
		  	String userid = pref.getString("user_id", null);
		  	//Log.v("my score",userid);
		  	
	        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("Tester15", 0); // 0 - for private mode
  			String auth = pref1.getString("BYR_session", null);
  			
		  	String link = "http://ec2-54-212-221-3.us-west-2.compute.amazonaws.com/api/v3/users/"+userid+"/invitations/check?auth_token="+auth;
		  	//Log.v("link",link);
		  	
		  	HttpGet get = new HttpGet(link);
		  	get.setHeader("Content-type", "application/json");
		  	get.setHeader("Accept", "application/json");

			HttpClient client = new DefaultHttpClient();
			InputStream inputStream = null;
			String result = null;
			try {
				HttpResponse response = client.execute(get);
			    HttpEntity entity = response.getEntity();

				String result1 = EntityUtils.toString(response.getEntity()); 
				//Log.v("Res",result1);
			    inputStream = entity.getContent();
			    // json is UTF-8 by default
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			    StringBuilder sb = new StringBuilder();

			    String line = null;
			    while ((line = reader.readLine()) != null)
			    {
			        sb.append(line + "\n");
			    }
			    result = sb.toString();
			} catch (Exception e) { 
			    // Oops
			}
			finally {
			    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
			}
			 InputStream is = null;
		     JSONObject jObj = null;
		     String json = "";
			try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
			  	HttpGet get1 = new HttpGet(link);
	 
	            HttpResponse httpResponse = httpClient.execute(get1);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();           
	 
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	         
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            json = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	 
	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(json);
	            //Log.v("Find match", jObj.getJSONObject("data").getString("invitations"));
	            if(jObj.getJSONObject("data").getString("invitations").length()>4)
	            	showDialog(10);
	            	//Log.v("Show dia","Show dia");
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	        
	 }
	 
}