package com.vikas.secret.ui.login;

import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.vikas.secret.R;
import com.vikas.secret.data.GmailLoginRepository;

public class GmailLoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private GmailLoginRepository gmailLoginRepository;

    GmailLoginViewModel(GmailLoginRepository gmailLoginRepository) {
        this.gmailLoginRepository = gmailLoginRepository;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(Activity activity, String defaultWebClientId) {
        gmailLoginRepository.login(activity, defaultWebClientId);

        gmailLoginRepository.getFirebaseUser().observe((LifecycleOwner) activity, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(@Nullable FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    loginResult.setValue(new LoginResult(new LoggedInUserView(firebaseUser.getDisplayName())));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }
        });
    }

    public void logout(Activity activity) {
        gmailLoginRepository.logout(activity);
    }

    public void revokeAccess(Activity activity) {
        gmailLoginRepository.revokeAccess(activity);
    }
}
