package com.example.a201613709028_vize;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.ArraySet;

import java.util.ArrayList;

public class Araci {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String isim;
    ArrayList<String> akraba_grup=new ArrayList<String>();
    ArrayList<String> arkadas_grup=new ArrayList<String>();
    ArrayList<String> is_grup=new ArrayList<String>();

    static Araci araci=null;

    private Araci(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("Aracı",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public static Araci getInstance(Context context){
        if(araci==null)
            araci=new Araci(context);
        return araci;
    }

    public ArrayList<String> getAkraba_grup(){
        return akraba_grup;
    }public void setGrup_akraba(String akraba){
        akraba_grup.add(akraba);
    }
    public ArrayList<String> getArkadas_grup(){
        return arkadas_grup;
    }public void setGrup_arkadas(String arkadas){
        arkadas_grup.add(arkadas);
    }
    public ArrayList<String> getIs_grup(){
        return is_grup;
    }public void setGrup_is(String is){
        is_grup.add(is);
    }

    public void setValues(String isim, int grup_index){
        if(grup_index==1)
            setGrup_akraba(isim);
        if(grup_index==2)
            setGrup_arkadas(isim);
        if(grup_index==3)
            setGrup_is(isim);
    }

    public void saveValues(){
        editor.putString("akraba", String.valueOf(getAkraba_grup()));
        editor.putString("arkadas",String.valueOf(getAkraba_grup()));
        editor.putString("iş",String.valueOf(getIs_grup()));
    }

    public void readValues(){

    }


}
