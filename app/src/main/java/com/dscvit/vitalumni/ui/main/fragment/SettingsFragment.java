package com.dscvit.vitalumni.ui.main.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.ui.auth.LoginActivity;
import com.dscvit.vitalumni.ui.main.MainActivity;
import com.dscvit.vitalumni.ui.main.fragment.bottomsheet.AboutusBottomSheetFragment;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static String PREF_NOTIF_KEY = "PREF_IS_NOTIFS_ENABLED";
    public static String PREF_MDATA_KEY = "PREF_IS_MDATA_ENABLED";

    boolean isNotifisOn;
    boolean isUseMData;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;

    private TextView textButtonNotifs;
    private TextView textButtonMData;
    private TextView textButtonBottom;
    private CardView cardAboutUs;
    private TextView buttonLogout;

    private TextView desginedTextView;

    private CoordinatorLayout parentLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefEditor = prefs.edit();

        parentLayout = getActivity().findViewById(R.id.layout_main);

        textButtonNotifs = view.findViewById(R.id.text_button_settings_notifs);
        textButtonMData = view.findViewById(R.id.text_button_settings_mdata);
        cardAboutUs = view.findViewById(R.id.card_settings_aboutus);
        buttonLogout = view.findViewById(R.id.text_button_settings_logout);
        textButtonBottom = view.findViewById(R.id.text_settings_bootm_text);

        isNotifisOn = prefs.getBoolean(PREF_NOTIF_KEY, true);
        isUseMData = prefs.getBoolean(PREF_MDATA_KEY, false);
        updateUi(textButtonNotifs, isNotifisOn);
        updateUi(textButtonMData, isUseMData);

        textButtonNotifs.setOnClickListener(this);
        textButtonMData.setOnClickListener(this);
        cardAboutUs.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        textButtonBottom.setOnClickListener(this);

        textButtonBottom.setText("Designed and Developed with " + getEmojiByUnicode(0x2764) + " by DSC VIT");

        return view;
    }

    @Override
    public void onClick(View view) {
        if (!MainActivity.isMenuActive) {
            switch (view.getId()) {
                case R.id.text_button_settings_notifs :
                    isNotifisOn = !isNotifisOn;
                    prefEditor.putBoolean(PREF_NOTIF_KEY, isNotifisOn).apply();
                    updateUi(textButtonNotifs, isNotifisOn);
                    break;
                case R.id.text_button_settings_mdata :
                    isUseMData = !isUseMData;
                    prefEditor.putBoolean(PREF_MDATA_KEY, isUseMData).apply();
                    updateUi(textButtonMData, isUseMData);
                    break;
                case R.id.card_settings_aboutus :
                    AboutusBottomSheetFragment aboutusBottomSheetFragment = new AboutusBottomSheetFragment();
                    aboutusBottomSheetFragment.show(getFragmentManager(), aboutusBottomSheetFragment.getTag());
                    break;
                case R.id.text_button_settings_logout :
                    boolean isLoggedIn = prefs.getBoolean(LoginActivity.PREF_ISLOGGEDIN, false);
                    if (isLoggedIn) {
                        prefEditor.putBoolean(LoginActivity.PREF_ISLOGGEDIN, false).commit();
                        Snackbar.make(parentLayout, "Logged out successfully", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(parentLayout, "Not logged in", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                default: break;
            }
        }
    }

    private void openUrl(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }

    private void updateUi(TextView textView, Boolean isEnabled) {
        if (isEnabled) {
            textView.setText("ON");
            textView.setBackgroundResource(R.drawable.bg_blue);
        } else {
            textView.setText("OFF");
            textView.setBackgroundResource(R.drawable.bg_red);
        }
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
