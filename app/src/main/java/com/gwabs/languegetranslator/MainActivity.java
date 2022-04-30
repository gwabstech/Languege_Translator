package com.gwabs.languegetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.gwabs.languegetranslator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding ;
    private String textFrom, textTo;
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
                        break;
                    case 1:
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
             //sendTranslation();
        });
    }

    private void sendTranslation(String textFrom,String textTo,String text){

        if (text !=null){
            if (textFrom.equals("None") || textTo.equals("None")){
                Toast.makeText(this, "Please select Source lang and Target lang", Toast.LENGTH_SHORT).show();
            }
        }else {
            mainBinding.edtTextToTranslate.setError("Please Enter Text");
        }

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(textFrom)
                        .setTargetLanguage(textTo)
                        .build();
        final Translator englishGermanTranslator =
                Translation.getClient(options);
    }
}