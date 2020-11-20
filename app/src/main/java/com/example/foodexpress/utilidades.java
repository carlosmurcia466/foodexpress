package com.example.foodexpress;

import android.database.sqlite.SQLiteDatabase;

public class utilidades {

    public  static  final String TABLA_USUARIO="usuario";
    public  static  final String id_usuario ="id_usuario";
    public  static  final String nombre_completo="nombre_completo";
    public  static  final String telefono="telefono";
    public  static  final String correo="correo";
    public  static  final String departamento="departamento";
    public  static  final String direccion_completa ="direccion_completa";
    public  static  final String detalle_direccion="detalle_direccion";
    public  static  final String id_tipo_usuario ="id_tipo_usuario";
    public  static  final String password="password";

    public  static final String CREAR_TABLA_USUARIO="CREATE TABLE"+" "+TABLA_USUARIO+" ("+id_usuario+" "+" INTEGER  PRIMARY KEY AUTOINCREMENT , "+nombre_completo+" TEXT, "+telefono+" TEXT, "+correo+" TEXT, "+
            " "+departamento+" TEXT, "+direccion_completa+" TEXT, "+detalle_direccion+" TEXT, "+id_tipo_usuario+" INTEGER, "+password+" TEXT )";



}
