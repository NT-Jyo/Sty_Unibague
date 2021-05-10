package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trbj.sty.Adapters.RecycleViewAdapterCourses;
import com.trbj.sty.Models.Annotations;
import com.trbj.sty.Models.Constants;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.trbj.sty.Models.Constants.IMAGE_COD;
import static com.trbj.sty.Models.Constants.INTENT_GALLERY;

public class AnnotationsNoteActivity extends AppCompatActivity implements View.OnClickListener {


    FirebaseAuth firebaseAuthB;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    StorageReference storageReference;

    SharedPreferenceUserData sharedPreferenceUserData;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    TextInputLayout text_input_layout_annotation_note_titleB;
    TextInputLayout text_input_layout_annotation_note_keywordsB;
    TextInputLayout text_input_layout_annotation_note_resumeB;
    TextInputLayout text_input_layout_annotation_note_exampleB;

    TextInputEditText text_input_edit_annotation_note_titleB;
    TextInputEditText text_input_edit_annotation_note_keywordsB;
    TextInputEditText text_input_edit_annotation_note_resumeB;
    TextInputEditText text_input_edit_annotation_note_exampleB;

    ImageView image_view_annotation_noteB;

    MaterialButton material_button_annotation_uploadB;
    MaterialButton material_button_photoB;
    MaterialButton material_button_galleryB;

