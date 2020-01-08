package com.dscvit.vitalumni.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.TaskStackBuilder;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.api.LoginApiModel;
import com.dscvit.vitalumni.model.api.LoginResponse;
import com.dscvit.vitalumni.ui.main.MainActivity;
import com.dscvit.vitalumni.web.WebProvider;
import com.dscvit.vitalumni.web.WebService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static String PREF_ISLOGGEDIN = "PREF_ISLOGGEDIN";
    public static String PREF_TOKEN = "PREF_TOKEN";
    public static String PREF_ISREG = "PEF_ISREG";
    public static String PREF_USERNAME = "PREF_USERNAME";

    WebService webService = WebProvider.provide();

    private TextView loginButton;
    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private CoordinatorLayout parentLayout;

    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = prefs.edit();

        initViews();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = inputUsername.getEditText().getText().toString();
                String password = inputPassword.getEditText().getText().toString();
                if (formValidate(userName, password)) {
                    login(userName, password);
                }
            }
        });

    }

    private void startMainActivity() {
        prefEditor.putBoolean(PREF_ISLOGGEDIN, true).commit();
        TaskStackBuilder.create(LoginActivity.this)
                .addNextIntentWithParentStack(new Intent(LoginActivity.this, MainActivity.class))
                .startActivities();
        finish();
    }

    private void login(String userName, String password) {

        Call<LoginResponse> testCall = webService.login(new LoginApiModel(userName, password));

        testCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getResponseCode().equals("101")) {
                        Snackbar.make(parentLayout, "Invalid credentials", Snackbar.LENGTH_SHORT).show();
                    } else if (loginResponse.getResponseCode().equals("100")) {
                        Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                        prefEditor.putBoolean(PREF_ISREG, loginResponse.isRegistered()).apply();
                        prefEditor.putString(PREF_TOKEN, loginResponse.getToken()).apply();
                        prefEditor.putString(PREF_USERNAME, loginResponse.getName()).apply();
                        startMainActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(parentLayout, "Error occurred", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Snackbar.make(parentLayout, "Error occurred", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean formValidate(String userName, String password) {
        boolean isValidUserName = true;
        boolean isValidPassword = true;

        inputUsername.setError(null);
        inputPassword.setError(null);

        if (userName.isEmpty() || !isValidEmail(userName)) {
            inputUsername.setError("Invalid email");
            isValidUserName = false;
        }

        if (password.isEmpty()) {
            inputPassword.setError("Should not be empty");
            isValidPassword = false;
        }

        return isValidUserName && isValidPassword;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void initViews() {
        loginButton = findViewById(R.id.button_auth_login);
        inputUsername = findViewById(R.id.input_auth_username);
        inputPassword = findViewById(R.id.input_auth_password);

        parentLayout = findViewById(R.id.layout_auth_parent);
    }

}
