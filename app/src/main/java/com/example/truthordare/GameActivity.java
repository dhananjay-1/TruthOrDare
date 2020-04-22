package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity
        implements NoticeDialogFragment.NoticeDialogListener, EndGameModal.EndGameModalListener {

    GlobalClass globalVar;
    Button btnAddPlayers;
    Button btnGo;
    TextView TextViewPlayer;
    ListView list_view;
    ArrayAdapter<String> arrayAdapter;
    Timer timer;
    Random rand;
    int count;
    int player_idx;

    Button btnTruth;
    Button btnDare;
    Button btnEndGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalVar = (GlobalClass) getApplicationContext();

        //if(globalVar.arrayListPlayers.size()==0)
            setContentView(R.layout.activity_game);
            btnAddPlayers =findViewById(R.id.btnAddPlayers);

    }

    @Override
    protected void onResume() {
        super.onResume();

        globalVar = (GlobalClass) getApplicationContext();

        if(globalVar.arrayListPlayers.size()==0) {
            setContentView(R.layout.activity_game);
            btnAddPlayers =findViewById(R.id.btnAddPlayers);
            btnAddPlayers.setEnabled(true);
        }
        else {
            startGame();
        }



    }

    public void addPlayers(View v)
    {
        Intent intent = new Intent(GameActivity.this, AddPlayers.class);
        btnAddPlayers.setEnabled(false);
        startActivity(intent);

    }


    public void startGame()
    {
        setContentView(R.layout.game_start);

        btnGo = findViewById(R.id.btnGo);
        TextViewPlayer = findViewById(R.id.TextViewPlayer);
        list_view = findViewById(R.id.ListView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, globalVar.arrayListScoreBoard);

        list_view.setAdapter(arrayAdapter);


        btnTruth = findViewById(R.id.btnTruth);
        btnDare = findViewById(R.id.btnDare);
        btnEndGame = findViewById(R.id.btnEndGame);

        btnTruth.setEnabled(false);
        btnDare.setEnabled(false);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rand = new Random();
                count=0;
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        while(count<20)
                        {
                            try {

                                Thread.sleep(100);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        int i = rand.nextInt(globalVar.arrayListPlayers.size());
                                        player_idx = i;
                                        String player = globalVar.arrayListPlayers.get(i);
                                        TextViewPlayer.setText(player);

                                        ++count;

                                        if(count==20)
                                        {
                                            btnTruth.setEnabled(true);
                                            btnDare.setEnabled(true);
                                            Toast toast = Toast.makeText(getApplicationContext(), "Hey player, choose truth or dare !", Toast.LENGTH_SHORT);
                                            toast.getView().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.modal_background));
                                            toast.show();
                                        }

                                    }
                                });

                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }


                    }

                };

                t.start();


            }

        });


        btnTruth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = rand.nextInt(globalVar.arrayListTruths.size());
                String s = globalVar.arrayListTruths.get(i);
                int colorId = ContextCompat.getColor(getApplicationContext(), R.color.colorBlue);
                showModal(s, colorId);
            }
        });


        btnDare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = rand.nextInt(globalVar.arrayListDares.size());
                String s = globalVar.arrayListDares.get(i);
                int colorId = ContextCompat.getColor(getApplicationContext(), R.color.colorRed);
                showModal(s, colorId);
            }
        });


        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> winnerList = globalVar.getWinners();
                showEndGameModal(winnerList);
            }
        });


    }




    public void showModal(String str, int colorId) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment(str, colorId);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");

    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        globalVar.incrementScore(player_idx);
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
    }




    public void showEndGameModal(ArrayList<String> list) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new EndGameModal(list);
        dialog.show(getSupportFragmentManager(), "EndGameModalFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onPlayAgainClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        globalVar.resetScores();
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Start again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeaveClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        globalVar.resetScores();
        Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
        finish();
    }



}
