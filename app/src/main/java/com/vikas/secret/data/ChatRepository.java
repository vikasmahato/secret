package com.vikas.secret.data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.ui.chat.ChatCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatRepository {

    private static ChatRepository instance = null;

    private final FirebaseFirestore db;
    private final String TAG = "CHAT_REPO";
    private final static String CHATPERSON = "chatperson";
    private final static String MESSAGES = "messages";

    private ChatRepository() {
        this.db = FirebaseFirestore.getInstance();;
    }

    public static ChatRepository getInstance() {
        if(instance == null) {
            instance = new ChatRepository();
        }
        return instance;
    }


    public void getChatPersonAndMessageID(String userid, ChatCallbacks callbacks) {
        db.collection(CHATPERSON).document(userid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        callbacks.onGetChatPersonCallback(Objects.requireNonNull(documentSnapshot.get("personId")).toString(), Objects.requireNonNull(documentSnapshot.get("messageId")).toString());
                })
                .addOnFailureListener(e -> {

                });
    }

    public void getChatByUUID(String chatId, ChatCallbacks callbacks) {
        db.collection(MESSAGES).document(chatId).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if(value == null || value.isEmpty())
                        return;
                    List<MessageModel> messages = new ArrayList<>();
                    for(DocumentSnapshot snapshot : value.getDocuments()) {
                        messages.add(new MessageModel(
                                Objects.requireNonNull(snapshot.get("uId")).toString(),
                                Objects.requireNonNull(snapshot.get("message")).toString(),
                                Long.valueOf(Objects.requireNonNull(snapshot.get("timestamp")).toString())
                        ));
                        callbacks.onGetMessageList(messages);
                    }
                });
    }


    public void sendMessage(MessageModel message, String messageId) {
        db.collection(MESSAGES).document(messageId).collection("messages").add(message)
        .addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

            }
        }).addOnFailureListener(e -> {

        });
    }
}
