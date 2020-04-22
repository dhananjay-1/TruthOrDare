package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    GlobalClass globalVar;
    Button btnTruth;
    Button btnDare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        globalVar = (GlobalClass) getApplicationContext();
        btnTruth = findViewById(R.id.addTruths);
        btnDare = findViewById(R.id.addDares);
    }


    @Override
    protected void onResume() {
        super.onResume();

        btnTruth = findViewById(R.id.addTruths);
        btnDare = findViewById(R.id.addDares);

        btnTruth.setEnabled(true);
        btnDare.setEnabled(true);
    }


    public void goToAddTruths(View v)
    {
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        btnTruth.setEnabled(false);
        startActivity(intent);
    }

    public void goToAddDares(View v)
    {
        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
        btnDare.setEnabled(false);
        startActivity(intent);
    }

    public void startGame(View v)
    {
        int n1 = globalVar.arrayListTruths.size();
        int n2 = globalVar.arrayListDares.size();

        if(n1==0 || n2==0)
        {
            Toast.makeText(getApplicationContext(), "Please add at least 1 question in each of truth and dare sections", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent(Main2Activity.this, GameActivity.class);
        startActivity(intent);
    }

}
