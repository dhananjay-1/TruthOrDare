package com.example.truthordare;

import android.app.Application;

import java.util.ArrayList;
import java.util.Vector;

public class GlobalClass extends Application {

    public ArrayList<String> arrayListTruths;
    public ArrayList<String> arrayListDares;
    public ArrayList<String> arrayListPlayers;
    public Vector<Integer> vectorScores;
    public ArrayList<String> arrayListScoreBoard;

    public GlobalClass()
    {
        arrayListTruths = new ArrayList<>();
        arrayListDares = new ArrayList<>();
        arrayListPlayers = new ArrayList<>();
        vectorScores = new Vector<Integer>();
        arrayListScoreBoard = new ArrayList<>();
    }


    public void setArrayListTruths(ArrayList<String> arrayListTruths) {
        this.arrayListTruths = arrayListTruths;
    }

    public void setArrayListDares(ArrayList<String> arrayListDares) {
        this.arrayListDares = arrayListDares;
    }

    public void addTruth(String str)
    {
        arrayListTruths.add(str);
    }

    public void removeTruth(int idx)
    {
        arrayListTruths.remove(idx);
    }

    public void addDare(String str)
    {
        arrayListDares.add(str);
    }

    public void removeDare(int idx)
    {
        arrayListDares.remove(idx);
    }

    public void addPlayers(String str)
    {
        arrayListPlayers.add(str);
        vectorScores.add(0);
        String s = str+" : 0";
        arrayListScoreBoard.add(s);
    }

    public void removePlayers(int idx)
    {
        arrayListPlayers.remove(idx);
        vectorScores.remove(idx);
        arrayListScoreBoard.remove(idx);
    }

    public void incrementScore(int idx)
    {
        int score = vectorScores.get(idx)+1;
        String player = arrayListPlayers.get(idx);

        vectorScores.set(idx, score);
        arrayListScoreBoard.set(idx, player+" : "+score);
    }

    public ArrayList<String> getWinners()
    {
        ArrayList<String> winnerList = new ArrayList<>();
        int big=0;

        for(int i=0; i<vectorScores.size(); i++)
        {
            int score = vectorScores.get(i);

            if(score>big)
            {
                big = score;
            }
        }

        for(int i=0; i<vectorScores.size(); i++)
        {
            int score = vectorScores.get(i);

            if(score==big)
            {
                String player = arrayListPlayers.get(i);
                winnerList.add(player);
            }

        }

        return winnerList;

    }

    public void resetScores()
    {
        for(int i=0; i<vectorScores.size(); i++)
        {
            vectorScores.set(i, 0);

            String player = arrayListPlayers.get(i);

            arrayListScoreBoard.set(i, player+" : 0");

        }
    }


}
