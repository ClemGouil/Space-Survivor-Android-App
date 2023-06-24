package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    TextInputEditText editTextDate, editTextTitle, editTextMessage;
    Button button_send;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        editTextDate = findViewById(R.id.date);
        editTextTitle = findViewById(R.id.title);
        editTextMessage = findViewById(R.id.message);
        button_send = findViewById(R.id.btn_Send);


        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String mail = sharedPreferences.getString("mail", null);


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionRequest question = new QuestionRequest();
                question.setSender(mail);
                question.setDate(editTextDate.getText().toString());
                question.setTitle(editTextTitle.getText().toString());
                question.setMessage(editTextMessage.getText().toString());
                question.setResponse("");

                sendQuestion(question);}
        });

    }

    public void sendQuestion(QuestionRequest question){
        Call<QuestionRequest> questionResponseCall = ApiClient.getService().sendQuestion(question);
        questionResponseCall.enqueue(new Callback<QuestionRequest>() {
            @Override
            public void onResponse(Call<QuestionRequest> call, Response<QuestionRequest> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(QuestionActivity.this,message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(QuestionActivity.this, MainActivity.class));
                    finish();

                } else {
                    String message = "An error occurred";
                    Toast.makeText(QuestionActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionRequest> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(QuestionActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
