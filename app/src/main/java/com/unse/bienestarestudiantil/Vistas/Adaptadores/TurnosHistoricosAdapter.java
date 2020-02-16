package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TurnosHistoricosAdapter extends RecyclerView.Adapter<TurnosHistoricosAdapter.EventosViewHolder> {


    private ArrayList<Turno> mTurnos;
    private Context context;
    View view;

    public TurnosHistoricosAdapter(ArrayList<Turno> list, Context ctx) {
        this.mTurnos = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public TurnosHistoricosAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_historico, parent, false);

        return new TurnosHistoricosAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosHistoricosAdapter.EventosViewHolder holder, int position) {
        Turno turno = mTurnos.get(position);

        holder.mTitulo.setText(turno.getTitulo());
        holder.mDni.setText(turno.getDni());
        holder.mNombre.setText(turno.getNombre());
        holder.mApellido.setText(turno.getApellido());
        holder.mEstado.setText(turno.getEstado());
    }


    @Override
    public long getItemId(int position) {
        return mTurnos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mTurnos.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mTitulo, mDni, mNombre, mApellido, mEstado;
        String estado = "Pendiente";

        EventosViewHolder(View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mDni = itemView.findViewById(R.id.txtDni);
            mNombre = itemView.findViewById(R.id.txtNombre);
            mApellido = itemView.findViewById(R.id.txtApellido);
            mEstado = itemView.findViewById(R.id.txtEstado);

        }

    }


}
