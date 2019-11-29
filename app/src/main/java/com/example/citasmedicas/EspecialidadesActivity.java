package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.citasmedicas.models.EspecialidadesModel;
import com.example.citasmedicas.models.EstadosModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class EspecialidadesActivity extends BaseActivity {

    private EditText et_especialidades_activity_especialidad;
    private Button btn_especialidades_activity_guardar;
    private EspecialidadesModel modelEspecialidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidades);

        inicializar();

        btn_especialidades_activity_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEspecialidades();
                limpiarEspecialidades();
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_especialidades_activity_especialidad = findViewById(R.id.et_especialidades_activity_especialidad);
        btn_especialidades_activity_guardar = findViewById(R.id.btn_especialidades_activity_guardar);
        modelEspecialidades = new EspecialidadesModel();
    }

    private void guardarEspecialidades(){

        try {

            if (et_especialidades_activity_especialidad.getText().toString().isEmpty() == false) {

                DocumentReference refEspecialidad = db.collection("Especialidades").document();
                modelEspecialidades = new EspecialidadesModel(refEspecialidad.getId(),et_especialidades_activity_especialidad.getText().toString(),"Activo");

                refEspecialidad.set(modelEspecialidades)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alert("Especialidad registrada exitosamente.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("La Especialidad no pudó ser registrada. Error: " + e);
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                alert("La Especialidad se ha cancelado.");
                            }
                        });

            }else{
                alert("Todos los campos con * son obligatorios.");
            }
        }catch (Exception e){
            alert("Error: "+e);
        }
    }


    public void limpiarEspecialidades(){

        et_especialidades_activity_especialidad.setText("");
        et_especialidades_activity_especialidad.requestFocus();
    }

}
