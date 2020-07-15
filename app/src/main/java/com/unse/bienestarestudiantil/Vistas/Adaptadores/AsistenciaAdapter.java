package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Interfaces.CustomItemClickListener;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.EventosViewHolder> implements CustomItemClickListener{

    private ArrayList<Asistencia> asistencia;
    private ArrayList<Usuario> usuario;
    private Context context;
    private CustomItemClickListener itemClick;

    public AsistenciaAdapter(ArrayList<Asistencia> asistencias, ArrayList<Usuario> usuarios, Context ctx) {
        this.asistencia = asistencias;
        this.usuario = usuarios;
        this.context = ctx;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno_asistencia, parent, false);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventosViewHolder holder, int position) {
        Asistencia depo = asistencia.get(position);

        holder.idAsistencia.setText(String.valueOf(depo.getId()+1));
        String name = searchName(depo.getIdAlumno());
        holder.mNameAlum.setText(name);
        holder.idAlumno.setText(String.valueOf(depo.getIdAlumno()));
        holder.asistencia.setText(depo.getAsistencia());
        if(depo.getAsistencia().equals("A")){
            holder.asistencia.setTextColor(Color.parseColor("#D32F2F"));
        }

    }

    private String searchName(int idAlumno) {
        String name = "";
        for(Usuario user : usuario){
            if(user.getIdUsuario() == idAlumno){
                name = user.getNombre()+ " " + user.getApellido();
            }
        }
        return name;
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

        TextView mNameAlum, idAsistencia, idAlumno, asistencia;

        EventosViewHolder(View itemView) {
            super(itemView);

            idAsistencia = itemView.findViewById(R.id.idAlumno);
            mNameAlum = itemView.findViewById(R.id.txtNameAlumno);
            idAlumno = itemView.findViewById(R.id.txtDniAlumno);
            asistencia = itemView.findViewById(R.id.txtAsistencia);

        }
    }
}
