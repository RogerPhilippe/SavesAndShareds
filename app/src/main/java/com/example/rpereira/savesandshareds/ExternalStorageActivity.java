package com.example.rpereira.savesandshareds;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
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

    private String mfileName = "SampleFile.txt";

    private File mMyExternalFile;

    private StringBuilder mMyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        meditPersist = findViewById(R.id.ideditPersist);

        Button mbtnPersis = findViewById(R.id.idbtnPersistir);

        Button mbtnRecuperar = findViewById(R.id.idbtnRecuperar);

        if (isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            mbtnPersis.setEnabled(false);
        }

        mbtnPersis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = new FileOutputStream(mMyExternalFile);
                    fos.write(meditPersist.getText().toString().getBytes());
                    fos.close();
                    /* Multiples lines
                    PrintWriter writer = new PrintWriter( new OutputStreamWriter( fos ) );
                    writer.println(meditPersist.getText().toString());
                    writer.println(meditPersist2.getText().toString());
                    writer.close();
                     */
                    meditPersist.setText("");
                    Toast.makeText(ExternalStorageActivity.this, mfileName+
                            " salvo no armazenamento externo...", Toast.LENGTH_SHORT).show();
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
                    mMyData = new StringBuilder();
                    while((strLine = bufferedReader.readLine()) != null) {
                        mMyData.append(strLine);
                    }
                    inputStream.close();
                    meditPersist.setText(mMyData);
                    Toast.makeText(ExternalStorageActivity.this, mfileName+
                            " Recuperado do armazenamento externo...", Toast.LENGTH_SHORT).show();
                } catch (Exception er) { er.printStackTrace(); }
            }
        });

        if (isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            mbtnPersis.setEnabled(false);
        }
        else {
            String mfilePath = "MyFileStorage";
            mMyExternalFile = new File(getExternalFilesDir(mfilePath), mfileName);
        }

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return !Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

}
