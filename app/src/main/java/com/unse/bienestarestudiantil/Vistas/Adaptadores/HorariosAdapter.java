package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.ReservaHorario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class HorariosAdapter extends RecyclerView.Adapter<HorariosAdapter.HorarioViewHolder> {

    private ArrayList<ReservaHorario> mList;
    private Context mContext;
    private boolean isCancha = false;

    public HorariosAdapter(ArrayList<ReservaHorario> list, Context context, boolean c) {
        mList = list;
        mContext = context;
        isCancha = c;
    }

    @Override
    public int getItemViewType(int position) {
        return isCancha ? Utils.TIPO_CANCHA : Utils.TIPO_QUINCHO;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if(viewType == Utils.TIPO_QUINCHO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario_reserva,parent, false);

        }else if(viewType == Utils.TIPO_CANCHA){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva_cancha,parent, false);

        }
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {

        ReservaHorario horario = mList.get(position);


        String hora = String.format("%s-%s", horario.getHoraInicio(), horario.getHoraFin());
        holder.txtHorario.setText(hora);
        switch (horario.getEstado()){
            case 1:
                holder.txtTitulo.setText("LIBRE");
                Glide.with(holder.imgIcon.getContext()).load(R.drawable.ic_reserva_libre).into(holder.imgIcon);
                holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
                break;
            case 2:
                holder.txtTitulo.setText("OCUPADO");
                Glide.with(holder.imgIcon.getContext()).load(R.drawable.ic_reserva_ocupado).into(holder.imgIcon);
                holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
                break;
            case 3:
                holder.txtTitulo.setText("A CONFIRMAR");
                Glide.with(holder.imgIcon.getContext()).load(R.drawable.ic_reserva_espera).into(holder.imgIcon);
                holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                break;
        }

        if(getItemViewType(position) == Utils.TIPO_QUINCHO) {



            if (horario.getTurno() == 1) {
                holder.txtTurno.setText("TURNO MAÑANA");
            } else {
                holder.txtTurno.setText("TURNO NOCHE");
            }
        }else{
            if (horario.getTurno() == 1) {
                holder.txtTurno.setText("CANCHA C/LUZ");
            } else {
                holder.txtTurno.setText("CANCHA S/LUZ");
            }
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon, imgIconPelota;
        CardView mCardView;
        TextView txtTitulo, txtHorario, txtTurno;

        public HorarioViewHolder(View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            imgIconPelota = itemView.findViewById(R.id.imgIconPelota);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtHorario = itemView.findViewById(R.id.txtDescripcion);
            mCardView = itemView.findViewById(R.id.card);
            txtTurno = itemView.findViewById(R.id.txtTurno);
        }
    }
}
