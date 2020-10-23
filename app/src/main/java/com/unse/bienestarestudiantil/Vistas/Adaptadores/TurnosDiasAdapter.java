package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogAtenderBecas;

import java.util.ArrayList;

public class TurnosDiasAdapter extends RecyclerView.Adapter<TurnosDiasAdapter.EventosViewHolder> {

    ArrayList<Turno> mTurnos;
    private Context context;
    FragmentManager mFragmentManager;
    View view;

    public TurnosDiasAdapter(ArrayList<Turno> list, Context ctx, FragmentManager fragmentManager) {
        this.mTurnos = list;
        this.context = ctx;
        this.mFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TurnosDiasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_dia, parent, false);

        return new TurnosDiasAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosDiasAdapter.EventosViewHolder holder, final int position) {
        Turno turno = mTurnos.get(position);

        holder.mTitulo.setText(turno.getNomBeca());
        holder.mDni.setText(String.valueOf(turno.getIdUsuario()));
        String nombre = turno.getNom() + " " + turno.getApe();
        holder.mNombre.setText(nombre);
        holder.mHorario.setText(turno.getHorario());

        switch (turno.getDescBeca()) {
            case "PENDIENTE":
                holder.mEstado.setText("PENDIENTE");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorYellow));
                break;
            case "CONFIRMADO":
                holder.mEstado.setText("CONFIRMADO");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
            case "AUSENTE":
                holder.mEstado.setText("AUSENTE");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorOrange));
                break;
            case "CANCELADO":
                holder.mEstado.setText("CANCELADO");
                holder.mEstado.setTextColor(context.getResources().getColor(R.color.colorRed));
                break;
        }

        if(!turno.getDescBeca().equals("PENDIENTE")){
            holder.btnAtender.setText("MODIFICAR");
        }

        holder.btnAtender.setTag(position);
        holder.btnAtender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAtenderBecas dialogAtender = new DialogAtenderBecas();
                dialogAtender.loadData(mTurnos.get(position));
                dialogAtender.setFragmentManager(mFragmentManager);
                dialogAtender.setContext(context);
                dialogAtender.show(mFragmentManager,"dialog_turnos");
            }
        });
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

        TextView mTitulo, mDni, mNombre, mEstado, mHorario;
        Button btnAtender;

        EventosViewHolder(final View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mDni = itemView.findViewById(R.id.txtDni);
            mNombre = itemView.findViewById(R.id.txtNomAp);
            mHorario = itemView.findViewById(R.id.txtHorario);
            btnAtender = itemView.findViewById(R.id.btnAtender);
            mEstado = itemView.findViewById(R.id.txtEstado);

        }

    }

}
