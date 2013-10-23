package com.example.tournamentpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishedRecording extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishedrecording);
       Button b =(Button) findViewById(R.id.btnLogin);
       Button b1 =(Button) findViewById(R.id.btnLogin1);
      //  TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
      b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent intent = new Intent(FinishedRecording.this,VideoDemo.class);
				startActivity(intent);
			}
		});
      //  TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
        // Listening to register new account link
      b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), AndroidVideoCapture.class);
				startActivity(i);
			}
		});
    }
}