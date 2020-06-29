package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Regularidad;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class RegularidadAdapter extends RecyclerView.Adapter<RegularidadAdapter.RegularidadViewHolder> {

    private Context mContext;
    private ArrayList<Regularidad> mList;

    public RegularidadAdapter(Context context, ArrayList<Regularidad> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RegularidadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_regularidad, viewGroup, false);
        return new RegularidadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegularidadViewHolder holder, int i) {

        Regularidad regularidad = mList.get(i);

        holder.txtTitulo.setText(String.format("%s %s", regularidad.getValidez() == 1 ? "VIGENTE" : "NO VIGENTE"
        , regularidad.getAnio()));
        Glide.with(holder.imgIcon.getContext()).load(regularidad.getValidez() == 1 ? R.drawable.ic_chek : R.drawable.ic_error)
                .into(holder.imgIcon);
        holder.txtFecha.setText(Utils.getFechaFormat(regularidad.getFechaOtorg()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class RegularidadViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgIcon;
        private TextView txtTitulo, txtFecha;

        public RegularidadViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtDescripcion);

        }
    }
}
