package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MessageInboxActivity extends AppCompatActivity {

    RecyclerView recycle;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_inbox);
        recycle = findViewById(R.id.recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(layoutManager);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        Call<List<Message>> messagelistcall = ApiClient.getService().getMessage(sharedPreferences.getString("mail",null),sharedPreferences.getString("password",null));

        messagelistcall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messageList = response.body();
                    MessageInboxActivity.recycleadapter adapter = new MessageInboxActivity.recycleadapter(messageList);
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    class recycleadapter extends RecyclerView.Adapter<MessageInboxActivity.recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Message> list;
        private View.OnClickListener listener;
        public recycleadapter(List<Message> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MessageInboxActivity.recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,null);
            MessageInboxActivity.recycleadapter.MyViewHolder viewHolder = new MessageInboxActivity.recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MessageInboxActivity.recycleadapter.MyViewHolder holder, int position) {
            holder.message.setText( "  "+list.get(position).getMessage());

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
            TextView message;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                message = itemView.findViewById(R.id.message);
            }
        }
    }
}