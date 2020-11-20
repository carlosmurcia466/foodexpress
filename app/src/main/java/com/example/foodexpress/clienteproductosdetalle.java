package com.example.foodexpress;

public class clienteproductosdetalle {
    private String nombre_producto;
    private String precio;
    private String cantidad;
    private String imagen;
    private String id_producto;
    private String ticket_cliente;
    private String id_proceso;
    private String total;

    private String id_usuario;
    private String nombre_cliente;
    private String departamento;
    private String direccion;
    private String detalle_direccion;


    public clienteproductosdetalle(String nombre_producto, String precio, String cantidad, String imagen, String id_producto, String ticket_cliente, String id_proceso, String total, String id_usuario, String nombre_cliente, String departamento, String direccion, String detalle_direccion) {
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.id_producto = id_producto;
        this.ticket_cliente = ticket_cliente;
        this.id_proceso = id_proceso;
        this.total = total;
        this.id_usuario = id_usuario;
        this.nombre_cliente = nombre_cliente;
        this.departamento = departamento;
        this.direccion = direccion;
        this.detalle_direccion = detalle_direccion;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public String getId_producto() {
        return id_producto;
    }

    public String getTicket_cliente() {
        return ticket_cliente;
    }

    public String getId_proceso() {
        return id_proceso;
    }

    public String getTotal() {
        return total;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
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
