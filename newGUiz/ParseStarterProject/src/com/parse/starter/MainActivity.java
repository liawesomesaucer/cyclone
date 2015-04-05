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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	/** Called when the activity is first created. */
    private String url = "https://frozen-tor-9289.herokuapp.com/";
    private User user;
    private User entered_user;
    private String entered_password;
    private String entered_email;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        buildGoogleApiClient();
        setUp();

    }

    public void setUp() {
        Button loginButton = (Button) findViewById(R.id.login_button);
        Button registerButton = (Button) findViewById(R.id.register_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StreamRetriever retriever = new StreamRetriever();
                EditText password_text = (EditText) findViewById(R.id.password);
                EditText email_text = (EditText) findViewById(R.id.email);
                entered_password = password_text.getText().toString();
                entered_email = email_text.getText().toString();
                entered_user = new User(entered_email, entered_password,(float) mLastLocation.getLongitude(), (float) mLastLocation.getLatitude());
                retriever.execute();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpActivity();
            }
        });
    }

    public void signUpActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void tutorActivity() {
        Intent intent = new Intent(MainActivity.this, TutorActivity.class);
        Gson gson = new Gson();
        intent.putExtra("User", gson.toJson(user));
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

    private class StreamRetriever extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost("https://peaceful-falls-5727.herokuapp.com/login_user");

            try {
                Gson gson = new Gson();
                StringEntity se = new StringEntity(gson.toJson(entered_user));
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                httppostreq.setEntity(se);
                HttpResponse httpresponse = httpclient.execute(httppostreq);
                if (httpresponse.getEntity().getContentLength() > 1) {
                    InputStream content = httpresponse.getEntity().getContent();
                    Reader reader = new InputStreamReader(content);
                    user = gson.fromJson(reader, User.class);
                    tutorActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Login Info", Toast.LENGTH_SHORT).show();
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
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
