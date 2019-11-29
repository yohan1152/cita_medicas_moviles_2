package com.example.citasmedicas;

import androidx.annotation.NonNull;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citasmedicas.models.EspecialidadesModel;
import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.RolesModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends BaseActivity {

    private EditText et_registrar_usuario_nombre, et_registrar_usuario_cedula, et_registrar_usuario_telefono, et_registrar_usuario_celular,
            et_registrar_usuario_direccion, et_registrar_usuario_email, et_registrar_usuario_contraseña, et_registrar_usuario_apellido;
    private Spinner sp_registrar_usuario_estado, sp_registrar_usuario_rol, sp_registrar_usuario_especialidad;
    private Button btn_registrar_usuario_registrar, btn_registrar_usuario_cancelar;
    private UsuariosModel modelUser;
    private RolesModel modelRol;
    private EspecialidadesModel modelEspecialidad;
    private EstadosModel modelEstado;

    private ArrayList<RolesModel> listRoles;
    private ArrayAdapter<RolesModel> adapterRoles;
    private ArrayList<EstadosModel> listEstados;
    private ArrayAdapter<EstadosModel> adapterEstados;
    private ArrayList<EspecialidadesModel> listEspecialidades;
    private ArrayAdapter<EspecialidadesModel> adapterEspecialidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        inicializar();

        verificarSesion();

        //OnClick de botones
        btn_registrar_usuario_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               guardarUsuario(cargarDatos());
            }
        });

        btn_registrar_usuario_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarRegistro();
                validarRol(obtenerRol());
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_registrar_usuario_cedula = findViewById(R.id.et_registrar_usuario_cedula);
        et_registrar_usuario_nombre = findViewById(R.id.et_registrar_usuario_nombre);
        et_registrar_usuario_apellido = findViewById(R.id.et_registrar_usuario_apellido);
        et_registrar_usuario_telefono = findViewById(R.id.et_registrar_usuario_telefono);
        et_registrar_usuario_celular = findViewById(R.id.et_registrar_usuario_celular);
        et_registrar_usuario_direccion = findViewById(R.id.et_registrar_usuario_direccion);
        et_registrar_usuario_email = findViewById(R.id.et_registrar_usuario_email);
        et_registrar_usuario_contraseña = findViewById(R.id.et_registrar_usuario_password);
        sp_registrar_usuario_estado = findViewById(R.id.sp_registrar_usuario_estado);
        sp_registrar_usuario_rol = findViewById(R.id.sp_registrar_usuario_rol);
        sp_registrar_usuario_especialidad = findViewById(R.id.sp_registrar_usuario_especialidad);

        btn_registrar_usuario_registrar = findViewById(R.id.btn_registrar_usuario_registrar);
        btn_registrar_usuario_cancelar = findViewById(R.id.btn_registrar_usuario_cancelar);
        modelUser = new UsuariosModel();

        inicializarSpinnerRoles();
        inicializarSpinnerEspecialidades();
        inicializarSpinnerEstados();
    }

    //Cargar datos de los Editext al modelo dependiendo del rol
    private UsuariosModel cargarDatos(){

        if (seleccionRol().getRol().equals("Médico")){
            modelUser = new UsuariosModel(et_registrar_usuario_cedula.getText().toString(), et_registrar_usuario_nombre.getText().toString(),
                    et_registrar_usuario_apellido.getText().toString(), et_registrar_usuario_email.getText().toString(),
                    et_registrar_usuario_telefono.getText().toString(), et_registrar_usuario_celular.getText().toString(),
                    et_registrar_usuario_direccion.getText().toString(), et_registrar_usuario_contraseña.getText().toString(),
                    seleccionRol().getRol(), seleccionEstado().getEstado(),seleccionEspecialidad().getEspecialidad());
        }else {
            modelUser = new UsuariosModel(et_registrar_usuario_cedula.getText().toString(), et_registrar_usuario_nombre.getText().toString(),
                    et_registrar_usuario_apellido.getText().toString(), et_registrar_usuario_email.getText().toString(),
                    et_registrar_usuario_telefono.getText().toString(), et_registrar_usuario_celular.getText().toString(),
                    et_registrar_usuario_direccion.getText().toString(), et_registrar_usuario_contraseña.getText().toString(),
                    seleccionRol().getRol(), seleccionEstado().getEstado(),"Ninguna");
        }
        return modelUser;
    }


    private void guardarUsuario(UsuariosModel model){

        try {

            if (model.getNombre().isEmpty() == false && model.getApellidos().isEmpty() == false && model.getIdentificacion().isEmpty() == false &&
                    model.getCelular().isEmpty() == false && model.getCelular().isEmpty() == false && model.getDireccion().isEmpty() == false &&
                    model.getEmail().isEmpty() == false && model.getPassword().isEmpty() == false && model.getEstado().isEmpty() == false &&
                    model.getRol().isEmpty() == false) {

                DocumentReference refUsuarios = db.collection("usuarios").document();
                model.setId(refUsuarios.getId());

                refUsuarios.set(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                limpiarRegistro();
                                alert("Usuario registrado exitosamente.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("El usuario no pudó ser registrado. Error: " + e);
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                alert("El registro se ha cancelado.");
                            }
                        });

            }else{
                alert("Todos los campos con * son obligatorios.");
            }
        }catch (Exception e){
            alert("Error: "+e);
        }
    }


    public void limpiarRegistro(){

        et_registrar_usuario_cedula.setText("");
        et_registrar_usuario_nombre.setText("");
        et_registrar_usuario_apellido.setText("");
        et_registrar_usuario_telefono.setText("");
        et_registrar_usuario_celular.setText("");
        et_registrar_usuario_direccion.setText("");
        et_registrar_usuario_email.setText("");
        et_registrar_usuario_contraseña.setText("");

        inicializarSpinnerRoles();
        inicializarSpinnerEspecialidades();
        inicializarSpinnerEstados();

        et_registrar_usuario_nombre.requestFocus();
    }


    //Métodos para listar opciones en Spinners Roles
    private void inicializarSpinnerRoles() {

        try {
            listRoles = new ArrayList<RolesModel>();

            db.collection("Roles")
                    .whereEqualTo("estado", "Activo")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        listRoles.add(document.toObject(RolesModel.class));
                                    }
                                }
                            }
                            adapterRoles = new ArrayAdapter<RolesModel>(RegistrarUsuario.this, R.layout.support_simple_spinner_dropdown_item, listRoles);
                            sp_registrar_usuario_rol.setAdapter(adapterRoles);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alert("Falló el listar: " + e);
                        }
                    });

        } catch (Exception e) {
            alert("Error: " + e);
        }
    }


    private void inicializarSpinnerEstados() {

        try {
            listEstados = new ArrayList<EstadosModel>();

            db.collection("Estados")
                    .whereEqualTo("estado", "Activo")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        listEstados.add(document.toObject(EstadosModel.class));
                                    }
                                }
                            }
                            adapterEstados = new ArrayAdapter<EstadosModel>(RegistrarUsuario.this, R.layout.support_simple_spinner_dropdown_item, listEstados);
                            sp_registrar_usuario_estado.setAdapter(adapterEstados);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alert("Falló el listar: " + e);
                        }
                    });

        } catch (Exception e) {
            alert("Error: " + e);
        }
    }


    private void inicializarSpinnerEspecialidades() {

        try {
            listEspecialidades = new ArrayList<EspecialidadesModel>();

            db.collection("Especialidades")
                    .whereEqualTo("estado", "Activo")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        listEspecialidades.add(document.toObject(EspecialidadesModel.class));
                                    }
                                }
                            }
                            adapterEspecialidades = new ArrayAdapter<EspecialidadesModel>(RegistrarUsuario.this, R.layout.support_simple_spinner_dropdown_item, listEspecialidades);
                            sp_registrar_usuario_especialidad.setAdapter(adapterEspecialidades);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alert("Falló el listar: " + e);
                        }
                    });

        } catch (Exception e) {
            alert("Error: " + e);
        }


    }

    //CAPTURAR VALORES SPINNERS
    private RolesModel seleccionRol(){
        return modelRol = (RolesModel) sp_registrar_usuario_rol.getSelectedItem();
    }

    private EspecialidadesModel seleccionEspecialidad(){
        return modelEspecialidad = (EspecialidadesModel) sp_registrar_usuario_especialidad.getSelectedItem();
    }

    private EstadosModel seleccionEstado(){
        return modelEstado = (EstadosModel) sp_registrar_usuario_estado.getSelectedItem();
    }


}
