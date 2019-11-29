package com.example.citasmedicas.models;

public class RolesModel {

    private String id;
    private String rol;
    private String estado;

    public RolesModel() {

    }

    public RolesModel(String rol) {
        this.rol = rol;
    }

    public RolesModel(String id, String rol, String estado) {
        this.id = id;
        this.rol = rol;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return rol;
    }


}
