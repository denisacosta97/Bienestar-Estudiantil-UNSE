package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TurnosDiasAdapter extends RecyclerView.Adapter<TurnosDiasAdapter.EventosViewHolder> {

    ArrayList<Turno> mTurnos;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public TurnosDiasAdapter(ArrayList<Turno> list, Context ctx, OnClickOptionListener listener) {
        this.mTurnos = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TurnosDiasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_dia, parent, false);

        return new TurnosDiasAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosDiasAdapter.EventosViewHolder holder, final int position) {
        Turno turno = mTurnos.get(position);

        holder.mTitulo.setText(turno.getNomBeca());
        holder.mDni.setText(String.valueOf(turno.getIdUsuario()));
        String nombre = turno.getNom() + " " + turno.getApe();
        holder.mNombre.setText(nombre);
        holder.mHorario.setText(turno.getHorario());
        holder.mReceptor.setText(turno.getReceptorString());

        switch (turno.getDescBeca()) {
            case "PENDIENTE":
                holder.mEstado.setText("PENDIENTE");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorOrange));
                break;
            case "CONFIRMADO":
                holder.mEstado.setText("CONFIRMADO");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
            case "AUSENTE":
                holder.mEstado.setText("AUSENTE");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorPink));
                break;
            case "CANCELADO":
                holder.mEstado.setText("CANCELADO");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorRed));
                break;
        }

        if (listener == null) {
            holder.btnAtender.setVisibility(View.GONE);
        } else if (!turno.getDescBeca().equals("PENDIENTE")) {
            holder.btnAtender.setText("MODIFICAR");
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

        TextView mTitulo, mDni, mNombre, mEstado, mHorario, mReceptor;
        Button btnAtender;
        OnClickOptionListener listener;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mDni = itemView.findViewById(R.id.txtDni);
            mNombre = itemView.findViewById(R.id.txtNomAp);
            mHorario = itemView.findViewById(R.id.txtHorario);
            btnAtender = itemView.findViewById(R.id.btnAtender);
            mEstado = itemView.findViewById(R.id.txtEstado);
            mReceptor = itemView.findViewById(R.id.txtReceptor);
            this.listener = listener;

            btnAtender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });

        }

    }

}
