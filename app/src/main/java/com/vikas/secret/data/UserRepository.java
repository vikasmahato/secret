package com.vikas.secret.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final FirebaseFirestore db;
    private final String TAG = "USER_REPO";

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUser(FirebaseUser user) {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();

                    Map<String, Object> data = new HashMap<>();
                    data.put("name", user.getDisplayName());
                    data.put("email", user.getEmail());
                    data.put("photo", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");
                    data.put("token", token);

                    db.collection("users").document(user.getUid()).set(data)
                            .addOnSuccessListener(unused -> Log.d(TAG, "DocumentSnapshot added with ID: " + user.getUid()))
                            .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                });


    }


}
