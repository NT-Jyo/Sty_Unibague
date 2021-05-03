package com.trbj.sty.Shareds;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUserData {
    SharedPreferences sharedPreferences;

    public SharedPreferenceUserData(Context context){
        sharedPreferences = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
    }

    public void setDataUser(String name, String photo, String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nameUser", name);
        editor.putString("photoUser",photo);
        editor.putString("emailUser",email);
        editor.commit();
    }

    public String getNameUser(){
        String nameUser=sharedPreferences.getString("nameUser","");
        return nameUser;
    }
    //TODO Cambiar ruta de fotografia al migrar a base de datos principal
    public String getPhotoUser(){
        String photoUser = sharedPreferences.getString("photoUser","https://firebasestorage.googleapis.com/v0/b/trabajogrado-77f48.appspot.com/o/Image%20Default%2FuserDefault.jpg?alt=media&token=d2c4929d-168f-499f-8031-be71eca79a56");
        return photoUser;

    }

    public String getEmailUser(){
        String emailUser = sharedPreferences.getString("emailUser","");
        return emailUser;
    }

    public void deleteData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }


}
