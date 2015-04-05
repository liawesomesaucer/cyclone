package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "R6IOvMMG2EG4m6C1IG3axXiaN91xPKX2PxUsEGku", "Bh3yQzOPdIeHac4fzuT4KjMuJMTmkb6c3s5oq2AQ");
    ParseUser.enableRevocableSessionInBackground();
    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
