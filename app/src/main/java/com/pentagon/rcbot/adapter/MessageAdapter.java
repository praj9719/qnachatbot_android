package com.pentagon.rcbot.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentagon.rcbot.R;
import com.pentagon.rcbot.object.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = "MessageAdapter";
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    private Context context;
    private List<Message> mList;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void addMessage(Message message){
        mList.add(message);
        notifyItemInserted(mList.size());
    }

    public void updateMessages(List<Message> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mList.get(position);
        if (message.getIsFromUser()) return VIEW_TYPE_SENT;
        return VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENT)
            view = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.recive_layout, parent, false);
        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message message = mList.get(position);
        holder.message.setText(message.getMessage());
        holder.message.setOnClickListener(view -> detailedMessage(message));
    }

    private void detailedMessage(Message message){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(message.getMessage())
                .setMessage(message.getDetailed())
                .setPositiveButton("Ok", (dialogInterface, i) -> {})
                .create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text);
        }
    }
}
