package com.example.citasmedicas;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends BaseActivity {

    private EditText et_login_user, et_login_password;
    private Button btn_login_ingresar, btn_login_registrarse;
    private UsuariosModel modelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        btn_login_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarUsuario();
            }
        });

    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_login_user = findViewById(R.id.et_login_user);
        et_login_password = findViewById(R.id.et_login_password);
        btn_login_ingresar = findViewById(R.id.btn_login_ingresar);

        modelUser = new UsuariosModel();
    }

    private void limpiarLogin(){
        et_login_user.setText("");
        et_login_password.setText("");

        et_login_user.requestFocus();
    }

    private void consultarUsuario(){

        try {
            String user = et_login_user.getText().toString();
            String password = et_login_password.getText().toString();

            if (user.isEmpty() == false && password.isEmpty() == false){
                db.collection("usuarios")
                        .whereEqualTo("identificacion", user)
                        .whereEqualTo("password",password)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                if (queryDocumentSnapshots != null){
                                    if (!queryDocumentSnapshots.getDocuments().isEmpty()){
                                        for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                                            modelUser = document.toObject(UsuariosModel.class);
                                            //Guardar datos de sesión
                                            guardarPreferencias(modelUser, document.getId());
                                            limpiarLogin();
                                            validarRol(modelUser.getRol());
                                        }
                                    }else{
                                        alert("Usuario y/o contraseña incorrectos.");
                                    }
                                }
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                alert("La transacción fue cancelada.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("Falló la transacción: "+e);
                            }
                        });
            }else {
                alert("Todos los campos son obligatorios. No deben estar vacíos.");
            }

        }catch (Exception e){
            alert("Error: "+e);
        }

    }


}
