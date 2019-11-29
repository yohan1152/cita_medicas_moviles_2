package com.example.citasmedicas.models;

public class EstadosModel {

    private String id;
    private String nomEstado;
    private String estado;


    public EstadosModel() {

    }

    public EstadosModel(String nomEstado) {
        this.nomEstado = nomEstado;
    }

    public EstadosModel(String id, String nomEstado, String estado) {
        this.id = id;
        this.nomEstado = nomEstado;
        this.estado = estado;
    }


    public String getNomEstado() {
        return nomEstado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNomEstado(String nomEstado) {
        this.nomEstado = nomEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return nomEstado;
    }
}
