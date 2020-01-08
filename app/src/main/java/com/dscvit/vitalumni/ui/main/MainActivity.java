package com.dscvit.vitalumni.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.ui.auth.LoginActivity;
import com.dscvit.vitalumni.ui.main.fragment.HomeFragment;
import com.dscvit.vitalumni.ui.main.fragment.MessageFragment;
import com.dscvit.vitalumni.ui.main.fragment.RegisterFragment;
import com.dscvit.vitalumni.ui.main.fragment.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int ANIMATION_INTERVAL = 200;

    public static boolean isMenuActive = false;

    private ConstraintLayout layoutMenuParent;
    private ConstraintLayout layoutMenuChild;
    private ConstraintLayout layoutMainContent;

    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;

    private FrameLayout containerLayout;

    private FloatingActionButton fabMenuMain;
    private FloatingActionButton fabMenuHome;
    private FloatingActionButton fabMenuProfile;
    private FloatingActionButton fabMenuMessages;
    private FloatingActionButton fabMenuSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = prefs.edit();

//        boolean isLoggedIn = prefs.getBoolean(LoginActivity.PREF_ISLOGGEDIN, false);
//
//        if (!isLoggedIn) {
//            startLoginActivity();
//        }

        setContentView(R.layout.activity_main);

        initViews();
//        updateMenuUi();

        updateFragment(0);

    }

    private void startLoginActivity() {
        TaskStackBuilder.create(MainActivity.this)
                .addNextIntentWithParentStack(new Intent(MainActivity.this, LoginActivity.class))
                .startActivities();
        finish();
    }

    private void updateFragment(int i) {
        Fragment fragment = new HomeFragment();

        switch (i) {
            case 0 :
                fragment = new HomeFragment();
                break;
            case 1 :
                fragment = new RegisterFragment();
                break;
            case 2 :
                fragment = new MessageFragment();
                break;
            case 3 :
                fragment = new SettingsFragment();
                break;
            default: break;
        }

        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container_main, fragment)
                    .addToBackStack(backStateName)
                    .commit();
        }

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container_main, fragment)
//                .addToBackStack(null)
//                .commit();

        isMenuActive = false;
        updateMenuUi();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    private void updateMenuUi() {
        if (isMenuActive) {
            showMenu();
        } else {
            hideMenu();
        }
    }

    private void showMenu() {

        layoutMenuChild.setVisibility(View.VISIBLE);
        layoutMenuChild.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(ANIMATION_INTERVAL);

//        layoutMenuParent.setBackgroundColor(getResources().getColor(R.color.colorMenuBg));
        //TODO: Animate background
        containerLayout.animate().alpha(0.5f).setDuration(ANIMATION_INTERVAL);
        containerLayout.setClickable(false);
        containerLayout.setEnabled(false);

        fabMenuMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close_black_24dp));
    }

    private void hideMenu() {

//        layoutMenuChild.animate().translationY(layoutMenuChild.getHeight());
        layoutMenuChild.animate()
                .translationY(layoutMenuChild.getHeight())
                .alpha(0.0f)
                .setDuration(ANIMATION_INTERVAL)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (isMenuActive) {
                            layoutMenuChild.setVisibility(View.VISIBLE);
                        } else {
                            layoutMenuChild.setVisibility(View.GONE);
                        }
                    }
                });

//        layoutMenuParent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //TODO: Animate background
        containerLayout.animate().alpha(1.0f).setDuration(ANIMATION_INTERVAL);
        containerLayout.setClickable(true);
        containerLayout.setEnabled(true);

        fabMenuMain.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp));
    }

    private void initViews() {
        layoutMenuParent = findViewById(R.id.layout_menu_parent);
        layoutMenuChild = findViewById(R.id.layout_menu_child);
//        layoutMainContent = findViewById(R.id.layout_content_main);

        containerLayout = findViewById(R.id.container_main);

        fabMenuMain = findViewById(R.id.fab_menu_main);
        fabMenuHome = findViewById(R.id.fab_menu_home);
        fabMenuProfile = findViewById(R.id.fab_menu_profile);
        fabMenuMessages = findViewById(R.id.fab_menu_messages);
        fabMenuSettings = findViewById(R.id.fab_menu_settings);

        layoutMenuParent.setOnClickListener(this);
        layoutMenuChild.setOnClickListener(this);

        fabMenuMain.setOnClickListener(this);
        fabMenuHome.setOnClickListener(this);
        fabMenuProfile.setOnClickListener(this);
        fabMenuMessages.setOnClickListener(this);
        fabMenuSettings.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab_menu_main :
                isMenuActive = !isMenuActive;
                updateMenuUi();
                break;
            case R.id.fab_menu_home :
                updateFragment(0);
                break;
            case R.id.fab_menu_profile :
                updateFragment(1);
                break;
            case R.id.fab_menu_messages :
                updateFragment(2);
                break;
            case R.id.fab_menu_settings :
                updateFragment(3);
                break;
            default: break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
