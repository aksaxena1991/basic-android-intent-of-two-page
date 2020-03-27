package com.example.anubhav.imageviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class HomeActivity extends AppCompatActivity {

    private Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            TextView textView = (TextView) findViewById(R.id.list_item);
            textView.setText(getCallDetails());
        }

        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this,"Permission Granted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No Permission Granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        }
    }

    private String getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,null, null, null,null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details: \n\n");
        while (managedCursor.moveToNext()) {
            String phoneNumber = managedCursor.getString(number);
            String phoneType = managedCursor.getString(type);
            String phoneDate = managedCursor.getString(date);
            Date çallDayTime = new Date(Long.valueOf(phoneDate));
            String phoneDuration =  managedCursor.getString(duration);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yy HH:mm:ss");
            String dateFormated = formatter.format(çallDayTime);
            String dir = null;
            int dirCode = Integer.parseInt(phoneType);
            switch (dirCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing Call";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming Call";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed Call";
                    break;
                case CallLog.Calls.BLOCKED_TYPE:
                    dir = "Blocked Call";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    dir = "Rejected Call";
                    break;
                case CallLog.Calls.VOICEMAIL_TYPE:
                    dir = "Voicemail Call";
                    break;

            }
            sb.append("\nContact Number " + phoneNumber + "\nContact Type " + dir + "\nDate "+ dateFormated + "\nCall Duration "+ phoneDuration);
            sb.append("\n-------------------------------------------------------------------");

        }
        managedCursor.close();
        return sb.toString();
    }
}
