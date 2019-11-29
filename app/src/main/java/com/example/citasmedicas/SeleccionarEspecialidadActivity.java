package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.citasmedicas.adapters.EspecialidadesAdapter;
import com.example.citasmedicas.models.EspecialidadesModel;
import com.example.citasmedicas.models.RolesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SeleccionarEspecialidadActivity extends BaseActivity {

    private ListView lv_seleccionar_especialidad_listar;
    private EspecialidadesModel model;
    private ArrayList<EspecialidadesModel> list;
    private EspecialidadesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_especialidad);

        inicializar();

        verificarSesion();

        listarEspecialidades();

        lv_seleccionar_especialidad_listar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model = (EspecialidadesModel) adapterView.getItemAtPosition(i);
                clickItem(model);
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        lv_seleccionar_especialidad_listar = findViewById(R.id.lv_seleccionar_especialidad_listar);
        model = new EspecialidadesModel();
        list = new ArrayList<EspecialidadesModel>();
    }

    private void listarEspecialidades(){

        try {

            db.collection("Especialidades")
                    .whereEqualTo("estado", "Activo")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        list.add(document.toObject(EspecialidadesModel.class));
                                    }
                                }
                            }

                            adapter = new EspecialidadesAdapter(list, SeleccionarEspecialidadActivity.this);
                            lv_seleccionar_especialidad_listar.setAdapter(adapter);
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

    private void clickItem(EspecialidadesModel model){
        //Toast.makeText(this, "Usted presionó a " + model.getEspecialidad(), Toast.LENGTH_LONG).show();
        String id = model.getEspecialidad();
        irAsignarCita(id);
    }

}
