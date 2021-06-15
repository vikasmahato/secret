package com.vikas.secret;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.vikas.secret.data.UserRepository;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository;

    public MainViewModel() {
        userRepository = new UserRepository();
    }

    public void saveUser(FirebaseUser user) {
        try {
            userRepository.saveUser(user);
        } catch (Exception e) {
            Log.e("USER_SAVE", "Exception", e);
        }
    }
}
