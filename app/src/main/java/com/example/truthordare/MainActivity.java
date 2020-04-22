package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    GlobalClass globalVar;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalVar = (GlobalClass) getApplicationContext();
        loadData();

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

                finish();
            }
        }, 1000);

    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTruths = sharedPreferences.getString("truth list", null);
        String jsonDares = sharedPreferences.getString("dare list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> truths = gson.fromJson(jsonTruths, type);
        ArrayList<String> dares = gson.fromJson(jsonDares, type);

        if(truths==null)
        {
            truths = new ArrayList<>();
        }

        if(dares==null)
        {
            dares = new ArrayList<>();
        }

        globalVar.setArrayListTruths(truths);
        globalVar.setArrayListDares(dares);

    }


}
