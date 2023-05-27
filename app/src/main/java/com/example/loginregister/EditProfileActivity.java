package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    TextView editUsername, editMail, editPassword;

    SharedPreferences sharedPreferences;

    Button button_change, button_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editUsername = findViewById(R.id.username);
        editMail = findViewById(R.id.mail);
        editPassword = findViewById(R.id.password);
        button_change = findViewById(R.id.btn_Change);
        button_logout = findViewById(R.id.btn_LogOut);


        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        editUsername.setText("Username : " + sharedPreferences.getString("username",null));
        editMail.setText("Mail : " + sharedPreferences.getString("mail",null));
        editPassword.setText("Password : " + sharedPreferences.getString("password",null));

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(EditProfileActivity.this,SplashScreenActivity.class));
                finish();
            }
        });

        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this,ChangesProfilActivity.class));
                finish();
            }
        });


    }
}