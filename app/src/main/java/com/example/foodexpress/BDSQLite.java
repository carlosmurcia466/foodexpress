package com.example.foodexpress;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;

public class BDSQLite extends SQLiteOpenHelper {

    public static final String TABLA_USUARIO = "usuarios";
    public static final String COLUMNA_ID = "Id_Usuario";
    public static final String COLUMNA_NOMBRE = "Nombre_Completo";
    public static final String COLUMNA_TELEFONO = "Telefono";
    public static final String COLUMNA_CORREO = "Correo";
    public static final String COLUMNA_CLAVE = "Contraseña";
    public static final String COLUMNA_DEPARTAMENTO = "Departamento";
    public static final String COLUMNA_DIRECCION = "Direccion_Completa";
    public static final String COLUMNA_DETALLE = "Detalle_Direccion";
    public static final String COLUMNA_ID_TIPO = "Id_Tipo_Usuario";

    private static final String DATABASE_NAME = "delivery3.db";
    public static final int DATABASE_VERSION = 1;

    private String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLA_USUARIO + "(" + COLUMNA_ID + " integer primary key autoincrement, " +
            COLUMNA_NOMBRE + " text not null, " + COLUMNA_TELEFONO +
            " text not null, " + COLUMNA_CORREO + " integer, " +
            COLUMNA_CLAVE + " text not null," +
            COLUMNA_DEPARTAMENTO + " text not null," +
            COLUMNA_DIRECCION + "text not null," +
            COLUMNA_DETALLE +"text not null," +
            COLUMNA_ID_TIPO + "integer not null);";




    public BDSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String getJSONExportString(SQLiteDatabase database) {
        String selectQuery = "SELECT * FROM " + TABLA_USUARIO;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Convert the database to JSON
        JSONArray databaseSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            databaseSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        // Convert databaseSet to string and put in file
        return databaseSet.toString();
    }

    public static String getDatabaseFileName() {
        return DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create the database with the database creation statement.
        database.execSQL(DATABASE_CREATE);
        poblarDB(database);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (oldVersion > 1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLA_USUARIO);
        this.onCreate(db);
        //}
    }

    public boolean addUser(String name, String email, String clave, String dep, String direccion, String detalle, int id_tipo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(COLUMNA_NOMBRE,name);
        c.put(COLUMNA_CORREO, email);
        c.put(COLUMNA_CLAVE,clave);
        c.put(COLUMNA_DEPARTAMENTO,dep);
        c.put(COLUMNA_DIRECCION,direccion);
        c.put(COLUMNA_DETALLE,detalle);
        c.put(COLUMNA_ID_TIPO,id_tipo);
        long result=db.insert(TABLA_USUARIO,null,c);
        if (result==-1){
            return false;
        } else {
            return true;
        }

    }

    public String getLoginData(String email,String password)
    {
        SQLiteDatabase sql=this.getReadableDatabase();
        String query=" select count(*) from "+TABLA_USUARIO+" where Correo ='"+email+"' and Contraseña='"+password+"'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;

    }

    public void poblarDB(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO "+TABLA_USUARIO+"(Nombre_Completo,Telefono,Correo,Contraseña,Departamento,Direccion_Completa,Detalle_Direccion,Id_Tipo_Usuario) VALUES ('Diego','2222-2222','Diego','password','San Salvador','Buena vista','Buena vista',1)");


    }

}
