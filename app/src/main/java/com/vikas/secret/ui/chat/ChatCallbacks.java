package com.vikas.secret.ui.chat;

import com.vikas.secret.data.models.MessageModel;

import java.util.List;

public interface ChatCallbacks {
    void onGetChatPersonCallback(String chatPersonId);
    void onGetMessageList(List<MessageModel> messageModelList);
}
