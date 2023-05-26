package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button_Shop;
    ImageButton button_profil;
    TextView username;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_Shop = findViewById(R.id.btn_shop);
        button_profil = findViewById(R.id.btn_profil);
        username = findViewById(R.id.username);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        String usernameS = sharedPreferences.getString("username",null);
        username.setText(usernameS);

        button_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TiendaActivity.class));
            }
        });

        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EditProfileActivity.class));
            }
        });

    }
}