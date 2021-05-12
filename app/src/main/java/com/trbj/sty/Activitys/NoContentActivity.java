package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.trbj.sty.R;

public class NoContentActivity extends AppCompatActivity {

    MaterialButton material_button_no_contentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_content);

        material_button_no_contentB =findViewById(R.id.material_button_no_content);
        material_button_no_contentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

}