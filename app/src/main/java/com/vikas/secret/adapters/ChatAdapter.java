package com.vikas.secret.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.vikas.secret.R;
import com.vikas.secret.data.models.MessageModel;
import com.vikas.secret.ui.chat.ChatFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
    private final List<MessageModel> messageModels;
    private Context context;
    private static final int SENDER_VIEW_TYPE = 1;
    private static final int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter( Context context) {
        this.messageModels = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.message_sender, parent, false);
            return new SenderViewVolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_reciever, parent, false);
            return new RecieverViewVolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        if (holder.getClass() == SenderViewVolder.class) {
            ((SenderViewVolder)holder).senderMsg.setText(messageModel.getMessage());
            ((SenderViewVolder)holder).senderTime.setText(messageModel.getTime());
        } else {
            ((RecieverViewVolder) holder).receiverMsg.setText(messageModel.getMessage());
            ((RecieverViewVolder) holder).receiverTime.setText(messageModel.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public void update(List<MessageModel> data) {
        messageModels.clear();
        messageModels.addAll(data);
        notifyDataSetChanged();
    }

    public class RecieverViewVolder extends RecyclerView.ViewHolder {
        TextView receiverMsg, receiverTime;

        public RecieverViewVolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.recieverText);
            receiverTime = itemView.findViewById(R.id.recieverTime);
        }

    }

    public class SenderViewVolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime;

        public SenderViewVolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);

        }
    }
}
