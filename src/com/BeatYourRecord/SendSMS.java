package com.BeatYourRecord;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;  
import android.provider.ContactsContract.CommonDataKinds.Email;  
public class SendSMS extends Activity {
Button btnSendSMS;
final int CONTACT_PICKER_RESULT = 1001;  
String email = "";  

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setContentView(R.layout.sms);
btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
btnSendSMS.setOnClickListener(new View.OnClickListener()
{
	public void onClick(View v)
	{
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,  
	            Contacts.CONTENT_URI);  
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	//sendSMS("3106510207", "Hi You got a message!");
	}
});
}
//---sends an SMS message to another device---

@SuppressWarnings("deprecation")
private void sendSMS(String phoneNumber, String message)
{        
    Log.v("phoneNumber",phoneNumber);
    Log.v("MEssage",message);
              
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
    if (resultCode == RESULT_OK) {  
        switch (requestCode) {  
        case CONTACT_PICKER_RESULT:  
            Cursor cursor = null;  
            try {  
                Uri result = data.getData();  
                Log.v("lol", "Got a contact result: "  
                        + result.toString());  
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
                    Log.v("lol", "Got email: " + email);  
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
}

