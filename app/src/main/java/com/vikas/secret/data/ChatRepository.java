package com.vikas.secret.data;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.okhttp.ResponseBody;
import com.vikas.lib.ApiClient;
import com.vikas.lib.ApiInterface;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.notification.DataModel;
import com.vikas.secret.notification.NotificationModel;
import com.vikas.secret.notification.RootModel;
import com.vikas.secret.ui.chat.ChatCallbacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Callback;

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
                    for(DocumentChange snapshot : value.getDocumentChanges()) {
                        messages.add(new MessageModel(
                                Objects.requireNonNull(snapshot.getDocument().get("uId")).toString(),
                                Objects.requireNonNull(snapshot.getDocument().get("message")).toString(),
                                Long.valueOf(Objects.requireNonNull(snapshot.getDocument().get("timestamp")).toString())
                        ));
                    }
                    callbacks.onGetMessageList(messages);
                });
    }


    public void sendMessage(MessageModel message, String messageId, String chatPersonId) {
        db.collection(MESSAGES).document(messageId).collection("messages").add(message)
        .addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                sendNotificationToUser(chatPersonId);
            }
        }).addOnFailureListener(e -> {

        });
    }

    private void sendNotificationToUser(String chatPersonId) {

        db.collection("users").document(chatPersonId).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }

            String token = Objects.requireNonNull(task.getResult().get("token")).toString();

            RootModel rootModel = new RootModel(token, new NotificationModel("Title", "Body"), new DataModel("Name", "30"));

            ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
            retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(rootModel);

            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    Log.d(TAG,"Successfully notification send by using retrofit.");
                }

                @Override
                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                }
            });
        });
    }
}
