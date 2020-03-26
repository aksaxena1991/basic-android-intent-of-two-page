package com.example.anubhav.imageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private TextView forgotPassword;
    private Button loginRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
         username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginRegisterBtn = (Button) findViewById(R.id.login_register_btn);
        forgotPassword = (TextView) findViewById(R.id.login_forgot);
        loginBtn.setEnabled(false);
        loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(username.toString(), password.toString());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });




    }
    private void validate(String username, String password) {
        if((username.length() == 0) && (password.length() == 0)) {
            loginBtn.setEnabled(false);
        }  else {
            loginBtn.setEnabled(true);
            Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
            startActivity(intent);

        }
    }
}
