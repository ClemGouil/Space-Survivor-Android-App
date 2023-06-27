package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelActivity extends AppCompatActivity {

    TextView level1, level2, level3, level4, level5, level6,level7, level8, level9,level10;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        level1= findViewById(R.id.level1);
        level2= findViewById(R.id.level2);
        level3= findViewById(R.id.level3);
        level4= findViewById(R.id.level4);
        level5= findViewById(R.id.level5);
        level6= findViewById(R.id.level6);
        level7= findViewById(R.id.level7);
        level8= findViewById(R.id.level8);
        level9= findViewById(R.id.level9);
        level10= findViewById(R.id.level10);

        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        Call<Level> scorecall = ApiClient.getService().getLevel(sharedPreferences.getString("mail",null),sharedPreferences.getString("password",null));

        scorecall.enqueue(new Callback<Level>() {
            @Override
            public void onResponse(Call<Level> call, Response<Level> response) {
                if (response.isSuccessful()) {
                    Level level = response.body();
                    level1.setText(" Level 1 : " + level.getLevel1()+" points");
                    level2.setText(" Level 2 : " + level.getLevel2()+" points");
                    level3.setText(" Level 3 : " + level.getLevel3()+" points");
                    level4.setText(" Level 4 : " + level.getLevel4()+" points");
                    level5.setText(" Level 5 : " + level.getLevel5()+" points");
                    level6.setText(" Level 6 : " + level.getLevel6()+" points");
                    level7.setText(" Level 7 : " + level.getLevel7()+" points");
                    level8.setText(" Level 8 : " + level.getLevel8()+" points");
                    level9.setText(" Level 9 : " + level.getLevel9()+" points");
                    level10.setText(" Level 10 : " + level.getLevel10()+" points");

                }
            }

            @Override
            public void onFailure(Call<Level> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}