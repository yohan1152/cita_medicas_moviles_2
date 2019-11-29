package com.example.citasmedicas;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.citasmedicas.adapters.CitasAdapter;
import com.example.citasmedicas.models.CitasModel;
import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistorialCitasActivity extends BaseActivity {

    private Spinner sp_historial_citas_filtro;
    private ListView lv_historial_citas_listar;
    private ArrayAdapter<EstadosModel> adapterEstados;
    private ArrayList<EstadosModel> listEstados;
    private EstadosModel modelEstados;

    private ArrayList<CitasModel> listCitas;
    private CitasAdapter adapterCitas;
    private CitasModel modelCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_citas);

        inicializar();

        verificarSesion();

        listarSegunUsuario();

    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        sp_historial_citas_filtro = findViewById(R.id.sp_historial_citas_filtro);
        lv_historial_citas_listar = findViewById(R.id.lv_historial_citas_listar);
        listEstados = new ArrayList<EstadosModel>();
        listCitas = new ArrayList<CitasModel>();
        modelEstados = new EstadosModel();
        modelCitas = new CitasModel();
        cargarEstados();
    }


    private void listarCitasMedico(){

        try {

            db.collection("Citas")
                    .whereEqualTo("idMedico", obtenerId())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        modelCitas = document.toObject(modelCitas.getClass());
                                        listCitas.add(modelCitas);
                                    }
                                }
                            }
                            adapterCitas = new CitasAdapter (listCitas, HistorialCitasActivity.this);
                            lv_historial_citas_listar.setAdapter(adapterCitas);
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


    private void listarCitasPacientes(){

        try {

            db.collection("Citas")
                    .whereEqualTo("idPaciente", obtenerId())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        modelCitas = document.toObject(modelCitas.getClass());
                                        listCitas.add(modelCitas);
                                    }
                                }
                            }
                            adapterCitas = new CitasAdapter (listCitas, HistorialCitasActivity.this);
                            lv_historial_citas_listar.setAdapter(adapterCitas);
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


    //Valida que tipo de rol tiene y llama la consulta para traer los datos filtrados
    public void listarSegunUsuario(){

        limpiarListaCitasDetalle();

        if (obtenerRol().equals("Médico")){
            listarCitasMedico();
        }else{
            listarCitasPacientes();
        }
    }


    private void cargarEstados(){

            try {
                modelEstados = new EstadosModel("1","Todos","Activo");
                listEstados.add(modelEstados);

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

                        adapterEstados = new ArrayAdapter<EstadosModel>(HistorialCitasActivity.this, R.layout.support_simple_spinner_dropdown_item, listEstados);
                        sp_historial_citas_filtro.setAdapter(adapterEstados);

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

    private EstadosModel seleccionEstado(){
        return modelEstados = (EstadosModel) sp_historial_citas_filtro.getSelectedItem();
    }

    private void limpiarListaCitasDetalle(){
        listCitas.clear();
    }
}
