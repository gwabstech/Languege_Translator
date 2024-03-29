package com.gwabs.languegetranslator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.gwabs.languegetranslator.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  private   ActivityMainBinding mainBinding ;
    private String textFrom, textTo;
    private ProgressDialog progress;
    //private boolean isConnected = false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainBinding.spinnerFrom.setAdapter(adapter);
        mainBinding.spinnerTo.setAdapter(adapter);
          progress= new ProgressDialog(this);
          progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          progress.setTitle("Please wait....");
          progress.setCancelable(false);


        mainBinding.spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        textFrom = "None";
                        break;
                    case 1:
                        textFrom ="en";
                        break;
                    case 2:
                        textFrom ="ar";
                        break;
                    case 3:
                        textFrom ="fr";
                        break;
                    case 4:
                        textFrom ="sw";
                        break;
                    case 5:
                        textFrom ="tr";
                        break;
                    case 6:
                        textFrom ="pt";
                        break;
                    case 7:
                        textFrom ="de";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Noting Selected", Toast.LENGTH_SHORT).show();
            }
        });

        mainBinding.spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        textTo = "None";
                        break; case 1:
                        textTo ="en";
                        break;
                    case 2:
                        textTo ="ar";
                        break;
                    case 3:
                        textTo ="fr";
                        break;
                    case 4:
                        textTo ="sw";
                        break;
                    case 5:
                        textTo ="tr";
                        break;
                    case 6:
                        textTo ="pt";
                        break;
                    case 7:
                        textTo ="de";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Noting Selected", Toast.LENGTH_SHORT).show();
            }
        });

        mainBinding.btnTranslate.setOnClickListener(view1 -> {

            if (TextUtils.isEmpty(mainBinding.edtTextToTranslate.getText().toString())){
                mainBinding.edtTextToTranslate.setError("Please Enter Text");
            }else {

                if (textFrom.equals("None") || textTo.equals("None")){
                    Toast.makeText(this, "Please select Source lang and Target lang", Toast.LENGTH_SHORT).show();
                }else if (textFrom.equals(textTo)){
                    Toast.makeText(this, "Please select Source lang and Target lang", Toast.LENGTH_SHORT).show();
                }else{
                    // progress.show();
                    sendTranslation(textFrom,textTo,mainBinding.edtTextToTranslate.getText().toString());
                }

            }
        });

        mainBinding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mainBinding.txResult.getText() != null){
                    copyText(mainBinding.txResult.getText().toString());
                    mainBinding.txResult.setTextIsSelectable(true);
                }else {
                    mainBinding.txResult.setTextIsSelectable(true);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendTranslation(String textFrom, String textTo, String text){
        progress.show();


        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(textFrom)
                        .setTargetLanguage(textTo)
                        .build();
        final Translator translator =
                Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();
        translator.downloadModelIfNeeded(conditions)
        .addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String s) {
                                progress.dismiss();
                                Log.i("Tag",s.toString());
                                mainBinding.txResult.setText(s.toString());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress.dismiss();
                                mainBinding.txResult.setText(e.toString());
                            }
                        });

                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress.dismiss();
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
      //  translator.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void copyText(String text){
        ClipboardManager cm = (ClipboardManager)  getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }


    private boolean isConnected(){

            boolean connected = false;
            try {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                return connected;
            } catch (Exception e) {
                Log.e("Connectivity Exception", e.getMessage());
            }
            return connected;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isConnected()){
            Toast.makeText(MainActivity.this,"Please connect to Internet for maximum performance",Toast.LENGTH_SHORT).show();
        }
    }
}