package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        holder.mTitulo.setText(turno.getNomBeca());
        holder.mDni.setText(String.valueOf(turno.getIdUsuario()));
        String nombre = turno.getNom() + " " + turno.getApe();
        holder.mNombre.setText(nombre);
        holder.mEstado.setText(turno.getEstado());
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

        TextView mTitulo, mDni, mNombre, mApellido, mEstado;
        String estado = "Pendiente";

        EventosViewHolder(View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mDni = itemView.findViewById(R.id.txtDni);
            mNombre = itemView.findViewById(R.id.txtNomAp);
            mEstado = itemView.findViewById(R.id.txtEstado);

        }

    }


}
