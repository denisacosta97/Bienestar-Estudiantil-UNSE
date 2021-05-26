package com.unse.bienestarestudiantil.Vistas.Adaptadores;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspecial;
import com.unse.bienestarestudiantil.R;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.EventosViewHolder> {
    
    private ArrayList<Reserva> mReservas;
    private ArrayList<Reserva> mListCopia;
    private Context context;
    private int tipo;

    public static final int USER = 1;
    public static final int ADMIN = 2;
    public static final int ESPECIAL = 3;

    public ReservasAdapter(ArrayList<Reserva> list, Context ctx, int tipo) {
        this.mReservas = list;
        this.context = ctx;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mReservas);
        this.tipo = tipo;

    }

    @NonNull
    @Override
    public ReservasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);

        return new ReservasAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasAdapter.EventosViewHolder holder, int position) {
        Reserva reserva = mReservas.get(position);

        if (tipo == ESPECIAL) {
            holder.idReserva.setText(String.format("RESERVA #%s - Cant.: %s", reserva.getIdReserva(),
                    ((ReservaEspecial) reserva).getCantidad()));
            holder.fecha.setText(Utils.getFechaOrder(Utils.getFechaDateWithHour(reserva.getFechaReserva())));
            holder.estado.setText(reserva.getDescripcion());
            if (reserva.getDescripcion().equals("RESERVADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#E64A19"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("RETIRADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("CANCELADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#DF1A1A"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("NO RETIRADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#A30E49"), PorterDuff.Mode.SRC_OVER);

            }
            holder.nombre.setVisibility(View.VISIBLE);
            holder.nombre.setText(((ReservaEspecial) reserva).getDescripcionEspecial());

        } else {

            holder.idReserva.setText(String.format("RESERVA #%s", reserva.getIdReserva()));
            holder.fecha.setText(Utils.getFechaOrder(Utils.getFechaDateWithHour(reserva.getFechaReserva())));
            holder.estado.setText(reserva.getDescripcion());
            if (reserva.getDescripcion().equals("RESERVADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#E64A19"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("RETIRADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("CANCELADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#DF1A1A"), PorterDuff.Mode.SRC_OVER);

            } else if (reserva.getDescripcion().equals("NO RETIRADO")) {
                holder.mBackg.getBackground().setColorFilter(Color.parseColor("#A30E49"), PorterDuff.Mode.SRC_OVER);

            }
            if (tipo == ADMIN) {
                holder.nombre.setVisibility(View.VISIBLE);
                holder.nombre.setText(String.format("%s %s", reserva.getNombre(), reserva.getApellido()));
            } else {
                holder.fecha.setVisibility(View.VISIBLE);
                holder.nombre.setVisibility(View.GONE);
            }
        }
    }

    public void filtrar(String txt, int tipo) {
        ArrayList<Reserva> result = new ArrayList<>();
        switch (tipo) {
            case Utils.LIST_RESET:
                mReservas.clear();
                mReservas.addAll(mListCopia);
                break;
            case Utils.LIST_DNI:
                for (Reserva item : mListCopia) {
                    if (String.valueOf(item.getIdUsuario()).contains(txt)) {
                        result.add(item);
                    }

                }
                mReservas.clear();
                mReservas.addAll(result);
                break;
        }
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return mReservas.get(position).getIdUsuario();
    }

    @Override
    public int getItemCount() {
        return mReservas.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView idReserva, fecha, estado, nombre;
        LinearLayout mBackg;

        EventosViewHolder(View itemView) {
            super(itemView);

            idReserva = itemView.findViewById(R.id.txtIdReserva);
            fecha = itemView.findViewById(R.id.txtFecha);
            estado = itemView.findViewById(R.id.txtEstado);
            mBackg = itemView.findViewById(R.id.linlay);
            nombre = itemView.findViewById(R.id.txtNombreUser);
        }
    }

    public void change(ArrayList<Reserva> list) {
        mReservas = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mReservas);
        notifyDataSetChanged();
    }
}
