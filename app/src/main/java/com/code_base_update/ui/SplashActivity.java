package com.code_base_update.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import officials_module.ui.OfficialDashboard;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareOfflineMode();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void prepareOfflineMode() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
