package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.citasmedicas.models.EspecialidadesModel;
import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.RolesModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PerfilActivity extends BaseActivity {

    private EditText et_perfil_nombre, et_perfil_cedula, et_perfil_telefono, et_perfil_celular,
            et_perfil_direccion, et_perfil_email, et_perfil_contraseña, et_perfil_apellido;
    private TextView tv_perfil_estado, tv_perfil_rol, tv_perfil_especialidad;
    private Button btn_perfil_guardar;
    private UsuariosModel modelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializar();

        verificarSesion();

        detalleUsuarios();

        //OnClick de botones
        btn_perfil_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_perfil_cedula = findViewById(R.id.et_perfil_cedula);
        et_perfil_nombre = findViewById(R.id.et_perfil_nombre);
        et_perfil_apellido = findViewById(R.id.et_perfil_apellido);
        et_perfil_telefono = findViewById(R.id.et_perfil_telefono);
        et_perfil_celular = findViewById(R.id.et_perfil_celular);
        et_perfil_direccion = findViewById(R.id.et_perfil_direccion);
        et_perfil_email = findViewById(R.id.et_perfil_email);
        et_perfil_contraseña = findViewById(R.id.et_perfil_contraseña);
        tv_perfil_estado = findViewById(R.id.tv_perfil_estado);
        tv_perfil_especialidad = findViewById(R.id.tv_perfil_especialidad);
        tv_perfil_rol = findViewById(R.id.tv_perfil_rol);

        btn_perfil_guardar = findViewById(R.id.btn_perfil_guardar);
        modelUser = new UsuariosModel();
    }

    private void detalleUsuarios() {

        try {

            db.collection("usuarios")
                    .whereEqualTo("estado", "Activo")
                    .whereEqualTo("id", obtenerId())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                                        modelUser = document.toObject(UsuariosModel.class);

                                        et_perfil_nombre.setText(modelUser.getNombre());
                                        et_perfil_apellido.setText(modelUser.getApellidos());
                                        et_perfil_cedula.setText(modelUser.getIdentificacion());
                                        et_perfil_telefono.setText(modelUser.getTelefono());
                                        et_perfil_celular.setText(modelUser.getCelular());
                                        et_perfil_direccion.setText(modelUser.getDireccion());
                                        et_perfil_email.setText(modelUser.getEmail());
                                        et_perfil_contraseña.setText(modelUser.getPassword());
                                        tv_perfil_estado.setText(modelUser.getEstado());
                                        tv_perfil_rol.setText(modelUser.getRol());
                                        tv_perfil_especialidad.setText(modelUser.getEspecialidad());
                                    }
                                }
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alert("Falló la consulta del usuario: " + e);
                        }
                    });

        } catch (Exception e) {
            alert("Error: " + e);
        }
    }


}
