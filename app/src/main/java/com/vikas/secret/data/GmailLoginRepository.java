package com.vikas.secret.data;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GmailLoginRepository {
    private static volatile GmailLoginRepository instance;

    private static final String TAG = "LOGIN";

    private MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    private GmailLoginRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public static GmailLoginRepository getInstance() {
        if (instance == null) {
            instance = new GmailLoginRepository();
        }
        return instance;
    }

    public void login(Activity activity, String defaultWebClientId) {

        AuthCredential credential = GoogleAuthProvider.getCredential(defaultWebClientId, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        firebaseUser.setValue(user);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        firebaseUser.setValue(null);
                    }
                });
    }

    public void logout(Activity activity) {
        mAuth.signOut();
    }

    public void revokeAccess(Activity activity) {
        mAuth.signOut();
    }
}
