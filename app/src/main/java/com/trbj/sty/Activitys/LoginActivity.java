package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trbj.sty.Shareds.SharedPreferenceUserData;
import com.trbj.sty.Models.User;
import com.trbj.sty.R;

import java.util.regex.Pattern;

import static com.trbj.sty.Models.Constants.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Attributes
     */

    GoogleSignInClient googleSignInClient_google;
    SignInButton  signInButton_google;

    MaterialButton materialButtonSign_in;
    MaterialButton materialButtonSign_register;

    FirebaseAuth firebaseAuthB;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextPassword;

    TextView textView_recover_password;


    SharedPreferenceUserData sharedPreferenceUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * //Instanced
         */
        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB = FirebaseFirestore.getInstance();
        firebaseCrashlyticsB= FirebaseCrashlytics.getInstance();

        sharedPreferenceUserData = new SharedPreferenceUserData(this);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.text_input_edit_email);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.text_input_edit_password);
        textView_recover_password = (TextView) findViewById(R.id.text_view_recover_password);

        signInButton_google = (SignInButton) findViewById(R.id.button_sign_google);
        materialButtonSign_in = (MaterialButton) findViewById(R.id.button_sign_in);
        materialButtonSign_register = (MaterialButton) findViewById(R.id.button_sign_register);


        signInButton_google.setSize(SignInButton.SIZE_STANDARD);

        signInButton_google.setOnClickListener(this);
        materialButtonSign_in.setOnClickListener(this);
        materialButtonSign_register.setOnClickListener(this);
        textView_recover_password.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        googleSignInClient_google();
        loadPreferenceEmailUser();
    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        updateUi(firebaseUser);
        if(firebaseUser != null){
            firebaseUser.reload();
        }
    }

    /**
     * Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
     * Google Sign In was successful, authenticate with Firebase
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
                firebaseCrashlyticsB.log("onActivityResult");
                firebaseCrashlyticsB.recordException(e);

            }
        }
    }

    /**
     * Sign in success, update UI with the signed-in user's information
     * If sign in fails, display a message to the user.
     * @param idToken
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuthB.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("TAG", "signInWithCredential:success");
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            FirebaseUser user = firebaseAuthB.getCurrentUser();
                            updateUi(user);
                            registerUserCloudFirebase(user);
                            startActivity(intent);
                        } else {
                            firebaseCrashlyticsB.log("firebaseAuthWithGoogle");
                            firebaseCrashlyticsB.recordException(task.getException());
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }

    /**
     * User registration in the database according to the type of mail
     * Ibague University and others
     * Name, email address, and profile photo Url
     * Id of the provider (ex: google.com)
     * @param mCurrentUser
     */

    private void registerUserCloudFirebase(FirebaseUser mCurrentUser) {

        try {
            String providerId = "";
            String uid = "";
            String name = "";
            String email = "";
            String photoUrl = "";
            for (UserInfo profile : mCurrentUser.getProviderData()) {

                providerId = profile.getProviderId();
                uid = profile.getUid();
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl().toString();
            }

            User mUser = new User(name, photoUrl, email, providerId, uid);
            sharedPreferenceUserData.setDataUser(name,photoUrl,email);
            String[] idUser = email.split("\\@");

            if (idUser[1].equals("estudiantesunibague.edu.co") || idUser[1].equals("unibague.edu.co")) {
                firebaseFirestoreB.collection("Unibague").document(email).set(mUser);
            } else {
                firebaseFirestoreB.collection("Usuario").document(email).set(mUser);
            }
        } catch (Exception e) {

            firebaseCrashlyticsB.log("CollectionId");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "CollectionId:failure", e);
        }


    }

    /**
     * Configure Google Sign In
     */
    private void googleSignInClient_google() {
        try {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            googleSignInClient_google = GoogleSignIn.getClient(this, googleSignInOptions);
        } catch (Exception e) {
            firebaseCrashlyticsB.log("googleSignInClient_google");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "googleSignInClient_google:failure", e);
        }


    }

    private void signInGoogle() {
        try {
            Intent intentSignIn = googleSignInClient_google.getSignInIntent();
            startActivityForResult(intentSignIn, RC_SIGN_IN);
        } catch (Exception e) {
            firebaseCrashlyticsB.log("signInGoogle");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "signInGoogle:failure", e);
        }

    }


    /**
     * @param firebaseUser
     */

    private void updateUi(FirebaseUser firebaseUser) {

        try {
            if (firebaseUser != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
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
     * validate editText and login User
     */
    private void loginUser(){
        try{
            String emailValidate = textInputEditTextEmail.getText().toString().trim();
            String passwordValidate = textInputEditTextPassword.getText().toString().trim();


            if(!emailValidate.isEmpty() && !passwordValidate.isEmpty()){
                textInputLayoutEmail.setError(null);
                textInputLayoutPassword.setError(null);

                if(!validateEmail(emailValidate)){
                    textInputLayoutEmail.setError("Email no válido");
                }else {
                    loginUserFirebase(emailValidate,passwordValidate);
                }
            }if(emailValidate.isEmpty()){
                textInputLayoutEmail.setError("Este campo es necesario");
                textInputLayoutEmail.requestFocus();
            }if(passwordValidate.isEmpty()){
                textInputLayoutPassword.setError("Este campo es necesario");
                textInputLayoutPassword.requestFocus();
            }
        }catch (Exception e){
            firebaseCrashlyticsB.log("loginUser");
            firebaseCrashlyticsB.recordException(e);
        }
    }

    /**
     * Sign in success, update UI with the signed-in user's information
     * If sign in fails, display a message to the user.
     * login user firebase auth
     * @param email
     * @param password
     */

    private void loginUserFirebase(String email, String password) {

        firebaseAuthB.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = firebaseAuthB.getCurrentUser();
                            sharedPreferenceUserData.setDataUser(user.getDisplayName(),String.valueOf(user.getPhotoUrl()),user.getEmail());
                            updateUi(user);
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUi(null);
                        }
                    }
                });
    }



    private void recoverPassword() {
        try{
            String emailReset = textInputEditTextEmail.getText().toString();

            if(!validateEmail(emailReset)){
                textInputLayoutEmail.setError("Email no válido");
            }else {
                firebaseAuthB.sendPasswordResetEmail(emailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this,"Revisa la bandeja de entrada de tu correo electrónico", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,"Correo electrónico no encontrado ", Toast.LENGTH_SHORT).show();
                            firebaseCrashlyticsB.recordException(task.getException());
                        }
                    }
                });
            }
        }catch (Exception e){
            firebaseCrashlyticsB.log("recoverPassword");
            firebaseCrashlyticsB.recordException(e);
        }

    }

    /**
     * load shared preference email user
     */

    private void loadPreferenceEmailUser(){
        textInputEditTextEmail.setText(sharedPreferenceUserData.getEmailUser());
    }

    /**
     * Intent Register activity
     */

    private void registerButton(){
        try{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception e){
            firebaseCrashlyticsB.log("register");
            firebaseCrashlyticsB.recordException(e);
            Log.w("TAG", "register:failure", e);
        }
    }


    /**
     * Button function
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sign_google:
                signInGoogle();
                break;

            case R.id.button_sign_register:
                registerButton();
                break;
            case R.id.button_sign_in:
                loginUser();
                break;
            case R.id.text_view_recover_password:
                recoverPassword();
                break;
        }
    }
}