package com.example.transporte_pay.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.transporte_pay.R;

public class ChangePassDialog extends AppCompatDialogFragment{
    private EditText current, newPass, confirm;
    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.changepass_dialog, null);
        builder.setView(view)
                .setTitle("CHANGE PASSWORD")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String currentPass = current.getText().toString();
                        String newPassword = newPass.getText().toString();
                        String confirmPass = confirm.getText().toString();
                        listener.applyTexts(currentPass,newPassword,confirmPass);
                    }
                });
        current = view.findViewById(R.id.currentPass_et);
        newPass = view.findViewById(R.id.newPass_et);
        confirm = view.findViewById(R.id.confirmPass_et);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement ExampleDialogListener");
        }
    }
    public interface DialogListener {
        void applyTexts(String currentPass,String newPassword,String confirmPass);
    }

}
