package com.dscvit.vitalumni.util.fcm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dscvit.vitalumni.ui.main.fragment.SettingsFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class VItAlumniFcm extends FirebaseMessagingService {

    private String TAG = "FIREBASE NOTIFICATION";

    SharedPreferences prefs;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isNotificationOn = prefs.getBoolean(SettingsFragment.PREF_NOTIF_KEY, true);

        if (isNotificationOn && remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            FcmNotification.notify(this, remoteMessage.getNotification().getBody());
        }

    }
}
