package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TurnosAdapter extends RecyclerView.Adapter<TurnosAdapter.TurnoViewHolder> {

    private ArrayList<Turno> mList;
    private Context mContext;

    public TurnosAdapter(ArrayList<Turno> list, Context context) {
        this.mContext = context;
        this.mList = list;

    }

    @NonNull
    @Override
    public TurnosAdapter.TurnoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_turnos, viewGroup, false);

        return new TurnosAdapter.TurnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnosAdapter.TurnoViewHolder holder, int i) {

        if (mList.size() != 0) {
            Turno turno = mList.get(i);
            holder.txtTitulo.setText(turno.getNomBeca());
            String fecha = turno.getDia() + "/" + turno.getMes() + "/" + turno.getAnio();
            holder.txtFecha.setText(String.format("%s", fecha));
            int estado = 0;
            switch (turno.getDescBeca()) {
                case "PENDIENTE":
                    estado = R.drawable.ic_reserva_espera_turno;
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                    break;
                case "CONFIRMADO":
                    estado = R.drawable.ic_reserva_libre_turno;
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
                    break;
                case "AUSENTE":
                    estado = R.drawable.ic_reserva_ocupado_turno;
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                    break;
                case "CANCELADO":
                    estado = R.drawable.ic_reserva_ocupado_turno;
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                    break;
            }
            Glide.with(holder.imgIcon.getContext()).load(estado).into(holder.imgIcon);
            holder.txtEstado.setText(turno.getEstado());
            holder.txtDesc.setText(turno.getDescBeca());

           /*Drawable drawable = holder.imgIcon.;
           // drawable.mutate();
            switch (turno.getEstado()) {
                case "PENDIENTE":
                    holder.imgIcon.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                    //Utils.changeColorDrawable(holder.imgIcon, mContext, R.color.colorAccent);
                    // drawable.setColorFilter(mContext.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

                    break;
                case "CONFIRMADO":
                    //Utils.changeColorDrawable(holder.imgIcon, mContext, R.color.colorGreen);
                    holder.imgIcon.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
                    //drawable.setColorFilter(mContext.getResources().getColor(R.color.colorGreen), PorterDuff.Mode.MULTIPLY);
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
                    break;
                case "AUSENTE":
                    //Utils.changeColorDrawable(holder.imgIcon, mContext, R.color.colorOrange);
                    holder.imgIcon.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                    //drawable.setColorFilter(mContext.getResources().getColor(R.color.colorOrange), PorterDuff.Mode.MULTIPLY);
                    holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                    break;
            }*/

        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TurnoViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, txtDesc, txtFecha, txtEstado;
        ImageView imgIcon;
        CardView mCardView;

        TurnoViewHolder(View view) {
            super(view);

            mCardView = view.findViewById(R.id.card);
            txtTitulo = view.findViewById(R.id.txtTitulo);
            txtDesc = view.findViewById(R.id.txtDesc);
            txtFecha = view.findViewById(R.id.txtFecha);
            imgIcon = view.findViewById(R.id.imgIcon);
            txtEstado = view.findViewById(R.id.txtEstado);


        }

    }
}
