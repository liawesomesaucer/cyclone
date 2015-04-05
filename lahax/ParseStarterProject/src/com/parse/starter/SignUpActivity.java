package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SignUpActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String username;
    private String password;
    private String email;
    private User signupuser;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        buildGoogleApiClient();
        setUp();

    }
    private void setUp() {
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_text = (EditText) findViewById(R.id.username);
                EditText password_text = (EditText) findViewById(R.id.password);
                EditText email_text = (EditText) findViewById(R.id.email);
                username = username_text.getText().toString();
                password = password_text.getText().toString();
                email = email_text.getText().toString();
                signupuser = new User(email, password, username, (float) mLastLocation.getLongitude(), (float) mLastLocation.getLatitude());
                Requester requester = new Requester();
                requester.execute();
            }
        });
    }

    private void signUpUser() {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppostreq = new HttpPost("https://peaceful-falls-5727.herokuapp.com/create_user");

        try {
        Gson gson = new Gson();
        StringEntity se = new StringEntity(gson.toJson(signupuser));
        se.setContentType("application/json;charset=UTF-8");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
        httppostreq.setEntity(se);
        HttpResponse httpresponse = httpclient.execute(httppostreq);
        if (httpresponse.getEntity().getContentLength() > 0) {
            tutorActivity();
        }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void tutorActivity() {
        Intent intent = new Intent(SignUpActivity.this, TutorActivity.class);
        Gson gson = new Gson();
        intent.putExtra("User", gson.toJson(signupuser));
        startActivity(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        // Disconnect the client.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private class Requester extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            signUpUser();
            return null;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
