package com.example.citasmedicas;

import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.citasmedicas.adapters.CitasAdapter;
import com.example.citasmedicas.models.CitasModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class AsignarCitaActivity extends BaseActivity {

    private EditText et_asignar_citas_fecha;
    private Button btn_asignar_citas_consultar;
    private Spinner sp_asignar_citas_medico;
    private ListView lv_asignar_citas_listar;
    private ArrayList<UsuariosModel> listMedicos;
    private ArrayAdapter<UsuariosModel> adapterMedicos;
    private ArrayList<CitasModel> listCitas;
    private CitasAdapter adapterCitas;
    private String nom_especialidad;
    private UsuariosModel modelUsuarios;
    private CitasModel modelCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_cita);

        inicializar();

        verificarSesion();

        btn_asignar_citas_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarCitas();
            }
        });

        et_asignar_citas_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_asignar_citas_fecha = findViewById(R.id.et_asignar_citas_fecha);
        lv_asignar_citas_listar = findViewById(R.id.lv_asignar_citas_listar);
        sp_asignar_citas_medico = findViewById(R.id.sp_asignar_citas_medico);
        btn_asignar_citas_consultar = findViewById(R.id.btn_asignar_citas_consultar);

        inicializarSpinnerMedicos();

        listMedicos = new ArrayList<UsuariosModel>();
        listCitas = new ArrayList<CitasModel>();
        modelCitas = new CitasModel();
    }


    public void listarCitas(){

        limpiarListaCitas();

        try {

            if (et_asignar_citas_fecha.getText().toString().isEmpty() == false && seleccionMedico().getNombre().isEmpty() == false ){

                db.collection("Citas")
                        .whereEqualTo("estado", "Activo")
                        .whereEqualTo("idMedico", seleccionMedico().getId())
                        .whereEqualTo("fecha", et_asignar_citas_fecha.getText().toString())
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
                                    }else {
                                        alert("No hay citas disponibles en esa fecha.");
                                    }
                                }
                                adapterCitas = new CitasAdapter (listCitas, AsignarCitaActivity.this);
                                lv_asignar_citas_listar.setAdapter(adapterCitas);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alert("Falló el listar: " + e);
                            }
                        });
            }else{
                alert("Todos los campos son obligatorios");
            }

        } catch (Exception e) {
            alert("Error: " + e);
        }
    }


    private void inicializarSpinnerMedicos() {

        try {

            db.collection("usuarios")
                    .whereEqualTo("estado", "Activo")
                    .whereEqualTo("especialidad", obtenerExtras())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                                        listMedicos.add(document.toObject(UsuariosModel.class));
                                    }
                                }
                            }
                            adapterMedicos = new ArrayAdapter<UsuariosModel>(AsignarCitaActivity.this, R.layout.support_simple_spinner_dropdown_item, listMedicos);
                            sp_asignar_citas_medico.setAdapter(adapterMedicos);
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

    private String obtenerExtras(){
       return nom_especialidad = getIntent().getStringExtra("especialidad");
    }

    private UsuariosModel seleccionMedico(){
        return modelUsuarios = (UsuariosModel) sp_asignar_citas_medico.getSelectedItem();
    }


    private void limpiarListaCitas(){
        listCitas.clear();
    }


    //Metodos para las fechas
    private void obtenerFecha(){

        int day = calendario.get(Calendar.DAY_OF_MONTH);
        int month = calendario.get(Calendar.MONTH);
        int year = calendario.get(Calendar.YEAR);

        dpDialogo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                et_asignar_citas_fecha.setText(validarDigitos(mDay)+"/"+ validarDigitos((mMonth+1))+"/"+ validarDigitos(mYear));
            }
        },year,month,day);
        //Captura la fecha actual y lo coloca como valor minimo en el calendario
        dpDialogo.getDatePicker().setMinDate(System.currentTimeMillis());
        //se incrementan los días que deseamos poner como valor máximo en el calendario
        calendario.add(Calendar.DAY_OF_MONTH,30);
        //Captura la fecha limite para mostrar en el calendario
        dpDialogo.getDatePicker().setMaxDate(calendario.getTimeInMillis());
        //Mostramos el calendario
        dpDialogo.show();
    }

}
