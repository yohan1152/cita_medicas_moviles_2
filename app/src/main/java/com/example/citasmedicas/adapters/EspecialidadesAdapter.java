package com.example.citasmedicas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.citasmedicas.R;
import com.example.citasmedicas.models.EspecialidadesModel;

import java.util.ArrayList;

public class EspecialidadesAdapter extends BaseAdapter {

    private ArrayList<EspecialidadesModel> list;
    private EspecialidadesModel model;
    private Context context;

    public EspecialidadesAdapter(ArrayList<EspecialidadesModel> list, Context context) {
        this.list = list;
        this.context = context;
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
            item = inflater.inflate(R.layout.item_especialidad, viewGroup, false);
        }

        TextView tv_item_especialidad_nombre = item.findViewById(R.id.tv_item_especialidad_nombre);

        model = list.get(i);

        tv_item_especialidad_nombre.setText(model.getEspecialidad());

        return item;

    }
}
