package com.vikas.secret.data;

import com.google.firebase.firestore.FirebaseFirestore;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.ui.chat.ChatCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatRepository {
    private final FirebaseFirestore db;
    private final String TAG = "CHAT_REPO";
    private final static String CHATPERSON = "chatperson";
    private final static String MESSAGES = "messages";

    public ChatRepository() {
        this.db = FirebaseFirestore.getInstance();;
    }

    public void getChatPersonUUID(String userid, ChatCallbacks callbacks) {
        db.collection(CHATPERSON).document(userid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists())
                        callbacks.onGetChatPersonCallback(Objects.requireNonNull(documentSnapshot.get("personId")).toString());
                })
                .addOnFailureListener(e -> {

                });
    }

    public void getChatByUUID(String chatId, ChatCallbacks callbacks) {
        db.collection(MESSAGES).document(chatId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        List<MessageModel> messages = new ArrayList<>();
                        //TODO: parse data to list
                        callbacks.onGetMessageList(messages);
                    }
                })
                .addOnFailureListener(e -> {

                });
    }


}
