package com.example.foodexpress;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLite extends SQLiteOpenHelper {



    String sqlCreatetipousuarios = "Create Table Tipo_usuarios(cod_tipo_usuario INTEGER PRIMARY KEY, tipo_usuario TEXT)";
    int usuarioadministrador=1;
    int usuariococinero=2;
    int usuariomotorista=3;
    int usuariocliente=4;


    public UsuariosSQLite(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreatetipousuarios);
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
        poblarDB(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS Tipo_usuarios");
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TABLA_USUARIO);
        onCreate(db);

    }

    public void poblarDB(SQLiteDatabase db){
        db.execSQL("INSERT INTO TIPO_USUARIOS values (1,'ADMINISTRADOR')");
        db.execSQL("INSERT INTO TIPO_USUARIOS values (2,'COCINEROS')");
        db.execSQL("INSERT INTO TIPO_USUARIOS values (3,'MOTORISTAS')");
        db.execSQL("INSERT INTO TIPO_USUARIOS values (4,'CLIENTES')");
        db.execSQL("INSERT INTO "+utilidades.TABLA_USUARIO+" values (1,'juan','74589654','juan@gmail.com','San Salvador','ss','ss',1,'123') ");
        db.execSQL("INSERT INTO "+utilidades.TABLA_USUARIO+" values (2,'carlos','74589654','carlos@gmail.com','San Salvador','ss','ss',2,'123') ");
        db.execSQL("INSERT INTO "+utilidades.TABLA_USUARIO+" values (3,'alberto','74589654','alberto@gmail.com','San Salvador','ss','ss',3,'123') ");
        db.execSQL("INSERT INTO "+utilidades.TABLA_USUARIO+" values (4,'luis','74589654','luis@gmail.com','San Salvador','ss','ss',4,'123') ");


    }

    //login



    public Cursor consultausuarioadministradorpassword(String correo, String password)throws  SQLException{
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query(utilidades.TABLA_USUARIO,new String[]{utilidades.id_usuario,utilidades.nombre_completo,utilidades.telefono,utilidades.correo,utilidades.departamento,utilidades.direccion_completa,utilidades.detalle_direccion,utilidades.password}, ""+utilidades.correo +" like '"+correo+"' and  "+utilidades.password+" like '"+password+"' and "+utilidades.id_tipo_usuario+" = "+usuarioadministrador+"  ",null,null,null,null);

        return  mcursor;
    }

    public Cursor consultausuariococineropassword(String correo, String password)throws  SQLException{
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query(utilidades.TABLA_USUARIO,new String[]{utilidades.id_usuario,utilidades.nombre_completo,utilidades.telefono,utilidades.correo,utilidades.departamento,utilidades.direccion_completa,utilidades.detalle_direccion,utilidades.password}, ""+utilidades.correo +" like '"+correo+"' and  "+utilidades.password+" like '"+password+"' and "+utilidades.id_tipo_usuario+" = "+usuariococinero+"  ",null,null,null,null);

        return  mcursor;
    }
    public Cursor consultausuariomotoristapassword(String correo, String password)throws  SQLException{
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query(utilidades.TABLA_USUARIO,new String[]{utilidades.id_usuario,utilidades.nombre_completo,utilidades.telefono,utilidades.correo,utilidades.departamento,utilidades.direccion_completa,utilidades.detalle_direccion,utilidades.password}, ""+utilidades.correo +" like '"+correo+"' and  "+utilidades.password+" like '"+password+"' and "+utilidades.id_tipo_usuario+" = "+usuariomotorista+"  ",null,null,null,null);

        return  mcursor;
    }

    public Cursor consultausuarioclientepassword(String correo, String password)throws  SQLException{
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query(utilidades.TABLA_USUARIO,new String[]{utilidades.id_usuario,utilidades.nombre_completo,utilidades.telefono,utilidades.correo,utilidades.departamento,utilidades.direccion_completa,utilidades.detalle_direccion,utilidades.password}, ""+utilidades.correo +" like '"+correo+"' and  "+utilidades.password+" like '"+password+"' and "+utilidades.id_tipo_usuario+" = "+usuariocliente+"  ",null,null,null,null);

        return  mcursor;
    }


}
