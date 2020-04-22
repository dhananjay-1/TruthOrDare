package com.example.truthordare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class EndGameModal extends DialogFragment {

    public ArrayList<String> winnerList;
    public ArrayAdapter arrayAdapter;

    public EndGameModal(ArrayList<String> list)
    {
        winnerList = list;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface EndGameModalListener {
        public void onPlayAgainClick(DialogFragment dialog);
        public void onLeaveClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    EndGameModal.EndGameModalListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (EndGameModal.EndGameModalListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement NoticeDialogListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        View v = inflater.inflate(R.layout.end_game_modal, null);

        builder.setView(v)
                .setPositiveButton("Play Again",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onPlayAgainClick(EndGameModal.this);
                    }
                })
                .setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onLeaveClick(EndGameModal.this);
                    }
                });


        ListView list_view = v.findViewById(R.id.ListView);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, winnerList);

        list_view.setAdapter(arrayAdapter);


        // Create the AlertDialog object and return it
        Dialog dialog =  builder.create();


        return dialog;
    }



}
