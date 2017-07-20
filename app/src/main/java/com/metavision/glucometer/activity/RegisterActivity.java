package com.metavision.glucometer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.metavision.glucometer.R;
import com.metavision.glucometer.other.RealmObjectGluco;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.confirm_password) EditText _confirmPassword;
    @InjectView(R.id.input_fullname) EditText _fullnameText;
    @InjectView(R.id.input_weight) EditText _weightNumber;
    @InjectView(R.id.input_height) EditText _heightNumber;
    @InjectView(R.id.btn_create_account) Button _createButton;
    @InjectView(R.id.link_already) TextView _alreadySignup;

    private Realm realm;
    private RealmObjectGluco realmObjectGluco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        _createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup();
                checkRealm();
            }
        });

        _alreadySignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    public void checkRealm() {
        try {

            realm.beginTransaction();

            realmObjectGluco = realm.createObject(RealmObjectGluco.class);
            realmObjectGluco.setUsername(_usernameText.getText().toString());
            realmObjectGluco.setPassword(_passwordText.getText().toString());
            realmObjectGluco.setConfirmPassword(_confirmPassword.getText().toString());
            realmObjectGluco.setFullname(_fullnameText.getText().toString());
            realmObjectGluco.setWeight(Integer.parseInt(_weightNumber.getText().toString()));
            realmObjectGluco.setHeight(Integer.parseInt(_heightNumber.getText().toString()));


            realm.commitTransaction();

            setNotification("Save success");

        } catch (RealmPrimaryKeyConstraintException e) {
            e.printStackTrace();
            setNotification("User found on database");
        }
    }

    private void setNotification(String msg) {
        try {
            Snackbar.make(findViewById(R.id.login_transaction), msg, Snackbar.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void signup(){
        Log.d(TAG, "Sign up");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _alreadySignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait for a moment");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String fullname = _fullnameText.getText().toString();
        Integer weight = Integer.parseInt(_weightNumber.getText().toString());
        Integer height = Integer.parseInt(_heightNumber.getText().toString());

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        _createButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Register has been succeed. Please check first.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Your account is failed. Please check again.", Toast.LENGTH_LONG).show();

        _createButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirm_password = _confirmPassword.getText().toString();
        String fullname = _fullnameText.getText().toString();
        Integer weight = Integer.parseInt(_weightNumber.getText().toString());
        Integer height = Integer.parseInt(_heightNumber.getText().toString());

        //        Check username
        if (username.isEmpty() || username.length() < 7) {
            _usernameText.setError("At least 7 characters");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

//        Check password
        if (password.isEmpty() || password.length() < 7 || password.length() > 32) {
            _passwordText.setError("Password must be between 7 and 32 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

//        Check confirm password
        if (confirm_password.isEmpty() || !confirm_password.equals(password)) {
            _passwordText.setError("Confirm password must be same as password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

//        Check fullname
        if (fullname.isEmpty() || fullname.trim().length() > 0) {
            _fullnameText.setError("Fill the blank one");
            valid = false;
        } else {
            _fullnameText.setError(null);
        }

//        Check weight
        if (weight == null) {
            _weightNumber.setError("Fill the blank one");
            valid = false;
        }  else {
            _weightNumber.setError(null);
        }

//        Check height
        if (height == null) {
            _heightNumber.setError("Fill the blank one");
            valid = false;
        } else {
            _heightNumber.setError(null);
        }

        return valid;
    }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
