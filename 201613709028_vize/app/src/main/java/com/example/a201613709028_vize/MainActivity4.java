package com.example.a201613709028_vize;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    public static final int request_read_contacts=1; //izni aldığımızı gösteren değişken
    ListView rehber_list;
    Button btn_smsGecis;
    boolean[] selectGrup;
    ArrayList<Integer> grupList=new ArrayList<>();
    String[] grupArray ={ "akraba","arkadaş","iş"};
    /*ArrayList<String> akraba_grup=new ArrayList<>();
    //ArrayList<String> arkadas_grup = new ArrayList<>();
    //ArrayList<String> is_grup = new ArrayList<>();*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Araci araci=null;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context=getApplicationContext();
        sharedPreferences=context.getSharedPreferences(context.getOpPackageName(),Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        rehber_list=findViewById(R.id.rehber_list);
        registerForContextMenu(rehber_list);
        ArrayList<kisiler> kisiler=new ArrayList<kisiler>();
        //if bloğu içerisinde kullanıcı rehber erişimi için izin vermiş mi vermemiş diye kontrol yapılır.
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            /*Telefonun rehberine erişmek için veritabanına erişeceğiz.
            * bunun için cursor yani her okuduğumuz satırı tek tek atlamamızı sağlayacak. telefonda ki content_uri ye ulaşacağız*/
            Cursor rehber=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            //* rehber içinde ulaştığımız veriler bitene kadar her satırı okulabilmek için moveToNext kullandım
            while(rehber.moveToNext())
            {
                /*   ulaşmak istediğimiz verileri string tipindeki değişkenlerle belirledim ve rehberde ki yani phone.__ diyerek değişkenlerime
                *   rehber uygulamamda görmek istediğim değişkenleri atamış oldum. */
                @SuppressLint("Range") String isim=rehber.getString(rehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String numara=rehber.getString(rehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                @SuppressLint("Range") String contactID=rehber.getString(rehber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                /*  r_nesnesi adında bir nesne oluşturudum.
                *   bunun sebebi telefon rehberinden almış olduğum verileri nesne içine atamış oldum.*/
                kisiler r_nesnesi=new kisiler();
                r_nesnesi.set_isim(isim);
                r_nesnesi.set_numara(numara);
                r_nesnesi.set_resim(ContactPhoto(contactID)); //resmi çekmek için bir fonksiyon yazdım en altta
                //burda oluşturmuş olduğum nesneleri ArrayList<kisiler>(55.satır) olarak oluşturduğum kişiler listeme ekledim.
                kisiler.add(r_nesnesi);
            }
            // açmış olduğum Cursor u .close() ile kapattım
            rehber.close();
            // kişilerAdapter içerisinde yapmış olduğumuz
            KisilerAdapter kisilerAdapter=new KisilerAdapter(this,kisiler);
            if(rehber_list!=null) //rehber de kişileri sıralayacak olan listview boş değilse
                rehber_list.setAdapter(kisilerAdapter);
        }
        //erişim izni verilmediyse eğer tekrar kullanıcıdan erişim izni istenecek
        else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},request_read_contacts);

        selectGrup = new boolean[grupArray.length];
        rehber_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity4.this);
                builder.setTitle("Grup Seçin");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(grupArray, selectGrup, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            grupList.add(which);}
                        else
                            grupList.remove(which);
                    }
                });
                builder.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                long indeks=parent.getItemIdAtPosition(position);
                String deneme=kisiler.get((int) indeks).get_isim();
                Toast.makeText(getApplicationContext(),deneme,Toast.LENGTH_SHORT).show();
                builder.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long index=parent.getItemIdAtPosition(position);
                        String kisi=kisiler.get((int)index).get_isim();
                        //String msg="";
                        for (int i=0;i<grupList.size();i++){
                            if(grupArray[grupList.get(i)]=="Akraba")
                                araci.setValues(kisi,1);
                            if(grupArray[grupList.get(i)]=="Arkadaş")
                                araci.setValues(kisi,2);
                            if(grupArray[grupList.get(i)]=="İş")
                                araci.setValues(kisi,3);
                            //msg=msg+"\n"+(i+1)+": "+grupArray[grupList.get(i)];
                        }
                        //araci saveValues();
                        //Toast.makeText(getApplicationContext(),"Toplam "+grupList.size()+" grup seçildi.\n"+msg,Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
                //String myItem="index: "+parent.getItemIdAtPosition(position+1);
                //String myItem="seçim: "+ parent.getItemAtPosition(position).toString();
                //Toast.makeText(MainActivity4.this,myItem,Toast.LENGTH_SHORT).show();
                //view.setSelected(true);
                return true;
            }
        });
        btn_smsGecis=(Button) findViewById(R.id.btn_smsGecis);
        btn_smsGecis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity4.this,MainActivity2.class);
                startActivity(i);
            }
        });
    }
    //resim çekmek için oluşturduğum fonksiyon
    public Bitmap ContactPhoto(String contractID){
        //önce content_url ile rehbere ulaştık ve  contractID yi long değişkenine çevirdim
        Uri contactUri= ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.valueOf(contractID));
        //ulşamış olduğum telefon rehberinde ki hafızasına ulaştım
        Uri photoUri=Uri.withAppendedPath(contactUri,ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        // satırları gezebilmek için tekrar cursor tanımladım
        Cursor cursor=getContentResolver().query(photoUri,new String[]{ContactsContract.Contacts.Photo.PHOTO},null,null,null);
        //eğer geriye bir değer dönüyorsa VE cursor içinde ki elemanı 0 dan büyükse
        if(cursor!=null&&cursor.getCount()>0){

            cursor.moveToNext(); //bir sonraki satıra geçecek
            //değerimizi getBlob ile data ya aldık
            byte[] data=cursor.getBlob(0);
            if(data!=null)
                //datayı yolladık ve geriye bitmap türünden bir değer döndürecek
                return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
            else //eğer boş işe boş döndürecek
                return null;
        }
        cursor.close();
        return null;
    }
}