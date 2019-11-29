package com.example.citasmedicas;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalAdministradores extends BaseActivity {

    private Button btn_pp_administrador_ir_perfil, btn_pp_administrador_ir_crear_citas, btn_pp_administrador_ir_roles,
            btn_pp_administrador_ir_especialidades, btn_pp_administrador_ir_estados, btn_pp_administrador_ir_registrar_usuario;
    private FloatingActionButton btn_fab_pp_administrador_salir;
    private TextView tv_pp_administrador_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_administradores);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializar();

        verificarSesion();

        //Sí esta logueado guarda nombre de usuario
        tv_pp_administrador_nombre.setText("Bienvenido(a): "+ obtenerNombre());

        btn_pp_administrador_ir_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irPerfil();
            }
        });

        btn_pp_administrador_ir_crear_citas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCrearCitas();
            }
        });

        btn_pp_administrador_ir_registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistroUsuario();
            }
        });

        btn_pp_administrador_ir_roles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRoles();
            }
        });

        btn_pp_administrador_ir_especialidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irEspecialidades();
            }
        });

        btn_pp_administrador_ir_estados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irEstados();
            }
        });

        btn_fab_pp_administrador_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
                irLogin();
            }
        });

    }

    private void inicializar(){
        init();
        btn_fab_pp_administrador_salir = findViewById(R.id.btn_fab_pp_administrador_salir);
        btn_pp_administrador_ir_perfil = findViewById(R.id.btn_pp_administrador_ir_perfil);
        btn_pp_administrador_ir_crear_citas = findViewById(R.id.btn_pp_administrador_ir_crear_citas);
        btn_pp_administrador_ir_roles = findViewById(R.id.btn_pp_administrador_ir_roles);
        btn_pp_administrador_ir_especialidades = findViewById(R.id.btn_pp_administrador_ir_especialidades);
        btn_pp_administrador_ir_estados = findViewById(R.id.btn_pp_administrador_ir_estados);
        btn_pp_administrador_ir_registrar_usuario = findViewById(R.id.btn_pp_administrador_ir_registrar_usuario);
        tv_pp_administrador_nombre = findViewById(R.id.tv_pp_administrador_nombre);
    }

}
