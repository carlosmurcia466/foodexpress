package com.example.foodexpress;

public class usuario {
    private  String id_usuario;
    private  String nombre_completo;
    private  String telefono;
    private  String correo;
    private  String departamento;
    private  String direccion_completa;
    private  String detalle_direccion;
    private  String id_tipo_usuario;
    private  String password;

    public usuario(String id_usuario, String nombre_completo, String telefono, String correo, String departamento, String direccion_completa, String detalle_direccion, String id_tipo_usuario, String password) {
        this.id_usuario = id_usuario;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.correo = correo;
        this.departamento = departamento;
        this.direccion_completa = direccion_completa;
        this.detalle_direccion = detalle_direccion;
        this.id_tipo_usuario = id_tipo_usuario;
        this.password = password;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion_completa() {
        return direccion_completa;
    }

    public void setDireccion_completa(String direccion_completa) {
        this.direccion_completa = direccion_completa;
    }

    public String getDetalle_direccion() {
        return detalle_direccion;
    }

    public void setDetalle_direccion(String detalle_direccion) {
        this.detalle_direccion = detalle_direccion;
    }

    public String getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(String id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
