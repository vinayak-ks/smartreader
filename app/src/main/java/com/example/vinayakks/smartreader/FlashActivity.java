package com.example.vinayakks.smartreader;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseUser;

public class FlashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        Parse.initialize(this, "BIBd71T4bK7kdul5voshtNTrFDyVvo87CxmFz1UT", "8X8sECbLFS6BeURNH4PvTY2Uw0MWA77X7VvcDZjc");
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//             Intent i = new Intent(FlashActivity.this, LoginActivity.class);
//                startActivity(i);


                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    // do stuff with the user
                    startActivity(new Intent(FlashActivity.this, MainActivity.class));
                } else {
                    // show the signup or login screen
                    startActivity(new Intent(FlashActivity.this, MainActivity.class));
                }
                // close this activity

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
