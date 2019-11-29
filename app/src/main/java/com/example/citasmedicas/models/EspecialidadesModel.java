package com.example.citasmedicas.models;

public class EspecialidadesModel {

    private String id;
    private String especialidad;
    private String estado;

    public EspecialidadesModel() {

    }

    public EspecialidadesModel(String especialidad) {
        this.especialidad = especialidad;
    }

    public EspecialidadesModel(String id, String especialidad, String estado) {
        this.id = id;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return especialidad;
    }
}
