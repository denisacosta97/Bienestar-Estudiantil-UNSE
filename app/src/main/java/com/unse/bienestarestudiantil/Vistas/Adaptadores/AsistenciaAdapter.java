package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.EventosViewHolder>{

    private ArrayList<Asistencia> asistencia;
    private Context context;

    public AsistenciaAdapter(ArrayList<Asistencia> list, Context ctx) {
        this.asistencia = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno_asistencia, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Asistencia depo = asistencia.get(position);

        holder.mImgFotoAlum.setImageResource(depo.getIdFotoAlum());
        holder.mNameAlum.setText(depo.getName());
    }


    @Override
    public long getItemId(int position) {
        return asistencia.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return asistencia.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgFotoAlum;
        TextView mNameAlum;
        //CheckBox mCheckBox, mNotCheckedBox;

        EventosViewHolder(View itemView) {
            super(itemView);

            mImgFotoAlum = itemView.findViewById(R.id.imgFotoAlumno);
            mNameAlum = itemView.findViewById(R.id.txtNameAlumno);
//            mCheckBox = itemView.findViewById(R.id.checkBoxPresente);
//            mNotCheckedBox = itemView.findViewById(R.id.checkBoxAusente);
        }
    }

}
