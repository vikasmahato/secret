package com.vikas.secret.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vikas.secret.MainActivity;
import com.vikas.secret.adapters.ChatAdapter;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.databinding.FragmentChatBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;
    private FragmentChatBinding binding;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        AtomicReference<String> messageID = new AtomicReference<>();

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = ((MainActivity) requireActivity());

        chatViewModel.getLiveMessages().observe(getViewLifecycleOwner(), s -> {
            //TODO: set messages in adapter
        });

        final ChatAdapter chatAdapter = new ChatAdapter(root.getContext());
        binding.chatRecylearView.setAdapter(chatAdapter);
        binding.chatRecylearView.setHasFixedSize(true);
        binding.chatRecylearView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        boolean chatMessagesCalled = false;

        if(StringUtils.isEmpty(activity.getMessageID())) {
            chatViewModel.fetchChatPersonAndMessageID();
            activity.showProgressBar();
        } else {
            messageID.set(activity.getMessageID());
            chatMessagesCalled = true;
            chatViewModel.getMessages(messageID.get());
        }

        boolean finalChatMessagesCalled = chatMessagesCalled;
        chatViewModel.getChatPersonAndMessageID().observe(getViewLifecycleOwner(), data -> {
            if(data != null) {
                activity.setChatPersonId(data.getLeft());
                activity.setMessageId(data.getRight());
                messageID.set(data.getRight());
                activity.hideProgressBar();
                if(!finalChatMessagesCalled)
                   chatViewModel.getMessages(messageID.get());
            }
        });


        //  messageModels.clear();
        //messageModels.addAll(data);
        chatViewModel.getLiveMessages().observe(getViewLifecycleOwner(), chatAdapter::update);

        binding.sendMessage.setOnClickListener(v -> {
            String message = binding.etMessage.getText().toString();
            if(StringUtils.isNotEmpty(message)) {
                chatViewModel.sendMessage(message, messageID.get(), activity.getChatPersonID());
                binding.etMessage.setText("");
            } else {
                //TODO: error
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        activity.showMessageButton();
    }
}