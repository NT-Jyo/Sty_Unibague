package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trbj.sty.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DataBookPageActivity extends AppCompatActivity {

    TextView text_view_title_pageB;
    TextView text_view_date_pageB;
    TextView text_view_keywords_pageB;
    TextView text_view_resume_pageB;
    TextView text_view_examples_pageB;
    ImageView image_view_pageB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_book_page);

        text_view_title_pageB=findViewById(R.id.text_view_title_page);
        text_view_date_pageB=findViewById(R.id.text_view_date_page);
        text_view_keywords_pageB=findViewById(R.id.text_view_keywords_page);
        text_view_resume_pageB=findViewById(R.id.text_view_resume_page);
        text_view_examples_pageB=findViewById(R.id.text_view_examples_page);
        image_view_pageB=findViewById(R.id.image_view_page);

        Bundle info=this.getIntent().getExtras();
        if(info!=null){
            String title= info.getString("title");
            String date= info.getString("date");
            String keyWords= info.getString("keyWords");
            String resume= info.getString("resume");
            String examples= info.getString("examples");
            String topic= info.getString("topic");
            String photoURL=info.getString("photoURL");

            setTitle(topic);

            text_view_title_pageB.setText(title);
            text_view_date_pageB.setText(date);
            text_view_keywords_pageB.setText(keyWords);
            text_view_resume_pageB.setText(resume);
            text_view_examples_pageB.setText(examples);
            System.out.println(photoURL+"laaaaaaa imagen es");
            Glide.with(DataBookPageActivity.this)
                    .load(photoURL)
                    .into(image_view_pageB);


        }


    }


}