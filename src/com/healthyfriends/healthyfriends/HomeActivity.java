package com.healthyfriends.healthyfriends;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthResultHandler;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class HomeActivity extends FragmentActivity 
{
	Button btnSignIn;
	EditText inputUserName, inputPassword;
	
    /***************************************
     *               GENERAL               *
     ***************************************/

    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    // Unique Key
    

	//Firebase ref = new Firebase("https://glowing-torch-7379.firebaseio.com/");
    Firebase ref;
    
    /* Data from the authenticated user */
    private AuthData authData;

    /* A tag that is used for logging statements */
    private static final String TAG = "HealthyFriends.HomeActivity";
	
    /***************************************
     *              FACEBOOK               *
     ***************************************/
    /* The login button for Facebook */
    private LoginButton mFacebookLoginButton;
    
    
	//HomeScreen obB=new HomeScreen();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     Firebase.setAndroidContext(this);
	     setContentView(R.layout.main);
	     
	     ref = new Firebase("https://glowing-torch-7379.firebaseio.com/");
	     
	     //final Dialog dialog = new Dialog(HomeActivity.this);
	     
	     // get the References of views
		    //inputUserName=(EditText)findViewById(R.id.editTextUserNameToLogin);
		    //inputPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
		    
		    //btnSignIn=(Button)findViewById(R.id.buttonSignIn);

	    	System.out.println("----- HomeActivity -----");
		    
		    /***************************************
             *              FACEBOOK               *
             ***************************************/
            // Load the Facebook login button and set up the session callback
	    	
            mFacebookLoginButton = (LoginButton)findViewById(R.id.login_with_facebook);
            mFacebookLoginButton.setSessionStatusCallback(new Session.StatusCallback() {
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                	System.out.println("preparing to call: onFacebookSessionStateChange(session, state, exception)");
                    onFacebookSessionStateChange(session, state, exception);
                }
            });
            
            
            

            /* Create the SimpleLogin class that is used for all authentication with Firebase */
            String firebaseUrl = getResources().getString(R.string.firebase_url);
            Firebase.setAndroidContext(getApplicationContext());
            ref = new Firebase(firebaseUrl);

            /* Setup the progress dialog that is displayed later when authenticating with Firebase */
            mAuthProgressDialog = new ProgressDialog(this);
            mAuthProgressDialog.setTitle("Loading");
            mAuthProgressDialog.setMessage("Authenticating with Firebase...");
            mAuthProgressDialog.setCancelable(false);
            mAuthProgressDialog.show();

            /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
             * user and hide hide any login buttons */
            ref.addAuthStateListener(new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    mAuthProgressDialog.hide();
                    setAuthenticatedUser(authData);

                }
                
                
            });
            
            
            
		    
	    // Set OnClick Listener on SignIn button
            /*
	    btnSignIn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		String userName=inputUserName.getText().toString();
	    		String password=inputPassword.getText().toString();
	    		
	    		
	    		//new LTLogin().execute(null, null, null);
	    		
	    		
	            
	    		
	    		
	    		// fetch the Password form database for respective user name
	    		String storedPassword="test";
			
	    		// check if the Stored password matches with  Password entered by user
	    		if(password.equals(storedPassword))
	    		{
	    			// here i call new screen;
	    			Intent i = new Intent(HomeActivity.this, MainActivity.class);
	    			i.putExtra("username",userName);
	    			startActivity(i);
	    		}
	    		else
	    		{
	    			Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
	    		}
	    		
			}
		});
		*/
		

	}
	
	@Override
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Session.getActiveSession()
	        .onActivityResult(this, requestCode, resultCode, data);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	
	

    /**
     * This method fires when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */
	/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
            // Otherwise, it's probably the request by the Facebook login button, keep track of the session 
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }
    */
	
	
	 private void setAuthenticatedUser(AuthData authData) {
	        if (authData != null) {
	        	System.out.println("authData: "+authData);
	        	String uid = authData.getUid();
            	Global global = Global.getInstance();
            	global.setData(uid);	
	            /* Hide all the login buttons */
              	    Intent i = new Intent(HomeActivity.this, MainActivity.class);
	    			//i.putExtra("username",userName);
	    			startActivity(i);
	            /* show a provider specific status text */
	            String name = null;
	            if (authData.getProvider().equals("facebook")
	                || authData.getProvider().equals("google")
	                || authData.getProvider().equals("twitter")) {
	                name = (String)authData.getProviderData().get("displayName");
	            } else if (authData.getProvider().equals("anonymous")
	                       || authData.getProvider().equals("password")) {
	                name = authData.getUid();
	                
	            } else {
	                Log.e(TAG, "Invalid provider: " + authData.getProvider());
	            }
	            if (name != null) {

	            }
	        } else {
	            /* No authenticated user show all the login buttons */
	            mFacebookLoginButton.setVisibility(View.VISIBLE);
	        }
	        this.authData = authData;
	        /* invalidate options menu to hide/show the logout button */
	        supportInvalidateOptionsMenu();
	    }

	
	
	/**
     * Once a user is logged in, take the authData provided from Firebase and "use" it.
     */
	/*
    private void setAuthenticatedUser(AuthData authData) {
    	System.out.println("setAuthenticatedUser");
        if (authData != null) {
        	System.out.println("authData != null");
            // Hide all the login buttons
            mFacebookLoginButton.setVisibility(View.GONE);
            // show a provider specific status text
            String name = null;
            if (authData.getProvider().equals("facebook")) 
            {
            	System.out.println("authData.getProvider().equals(\"facebook\")");
                name = (String)authData.getProviderData().get("displayName");
            } 
            else {
                Log.e(TAG, "Invalid provider: " + authData.getProvider());
            }
            if (name != null) {
                //mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
            }
        } else {
        	System.out.println("authData == null");
            // No authenticated user show all the login buttons
            mFacebookLoginButton.setVisibility(View.VISIBLE);
        }
        this.authData = authData;

    	System.out.println("end of setAuthenticatedUser");
        // invalidate options menu to hide/show the logout button
        //supportInvalidateOptionsMenu();
    }
	 */
	
    

    /**
     * Show errors to users
     */
	/*
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    */
    
    /**
     * Utility class for authentication results
     */
	/*
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }
    */
    


	private void onFacebookSessionStateChange(Session session, SessionState state, Exception exception) {
		System.out.println("onFacebookSesionStateChange");
		if (state.isOpened()) {
			System.out.println("state.isOpened()");
	        ref.authWithOAuthToken("facebook", session.getAccessToken(), new Firebase.AuthResultHandler() {
	            @Override
	            public void onAuthenticated(final AuthData authData) {
	                // The Facebook user is now authenticated with Firebase
	            	System.out.println("authData.getUid(): " + authData.getUid());

    	        	String uid = authData.getUid();
	            	Global global = Global.getInstance();
	            	global.setData(uid);	
	            	
	            	System.out.println("- global.getData(): "+ global.getData());
	            	System.out.println("- uid: "+ uid);
	            	
	            	System.out.println("Login Success");
	            	final Firebase myref = new Firebase("https://glowing-torch-7379.firebaseio.com/User/"+uid);
	            	
	            	myref.child("oAuth").addListenerForSingleValueEvent(new ValueEventListener() {
	            	    @Override
	            	    public void onDataChange(DataSnapshot snapshot) {
	            	        if (snapshot.getValue() != null) {
	            	        	System.out.println("snapshot.getValue() != null");
	            	        	String uid = authData.getUid();
	        	            	Global global = Global.getInstance();
	        	            	global.setData(authData.getUid());	            	       
	        	            } else {
	            	        	System.out.println("snapshot.getValue() == null");
	        	            	Map map = authData.getProviderData();
	        	            	String name = (String) map.get("displayName");
	        	            	String[] splitname = name.split(" "); // String 0 : first name, String 1 : second name
	        	            	System.out.println(map.toString());
	        	            	myref.child(authData.getUid());
	        	            	myref.child(authData.getUid()).child("first_name").setValue(splitname[0]);
	        	            	myref.child(authData.getUid()).child("last_name").setValue(splitname[1]);
	        	            	myref.child(authData.getUid()).child("email").setValue(splitname[0]+"@gmail.com");
	        	            	myref.child(authData.getUid()).child("score").setValue(0);
	        	            	myref.child(authData.getUid()).child("oAuth").setValue(11111);
	        	            	myref.child(authData.getUid()).child("friend");
	        	            	myref.child(authData.getUid()).child("items");
	        	            	
	        	            	Global global = Global.getInstance();
	        	            	global.setData(authData.getUid());	           
	        	            }
	            	    }
	            	    @Override
	            	    public void onCancelled(FirebaseError arg0) {
	            	    }
	            	});

	            	
	            	
	            	
	            	// here i call new screen;
	    			Intent i = new Intent(HomeActivity.this, MainActivity.class);
	    			//i.putExtra("username",userName);
	    			startActivity(i);
	            }

	            @Override
	            public void onAuthenticationError(FirebaseError firebaseError) {
	                // there was an error
	            	System.out.println("AuthenticationError");

	            }
	        });
	    } else if (state.isClosed()) {
			System.out.println("state.isClosed()");
	        /* Logged out of Facebook so do a logout from Firebase */
	        ref.unauth();
	    }
	    else
	    {
			System.out.println("state ELSE");	
	    }
	}
	
	
	
	
	
	
	
}
