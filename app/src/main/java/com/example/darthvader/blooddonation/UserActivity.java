package com.example.darthvader.blooddonation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    public TextView tvName, tvPhone, tvEmail, tvCity, tvState, tvPincode, tvBg;
    Intent intent;
    Button btnCall, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        intent = getIntent();

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvBg = findViewById(R.id.tv_bg);
        tvCity = findViewById(R.id.tv_city);
        tvState = findViewById(R.id.tv_state);
        tvPincode = findViewById(R.id.tv_pincode);
        btnCall = findViewById(R.id.btn_call);
        btnEmail = findViewById(R.id.btn_email);

        tvName.setText("Name:" + intent.getStringExtra("name"));
        tvPhone.setText("Phone:" + String.valueOf(intent.getLongExtra("phone", 0)));
        tvEmail.setText("Email:" + intent.getStringExtra("email"));
        tvBg.setText("Blood Group:" + intent.getStringExtra("bg"));
        tvCity.setText("City:" + intent.getStringExtra("city"));
        tvState.setText("State:" + intent.getStringExtra("state"));
        tvPincode.setText("Pincode:" + String.valueOf(intent.getIntExtra("pincode", 0)));

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UserActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, Manifest.permission.CALL_PHONE)) {

                    } else {
                        ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }

                } else {

                }
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{intent.getStringExtra("email")});
                i.putExtra(Intent.EXTRA_SUBJECT,"Regarding the donation of blood");
                i.putExtra(Intent.EXTRA_TEXT,"I require blood");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+91" + tvPhone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Enable permissions to use this feature", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        }
    }
}

