package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.TurnosUAPU;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TurnosDiaUAdapter extends RecyclerView.Adapter<TurnosDiaUAdapter.EventosViewHolder> {

    ArrayList<TurnosUAPU> mTurnos;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public TurnosDiaUAdapter(ArrayList<TurnosUAPU> list, Context ctx, OnClickOptionListener listener) {
        this.mTurnos = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TurnosDiaUAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_dia_uapu, parent, false);

        return new TurnosDiaUAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosDiaUAdapter.EventosViewHolder holder, final int position) {
        TurnosUAPU turno = mTurnos.get(position);

        holder.txtDni.setText(String.valueOf(turno.getIdUsuario()));
        holder.txtIdEsp.setText(String.valueOf(turno.getTitulo()));
        String nombre = turno.getNombre() + " " + turno.getApellido();
        holder.txtNomAp.setText(nombre);
        holder.txtHorario.setText(turno.getHorario());
        holder.txtFechaRes.setText(turno.getFechaRegistro());

        switch (turno.getDescripcion()) {
            case "RESERVADO":
                holder.txtEstado.setText("RESERVADO");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorOrange));
                break;
            case "CONFIRMADO":
                holder.txtEstado.setText("CONFIRMADO");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
            case "AUSENTE":
                holder.txtEstado.setText("AUSENTE");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorPink));
                break;
            case "CANCELADO":
                holder.txtEstado.setText("CANCELADO");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorRed));
                break;
        }

    }


    @Override
    public long getItemId(int position) {
        return mTurnos.get(position).getIdUsuario();
    }

    @Override
    public int getItemCount() {
        return mTurnos.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdEsp, txtDni, txtNomAp, txtHorario, txtEstado, txtFechaRes;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            txtIdEsp = itemView.findViewById(R.id.txtIdEsp);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtHorario = itemView.findViewById(R.id.txtHorario);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtFechaRes = itemView.findViewById(R.id.txtFechaRes);

        }

    }

}
