package com.example.citasmedicas;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.citasmedicas.models.RolesModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class RolesActivity extends BaseActivity {

    private EditText et_roles_activity_rol;
    private Button btn_roles_activity_guardar;
    private RolesModel modelRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);

        inicializar();

        btn_roles_activity_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRol();
                limpiarRoles();
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_roles_activity_rol = findViewById(R.id.et_roles_activity_rol);
        btn_roles_activity_guardar = findViewById(R.id.btn_roles_activity_guardar);
        modelRoles = new RolesModel ();
    }

    private void guardarRol(){

        try {

            if (et_roles_activity_rol.getText().toString().isEmpty() == false) {

                DocumentReference refRol = db.collection("Roles").document();
                modelRoles = new RolesModel(refRol.getId(),et_roles_activity_rol.getText().toString(),"Activo");

                refRol.set(modelRoles)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alert("Rol registrado exitosamente.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("El Rol no pudó ser registrado. Error: " + e);
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                alert("El Rol se ha cancelado.");
                            }
                        });

            }else{
                alert("Todos los campos con * son obligatorios.");
            }
        }catch (Exception e){
            alert("Error: "+e);
        }
    }


    public void limpiarRoles(){

        et_roles_activity_rol.setText("");
        et_roles_activity_rol.requestFocus();
    }


}
