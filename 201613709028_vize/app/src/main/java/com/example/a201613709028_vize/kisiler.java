package com.example.a201613709028_vize;

import android.graphics.Bitmap;

public class kisiler {
    private String isim;    //rehberden alınacak isim
    private String numara;
    private Bitmap resim=null;

    public void set_resim(Bitmap resim){this.resim=resim;}
    public Bitmap get_resim() { return resim;}

    public void set_isim(String isim){this.isim=isim;}
    public String get_isim(){return isim;}

    public void set_numara(String numara){this.numara=numara;}
    public String get_numara(){return numara;}
}
