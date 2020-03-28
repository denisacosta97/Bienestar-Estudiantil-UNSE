package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Interfaces.CustomItemClickListener;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.EventosViewHolder> implements CustomItemClickListener{

    private ArrayList<Asistencia> asistencia;
    private Context context;
    private CustomItemClickListener itemClick;

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
    public void onBindViewHolder(@NonNull final EventosViewHolder holder, int position) {
        Asistencia depo = asistencia.get(position);

        holder.idAlumno.setText(String.valueOf(depo.getId()+1));
        holder.mNameAlum.setText(depo.getName());

        holder.btnPresente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnAusente.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
                holder.btnPresente.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            }
        });

        holder.btnAusente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnPresente.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
                holder.btnAusente.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return asistencia.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return asistencia.size();
    }

    @Override
    public void onClickAsistencia(Asistencia asistencia) {

    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mNameAlum, idAlumno;
        Button btnPresente, btnAusente;

        EventosViewHolder(View itemView) {
            super(itemView);

            idAlumno = itemView.findViewById(R.id.idAlumno);
            mNameAlum = itemView.findViewById(R.id.txtNameAlumno);
            btnPresente = itemView.findViewById(R.id.btnPresente);
            btnAusente = itemView.findViewById(R.id.btnAusente);

        }
    }

}
