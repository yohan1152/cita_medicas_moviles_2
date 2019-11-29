package com.example.citasmedicas.models;

public class UsuariosModel {

    private String id;
    private String identificacion;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String celular;
    private String direccion;
    private String password;
    private String rol;
    private String estado;
    private String especialidad;

    //CONSTRUCTORES
    public UsuariosModel() {

    }

    public UsuariosModel(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public UsuariosModel(String identificacion, String nombre, String apellidos, String email, String telefono, String celular, String direccion, String password, String rol, String estado, String especialidad) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
        this.especialidad = especialidad;
    }

    public UsuariosModel(String id, String identificacion, String nombre, String apellidos, String email, String telefono, String celular, String direccion, String password, String rol, String estado, String especialidad) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
        this.especialidad = especialidad;
    }

    //SET Y GET


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
        return nombre+" "+ apellidos;
    }

}
