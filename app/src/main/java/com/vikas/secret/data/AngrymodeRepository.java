package com.vikas.secret.data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vikas.secret.data.models.AngerModel;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.ui.angrymode.AngerCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AngrymodeRepository {
    private static AngrymodeRepository instance = null;

    private final FirebaseFirestore db;
    private final String TAG = "ANGRY_REPO";
    private final static String CHATPERSON = "chatperson";
    private final static String ANGRYMODE = "angrymode";

    private AngrymodeRepository() {
        this.db = FirebaseFirestore.getInstance();;
    }

    public static AngrymodeRepository getInstance() {
        if(instance == null) {
            instance = new AngrymodeRepository();
        }
        return instance;
    }

    public void getAngerList(String chatId, AngerCallbacks callbacks) {
        db.collection(ANGRYMODE).document(chatId).collection("angerinstances")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if(value == null || value.isEmpty())
                        return;
                    List<AngerModel> messages = new ArrayList<>();
                    for(DocumentSnapshot snapshot : value.getDocuments()) {
                        messages.add(new AngerModel(
                                Objects.requireNonNull(snapshot.get("uId")).toString(),
                                Objects.requireNonNull(snapshot.get("reason")).toString(),
                                Objects.requireNonNull(snapshot.get("description")).toString(),
                                Objects.requireNonNull(snapshot.get("fromDate")).toString(),
                                Objects.requireNonNull(snapshot.get("toDate")).toString(),
                                Long.valueOf(Objects.requireNonNull(snapshot.get("timestamp")).toString())
                        ));
                        callbacks.onGetAngerList(messages);
                    }
                });
    }


    public void sendMessage(MessageModel message, String messageId) {
        db.collection(ANGRYMODE).document(messageId).collection("angerinstances").add(message)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                }).addOnFailureListener(e -> {

        });
    }


}
