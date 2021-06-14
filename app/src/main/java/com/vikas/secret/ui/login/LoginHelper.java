package com.vikas.secret.ui.login;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class LoginHelper {
    public static GoogleSignInClient getGoogleSignInClient(Activity activity, String defaulWebClientId) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaulWebClientId)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso);

    }


}