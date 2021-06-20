package com.vikas.secret.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vikas.secret.ui.maps.MapsCallbacks;

import java.util.Objects;

public class MapsRepository {

    private final FirebaseFirestore db;
    private static MapsRepository instance = null;
    private final String TAG = "GALLERY_REPO";
    private final static String CHATPERSONLOCATION = "chatpersonlocation";

    private MapsRepository() {
        this.db = FirebaseFirestore.getInstance();;
    }

    public static MapsRepository getInstance() {
        if(instance == null) {
            instance = new MapsRepository();
        }
        return instance;
    }

    public void getChatPersonLocation(String userid, MapsCallbacks callbacks) {
        db.collection(CHATPERSONLOCATION).document(userid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        LatLng targetLocation = new LatLng(Double.parseDouble(Objects.requireNonNull(documentSnapshot.get("latitude")).toString()), Double.parseDouble(Objects.requireNonNull(documentSnapshot.get("longitude")).toString()));
                        callbacks.onGetChatPersonLocation(targetLocation);
                    }
                })
                .addOnFailureListener(e -> {

                });
    }
}
