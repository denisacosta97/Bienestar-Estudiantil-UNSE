package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class DeportesAdapter extends RecyclerView.Adapter<DeportesAdapter.EventosViewHolder> {

    private ArrayList<Deporte> deport;
    private Context context;
    private boolean mBoolean;
    View view;
    int[] iconDeporte = {R.drawable.ic_ajedrez, R.drawable.ic_basquet, R.drawable.ic_becas,
            R.drawable.ic_futbol_masc, R.drawable.ic_futbol_fem, R.drawable.ic_futbol_masc,
            R.drawable.ic_futbol_masc, R.drawable.ic_hockey, R.drawable.ic_natacion,
            R.drawable.ic_rugby, R.drawable.ic_tenis_mesa, R.drawable.ic_voley_masc,
            R.drawable.ic_voley_fem};

    public DeportesAdapter(ArrayList<Deporte> list, Context ctx, boolean b) {
        this.deport = list;
        this.context = ctx;
        this.mBoolean = b;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mBoolean)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes_profesor, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes, parent, false);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Deporte depo = deport.get(position);
        holder.mIcon.setImageResource(iconDeporte[depo.getIdDep()]);
        deport.get(position).setIconDeporte(iconDeporte[depo.getIdDep()]);

        holder.mNameDeport.setText(depo.getName());
    }


    @Override
    public long getItemId(int position) {
        return deport.get(position).getIdDep();
    }

    @Override
    public int getItemCount() {
        return deport.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView mIcon;
        TextView mNameDeport;

        EventosViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.imgvIconDeporte);
            mNameDeport = itemView.findViewById(R.id.txtNameDeporte);
        }
    }
}
