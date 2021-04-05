package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.EventosViewHolder> {

    ArrayList<Medicamento> mMedicamentos;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public MedicamentoAdapter(ArrayList<Medicamento> list, Context ctx, OnClickOptionListener listener) {
        this.mMedicamentos = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicamentoAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento_dia, parent, false);

        return new MedicamentoAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoAdapter.EventosViewHolder holder, final int position) {
        Medicamento med = mMedicamentos.get(position);

        holder.mDni.setText(String.valueOf(med.getIdUsuario()));
        String name = med.getNombre() + " " + med.getApellido();
        holder.txtNomAp.setText(name);
        holder.txtFacultad.setText(med.getFacultad());
        holder.txtCarrera.setText(med.getCarrera());
        holder.txtFechaTurno.setText(med.getFechaRegistro());
        holder.txtFechaMod.setText(med.getFechaHora());

        switch (med.getDescripcion()) {
            case "PENDIENTE":
                holder.txtEstado.setText("PENDIENTE");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorOrange));
                break;
            case "RETIRADO":
                holder.txtEstado.setText("RETIRADO");
                holder.txtEstado.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
        }

    }


    @Override
    public long getItemId(int position) {
        return mMedicamentos.get(position).getIdUsuario();
    }

    @Override
    public int getItemCount() {
        return mMedicamentos.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mDni, txtFechaTurno, txtFechaMod, txtNomAp, txtCarrera, txtFacultad, txtEstado;
        Button btnAtender;
        OnClickOptionListener listener;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            mDni = itemView.findViewById(R.id.txtDni);
            txtFechaTurno = itemView.findViewById(R.id.txtFechaTurno);
            txtFechaMod = itemView.findViewById(R.id.txtFechaMod);
            btnAtender = itemView.findViewById(R.id.btnAtender);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtFacultad = itemView.findViewById(R.id.txtFacultad);
            txtCarrera = itemView.findViewById(R.id.txtCarrera);
            txtEstado = itemView.findViewById(R.id.txtEstado);

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
