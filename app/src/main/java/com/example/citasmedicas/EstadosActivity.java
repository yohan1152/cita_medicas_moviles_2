package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.RolesModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class EstadosActivity extends BaseActivity {

    private EditText et_estados_activity_nomEstado;
    private Button btn_estados_activity_guardar;
    private EstadosModel modelEstados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);

        inicializar();

        btn_estados_activity_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guardarEstaddo();
               limpiarEstado();
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_estados_activity_nomEstado = findViewById(R.id.et_estados_activity_nomEstado);
        btn_estados_activity_guardar = findViewById(R.id.btn_estados_activity_guardar);
        modelEstados = new EstadosModel();
    }

    private void guardarEstaddo(){

        try {

            if (et_estados_activity_nomEstado.getText().toString().isEmpty() == false) {

                DocumentReference refEstado = db.collection("Estados").document();
                modelEstados = new EstadosModel(refEstado.getId(),et_estados_activity_nomEstado.getText().toString(),"Activo");

                refEstado.set(modelEstados)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alert("Estado registrado exitosamente.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("El Estado no pudó ser registrado. Error: " + e);
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                alert("El Estado se ha cancelado.");
                            }
                        });

            }else{
                alert("Todos los campos con * son obligatorios.");
            }
        }catch (Exception e){
            alert("Error: "+e);
        }
    }


    public void limpiarEstado(){

        et_estados_activity_nomEstado.setText("");
        et_estados_activity_nomEstado.requestFocus();
    }

}
