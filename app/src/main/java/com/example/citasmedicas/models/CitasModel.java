package com.example.citasmedicas.models;

public class CitasModel {

    private String id;
    private String idMedico;
    private String nombre_medico;
    private String idPaciente;
    private String nombre_paciente;
    private String especialidad;
    private String fecha;
    private String hora;
    private String estado;


    public CitasModel() {

    }

    public CitasModel(String idMedico, String nombre_medico, String especialidad, String fecha, String hora, String estado) {
        this.idMedico = idMedico;
        this.nombre_medico = nombre_medico;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public CitasModel(String id, String idMedico, String nombre_medico, String idPaciente, String nombre_paciente, String especialidad, String fecha, String hora, String estado) {
        this.id = id;
        this.idMedico = idMedico;
        this.nombre_medico = nombre_medico;
        this.idPaciente = idPaciente;
        this.nombre_paciente = nombre_paciente;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre_medico() {
        return nombre_medico;
    }

    public void setNombre_medico(String nombre_medico) {
        this.nombre_medico = nombre_medico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
