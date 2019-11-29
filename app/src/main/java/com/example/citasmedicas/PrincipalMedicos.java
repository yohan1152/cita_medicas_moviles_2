package com.example.citasmedicas;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalMedicos extends BaseActivity {

    private Button btn_pp_medico_ir_perfil, btn_pp_medico_ir_agenda_citas, btn_pp_medico_ir_historial_citas;
    private FloatingActionButton btn_fab_pp_medico_salir;
    private TextView tv_pp_medico_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_medicos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializar();

        verificarSesion();

        //Sí esta logueado guarda nombre de usuario
        tv_pp_medico_nombre.setText("Bienvenido(a): "+ obtenerNombre());

        btn_pp_medico_ir_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irPerfil();
            }
        });

        btn_pp_medico_ir_historial_citas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irHistorialCitas();
            }
        });

        btn_fab_pp_medico_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cerrarSesion();
               irLogin();
            }
        });
    }

    private void inicializar(){
        init();
        btn_fab_pp_medico_salir = findViewById(R.id.btn_fab_pp_medico_salir);
        btn_pp_medico_ir_perfil = findViewById(R.id.btn_pp_medico_ir_perfil);
        btn_pp_medico_ir_historial_citas = findViewById(R.id.btn_pp_medico_ir_historial_citas);
        tv_pp_medico_nombre = findViewById(R.id.tv_pp_medico_nombre);
    }
}
