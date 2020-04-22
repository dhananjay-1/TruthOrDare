package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    GlobalClass globalVar;
    ListView list_view;
    EditText edit_text;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        globalVar = (GlobalClass) getApplicationContext();
        list_view = findViewById(R.id.ListView);
        edit_text = findViewById(R.id.EditText);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, globalVar.arrayListTruths);
        list_view.setAdapter(arrayAdapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                globalVar.removeTruth(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json  = gson.toJson(globalVar.arrayListTruths);
        editor.putString("truth list", json);
        editor.apply();
    }

    public void addTruth(View v)
    {
        String str = edit_text.getText().toString();

        if(str.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
            return;
        }

        globalVar.addTruth(str);
        arrayAdapter.notifyDataSetChanged();
        edit_text.setText("");
    }



}
