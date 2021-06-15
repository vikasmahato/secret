package com.vikas.secret.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vikas.secret.data.ChatRepository;
import com.vikas.secret.data.models.MessageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatViewModel extends ViewModel implements ChatCallbacks{

    private MutableLiveData<List<MessageModel>> messages;
    private ChatRepository chatRepository;
    private FirebaseUser user;

    public ChatViewModel() {
        messages = new MutableLiveData<>();
        chatRepository = new ChatRepository();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<List<MessageModel>> getLiveMessages() {
        return messages;
    }

    public List<MessageModel> getMessages() {

        String messageId = null;
        List<MessageModel> messages = new ArrayList<>();
        if(user != null)
            chatRepository.getChatPersonUUID(user.getUid(), this);


        return messages;
    }

    @Override
    public void onGetChatPersonCallback(String chatPersonId) {
        if(chatPersonId != null)
            chatRepository.getChatByUUID(user.getUid() + chatPersonId, this);
    }

    @Override
    public void onGetMessageList(List<MessageModel> messageModelList) {

    }
}