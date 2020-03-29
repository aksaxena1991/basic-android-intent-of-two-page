package com.example.anubhav.imageviewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private Button signUpBtn;
    private Button signInBtn;
    private EditText fName;
    private EditText lName;
    private EditText pswd;
    private EditText mail;
    private EditText uname;
    private FirebaseAuth fAuth;
    private ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = (Button) findViewById(R.id.signupButton);
        signInBtn= (Button) findViewById(R.id.signupLoginButton);
        fName = (EditText) findViewById(R.id.signupFirstname);
        lName = (EditText) findViewById(R.id.signupLastname);
        pswd = (EditText) findViewById(R.id.signupPassword);
        mail = (EditText) findViewById(R.id.signupEmail);
        uname = (EditText) findViewById(R.id.signupUsername);
        pBar = (ProgressBar) findViewById(R.id.signupProgressBar);
        pBar.setVisibility(View.INVISIBLE);
        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
            finish();
        }
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFirstname = fName.getText().toString().trim();
                String strLastname = lName.getText().toString().trim();
                String strEmail = mail.getText().toString().trim();
                String strPassword = pswd.getText().toString().trim();
                String strUsername = uname.getText().toString().trim();

                if(TextUtils.isEmpty(strFirstname)) {
                    fName.setError("Firstname is empty!");
                    return;
                }
                if(TextUtils.isEmpty(strLastname)) {
                    lName.setError("Lastname is empty!");
                    return;
                }
                if(TextUtils.isEmpty(strEmail)) {
                    mail.setError("Email ID is empty!");
                    return;
                }
                if(TextUtils.isEmpty(strUsername)) {
                    uname.setError("Username is empty!");
                    return;
                }
                if(TextUtils.isEmpty(strPassword)) {
                    pswd.setError("Password is empty!");
                    return;
                }
                if(strPassword.length() < 6) {
                    pswd.setError("Min password length should be 6 characters");
                    return;
                }
                pBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Thank you for registering with us!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                            pBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(SignupActivity.this, "Unable to register due to \n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });
    }
}
