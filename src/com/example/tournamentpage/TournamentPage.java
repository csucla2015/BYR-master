package com.example.tournamentpage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TournamentPage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button b = (Button) findViewById(R.id.button1);
        Drawable defaultDrawable = findViewById(R.id.button1).getBackground();
        //defaultDrawable=b.getDrawable();
        b.setBackgroundColor(Color.RED);
        //PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        
        //d.setColorFilter(filter);
      b.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), ReadJson.class);
				startActivity(i);
			}
		});
    }
}