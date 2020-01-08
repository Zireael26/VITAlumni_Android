package com.dscvit.vitalumni.ui.main.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dscvit.vitalumni.R;

public class ProfileFragment extends Fragment {

    Button buttonProfileEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        buttonProfileEdit = view.findViewById(R.id.button_profile_edit);

        buttonProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "edit", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
