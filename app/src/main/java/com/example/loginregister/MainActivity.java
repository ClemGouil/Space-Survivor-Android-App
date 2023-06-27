package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton button_Shop, button_profil, button_inventory, button_forum, button_play,button_messageInbox, button_level;
    TextView username;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_Shop = findViewById(R.id.btn_shop);
        button_profil = findViewById(R.id.btn_profil);
        button_inventory = findViewById(R.id.btn_inventory);
        button_forum = findViewById(R.id.btn_forum);
        username = findViewById(R.id.username);
        button_messageInbox = findViewById(R.id.btn_messageInbox);
        button_play = findViewById(R.id.btn_play);
        button_level = findViewById(R.id.btn_score);

        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username",null));

        button_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TiendaActivity.class));
            }
        });

        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        button_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InventoryActivity.class));
            }
        });

        button_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForumActivity.class));
            }
        });

        button_messageInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MessageInboxActivity.class));
            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.dsa.mygame", "com.unity3d.player.UnityPlayerActivity"));
                startActivity(i);
            }
        });

        button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
            }
        });



    }
}