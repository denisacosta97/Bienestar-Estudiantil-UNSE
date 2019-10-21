package com.unse.bienestarestudiantil.Vistas;

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
import com.unse.bienestarestudiantil.Modelos.ReservaHorario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class HorariosAdapter extends RecyclerView.Adapter<HorariosAdapter.HorarioViewHolder> {

    ArrayList<ReservaHorario> mList;
    Context mContext;

    public HorariosAdapter(ArrayList<ReservaHorario> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario_reserva,parent, false);

        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {

        ReservaHorario horario = mList.get(position);


        holder.txtHorario.setText(horario.getHoraInicio()+"-"+horario.getHoraFin());
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


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        CardView mCardView;
        TextView txtTitulo, txtHorario;

        public HorarioViewHolder(View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtHorario = itemView.findViewById(R.id.txtDescripcion);
            mCardView = itemView.findViewById(R.id.card);
        }
    }
}
