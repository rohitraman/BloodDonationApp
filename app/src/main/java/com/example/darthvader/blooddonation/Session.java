package com.example.darthvader.blooddonation;


import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("com.example.darthvader.blooddonation",Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void setLoggedIn(boolean loggedIn)
    {
        editor.putBoolean("loggedInMode",loggedIn);
        editor.commit();
    }
    public boolean loggedIn()
    {
        return preferences.getBoolean("loggedInMode",false);
    }
}
