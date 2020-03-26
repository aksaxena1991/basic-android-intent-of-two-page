package com.example.anubhav.imageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button letsGoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        letsGoBtn = (Button) findViewById(R.id.btn_lets_go);
        letsGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
//        Name = (EditText) findViewById(R.id.reg_name);
//        Password = (EditText) findViewById(R.id.reg_password);
//        Login = (Button) findViewById(R.id.reg_login_button);
//        Info = (TextView) findViewById(R.id.reg_text);
//        Info.setText("No of attempts remaining: "+ String.valueOf(counter));
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//validate(Name.getText().toString(), Password.getText().toString());
//            }
//        });
//    }
//    private void validate(String name, String password) {
//        if((name == "admin")) {
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            startActivity(intent);
//        } else {
//            counter--;
//            Info.setText("No of attempts remaining: "+ String.valueOf(counter));
//            if(counter <= 0) {
//                Login.setEnabled(false);
//            }
//        }
    }
}