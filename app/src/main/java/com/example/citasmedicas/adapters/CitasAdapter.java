package com.example.citasmedicas.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.citasmedicas.AsignarCitaActivity;
import com.example.citasmedicas.BaseActivity;
import com.example.citasmedicas.HistorialCitasActivity;
import com.example.citasmedicas.R;
import com.example.citasmedicas.models.CitasModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CitasAdapter extends BaseAdapter {

    private CitasAdapter miAdapter=null;

    private ArrayList<CitasModel> list;
    private CitasModel model;
    private Context context;
    private View div_vertical_largo;
    private TextView tv_item_estado, tv_item_asignar, tv_item_cancelar, tv_item_finalizar, tv_item_especialidad,
                     tv_item_medico, tv_item_hora, tv_item_fecha;
    private SharedPreferences preferences;
    private String rol, id_user, nom_user;
    private FirebaseFirestore db;

    public CitasAdapter(ArrayList<CitasModel> list, Context context) {
        this.list = list;
        this.context = context;
        preferences = context.getSharedPreferences("variables_session", Context.MODE_PRIVATE);
        rol = preferences.getString("rol_user", "false");
        id_user = preferences.getString("id_user", "false");
        nom_user = preferences.getString("nombre", "false");
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View item = view;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.items_citas, viewGroup, false);
        }

        tv_item_fecha = item.findViewById(R.id.tv_item_fecha);
        tv_item_hora = item.findViewById(R.id.tv_item_hora);
        div_vertical_largo = item.findViewById(R.id.div_vertical_largo);
        tv_item_especialidad = item.findViewById(R.id.tv_item_especialidad);
        tv_item_medico = item.findViewById(R.id.tv_item_medico);
        tv_item_cancelar = item.findViewById(R.id.tv_item_cancelar);
        tv_item_asignar = item.findViewById(R.id.tv_item_asignar);
        tv_item_finalizar = item.findViewById(R.id.tv_item_finalizar);
        tv_item_estado = item.findViewById(R.id.tv_item_estado);

        tv_item_asignar.setTag(i);
        tv_item_cancelar.setTag(i);
        tv_item_finalizar.setTag(i);

        model = list.get(i);

        tv_item_fecha.setText(model.getFecha());
        tv_item_hora.setText(model.getHora());
        tv_item_estado.setText(model.getEstado());

        //Se validal el rol y el estado de la cita
        if(rol.equals("Médico")){

            tv_item_asignar.setVisibility(View.GONE);
            tv_item_medico.setText(model.getNombre_paciente());

            switch (model.getEstado()){
                case "Activo":
                    div_vertical_largo.setBackgroundColor(Color.rgb(30,152,45));
                    break;
                case "Ocupado":
                    div_vertical_largo.setBackgroundColor(Color.rgb(29,138,165));
                    break;
                case "Finalizado":
                    div_vertical_largo.setBackgroundColor(Color.rgb(159,7,7));
                    tv_item_asignar.setVisibility(View.GONE);
                    tv_item_cancelar.setVisibility(View.GONE);
                    tv_item_finalizar.setVisibility(View.GONE);
                    break;
            }

        }else{

            tv_item_medico.setText(model.getNombre_medico());
            tv_item_especialidad.setText(model.getEspecialidad());

            switch (model.getEstado()){
                case "Activo":
                    div_vertical_largo.setBackgroundColor(Color.rgb(30,152,45));
                    tv_item_cancelar.setVisibility(View.GONE);
                    tv_item_finalizar.setVisibility(View.GONE);
                    break;
                case "Ocupado":
                    div_vertical_largo.setBackgroundColor(Color.rgb(29,138,165));
                    tv_item_asignar.setVisibility(View.GONE);
                    tv_item_finalizar.setVisibility(View.GONE);
                    break;
                case "Finalizado":
                    div_vertical_largo.setBackgroundColor(Color.rgb(159,7,7));
                    tv_item_asignar.setVisibility(View.GONE);
                    tv_item_cancelar.setVisibility(View.GONE);
                    tv_item_finalizar.setVisibility(View.GONE);
                    break;
            }
        }


        tv_item_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finalizarCita((list.get(Integer.valueOf(v.getTag().toString()))).getId());
            }
        });

        tv_item_asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarCita(list.get(Integer.valueOf(v.getTag().toString())).getId(), id_user, nom_user);
            }
        });

        tv_item_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarCita((list.get(Integer.valueOf(v.getTag().toString()))).getId());

            }
        });

        return item;


    }

    private void asignarCita(String idCita, String idPaciente, String nombrePaciente){

        DocumentReference refCita = db.collection("Citas").document(idCita);

        refCita
            .update("idPaciente", idPaciente,"nombre_paciente", nombrePaciente, "estado","Ocupado")
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    alert("Se asignó la cita correctamente.");
                    irHistorialCitas();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    alert("No se puede asignar la cita: "+e);
                }
            });

    }

    private void cancelarCita(String idCita){

        DocumentReference refCita = db.collection("Citas").document(idCita);

        refCita
                .update("idPaciente", "","nombre_paciente", "","estado","Activo")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        alert("La cita se canceló correctamente.");
                        irHistorialCitas();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alert("No se puede cancelar la cita: "+e);
                    }
                });

    }

    private void finalizarCita(String idCita){

        DocumentReference refCita = db.collection("Citas").document(idCita);

        refCita
                .update("estado","Finalizado")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        alert("La cita finalizó correctamente.");
                        irHistorialCitas();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alert("No se pudó finalizar la cita: "+e);
                    }
                });
    }


    private void alert(String mensaje){
        Toast.makeText(context,mensaje, Toast.LENGTH_LONG).show();
    }

    protected void irHistorialCitas(){
        Intent historialCitas = new Intent(context, HistorialCitasActivity.class);
        context.startActivity(historialCitas);
    }

}
