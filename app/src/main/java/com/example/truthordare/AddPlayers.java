package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddPlayers extends AppCompatActivity {

    GlobalClass globalVar;
    Button bt;
    EditText edit_text;
    ListView list_view;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        globalVar = (GlobalClass) getApplicationContext();
        bt = findViewById(R.id.btnAdd);
        edit_text = findViewById(R.id.EditText);
        list_view = findViewById(R.id.ListView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, globalVar.arrayListPlayers);

        list_view.setAdapter(arrayAdapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                globalVar.removePlayers(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "player removed", Toast.LENGTH_SHORT).show();
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = edit_text.getText().toString();

                if(str.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter plaayer's name", Toast.LENGTH_SHORT).show();
                    return;
                }

                globalVar.addPlayers(str);
                arrayAdapter.notifyDataSetChanged();
                edit_text.setText("");
            }
        });


    }
}
