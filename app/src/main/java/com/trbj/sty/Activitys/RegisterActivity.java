package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trbj.sty.Shareds.SharedPreferenceUserData;
import com.trbj.sty.Models.User;
import com.trbj.sty.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Attributes
     */

    TextInputLayout textInputLayoutNameRegister;
    TextInputLayout textInputLayoutEmailRegister;
    TextInputLayout textInputLayoutPasswordRegister;

    TextInputEditText textInputEditTextNameRegister;
    TextInputEditText textInputEditTextEmailRegister;
    TextInputEditText textInputEditTextPasswordRegister;

    MaterialButton materialButtonRegisterUser;

    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    FirebaseFirestore firebaseFirestoreB;

    SharedPreferenceUserData sharedPreferenceUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * Instanced
         */

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();
        firebaseFirestoreB = FirebaseFirestore.getInstance();

        sharedPreferenceUserData = new SharedPreferenceUserData(this);

        textInputLayoutNameRegister=(TextInputLayout)findViewById(R.id.text_input_layout_name_register);
        textInputLayoutEmailRegister=(TextInputLayout)findViewById(R.id.text_input_layout_email_register);
        textInputLayoutPasswordRegister=(TextInputLayout)findViewById(R.id.text_input_layout_password_register);

        textInputEditTextNameRegister=(TextInputEditText)findViewById(R.id.text_input_edit_name_register);
        textInputEditTextEmailRegister=(TextInputEditText)findViewById(R.id.text_input_edit__email_register);
        textInputEditTextPasswordRegister=(TextInputEditText)findViewById(R.id.text_input_edit_password_register);

        materialButtonRegisterUser=(MaterialButton)findViewById(R.id.button_sign_register_user);

        materialButtonRegisterUser.setOnClickListener(this);
    }

    /**
     * Check if user is signed in (non-null) and update UI accordingly.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuthB.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    /**
     * validate editText and register User
     */
    private void registerUser(){
        try{
            String emailValidate = textInputEditTextEmailRegister.getText().toString();
            String passwordValidate = textInputEditTextPasswordRegister.getText().toString();
            String nameValidate = textInputEditTextNameRegister.getText().toString();

            if(!emailValidate.isEmpty() && !passwordValidate.isEmpty() && !nameValidate.isEmpty()){
                textInputLayoutEmailRegister.setError(null);
                textInputLayoutPasswordRegister.setError(null);
                textInputLayoutNameRegister.setError(null);

                if(!validateEmail(emailValidate)){
                    textInputLayoutEmailRegister.setError("Email no válido");
                }else {
                    registerUserFirebase(nameValidate,emailValidate,passwordValidate);
                }
            }if(emailValidate.isEmpty()){
                textInputLayoutEmailRegister.setError("Este campo es necesario");
                textInputLayoutEmailRegister.requestFocus();
            }if(passwordValidate.isEmpty()){
                textInputLayoutPasswordRegister.setError("Este campo es necesario");
                textInputLayoutPasswordRegister.requestFocus();
            }if(nameValidate.isEmpty()){
                textInputLayoutNameRegister.setError("Este campo es necesario");
                textInputLayoutNameRegister.requestFocus();
            }
        }catch (Exception e){
            firebaseCrashlyticsB.log("validateDate");
            firebaseCrashlyticsB.recordException(e);
        }
    }


    /**
     * register user in auth firebase
     * @param name
     * @param email
     * @param password
     */
    private void registerUserFirebase(String name, String email,String password){

        String photo = "https://firebasestorage.googleapis.com/v0/b/trabajogrado-77f48.appspot.com/o/Image%20Default%2FuserDefault.jpg?alt=media&token=d2c4929d-168f-499f-8031-be71eca79a56";
        firebaseAuthB.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("TAG", "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuthB.getCurrentUser();
                    registerUserDataBase(name,photo,user.getEmail(),user.getProviderId(),user.getUid());
                    sharedPreferenceUserData.setDataUser(name,photo,email);
                    updateUi(user,name);
                }else {
                    Toast.makeText(RegisterActivity.this, "Este email ya existe.", Toast.LENGTH_SHORT).show();
                    updateUi(null,null);
                }
            }
        });

    }



    /**
     * Update the user's profile in the auth firebase
     * @param firebaseUser
     * @param name
     */

    private void updateUi(FirebaseUser firebaseUser, String name){
        try {
            if (firebaseUser != null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Tu registro ha sido exitoso!.", Toast.LENGTH_SHORT).show();

                                    Log.d("TAG", "User profile updated.");
                                }
                            }
                        });
            }
        } catch (Exception e) {
            firebaseCrashlyticsB.log("updateUi");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "updateUi:failure", e);
        }
    }


    /**
     * validate email @
     * @param email
     * @return
     */
    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    /**
     * register user in the database cloud firebase
     *
     * @param name
     * @param photoUrl
     * @param email
     * @param providerId
     * @param uid
     */
    private void registerUserDataBase(String name, String photoUrl, String email , String providerId, String uid){
        try {

            User mUser = new User(name, photoUrl, email, providerId, uid);
            String[] emailUser = email.split("\\@");

            if (emailUser[1].equals("estudiantesunibague.edu.co") || emailUser[1].equals("unibague.edu.co")) {
                firebaseFirestoreB.collection("Unibague").document(email).set(mUser);
            } else {
                firebaseFirestoreB.collection("Usuario").document(email).set(mUser);
            }
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

            firebaseCrashlyticsB.log("CollectionId");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "registerUserDataBase:failure", e);
        }

    }


    /**
     *buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sign_register_user:
                registerUser();
                break;

        }
    }
}