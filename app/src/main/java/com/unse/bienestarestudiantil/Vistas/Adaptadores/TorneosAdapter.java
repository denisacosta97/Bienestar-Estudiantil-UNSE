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
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TorneosAdapter extends RecyclerView.Adapter<TorneosAdapter.EventosViewHolder>{

    private ArrayList<Torneo> mTorneos;
    private Context context;
    View view;

    public TorneosAdapter(ArrayList<Torneo> list, Context ctx) {
        this.mTorneos = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_torneo, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Torneo torneo = mTorneos.get(position);

        holder.mIcon.setImageResource(torneo.getLogo());
        holder.mNameTorneo.setText(torneo.getNameTorneo());
    }


    @Override
    public long getItemId(int position) {
        return mTorneos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mTorneos.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView mIcon;
        TextView mNameTorneo;

        EventosViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.imgvIcon);
            mNameTorneo = itemView.findViewById(R.id.txtNameTorneo);
        }
    }

}
