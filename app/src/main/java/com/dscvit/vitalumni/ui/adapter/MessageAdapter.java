package com.dscvit.vitalumni.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.NotifModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    Context context;
    ArrayList<NotifModel> notifModels = new ArrayList<>();

    public MessageAdapter(Context context, ArrayList<NotifModel> notifModels) {
        this.context = context;
        this.notifModels = notifModels;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_row, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        final NotifModel notifModel = notifModels.get(position);

        holder.textNotifTitle.setText(notifModel.getTitle());
        holder.textNotifBody.setText(notifModel.getMessage());
        holder.textNotifDate.setText(notifModel.getDate());
        holder.textNotifMonth.setText(notifModel.getMonth());

        holder.parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notifModel.getUrl().isEmpty()) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(notifModel.getUrl()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (notifModels == null || notifModels.isEmpty()) {
            return 0;
        }
        return notifModels.size();
    }

    public void updateList(ArrayList<NotifModel> notifModels) {
        this.notifModels = notifModels;
        notifyDataSetChanged();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView textNotifTitle;
        TextView textNotifBody;
        TextView textNotifDate;
        TextView textNotifMonth;
        CardView parentCard;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            textNotifTitle = itemView.findViewById(R.id.text_notif_title);
            textNotifBody = itemView.findViewById(R.id.text_notif_body);
            textNotifDate = itemView.findViewById(R.id.secondImpDate);
            textNotifMonth = itemView.findViewById(R.id.secondimpMonth);
            parentCard = itemView.findViewById(R.id.card_notif_row);
        }
    }
}
