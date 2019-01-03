package com.example.rpereira.savesandshareds;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ExternalStorageActivity extends AppCompatActivity {

    private EditText meditPersist;

    private Button mbtnPersis;

    private Button mbtnRecuperar;

    private String mfileName = "SampleFile.txt";

    private String mfilePath = "MyFileStorage";

    private File mMyExternalFile;

    private String mMyData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        meditPersist = findViewById(R.id.ideditPersist);

        mbtnPersis = findViewById(R.id.idbtnPersistir);

        mbtnRecuperar = findViewById(R.id.idbtnRecuperar);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            mbtnPersis.setEnabled(false);
        }

        mbtnPersis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = new FileOutputStream(mMyExternalFile);
                    fos.write(meditPersist.getText().toString().getBytes());
                    fos.close();
                    meditPersist.setText("");
                    Toast.makeText(ExternalStorageActivity.this, mfileName+" salvo no armazenamento externo...", Toast.LENGTH_SHORT).show();
                } catch (Exception er) { er.printStackTrace(); }
            }
        });

        mbtnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = new FileInputStream(mMyExternalFile);
                    DataInputStream inputStream = new DataInputStream(fis);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String strLine;
                    while((strLine = bufferedReader.readLine()) != null) {
                        mMyData += strLine;
                    }
                    inputStream.close();
                    meditPersist.setText(mMyData);
                    Toast.makeText(ExternalStorageActivity.this, mfileName+" Recuperado do armazenamento externo...", Toast.LENGTH_SHORT).show();
                } catch (Exception er) { er.printStackTrace(); }
            }
        });

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
