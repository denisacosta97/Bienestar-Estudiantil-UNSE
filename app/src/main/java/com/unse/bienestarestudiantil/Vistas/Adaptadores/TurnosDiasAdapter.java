package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TurnosDiasAdapter extends RecyclerView.Adapter<TurnosDiasAdapter.EventosViewHolder> {

    private ArrayList<Turno> mTurnos;
    private Context context;
    View view;

    public TurnosDiasAdapter(ArrayList<Turno> list, Context ctx) {
        this.mTurnos = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public TurnosDiasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_dia, parent, false);

        return new TurnosDiasAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosDiasAdapter.EventosViewHolder holder, int position) {
        Turno turno = mTurnos.get(position);

        holder.mTitulo.setText(turno.getTitulo());
        holder.mDni.setText(turno.getDni());
        holder.mNombre.setText(turno.getNombre());
        holder.mApellido.setText(turno.getApellido());
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

        TextView mTitulo, mDni, mNombre, mApellido;
        Button btnConfirmar, btnAusente;
        String estado = "Pendiente";

        EventosViewHolder(final View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mDni = itemView.findViewById(R.id.txtDni);
            mNombre = itemView.findViewById(R.id.txtNombre);
            mApellido = itemView.findViewById(R.id.txtApellido);
            btnConfirmar = itemView.findViewById(R.id.btnConfirmar);
            btnAusente = itemView.findViewById(R.id.btnAusente);

            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    estado = "Confirmado";
                    Toast.makeText(itemView.getContext(), "Confirmado", Toast.LENGTH_SHORT).show();
                }
            });

            btnAusente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    estado = "Ausente";
                    Toast.makeText(itemView.getContext(), "Ausente", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}
