package com.sahasu.lazypizza;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by AnamBhatia on 24/05/17.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser(); //get currentto get uid
        if(user!=null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()); // create a reference to userUid in database

            if (refreshedToken != null) //
            {  mDatabase.child("token").setValue(refreshedToken); //creates a new node of user's token and set its value to true.

            }
            else
                Log.i(TAG, "onTokenRefresh: token was null");
            Log.d(TAG, "Refreshed token: " + refreshedToken);
        }

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}
