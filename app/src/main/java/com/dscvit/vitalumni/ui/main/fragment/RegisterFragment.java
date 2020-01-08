package com.dscvit.vitalumni.ui.main.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dscvit.vitalumni.R;
import com.dscvit.vitalumni.model.api.FormSaver;
import com.dscvit.vitalumni.model.api.GuestHelper;
import com.dscvit.vitalumni.model.api.LoginApiModel;
import com.dscvit.vitalumni.model.api.LoginResponse;
import com.dscvit.vitalumni.ui.adapter.NewMemberAdapter;
import com.dscvit.vitalumni.ui.auth.LoginActivity;
import com.dscvit.vitalumni.ui.main.MainActivity;
import com.dscvit.vitalumni.ui.main.fragment.bottomsheet.NewMemberBottomSheetFragment;
import com.dscvit.vitalumni.ui.main.fragment.bottomsheet.NewMemberListener;
import com.dscvit.vitalumni.web.WebProvider;
import com.dscvit.vitalumni.web.WebService;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dscvit.vitalumni.ui.auth.LoginActivity.PREF_ISLOGGEDIN;
import static com.dscvit.vitalumni.ui.auth.LoginActivity.PREF_ISREG;
import static com.dscvit.vitalumni.ui.auth.LoginActivity.PREF_TOKEN;
import static com.dscvit.vitalumni.ui.auth.LoginActivity.PREF_USERNAME;

public class RegisterFragment extends Fragment implements View.OnClickListener, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener, NewMemberListener {

    WebService webService = WebProvider.provide();

    private FormSaver form = new FormSaver();

    private CoordinatorLayout parentLayout;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    boolean isRegistered;
    boolean isLoggedIn;
    private TextView buttonLogin;
    private TextView buttonAuthRegister;
    private TextView buttonPassword;

    private TextInputLayout inputDob;
    private ImageView buttonDatePicker;
    private ChipGroup chipGroupEntrepreneur;
    private LinearLayout layoutEntrepreneur;
    private TextInputLayout inputCompanyName;
    private ChipGroup chipGroupBusinessType;
    private TextInputLayout inputCompanyAddress;
    private TextInputLayout inputBusinessName;
    private TextInputLayout inputCompanyWebsite;
    private TextInputLayout inputIndianMNo;
    private TextInputLayout inputForeignMNo;
    private TextInputLayout inputEmail;
    private Spinner spinnerSchool;
    private TextInputLayout inputFacebook;
    private TextInputLayout inputLinkedin;
    private TextInputLayout inputTwitter;
    private ChipGroup chipGroupGender;
    private ChipGroup chipGroupFood;
    private ChipGroup chipGroupMarital;
    private ImageView buttonNewMember;
    private RecyclerView recyclerViewNewMembers;
    private Button registerButton;

    private LinearLayout entrepreneurChipGroupError;
    private LinearLayout genderChipGroupError;
    private LinearLayout maritalChipGroupError;
    private LinearLayout foodChipGroupError;
    private LinearLayout schoolSpinnerError;
    private LinearLayout businessTypeChipGroupError;
    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private LinearLayout registrationDoneLayout;
    private LinearLayout registrationPendingLayout;

    String[] schools = {
            "School of Advanced Sciences",
            "School of Architecture",
            "School of Architecture and Planning",
            "School of Computer Engineering",
            "School of Mechanical and Building Sciences",
            "VIT Fashion Technology",
            "VIT School of Law",
            "School of Bio Sciences and Technology",
            "School of Civil and Chemical Engineering",
            "School of Computing Sciences and Engineering",
            "School of Electrical Engineering",
            "School of Electronics Engineering",
            "School of Information Technology and Engineering",
            "School of Mechanical Engineering",
            "School of Social Sciences and Languages",
            "VIT Business School"
    };

    private Boolean isValid;

    private String dob = "";
    private String entrepreneur = "";
    private String gender = "";
    private String businessType = "";
    private String foodType = "";
    private String maritalStatus = "";
    private String school = "";
    private boolean isEntrepreneur;

    NewMemberAdapter adapter;
    private LinearLayout layoutBeforeAuth;
    private NestedScrollView layoutAfterAuth;

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        parentLayout = getActivity().findViewById(R.id.layout_main);

//        Snackbar.make(parentLayout, "asdf", Snackbar.LENGTH_SHORT).show();

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();

