package com.metavision.glucometer.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
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
import io.realm.RealmResults;

/**
 * Created by MetaVision on 10/07/2017.
 */

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private Realm realm;
    private RealmObjectGluco realmObjectGluco;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.link_guest) TextView _guestLink;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.btn_signup) Button _signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });

        _guestLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //    Login function
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        /*RealmResults<RealmObjectGluco> realmObjectGlucos = realm.where(RealmObjectGluco.class).findAll();
        for (RealmObjectGluco realmObjectGluco : realmObjectGlucos) {
            if (username.equals(realmObjectGluco.getUsername()) && password.equals(realmObjectGluco.getPassword())) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Username or password did not match.", Toast.LENGTH_LONG).show();
                _loginButton.setEnabled(true);
            }
        }*/

//        Check authentication
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }
                , 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Login failed!", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

//    private boolean checkUser(String username, String password) {
//        RealmResults<RealmObjectGluco> realmObjectGlucos = realm.where(RealmObjectGluco.class).findAll();
//        for (RealmObjectGluco realmObjectGluco : realmObjectGlucos) {
//            if (username.equals(realmObjectGluco.getUsername()) && password.equals(realmObjectGluco.getPassword())) {
//                Log.e(TAG, realmObjectGluco.getUsername());
//                return true;
//            }
//        }
//        Log.e(TAG, String.valueOf(realm.where(RealmObjectGluco.class).contains("Username", username)));
//        return false;
//    }

    public boolean validate(){
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("Enter the correct username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 7 || password.length() > 32) {
            _passwordText.setError("Password must be between 7 and 32 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}