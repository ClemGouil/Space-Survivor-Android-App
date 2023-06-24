package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListQuestionActivity extends AppCompatActivity {

    RecyclerView recycle;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);
        recycle = findViewById(R.id.recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(layoutManager);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        Call<List<QuestionRequest>> questionlistcall = ApiClient.getService().getQuestion(sharedPreferences.getString("mail",null),sharedPreferences.getString("password",null));

        questionlistcall.enqueue(new Callback<List<QuestionRequest>>() {
            @Override
            public void onResponse(Call<List<QuestionRequest>> call, Response<List<QuestionRequest>> response) {
                if (response.isSuccessful()) {
                    List<QuestionRequest> questionList = response.body();
                    ListQuestionActivity.recycleadapter adapter = new ListQuestionActivity.recycleadapter(questionList);

                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), questionList.get(recycle.getChildAdapterPosition(view)).getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ListQuestionActivity.this, DetailQuestionActivity.class);
                            intent.putExtra("Date", questionList.get(recycle.getChildAdapterPosition(view)).getDate());
                            intent.putExtra("Title", questionList.get(recycle.getChildAdapterPosition(view)).getTitle());
                            intent.putExtra("Message", questionList.get(recycle.getChildAdapterPosition(view)).getMessage());
                            intent.putExtra("Response", questionList.get(recycle.getChildAdapterPosition(view)).getResponse());
                            startActivity(intent);
                            finish();
                        }
                    });

                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<QuestionRequest>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class recycleadapter extends RecyclerView.Adapter<ListQuestionActivity.recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<QuestionRequest> list;
        private View.OnClickListener listener;
        public recycleadapter(List<QuestionRequest> list){
            this.list = list;
        }

        @NonNull
        @Override
        public ListQuestionActivity.recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout,null);
            ListQuestionActivity.recycleadapter.MyViewHolder viewHolder = new ListQuestionActivity.recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ListQuestionActivity.recycleadapter.MyViewHolder holder, int position) {
            holder.title.setText("Title : " + list.get(position).getTitle());
            holder.date.setText("Date : " + list.get(position).getDate());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onClick(view);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView title, date;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                date = itemView.findViewById(R.id.date);
            }
        }
    }
}