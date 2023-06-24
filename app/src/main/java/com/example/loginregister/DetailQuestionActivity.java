package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailQuestionActivity extends AppCompatActivity {

    TextView date,title, question, response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);
        title = findViewById(R.id.Title);
        date = findViewById(R.id.Date);
        response = findViewById(R.id.Response);
        question = findViewById(R.id.Question);

        String titlereceived = getIntent().getExtras().getString("Title");
        String messagereceived = getIntent().getExtras().getString("Message");
        String responsereceived = getIntent().getExtras().getString("Response");
        String datereceived = getIntent().getExtras().getString("Date");

        title.setText("Title : " + titlereceived);
        date.setText("Date : " + datereceived);
        question.setText(messagereceived);
        response.setText(responsereceived);
    }
}