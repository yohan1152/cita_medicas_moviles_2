package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.RolesModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences preferences;
    protected FirebaseFirestore db;
    protected Context context;
    protected TimePickerDialog tpDialogo;
    protected DatePickerDialog dpDialogo;
    protected Calendar calendario;
    protected String hora="";
    protected String [] tiempo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Inicializar Métodos y objetos
    protected void init(){
        db = FirebaseFirestore.getInstance();
        context = this;
        preferences = context.getSharedPreferences("variables_session", Context.MODE_PRIVATE);
        calendario = Calendar.getInstance();
    }

    //Metodos para validar accesos y guardar preferencias
    protected void guardarPreferencias(UsuariosModel model, String id){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre", model.getNombre());
        editor.putString("id_user", id);
        editor.putString("rol_user", model.getRol());
        editor.commit();
    }

    protected void cerrarSesion(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("nombre");
        editor.remove("id_user");
        editor.remove("rol_user");
        editor.commit();
    }

    protected void verificarSesion(){
        if (obtenerNombre().equals("false")){
            irLogin();
        }
    }

    protected String obtenerNombre(){
        String nombre = preferences.getString("nombre", "false");
        return nombre;
    }

    protected String obtenerRol(){
        String rol = preferences.getString("rol_user", "false");
        return  rol;
    }

    protected String obtenerId(){
        String id = preferences.getString("id_user", "false");
        return  id;
    }

    protected void validarRol(String rol){

        //Redireccionamos a cada usuario dependiendo de su rol
        switch (rol){
            case "Administrador":
                irPrincipalAdministrador();
                break;
            case "Médico":
                irPrincipalMedico();
                break;
            case "Paciente":
                irPrincipalPaciente();
                break;
        }
    }


    //INTENTS DEL SISTEMA
    protected void irPrincipalPaciente(){
        Intent pPacientes = new Intent(this, PrincipalPacientes.class);
        startActivity(pPacientes);
    }

    protected void irPrincipalMedico(){
        Intent pMedico = new Intent(this, PrincipalMedicos.class);
        startActivity(pMedico);
    }

    protected void irPrincipalAdministrador(){
        Intent pAdministrador = new Intent(this, PrincipalAdministradores.class);
        startActivity(pAdministrador);
    }

    protected void irRegistroUsuario(){
        Intent registrarUsuario = new Intent(this, RegistrarUsuario.class);
        startActivity(registrarUsuario);
    }

    protected void irLogin(){
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

    protected void irRoles(){
        Intent roles = new Intent(this, RolesActivity.class);
        startActivity(roles);
    }

    protected void irEspecialidades(){
        Intent especialidades = new Intent(this, EspecialidadesActivity.class);
        startActivity(especialidades);
    }

    protected void irEstados(){
        Intent estados = new Intent(this, EstadosActivity.class);
        startActivity(estados);
    }

    protected void irPerfil(){
        Intent pefil = new Intent(this, PerfilActivity.class);
        startActivity(pefil);
    }

    protected void irAsignarCita(String id){
        Intent asignarCita = new Intent(this, AsignarCitaActivity.class);
        asignarCita.putExtra("especialidad", id);
        startActivity(asignarCita);
    }

    protected void irHistorialCitas(){
        Intent historialCitas = new Intent(this, HistorialCitasActivity.class);
        startActivity(historialCitas);
    }


    protected void irCrearCitas(){
        Intent crearCitas = new Intent(this, CrearCitasActivity.class);
        startActivity(crearCitas);
    }

    protected void irSeleccionarEspecialidad(){
        Intent seleccionarEspecialidad = new Intent(this, SeleccionarEspecialidadActivity.class);
        startActivity(seleccionarEspecialidad);
    }

    //Método para generar alertas
    public void alert(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }


    //METODOS PARA OBTENER, MOSTRAR Y MODIFICAR LAS FECHAS

    protected int convertirMinutos(String n){
        tiempo = n.split(":");
        int hMin = Integer.valueOf(tiempo[0])*60;
        return hMin + Integer.valueOf(tiempo[1]);
    }

    protected String validarDigitos(int n){
        return (n<10) ? ("0"+n) : String.valueOf(n);
    }



}
