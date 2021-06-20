package com.vikas.secret.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vikas.secret.data.ChatRepository;
import com.vikas.secret.data.models.MessageModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ChatViewModel extends ViewModel implements ChatCallbacks{

    private final MutableLiveData<List<MessageModel>> messages;
    private final MutableLiveData<Pair<String,String>> chatPersonAndMesageID;

    private final ChatRepository chatRepository;
    private final FirebaseUser user;

    public ChatViewModel() {
        messages = new MutableLiveData<>();
        chatPersonAndMesageID = new MutableLiveData<>();
        chatRepository = ChatRepository.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public LiveData<List<MessageModel>> getLiveMessages() {
        return messages;
    }

    public LiveData<Pair<String,String>> getChatPersonAndMessageID() {
        return chatPersonAndMesageID;
    }

    public void fetchChatPersonAndMessageID() {
        if(user != null)
            chatRepository.getChatPersonAndMessageID(user.getUid(), this);
    }

    public void getMessages(String chatID) {
        chatRepository.getChatByUUID(chatID, this);
    }

    @Override
    public void onGetChatPersonCallback(String chatPersonId, String messageID) {
        if(StringUtils.isNotEmpty(chatPersonId))
            chatPersonAndMesageID.setValue(Pair.of(chatPersonId, messageID));
    }

    @Override
    public void onGetMessageList(List<MessageModel> messageModelList) {
        messages.setValue(messageModelList);
    }

    public void sendMessage(String message, String messageId) {
        MessageModel model = new MessageModel(FirebaseAuth.getInstance().getUid(), message);
        chatRepository.sendMessage(model, messageId);
    }
}