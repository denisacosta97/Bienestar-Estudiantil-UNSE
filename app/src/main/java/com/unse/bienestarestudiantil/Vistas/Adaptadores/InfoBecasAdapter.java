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
import com.unse.bienestarestudiantil.Modelos.InfoBecas;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class InfoBecasAdapter extends RecyclerView.Adapter<InfoBecasAdapter.EventosViewHolder>{

    private ArrayList<InfoBecas> mInfoBecas;
    private Context context;
    View view;

    public InfoBecasAdapter(ArrayList<InfoBecas> list, Context ctx) {
        this.mInfoBecas = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public InfoBecasAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_becas, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new InfoBecasAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoBecasAdapter.EventosViewHolder holder, int position) {
        InfoBecas torneo = mInfoBecas.get(position);

        holder.mIcon.setImageResource(torneo.getIcon());
        holder.mNameBeca.setText(torneo.getNameBeca());
    }


    @Override
    public long getItemId(int position) {
        return mInfoBecas.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mInfoBecas.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView mIcon;
        TextView mNameBeca;

        EventosViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.imgvIconB);
            mNameBeca = itemView.findViewById(R.id.txtNameBeca);
        }
    }

}
