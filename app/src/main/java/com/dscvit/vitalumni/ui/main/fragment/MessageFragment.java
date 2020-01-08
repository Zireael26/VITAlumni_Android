package com.dscvit.vitalumni.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.NotifModel;
import com.dscvit.vitalumni.ui.adapter.MessageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    RecyclerView recyclerView;
    MessageAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference notifRef = database.getReference("notificationRequests");

    ArrayList<NotifModel> notifModels = new ArrayList<>();

    LinearLayout noNotifsLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        noNotifsLayout = view.findViewById(R.id.no_notifs_state_layout);

        recyclerView = view.findViewById(R.id.messages_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageAdapter(getContext(), notifModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);

        notifRef.keepSynced(true);

        notifRef.child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!notifModels.isEmpty()) {
                    notifModels.clear();
                }
                for (DataSnapshot notifSnapShot : dataSnapshot.getChildren()) {
                    NotifModel notifModel = notifSnapShot.getValue(NotifModel.class);
                    notifModels.add(0, notifModel);
                    adapter.updateList(notifModels);
                }

                if (adapter.getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    noNotifsLayout.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noNotifsLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recyclerView.setVisibility(View.GONE);
                noNotifsLayout.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}
