package inova.georef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class BDAyuda extends SQLiteOpenHelper {

    Context ctx;
    public BDAyuda(Context context) {
        super(context, "ddd", null, 1);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("CREATE TABLE coordenadas(x TEXT NOT NULL, y TEXT NOT NULL ,nit TEXT NOT NULL, nombre TEXT NOT NULL, icono TEXT NOT NULL );");
       // db.execSQL("CREATE TABLE barrios(comuna VARCHAR NOT NULL, barrio VARCHAR NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS coordenadas");

        onCreate(db);
    }

    //Variables generales
    BDAyuda ayuda;
    SQLiteDatabase DB;

    //Métodos para manera la BD
    public void abrir(){
        ayuda=new BDAyuda(ctx);
        DB=ayuda.getWritableDatabase();

    }



    public void cerrar(){
        DB.close();}

     public long registrar(String lat, String lng, String nombre, String  nit,String icono) throws Exception{
    //public long registrar(String lat, String lng) throws Exception{
        ContentValues valores= new ContentValues();
        valores.put("x", lat);
         valores.put("y", lng);
         valores.put("nombre", nombre);
         Log.v("nombree", nombre);
        valores.put("nit", nit);
        valores.put("icono", icono);
        Log.v("valores", String.valueOf(valores));
        return DB.insert("coordenadas", null, valores);
    }
    public void deleteTable() {
        if (DB != null && !DB.isOpen())
            DB.execSQL("delete from coordenadas");

    }
    public ArrayList<String>[] consultar() throws Exception{
        ArrayList<String>[] arr = new ArrayList[5];

        ArrayList<String> latitudab= new ArrayList<String>();
        ArrayList<String> longitudab= new ArrayList<String>();
        ArrayList<String> nitb= new ArrayList<String>();
        ArrayList<String> nombreb= new ArrayList<String>();
        ArrayList<String> iconob= new ArrayList<String>();

       // String [] columnas= new String[] {"x", "y"};
        String [] columnas= new String[] {"x", "y","nit","nombre", "icono"};
        Cursor c= DB.query("coordenadas", columnas, null,null, null,null,null);
        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
           latitudab.add(c.getString(c.getColumnIndex("x")));
           longitudab.add(c.getString(c.getColumnIndex("y")));
           nombreb.add(c.getString(c.getColumnIndex("nombre")));
           nitb.add(c.getString(c.getColumnIndex("nit")));
           iconob.add(c.getString(c.getColumnIndex("icono")));
       }

        arr[0] = latitudab;
        arr[1] = longitudab;
        arr[2] = nombreb;
        arr[3] = nitb;
        arr[4] = iconob;

        return arr;

    }



}
