package com.example.citasmedicas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.citasmedicas.models.CitasModel;
import com.example.citasmedicas.models.EstadosModel;
import com.example.citasmedicas.models.UsuariosModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class CrearCitasActivity extends BaseActivity {

    private EditText et_crear_citas_fecha, et_crear_citas_hora_inicio, et_crear_citas_hora_fin;
    private Button btn_crear_citas_generar;
    private TextView tv_crear_citas_cargando;
    private Spinner sp_crear_citas_medico;
    private LinearLayout base_crear_citas;
    private ArrayList<String> listaHorariosCitas;
    private ArrayList<UsuariosModel> listaMedicos;
    private ArrayAdapter<UsuariosModel> adapterMedicos;
    private UsuariosModel modelUsuarios;
    private CitasModel modelCitas;
    private int hour = 0, minutes =0, contar=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_citas);

        inicializar();

        btn_crear_citas_generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarHorasCita();
            }
        });

        et_crear_citas_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });

        et_crear_citas_hora_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHoraInicio();
            }
        });

        et_crear_citas_hora_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHoraFin();
            }
        });
    }

    //Método para inicializar variables y objetos
    private void inicializar(){
        init();
        et_crear_citas_fecha = findViewById(R.id.et_crear_citas_fecha);
        et_crear_citas_hora_inicio = findViewById(R.id.et_crear_citas_hora_inicio);
        et_crear_citas_hora_fin = findViewById(R.id.et_crear_citas_hora_fin);
        btn_crear_citas_generar = findViewById(R.id.btn_crear_citas_generar);
        tv_crear_citas_cargando = findViewById(R.id.tv_crear_citas_cargando);
        sp_crear_citas_medico = findViewById(R.id.sp_crear_citas_medico);
        base_crear_citas = findViewById(R.id.base_crear_citas);

        listaHorariosCitas = new ArrayList<String>();
        inicializarSpinnerMedicos();
    }


    private void guardarHorarioCitas(){

        try {
            //exito=false;
            String id_medico = seleccionMedico().getId();
            String nom_medico = (seleccionMedico().getNombre()+" "+seleccionMedico().getApellidos());
            String especialidad_medico = seleccionMedico().getEspecialidad();
            String fechaCita = et_crear_citas_fecha.getText().toString();

            if (fechaCita.isEmpty() == false && et_crear_citas_hora_inicio.getText().toString().isEmpty() == false
                    && et_crear_citas_hora_fin.getText().toString().isEmpty() == false && id_medico.isEmpty() == false && listaHorariosCitas.isEmpty() == false) {

                limpiarLayout();
                tv_crear_citas_cargando.setText("Guardando citas...");

                for (String horaCitas: listaHorariosCitas){

                    modelCitas = new CitasModel(id_medico,nom_medico,especialidad_medico,fechaCita,horaCitas,"Activo");

                    DocumentReference refHorarioCitas = db.collection("Citas").document();
                    modelCitas.setId(refHorarioCitas.getId());

                    refHorarioCitas.set(modelCitas)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //exito = true;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //exito=false;
                                    alert("Las Citas no pudieron ser registradas. Error: " + e);
                                }
                            })
                            .addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    //exito=false;
                                    alert("El registro de Citas se ha cancelado.");
                                }
                            });
                }
                //Al terminar de guardar las citas se limpia la lista y se refresca el layout
                limpiarLista();
                refreshLayout();
                alert("Citas registradas exitosamente.");

            }else{
                alert("Todos los campos con * son obligatorios.");
            }
        }catch (Exception e){
            alert("Error: "+e);
        }
    }


    private void inicializarSpinnerMedicos() {

        try {
            listaMedicos = new ArrayList<UsuariosModel>();

            db.collection("usuarios")
                    .whereEqualTo("estado", "Activo")
                    .whereEqualTo("rol", "Médico")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        listaMedicos.add(document.toObject(UsuariosModel.class));
                                    }
                                }
                            }
                            adapterMedicos = new ArrayAdapter<UsuariosModel>(CrearCitasActivity.this, R.layout.support_simple_spinner_dropdown_item, listaMedicos);
                            sp_crear_citas_medico.setAdapter(adapterMedicos);
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


    private UsuariosModel seleccionMedico(){
        return modelUsuarios = (UsuariosModel) sp_crear_citas_medico.getSelectedItem();
    }


    //Metodos para las fechas
    private void obtenerFecha(){

        int day = calendario.get(Calendar.DAY_OF_MONTH);
        int month = calendario.get(Calendar.MONTH);
        int year = calendario.get(Calendar.YEAR);

        dpDialogo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                et_crear_citas_fecha.setText(validarDigitos(mDay)+"/"+ validarDigitos((mMonth+1))+"/"+ validarDigitos(mYear));
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

    private void capturarHora(){
        int hour = calendario.get(Calendar.HOUR_OF_DAY);
        int minutes = calendario.get(Calendar.MINUTE);
    }

    private void obtenerHoraInicio(){

        capturarHora();
        tpDialogo = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int dHour, int dMinute) {
                et_crear_citas_hora_inicio.setText(validarDigitos(dHour)+":"+validarDigitos(dMinute));
            }
        },hour,minutes,true);
        tpDialogo.show();
    }

    private void obtenerHoraFin(){

        capturarHora();
        tpDialogo = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int dHour, int dMinute) {
                et_crear_citas_hora_fin.setText(validarDigitos(dHour)+":"+validarDigitos(dMinute));
            }
        },hour,minutes,true);
        tpDialogo.show();
    }


    private void generarHorasCita (){

        limpiarLista();
        limpiarLayout();

        int horaInicio=0, horaFin=0;

        TextView titulo = new TextView(this);
        titulo.setText("Seleccionar Horario: ");
        base_crear_citas.addView(titulo);

        horaInicio = convertirMinutos(et_crear_citas_hora_inicio.getText().toString());
        horaFin = convertirMinutos(et_crear_citas_hora_fin.getText().toString());

        while (horaInicio<(horaFin-14)){
            CheckBox hora = new CheckBox(this);
            hora.setId(Integer.valueOf(contar));
            hora.setText(validarDigitos(((horaInicio/60)%24)) + ":" + validarDigitos((horaInicio%60)));
            hora.setChecked(true);
            hora.setOnClickListener(checkHorario);
            listaHorariosCitas.add(hora.getText().toString());

            base_crear_citas.addView(hora);
            contar++;
            horaInicio += 15;
        }

        contar=0;

        Button guardarHorario = new Button(this);
        guardarHorario.setText("Guardar");
        guardarHorario.setId(Integer.valueOf(1000));
        guardarHorario.setOnClickListener(btnGuardarCitas);
        base_crear_citas.addView(guardarHorario);

        Button cancelarHorario = new Button(this);
        cancelarHorario.setText("Cancelar");
        cancelarHorario.setId(Integer.valueOf(1001));
        cancelarHorario.setOnClickListener(btnCancelCitas);
        base_crear_citas.addView(cancelarHorario);
    }

    private void limpiarLayout(){
        base_crear_citas.removeAllViewsInLayout();
    }

    private void refreshLayout(){
        this.finish();
        irCrearCitas();
    }

    private void limpiarLista(){
        listaHorariosCitas.clear();
    }

    //Metodo al hacer click sobre boton cancelar el layout base se limpia
    private View.OnClickListener btnCancelCitas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            limpiarLista();
            refreshLayout();
        }
    };

    //Metodo al hacer click sobre boton Guardar se envía a Firestore los horarios seleccionados
    private View.OnClickListener btnGuardarCitas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            guardarHorarioCitas();
        }
    };


    private View.OnClickListener checkHorario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();
            boolean checked = ((CheckBox) v).isChecked();
            String valor = ((CheckBox) v).getText().toString();

            if(checked){
                listaHorariosCitas.add(id,valor);
            }else {
                listaHorariosCitas.remove(new String(valor));
            }
        }
    };



}
