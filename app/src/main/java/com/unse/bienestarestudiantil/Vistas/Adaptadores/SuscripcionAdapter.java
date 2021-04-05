package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.SuscripcionSocio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class SuscripcionAdapter extends RecyclerView.Adapter<SuscripcionAdapter.SuscripcionViewHolder> {

    private ArrayList<SuscripcionSocio> mSuscripcionSocios;
    private Context mContext;

    public SuscripcionAdapter(ArrayList<SuscripcionSocio> suscripcionSocios, Context context) {
        mSuscripcionSocios = suscripcionSocios;
        mContext = context;
    }

    @NonNull
    @Override
    public SuscripcionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_suscripcion, viewGroup, false);

        return new SuscripcionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuscripcionViewHolder holder, int i) {

        SuscripcionSocio suscripcionSocio = mSuscripcionSocios.get(i);

        holder.txtFecha.setText(String.format("Inscripto el %s", suscripcionSocio.getFechaRegistro().substring(0, 10).trim()));

        //holder.txtFecha.setText(suscripcionSocio.getFechaRegistro());
        holder.txtNombre.setText(String.format("SOCIO - %s", suscripcionSocio.getAnio()));
        if (suscripcionSocio.getEstado() == 2) {
            Glide.with(holder.imgIcono.getContext()).load(R.drawable.ic_error).into(holder.imgIcono);
        } else if (suscripcionSocio.getEstado() == 1) {
            Glide.with(holder.imgIcono.getContext()).load(R.drawable.ic_chek).into(holder.imgIcono);
        } else
            Glide.with(holder.imgIcono.getContext()).load(R.drawable.ic_advertencia).into(holder.imgIcono);

    }

    @Override
    public int getItemCount() {
        return mSuscripcionSocios.size();
    }

    public static class SuscripcionViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtFecha;
        ImageView imgIcono;

        public SuscripcionViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcono = itemView.findViewById(R.id.imgIcon);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
