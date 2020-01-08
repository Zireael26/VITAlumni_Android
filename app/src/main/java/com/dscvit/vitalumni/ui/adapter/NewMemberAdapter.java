package com.dscvit.vitalumni.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.api.GuestHelper;
import com.dscvit.vitalumni.ui.main.MainActivity;

import java.util.ArrayList;

public class NewMemberAdapter extends RecyclerView.Adapter<NewMemberAdapter.NewMemberViewHolder> {

    Context context;
    public ArrayList<GuestHelper> memberModels = new ArrayList<>();

    public NewMemberAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_member, parent, false);
        return new NewMemberAdapter.NewMemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMemberViewHolder holder, final int position) {

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MainActivity.isMenuActive) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Remove guest");
                    alertDialog.setMessage("Are you sure you want to remove guest?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            memberModels.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        GuestHelper guestHelper = memberModels.get(position);

        holder.textGuestTitle.setText("New Member " + (position + 1));
        holder.textGuestName.setText(guestHelper.getName());
        holder.textGuestRelation.setText(guestHelper.getRelationship());
        holder.textGuestAge.setText(String.valueOf(guestHelper.getAge()));
        holder.textGuestFood.setText(guestHelper.getFoodPreference());

    }

    @Override
    public int getItemCount() {
        return memberModels.size();
    }

    public static class NewMemberViewHolder extends RecyclerView.ViewHolder {

        TextView textGuestTitle;
        ImageView buttonRemove;
        TextView textGuestName;
        TextView textGuestAge;
        TextView textGuestFood;
        TextView textGuestRelation;

        public NewMemberViewHolder(@NonNull View itemView) {
            super(itemView);

            textGuestTitle = itemView.findViewById(R.id.text_new_member_title);
            buttonRemove = itemView.findViewById(R.id.button_new_member_remove);
            textGuestName = itemView.findViewById(R.id.text_new_member_name);
            textGuestAge = itemView.findViewById(R.id.text_new_member_age);
            textGuestFood = itemView.findViewById(R.id.text_new_member_food);
            textGuestRelation = itemView.findViewById(R.id.text_new_member_relationn);
        }
    }
}
