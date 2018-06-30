package com.example.maask.bmax;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maask on 3/23/2018.
 */

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> conversationList;
    private Context context;

    public ConversationAdapter(ArrayList<String> conversationList, Context context) {
        this.conversationList = conversationList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        View v = inflater.inflate(R.layout.conversation_layout,parent,false);
        viewHolder = new ConversationAdapter.ConversationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ConversationViewHolder viewHolder = (ConversationViewHolder) holder;
        viewHolder.conversationPart.setText(conversationList.get(position));

        viewHolder.conversationPart.setCursorVisible(true);

        if (position%2!=0){
            viewHolder.conversationPart.setTextColor(Color.DKGRAY);
            viewHolder.conversationIcon.setImageResource(R.drawable.user_icon);
        }else {
            viewHolder.conversationPart.setTextColor(Color.GRAY);
            viewHolder.conversationIcon.setImageResource(R.drawable.bmax_icon);
        }

    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {

        TextView conversationPart;
        ImageView conversationIcon;

        public ConversationViewHolder(View v) {
            super(v);

            conversationPart = v.findViewById(R.id.conversation_part);
            conversationIcon = v.findViewById(R.id.conversation_icon);


        }
    }

    public void instantDataChang(ArrayList<String> conversationList) {
        this.conversationList = conversationList;
        notifyDataSetChanged();
    }

}
