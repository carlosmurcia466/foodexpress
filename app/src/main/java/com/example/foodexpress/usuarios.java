package com.example.foodexpress;

public class usuarios {

   private String contra;
   private String nivel;
   private String idusuario;
   private String nombreus;

    public usuarios(String contra, String nivel, String idusuario, String nombreus) {
        this.contra = contra;
        this.nivel = nivel;
        this.idusuario = idusuario;
        this.nombreus = nombreus;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreus() {
        return nombreus;
    }

    public void setNombreus(String nombreus) {
        this.nombreus = nombreus;
    }
}
