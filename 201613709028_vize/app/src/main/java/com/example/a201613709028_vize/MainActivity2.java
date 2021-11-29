package com.example.a201613709028_vize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    EditText baslik_et;
    EditText mesaj_et;
    RadioGroup radioGroup;
    Button buton;

    Araci araci=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        radioGroup  = (RadioGroup) findViewById(R.id.radioGroup);
        mesaj_et=(EditText) findViewById(R.id.mesaj_et);
        araci=Araci.getInstance(MainActivity2.this);
        araci.readValues();
        ArrayList<String> akraba_grup= (ArrayList<String>) araci.getAkraba_grup();
        ArrayList<String> arkadas_grup= (ArrayList<String>) araci.getArkadas_grup();
        ArrayList<String> is_grup=araci.getIs_grup();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId=radioGroup.getCheckedRadioButtonId();
                switch (selectedId){
                    case R.id.akraba_rb:
                        for (int i=0;i<akraba_grup.size();i++)
                            mesaj_et.setText(akraba_grup.get(i)+"\n");
                        break;
                    case R.id.arkadas_rb:
                        for (int i=0;i<arkadas_grup.size();i++)
                            mesaj_et.setText(arkadas_grup.get(i)+"\n");
                        break;
                    case R.id.is_rb:
                        for (int i=0;i<is_grup.size();i++)
                            mesaj_et.setText(is_grup.get(i)+"\n");
                        break;
                }
            }
        });

    }
}