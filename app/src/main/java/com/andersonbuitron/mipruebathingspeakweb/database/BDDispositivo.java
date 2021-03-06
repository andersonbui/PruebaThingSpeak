package com.andersonbuitron.mipruebathingspeakweb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.andersonbuitron.mipruebathingspeakweb.modelos.Dispositivo;

import java.util.ArrayList;

/**
 * para el crud de Dispositivo
 */

public class BDDispositivo {
    private static BDDispositivo bddispositivo = new BDDispositivo();

    private DispositivoHelper mHelper;
    private SQLiteDatabase mDatabase;
    private Context context;

    public static BDDispositivo getInstance(Context context){
        bddispositivo.setContext(context);
        return bddispositivo;
    }

    private BDDispositivo() {
        this.context = null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        mHelper = new DispositivoHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertDispositivo(Dispositivo dispositivo) {

        //compile the statement and start a transaction
        ContentValues valores = new ContentValues();
        mDatabase.beginTransaction();

        if (!dispositivo.getId().equals("")) {
            valores.put(DispositivoHelper.DatosTabla.COLUMNA_ID, dispositivo.getId());
            valores.put(DispositivoHelper.DatosTabla.COLUMNA_NOMBRE, dispositivo.getNombre());
            valores.put(DispositivoHelper.DatosTabla.COLUMNA_API_KEY, dispositivo.getApi_key_write());
            valores.put(DispositivoHelper.DatosTabla.COLUMNA_ICONO, dispositivo.getIcono());

            Long newRow = mDatabase.insert(DispositivoHelper.DatosTabla.NOMBRE_TABLA, null, valores);
            Toast.makeText(context, "Se guardo el dato" + dispositivo.toString(), Toast.LENGTH_LONG).show();
            mDatabase.setTransactionSuccessful();
        }else {
            Toast.makeText(context, "el Id es obligatorio.", Toast.LENGTH_SHORT).show();
        }
        //set the transaction as successful and end the transaction
        //L.m("inserting entries " + listCanal.size() + new Date(System.currentTimeMillis()));
        mDatabase.endTransaction();
    }

    public ArrayList<Dispositivo> leerDispositivos() {
        ArrayList<Dispositivo> listDispositivo = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {
                DispositivoHelper.DatosTabla.COLUMNA_ID,
                DispositivoHelper.DatosTabla.COLUMNA_NOMBRE,
                DispositivoHelper.DatosTabla.COLUMNA_API_KEY,
                DispositivoHelper.DatosTabla.COLUMNA_ICONO,
        };
        Cursor cursor = mDatabase.query(DispositivoHelper.DatosTabla.NOMBRE_TABLA, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {

                //create a new Dispositivo object and retrieve the data from the cursor to be stored in this Dispositivo object
                Dispositivo Dispositivo = new Dispositivo();
                //each step is a 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank Dispositivo object to contain our data
                Dispositivo.setId(cursor.getString(cursor.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_ID)));
                Dispositivo.setNombre(cursor.getString(cursor.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_NOMBRE)));
                Dispositivo.setApi_key_write(cursor.getString(cursor.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_API_KEY)));
                Dispositivo.setIcono(cursor.getString(cursor.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_ICONO)));
                //add the Dispositivo to the list of Dispositivo objects which we plan to return
                listDispositivo.add(Dispositivo);
            }
            while (cursor.moveToNext());
        }
        return listDispositivo;
    }

    public Dispositivo buscarDispositivo(String id){

        String[] projection = {
                DispositivoHelper.DatosTabla.COLUMNA_ID,
                DispositivoHelper.DatosTabla.COLUMNA_NOMBRE,
                DispositivoHelper.DatosTabla.COLUMNA_API_KEY,
                DispositivoHelper.DatosTabla.COLUMNA_ICONO,};
        // Filter results WHERE "title" = 'My Title'
        String selection = DispositivoHelper.DatosTabla.COLUMNA_ID + " = ?";
        String[] selectionArgs = {id};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DispositivoHelper.DatosTabla.COLUMNA_NOMBRE + " DESC";
        Dispositivo dispositivo = null;
        try {
            Cursor c = mDatabase.query(DispositivoHelper.DatosTabla.NOMBRE_TABLA, projection, selection, selectionArgs, null, null, sortOrder);

            dispositivo =  new Dispositivo();
            c.moveToFirst();
            dispositivo.setId(c.getString(c.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_ID)));
            dispositivo.setIcono(c.getString(c.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_NOMBRE)));
            dispositivo.setApi_key_write(c.getString(c.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_API_KEY)));
            dispositivo.setIcono(c.getString(c.getColumnIndex(DispositivoHelper.DatosTabla.COLUMNA_ICONO)));

        } catch (android.database.CursorIndexOutOfBoundsException e) {
            Toast.makeText(context, "No se encontro registro alguno.", Toast.LENGTH_LONG).show();
        }

        return dispositivo;
    }

    public void actualizarDispositivo(Dispositivo dispositivo){
        ContentValues valores = new ContentValues();
        valores.put(DispositivoHelper.DatosTabla.COLUMNA_NOMBRE, dispositivo.getNombre());
        valores.put(DispositivoHelper.DatosTabla.COLUMNA_ICONO, dispositivo.getIcono());

        String selection = DispositivoHelper.DatosTabla.COLUMNA_ID + " = ? ";
        String[] selectionArgs = {dispositivo.getId()};

        int res = mDatabase.update(DispositivoHelper.DatosTabla.NOMBRE_TABLA,valores,selection,selectionArgs);

        if(res > 0){
            Toast.makeText(context, "Se actualizo el dispositivo con id["+ dispositivo.getId()+"]", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"No se actualizo ningun registro: ",Toast.LENGTH_LONG).show();
        }
    }

    public void deleteDispositivo(String idDispositivo) {
        String selection = DispositivoHelper.DatosTabla.COLUMNA_ID + " = ? ";
        String[] selectionArgs = {idDispositivo};

        int res = mDatabase.delete(DispositivoHelper.DatosTabla.NOMBRE_TABLA, selection, selectionArgs);
        if(res > 0){
            Toast.makeText(context, "Se elimino el canal con id["+idDispositivo+"] #filas["+res+"]", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"No se elimino ningun registro: ",Toast.LENGTH_LONG).show();
        }
    }

    public class DispositivoHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "MiBaseDB.db";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String CREAR_TABLA =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA + " (" +
                        DatosTabla.COLUMNA_ID + " INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_NOMBRE + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_API_KEY + TEXT_TYPE +COMMA_SEP +
                        DatosTabla.COLUMNA_ICONO + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA;

        /* Inner class that defines the table contents */
        public final class DatosTabla implements BaseColumns {
            public static final String NOMBRE_TABLA = "Dispositivo";
            public static final String COLUMNA_ID = "id";
            public static final String COLUMNA_NOMBRE = "nombre";
            public static final String COLUMNA_API_KEY = "api_key";
            public static final String COLUMNA_ICONO = "icono";
        }

        public DispositivoHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_TABLA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);

        }

    }
}
