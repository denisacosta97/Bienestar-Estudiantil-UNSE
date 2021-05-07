package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Paciente;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class PacientesAdapter extends RecyclerView.Adapter<PacientesAdapter.EventosViewHolder> {

    ArrayList<Paciente> mPacientes;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public PacientesAdapter(ArrayList<Paciente> list, Context ctx, OnClickOptionListener listener) {
        this.mPacientes = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PacientesAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente, parent, false);
        return new PacientesAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PacientesAdapter.EventosViewHolder holder, final int position) {

        Paciente pas = mPacientes.get(position);

        holder.txtDni.setText(String.valueOf(pas.getIdUsuario()));
        String name = pas.getNombre() + " " + pas.getApellido();
        holder.txtNomAp.setText(name);
        holder.txtFecha.setText(pas.getFecha());
        holder.txtHora.setText(pas.getHora());
        holder.txtFecha.setVisibility(View.GONE);

    }


    @Override
    public long getItemId(int position) {
        return mPacientes.get(position).getIdUsuario();
    }

    @Override
    public int getItemCount() {
        return mPacientes.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView txtDni, txtFecha, txtNomAp, txtHora;
        OnClickOptionListener listener;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtHora = itemView.findViewById(R.id.txtHora);

            this.listener = listener;

        }
    }
}