    String path;
    File fileImage;
    Bitmap bitmap;
    Uri imageUri;
    Uri resultStartCrop;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotations_note);

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB = FirebaseFirestore.getInstance();
        firebaseCrashlyticsB= FirebaseCrashlytics.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog= new ProgressDialog(this);

        text_input_layout_annotation_note_titleB= findViewById(R.id.text_input_layout_annotation_note_title);
        text_input_layout_annotation_note_keywordsB= findViewById(R.id.text_input_layout_annotation_note_keywords);
        text_input_layout_annotation_note_resumeB= findViewById(R.id.text_input_layout_annotation_note_resume);
        text_input_layout_annotation_note_exampleB= findViewById(R.id.text_input_layout_annotation_note_example);

        text_input_edit_annotation_note_titleB= findViewById(R.id.text_input_edit_annotation_note_title);
        text_input_edit_annotation_note_keywordsB= findViewById(R.id.text_input_edit_annotation_note_keywords);
        text_input_edit_annotation_note_resumeB= findViewById(R.id.text_input_edit_annotation_note_resume);
        text_input_edit_annotation_note_exampleB= findViewById(R.id.text_input_edit_annotation_note_example);

        image_view_annotation_noteB= findViewById(R.id.image_view_annotation_note);

        material_button_annotation_uploadB= findViewById(R.id.material_button_annotation_upload);
        material_button_photoB=findViewById(R.id.material_button_photo);
        material_button_galleryB=findViewById(R.id.material_button_gallery);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        material_button_annotation_uploadB.setOnClickListener(this);
        material_button_galleryB.setOnClickListener(this);
        material_button_photoB.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(AnnotationsNoteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AnnotationsNoteActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AnnotationsNoteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }



    private String emailUser(){
        String emailUser=sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }

    private String loadNameTopic(){
        String nameSubject=sharedPreferenceSubjectsUser.getNameTopic();
        return nameSubject;
    }

    private String date() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        return currentDateandTime;
    }

    private String loadIdSubject(){
        String idSubject = sharedPreferenceSubjectsUser.getSubjectsUser();
        return idSubject;
    }

    private String loadNameSubject(){
        String nameSubject=sharedPreferenceSubjectsUser.getNameSubject();
        return nameSubject;
    }

    private void galleryPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,INTENT_GALLERY);
    }

    private void takePhoto(){
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileImage = createImageFile();
            imageUri = FileProvider.getUriForFile(AnnotationsNoteActivity.this, "com.trbj.sty.provider", fileImage);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, IMAGE_COD);
            }
        }catch (Exception e){
            Log.w("TAG", "takePhoto in failed", e);
            firebaseCrashlyticsB.log("takePhoto");
            firebaseCrashlyticsB.recordException(e);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "evidencia" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".png", storageDir
        );
        path = image.getAbsolutePath();
        return image;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_COD && resultCode==RESULT_OK){
            startCrop(imageUri);
        }else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            resultStartCrop = UCrop.getOutput(data);
            try {
                if (resultStartCrop != null) {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultStartCrop));
                    image_view_annotation_noteB.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(AnnotationsNoteActivity.this, "Por favor, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException e) {
                Log.w("TAG", "IMAGE_COD in failed", e);
                firebaseCrashlyticsB.log("onActivityResult");
                firebaseCrashlyticsB.recordException(e);
            }
        }

        if(requestCode==INTENT_GALLERY && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            if(imageUri!=null){
                startCrop(imageUri);
            }
        }else if (requestCode== UCrop.REQUEST_CROP&& resultCode==RESULT_OK){
            resultStartCrop = UCrop.getOutput(data);
            try {
                if (resultStartCrop != null) {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultStartCrop));
                    image_view_annotation_noteB.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(AnnotationsNoteActivity.this, "Por favor, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException e) {
                Log.w("TAG", "INTENT_GALLERYin failed", e);
                firebaseCrashlyticsB.log("onActivityResult");
                firebaseCrashlyticsB.recordException(e);
            }
        }

    }

    private void startCrop(@NonNull Uri uri){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "note"+timeStamp;
        UCrop ucrop = UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),imageFileName)));
        ucrop.useSourceImageAspectRatio();
        ucrop.withMaxResultSize(1080,1080);
        ucrop.withOptions(getCropOptions());
        ucrop.start(AnnotationsNoteActivity.this);
    }
    private UCrop.Options getCropOptions(){
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setToolbarTitle("Recortar Imagen");
        return options;
    }

    private void loadData(){

        try{
            String Title =text_input_edit_annotation_note_titleB.getText().toString().trim();
            String date=date();
            String keywords=text_input_edit_annotation_note_keywordsB.getText().toString().trim();
            String examples=text_input_edit_annotation_note_exampleB.getText().toString().trim();
            String resume=text_input_edit_annotation_note_resumeB.getText().toString().trim();
            String TopicName=loadNameTopic();
            String emailUser= emailUser();


            if(!Title.isEmpty() && !keywords.isEmpty() && !examples.isEmpty() && !resume.isEmpty()){
                text_input_layout_annotation_note_titleB.setError(null);
                text_input_layout_annotation_note_keywordsB.setError(null);
                text_input_layout_annotation_note_exampleB.setError(null);
                text_input_layout_annotation_note_resumeB.setError(null);

                progressDialog.setTitle("Cargando...");
                progressDialog.setMessage("\\(”▔□▔)/\\(”▔□▔)/\\(”▔□▔)/");
                progressDialog.setCancelable(false);
                progressDialog.show();

                StorageReference filePath = storageReference.child(emailUser).child("Course").child(loadNameSubject()).child(TopicName).child(resultStartCrop.getLastPathSegment());
                filePath.putFile(resultStartCrop).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadPhoto = taskSnapshot.getStorage().getDownloadUrl();
                        while(!downloadPhoto.isComplete());
                        Uri url = downloadPhoto.getResult();
                        Annotations annotations= new Annotations(Title,date,url.toString(),keywords,examples,resume,TopicName);
                        String[] idUser = emailUser.split("\\@");
                        if (idUser[1].equals("estudiantesunibague.edu.co") || idUser[1].equals("unibague.edu.co")) {
                            firebaseFirestoreB.collection("Unibague").document(emailUser()).collection("Course").document(loadIdSubject()).collection(TopicName).document().set(annotations).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    onBackPressed();
                                    Toast.makeText(AnnotationsNoteActivity.this,"Se guardo exitoxamente!",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        } else {
                            firebaseFirestoreB.collection("Usuario").document(emailUser()).collection("Course").document(loadIdSubject()).collection(TopicName).document().set(annotations).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    onBackPressed();
                                    Toast.makeText(AnnotationsNoteActivity.this,"Se guardo exitoxamente!",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });;
                        }
                    }
                });



            }if(Title.isEmpty()){
                text_input_layout_annotation_note_titleB.setError("Este campo es importante");
                text_input_layout_annotation_note_titleB.requestFocus();
            }if(keywords.isEmpty()){
                text_input_layout_annotation_note_keywordsB.setError("Este campo es importante");
                text_input_layout_annotation_note_keywordsB.requestFocus();
            }if(examples.isEmpty()){
                text_input_layout_annotation_note_exampleB.setError("Este campo es importante");
                text_input_layout_annotation_note_exampleB.requestFocus();
            }if(resume.isEmpty()){
                text_input_layout_annotation_note_resumeB.setError("Este campo es importante");
                text_input_layout_annotation_note_resumeB.requestFocus();
            }
        }catch (Exception e){
            firebaseCrashlyticsB.log("loginUser");
            firebaseCrashlyticsB.recordException(e);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_annotation_upload:
                loadData();
                break;
            case R.id.material_button_photo:
                takePhoto();
                break;
            case R.id.material_button_gallery:
                galleryPhoto();
                break;
        }
    }
}