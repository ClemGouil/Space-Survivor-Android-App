package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForumActivity extends AppCompatActivity {

    Button button_showForum, button_sendQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        button_showForum = findViewById(R.id.btn_showForum);
        button_sendQuestion = findViewById(R.id.btn_SendQuestion);

        button_sendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForumActivity.this,QuestionActivity.class));
            }
        });

        button_showForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForumActivity.this,ListQuestionActivity.class));
            }
        });
    }
}