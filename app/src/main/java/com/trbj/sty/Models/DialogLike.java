package com.trbj.sty.Models;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.trbj.sty.R;

public class DialogLike {

    public interface DialogResult{
        void resultDialog (String state);
    }

    private DialogResult dialogResultInterface;

    public DialogLike(Context context, DialogResult activity){
        dialogResultInterface= activity;
        final Dialog dialog =  new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.dialog_like);

        MaterialButton materialButtonLike= (MaterialButton) dialog.findViewById(R.id.material_button_dialog_yes);
        MaterialButton materialButtonNotLike=(MaterialButton)dialog.findViewById(R.id.material_button_dialog_not);

        materialButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResultInterface.resultDialog("like");
                dialog.dismiss();
            }
        });

        materialButtonNotLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResultInterface.resultDialog("notLike");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