        isRegistered = prefs.getBoolean(PREF_ISREG, false);
        isLoggedIn = prefs.getBoolean(LoginActivity.PREF_ISLOGGEDIN, false);

        initViews(view);

        updateUI();

        recyclerViewNewMembers.setHasFixedSize(true);
        recyclerViewNewMembers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewMemberAdapter(getContext());
        recyclerViewNewMembers.setAdapter(adapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, schools);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchool.setAdapter(arrayAdapter);

        chipGroupEntrepreneur.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == R.id.chip_entrepreneur_yes) {
                    layoutEntrepreneur.setVisibility(View.VISIBLE);
                    entrepreneur = "yes";
                    isEntrepreneur = true;
                } else if (checkedId == R.id.chip_entrepreneur_no) {
                    layoutEntrepreneur.setVisibility(View.GONE);
                    entrepreneur = "no";
                    isEntrepreneur = false;
                }
            }
        });

        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                school = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                school = "";
            }
        });

        chipGroupBusinessType.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == R.id.chip_business_self) {
                    businessType = "self";
                } else if (checkedId == R.id.chip_business_family) {
                    businessType = "family";
                }
            }
        });

        chipGroupGender.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == R.id.chip_gender_male) {
                    gender = "male";
                } else if (checkedId == R.id.chip_gender_female) {
                    gender = "female";
                }
            }
        });


        chipGroupFood.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == R.id.chip_food_veg) {
                    foodType = "veg";
                } else if (checkedId == R.id.chip_food_nonveg) {
                    foodType = "nonveg";
                }
            }
        });

        chipGroupMarital.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == R.id.chip_marital_single) {
                    maritalStatus = "single";
                } else if (checkedId == R.id.chip_marital_married) {
                    maritalStatus = "married";
                }
            }
        });

        buttonLogin.setOnClickListener(this);
        buttonPassword.setOnClickListener(this);
        buttonAuthRegister.setOnClickListener(this);

        buttonDatePicker.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        buttonNewMember.setOnClickListener(this);

        return view;
    }

    private Boolean validateFormFields() {
        boolean valid = true;

        if (!emailValid(inputEmail)) {
            inputEmail.setError("Enter a valid email.");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (isEmpty(inputDob)) {
            inputDob.setError("Please select a date of birth.");
            valid = false;
        } else {
            inputDob.setError(null);
        }

        if (!phoneValid(inputIndianMNo)) {
            inputIndianMNo.setError("Enter a valid phone number");
            valid = false;
        } else {
            inputIndianMNo.setError(null);
        }

        if (!isEmpty(inputForeignMNo)) {
            if (!phoneValid(inputForeignMNo)) {
                inputForeignMNo.setError("Enter a valid phone number");
                valid = false;
            } else {
                inputForeignMNo.setError(null);
            }
        }

        if (entrepreneur.length() == 0) {
            entrepreneurChipGroupError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            entrepreneurChipGroupError.setVisibility(View.GONE);
        }

        if (gender.length() == 0) {
            genderChipGroupError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            genderChipGroupError.setVisibility(View.GONE);
        }

        if (maritalStatus.length() == 0) {
            maritalChipGroupError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            maritalChipGroupError.setVisibility(View.GONE);
        }

        if (foodType.length() == 0) {
            foodChipGroupError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            foodChipGroupError.setVisibility(View.GONE);
        }

        if (school.length() == 0) {
            schoolSpinnerError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            schoolSpinnerError.setVisibility(View.GONE);
        }

        if (isEntrepreneur) {
            if (isEmpty(inputCompanyName)) {
                inputCompanyName.setError("Enter a company name");
                valid = false;
            } else {
                inputCompanyName.setError(null);
            }

            if (isEmpty(inputCompanyAddress)) {
                inputCompanyAddress.setError("Enter the company address");
                valid = false;
            } else {
                inputCompanyAddress.setError(null);
            }

            if (isEmpty(inputBusinessName)) {
                inputBusinessName.setError("Enter the Nature of Business");
                valid = false;
            } else {
                inputBusinessName.setError(null);
            }

            if (!websiteValid(inputCompanyWebsite)) {
                inputCompanyWebsite.setError("Enter a valid website");
                valid = false;
            } else {
                inputCompanyWebsite.setError(null);
            }

            if (businessType.length() == 0) {
                businessTypeChipGroupError.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                businessTypeChipGroupError.setVisibility(View.GONE);
            }
        }

        return valid;
    }

    private void initViews(View view) {

        buttonLogin = view.findViewById(R.id.button_auth_login);
        buttonAuthRegister = view.findViewById(R.id.text_auth_register);
        buttonPassword = view.findViewById(R.id.button_auth_password);
        inputUsername = view.findViewById(R.id.input_auth_username);
        inputPassword = view.findViewById(R.id.input_auth_password);

        inputDob = view.findViewById(R.id.input_register_dob);
        buttonDatePicker = view.findViewById(R.id.image_button_date_picker);
        chipGroupEntrepreneur = view.findViewById(R.id.chip_group_entrepreneur);
        layoutEntrepreneur = view.findViewById(R.id.layout_register_entrepreneur);
        inputCompanyName = view.findViewById(R.id.input_register_company_name);
        chipGroupBusinessType = view.findViewById(R.id.chip_group_business_type);
        inputCompanyAddress = view.findViewById(R.id.input_register_company_address);
        inputBusinessName = view.findViewById(R.id.input_register_business_name);
        inputCompanyWebsite = view.findViewById(R.id.input_register_company_website);
        inputIndianMNo = view.findViewById(R.id.input_register_india_mno);
        inputForeignMNo = view.findViewById(R.id.input_register_foreign_mno);
        inputEmail = view.findViewById(R.id.input_register_email);
        spinnerSchool = view.findViewById(R.id.spinner_register_school);
        inputFacebook = view.findViewById(R.id.input_register_facebook);
        inputLinkedin = view.findViewById(R.id.input_register_linkedin);
        inputTwitter = view.findViewById(R.id.input_register_twitter);
        chipGroupGender = view.findViewById(R.id.chip_group_gender);
        chipGroupFood = view.findViewById(R.id.chip_group_food);
        chipGroupMarital = view.findViewById(R.id.chip_group_marital_status);
        buttonNewMember = view.findViewById(R.id.button_new_member);
        recyclerViewNewMembers = view.findViewById(R.id.rv_new_members);

        registerButton = view.findViewById(R.id.button_register);

        entrepreneurChipGroupError = view.findViewById(R.id.entrepreneurChipGroupError);
        genderChipGroupError = view.findViewById(R.id.genderChipGroupError);
        maritalChipGroupError = view.findViewById(R.id.maritalStatusChipGroupError);
        foodChipGroupError = view.findViewById(R.id.foodChipGroupError);
        schoolSpinnerError = view.findViewById(R.id.schoolSpinnerError);
        businessTypeChipGroupError = view.findViewById(R.id.businessTypeChipGroupError);

        layoutBeforeAuth = view.findViewById(R.id.layout_before_auth);
        layoutAfterAuth = view.findViewById(R.id.layout_after_auth);
        registrationDoneLayout = view.findViewById(R.id.registration_done_layout);
        registrationPendingLayout = view.findViewById(R.id.registration_pending_layout);
    }

    @Override
    public void onClick(View view) {
        if (!MainActivity.isMenuActive) {
            switch (view.getId()) {
                case R.id.image_button_date_picker :
                    new SpinnerDatePickerDialogBuilder()
                            .context(getContext())
                            .callback(this)
//                            .spinnerTheme(R.style.NumberPickerStyle)
                            .showTitle(true)
                            .showDaySpinner(true)
                            .defaultDate(1990, 0, 1)
                            .maxDate(2018, 0, 1)
//                            .minDate(2000, 0, 1)
                            .build()
                            .show();
                    break;
                case R.id.button_register:
                    isValid = validateFormFields();
                    if (isValid) {
                        submitForm();
                    }
                    break;
                case R.id.button_new_member:
                    if (adapter.memberModels.size() <= 3) {
                        NewMemberBottomSheetFragment bottomSheetFragment = new NewMemberBottomSheetFragment();
                        bottomSheetFragment.init(this);
                        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
                    } else {
                        Snackbar.make(parentLayout, "Sorry, only 3 members are allowed", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.button_auth_login:
                    String userName = inputUsername.getEditText().getText().toString();
                    String password = inputPassword.getEditText().getText().toString();
                    if (loginFormValidate(userName, password)) {
                        login(userName, password);
                    }
                    break;
                case R.id.text_auth_register:
                    openUrl("http://www.vitaa.org/inviteRequest/create");
                    break;
                case R.id.button_auth_password:
                    openUrl("http://www.vitaa.org/vitaa/PasswordRecovery");
                    break;
                default : break;
            }
        }
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
                        Snackbar.make(parentLayout, "Logged In Successfully", Snackbar.LENGTH_SHORT).show();
                        editor.putBoolean(PREF_ISLOGGEDIN, true).commit();
                        editor.putBoolean(PREF_ISREG, loginResponse.isRegistered()).apply();
                        editor.putString(PREF_TOKEN, loginResponse.getToken()).apply();
                        editor.putString(PREF_USERNAME, loginResponse.getName()).apply();
                        isLoggedIn = true;
                        isRegistered = loginResponse.isRegistered();
                        updateUI();
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

    private void updateUI() {
        if (!isLoggedIn) {
            showLogin();
        } else {
            hideLogin();
            if (isRegistered) {
                hideForm();
            } else {
                showForm();
            }
        }
    }

    private boolean loginFormValidate(String userName, String password) {
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        inputDob.getEditText().setText("" + dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
        dob = "" + dayOfMonth + " / " + (monthOfYear + 1) + " / " + year;
    }

    @Override
    public void onAddNewMember(GuestHelper guestHelper) {
        adapter.memberModels.add(guestHelper);
        adapter.notifyDataSetChanged();
    }

    private void submitForm() {
        form.setDateOfBirth(dob);
        form.setEntrepreneur(isEntrepreneur);
        form.setCompanyName(inputCompanyName.getEditText().getText().toString());
        form.setBusType(inputBusinessName.getEditText().getText().toString());
        form.setCompanyAddress(inputCompanyAddress.getEditText().getText().toString());
        form.setNatureOfBusiness(inputCompanyName.getEditText().getText().toString());
        form.setCompanyWebsite(inputCompanyWebsite.getEditText().getText().toString());
        form.setMobileNumber(Long.parseLong(inputIndianMNo.getEditText().getText().toString()));
        form.setMobileNumberFor(Long.parseLong(inputForeignMNo.getEditText().getText().toString()));
        form.setEmailId(inputEmail.getEditText().getText().toString());
        form.setSchool(school);
        form.setFbid(inputFacebook.getEditText().getText().toString());
        form.setLinkId(inputLinkedin.getEditText().getText().toString());
        form.setTwitterId(inputTwitter.getEditText().getText().toString());
        form.setGender(gender);
        form.setFoodPref(foodType);
        form.setMaritalStatus(maritalStatus);
        form.setMariedDate("");
        form.setGuestList(adapter.memberModels);
        form.setBusNeeded(false);
        form.setTravellersCount(0);

        String token = prefs.getString(PREF_TOKEN, "");

        Call<String> registerCall = webService.register(token, form);

        registerCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("1")) {
                    editor.putBoolean(PREF_ISREG, true).apply();
                    isRegistered = true;
                    updateUI();
                } else {
                    Snackbar.make(parentLayout, "Invalid sumbission", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(parentLayout, "Error occurred", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showLogin() {
        layoutBeforeAuth.setVisibility(View.VISIBLE);
        layoutAfterAuth.setVisibility(View.GONE);
    }

    private void hideLogin() {
        layoutBeforeAuth.setVisibility(View.GONE);
        layoutAfterAuth.setVisibility(View.VISIBLE);
    }

    private void hideForm() {
        registrationPendingLayout.setVisibility(View.GONE);
        registrationDoneLayout.setVisibility(View.VISIBLE);
    }

    private void showForm() {
        registrationPendingLayout.setVisibility(View.VISIBLE);
        registrationDoneLayout.setVisibility(View.GONE);
    }

    private Boolean emailValid(TextInputLayout et) {
        CharSequence email = et.getEditText().getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private Boolean isEmpty(TextInputLayout et) {
        CharSequence str = et.getEditText().getText().toString();
        return TextUtils.isEmpty(str);
    }

    private Boolean phoneValid(TextInputLayout et) {
        CharSequence phone = et.getEditText().getText().toString();
        return (!TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches());
    }

    private Boolean websiteValid(TextInputLayout et) {
        CharSequence website = et.getEditText().getText().toString();
        return (!TextUtils.isEmpty(website) && Patterns.WEB_URL.matcher(website).matches());
    }

    private void openUrl(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }
}
