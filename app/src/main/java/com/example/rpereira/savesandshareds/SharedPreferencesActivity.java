package com.example.rpereira.savesandshareds;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SharedPreferencesActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private Switch mswSilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        mswSilent = findViewById(R.id.idswSilent);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("silentMode", false);
        mswSilent.setChecked(silent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", mswSilent.isChecked());

        // Commit the edits.
        editor.apply();

    }
}
