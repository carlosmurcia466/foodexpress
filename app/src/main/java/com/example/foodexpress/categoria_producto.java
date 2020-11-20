package com.example.foodexpress;

public class categoria_producto {

    private  String categoria_producto;
    private  String imagen;
    private  String id_tipo_producto;
    private  String id_usuario;
    private  String nombre;
    private  String departamento;
    private  String direccion;
    private  String detalle_direccion;


    public categoria_producto( String categoria_producto,String imagen,String id_tipo_producto,String id_usuario,String nombre, String departamento, String direccion, String detalle_direccion) {
        this.imagen=imagen;
        this.categoria_producto = categoria_producto;
        this.id_tipo_producto=id_tipo_producto;
        this.id_usuario=id_usuario;
        this.nombre=nombre;
        this.departamento=departamento;
        this.direccion=direccion;
        this.detalle_direccion=detalle_direccion;
    }


    public String getCategoria_producto() {
        return categoria_producto;
    }

    public String getImagen() {
        return imagen;
    }
    public String getId_tipo_producto() {
        return id_tipo_producto;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDetalle_direccion() {
        return detalle_direccion;
    }
}
