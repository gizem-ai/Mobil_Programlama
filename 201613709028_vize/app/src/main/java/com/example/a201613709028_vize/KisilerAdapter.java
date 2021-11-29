package com.example.a201613709028_vize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KisilerAdapter extends BaseAdapter { //BaseAdapter sınıfından miras alındı
    private LayoutInflater mInflater;   // kisi.xml de oluşturduğum isim,numara ve resimden oluşan satırı diğer tarafa aktarmamızı sağlayacak
    private ArrayList<kisiler> kisilerList;
    //
    public KisilerAdapter(Activity activity,ArrayList<kisiler>kisilerList){
        //
        this.mInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kisilerList=kisilerList;
    }
    //dizinin boyutu olacak
    public int getCount(){
        return kisilerList.size();
    }
    @Override
    public Object getItem(int position) { //konumları döndürecek bir fonksiyon
        return kisilerList.get(position);
    }
    public long getItemId(int position){ //
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        /* önce kisi.xml nin layout una kisi ismini verdim
        *  kişinin adını numarasını ve fotoğrafını önce tek tek değişkenlere atadım
        *  daha sonrasında hepsinin listview da tek bir satır olarak gözükebilmesi için convertView içine yerleş
        */
        convertView=mInflater.inflate(R.layout.kisi,null);
        TextView kisiad=(TextView) convertView.findViewById(R.id.isim_tv);
        TextView kisino=(TextView) convertView.findViewById(R.id.no_tv);
        ImageView kisiresim=(ImageView) convertView.findViewById(R.id.im_kisi);
        //kişilerden kişi aldım
        kisiler kisi=kisilerList.get(position);
        kisiad.setText(kisi.get_isim()); //kişi adını setText ile get_isim yaptım
        kisino.setText(kisi.get_numara()); //kişi numarasını get_numara yaptım
        if (kisi.get_resim()!=null) //kişinin resmi var ise
            kisiresim.setImageBitmap(kisi.get_resim()); //kişinin resmini döndürecek
        else //null ise R klasörü içinde bulunan bizim belirlemiş olduğumuz fotoğrafı kisiler
            kisiresim.setImageResource(R.drawable.ic_launcher_background);
        convertView.setTag(kisi.get_isim());
        return convertView;
    }
}
