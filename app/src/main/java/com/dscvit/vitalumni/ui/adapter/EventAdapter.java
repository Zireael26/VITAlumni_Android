package com.dscvit.vitalumni.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.EventModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    ArrayList<EventModel> eventModels;

    public EventAdapter(ArrayList<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_layout, viewGroup, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        EventModel event = eventModels.get(i);
        eventViewHolder.imageView.setImageResource(event.getImageResourceID());
        eventViewHolder.imageDescription.setText(event.getImageDescription());
        eventViewHolder.dateTextView.setText(event.getEventDate());
    }

    @Override
    public int getItemCount() {
        //TODO: Change this to dynamic
        return eventModels.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView imageDescription;
        TextView dateTextView;
        
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.homeImageView);
            imageDescription = itemView.findViewById(R.id.footnoteTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
