package com.vikas.secret.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vikas.secret.ui.maps.GalleryCallbacks;

import java.util.Objects;

public class GalleryRepository {

    private final FirebaseFirestore db;
    private final String TAG = "GALLERY_REPO";
    private final static String CHATPERSONLOCATION = "chatpersonlocation";

    public GalleryRepository() {
        this.db = FirebaseFirestore.getInstance();;
    }

    public void getChatPersonLocation(String userid, GalleryCallbacks callbacks) {
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
