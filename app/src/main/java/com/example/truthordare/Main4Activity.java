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

public class Main4Activity extends AppCompatActivity {

    GlobalClass globalVar;
    EditText edit_text;
    ListView list_view;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        globalVar = (GlobalClass) getApplicationContext();
        edit_text = findViewById(R.id.EditText);
        list_view = findViewById(R.id.ListView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, globalVar.arrayListDares);

        list_view.setAdapter(arrayAdapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                globalVar.removeDare(position);
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
        String json  = gson.toJson(globalVar.arrayListDares);
        editor.putString("dare list", json);
        editor.apply();
    }


    public void addDare(View v)
    {
        String str = edit_text.getText().toString();

        if(str.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
            return;
        }

        globalVar.addDare(str);
        arrayAdapter.notifyDataSetChanged();
        edit_text.setText("");
    }

}
