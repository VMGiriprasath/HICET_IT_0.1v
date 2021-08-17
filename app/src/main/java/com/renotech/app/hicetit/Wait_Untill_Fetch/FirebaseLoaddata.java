package com.renotech.app.hicetit.Wait_Untill_Fetch;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseLoaddata extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}
