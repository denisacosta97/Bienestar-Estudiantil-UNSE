package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class GestionReservasAdapter extends RecyclerView.Adapter<GestionReservasAdapter.EventosViewHolder>{

    private ArrayList<Reserva> mGestionReservas;
    private Context context;

    public GestionReservasAdapter (ArrayList<Reserva> list, Context ctx) {
        this.mGestionReservas = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public GestionReservasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rerservas_actuales, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new GestionReservasAdapter.EventosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Reserva depo = mGestionReservas.get(position);

        holder.mIcon.setImageResource(depo.getIcon());
        holder.mTitulo.setText(depo.getTitulo());
        holder.mRangoHorario.setText(depo.getRangoHora().getHoraInicio());
    }


    @Override
    public long getItemId(int position) {
        return mGestionReservas.get(position).getIdReserva();
    }

    @Override
    public int getItemCount() {
        return mGestionReservas.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView mIcon;
        TextView mTitulo, mRangoHorario;

        EventosViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.imgIcono);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mRangoHorario = itemView.findViewById(R.id.txtRangoHorario);

        }
    }

}
