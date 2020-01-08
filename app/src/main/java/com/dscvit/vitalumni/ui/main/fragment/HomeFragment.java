package com.dscvit.vitalumni.ui.main.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.EventModel;
import com.dscvit.vitalumni.ui.adapter.EventAdapter;
import com.dscvit.vitalumni.ui.main.MainActivity;
import com.dscvit.vitalumni.ui.main.fragment.bottomsheet.ImportantDatesBottomSheetFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ConstraintLayout layoutLiveStream;
    private ImageView calanderImageView;
    private TextView textButtonImpDates;
    private TextView textButtonYoutube;
    private NestedScrollView scrollView;

    private ArrayList<EventModel> eventList;
    private RecyclerView homeRecyclerView;

    private CoordinatorLayout parentLayout;

    private String liveUrl = "";
    private String staticUrl = "FIdKet8JXzw";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutLiveStream = view.findViewById(R.id.layout_live_stream);
        layoutLiveStream.setOnClickListener(this);

        calanderImageView = view.findViewById(R.id.button_important_dates);
//        calanderImageView.setOnClickListener(this);

        textButtonYoutube = view.findViewById(R.id.text_button_home_youtube);
        textButtonYoutube.setOnClickListener(this);

        textButtonImpDates = view.findViewById(R.id.text_button_home_imp_dates);
        textButtonImpDates.setOnClickListener(this);

        parentLayout = getActivity().findViewById(R.id.layout_main);

        scrollView = view.findViewById(R.id.scrollview_home);
//        scrollView.fullScroll(ScrollView.FOCUS_UP);
//        scrollView.smoothScrollTo(0,0);

        eventList = new ArrayList<>();
        eventList.add(new EventModel("26 Jan 2016", R.drawable.image_one, "Distinguished Alumni Awards - Winners felicitated during VITAA Day 2016"));
        eventList.add(new EventModel("26 Jan 2018", R.drawable.img_one_one, "Distinguished Alumni Awards - Winners felicitated during VITAA Day 2018"));
        eventList.add(new EventModel("27 Dec 2018", R.drawable.image_two, "VITAA Singapore Chapter Dinner Gathering"));
        eventList.add(new EventModel("9 Dec 2018", R.drawable.image_three, "VITAA Sydney Chapter Lunch Gathering"));
        eventList.add(new EventModel("1-2 Sep 2018", R.drawable.image_four, "VIT Alumni North American Convention in New Jersey"));

        homeRecyclerView = view.findViewById(R.id.home_recycler_view);
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setNestedScrollingEnabled(false);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecyclerView.setAdapter(new EventAdapter(eventList));

//        FirebaseDatabase.getInstance().getReference("youtubeLiveUrl").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                liveUrl = dataSnapshot.getValue().toString();
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.smoothScrollTo(0, 0);
        homeRecyclerView.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        if (!MainActivity.isMenuActive) {
            switch (view.getId()) {
                case R.id.layout_live_stream:
                    if (liveUrl.isEmpty()) {
                        Snackbar.make(parentLayout, "Not streaming yet", Snackbar.LENGTH_SHORT).show();
                    } else {
                        watchYoutubeVideo(liveUrl);
                    }
                    break;
                case R.id.text_button_home_imp_dates:
                    new ImportantDatesBottomSheetFragment().show(getFragmentManager(), this.getTag());
                    break;
                case R.id.text_button_home_youtube:
                    watchYoutubeVideo(staticUrl);
                    break;
                default:
                    break;
            }
        }
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getContext().startActivity(webIntent);
        }
    }
}
