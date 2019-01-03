package com.example.rpereira.savesandshareds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class InternalStorageActivity extends AppCompatActivity {

    private TextView tvInternalStorage;

    private static final String FILE_NAME = "main_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        tvInternalStorage = findViewById(R.id.idtvInternalStorage);

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            int c;
            StringBuilder tmp = new StringBuilder();
            while((c = fis.read()) != -1) {
                tmp.append(Character.toString((char) c));
            }
            fis.close();
            tvInternalStorage.setText(tmp.toString());
        } catch (Exception er) { er.printStackTrace(); }

    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(tvInternalStorage.getText().toString().getBytes());
            fos.close();
        } catch (Exception er) { er.printStackTrace(); }

    }
}
