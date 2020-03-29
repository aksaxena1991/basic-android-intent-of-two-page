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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private TextView forgotPassword;
    private Button loginRegisterBtn;
    private ProgressBar pBar;
    private FirebaseAuth fAuth;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        fAuth = FirebaseAuth.getInstance();
        pBar = (ProgressBar) findViewById(R.id.signinProgressBar);
        pBar.setVisibility(View.INVISIBLE);
         username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginRegisterBtn = (Button) findViewById(R.id.login_register_btn);
        forgotPassword = (TextView) findViewById(R.id.login_forgot);
//        loginBtn.setEnabled(false);
        loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                pBar.setVisibility(View.INVISIBLE);

            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                validate(username.toString(), password.toString());

                String strUsername = username.getText().toString().trim();
                String strPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(strUsername)) {
                    username.setError("Username is empty");
                    counter++;
                    return;
                }
                if(TextUtils.isEmpty(strPassword)) {
                    password.setError("Password is empty");
                    counter++;
                    return;
                }



                    fAuth.signInWithEmailAndPassword(strUsername,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(getApplicationContext(),"Thank you for authenticating with us.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);

                                }
                            });
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Authentication has been failed due to the following reason:\n"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    });



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
