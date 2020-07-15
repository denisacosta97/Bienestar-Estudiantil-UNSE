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

import com.unse.bienestarestudiantil.Modelos.Espacio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class EspaciosAdapter extends RecyclerView.Adapter<EspaciosAdapter.EspacioViewHolder> {

    private ArrayList<Espacio> mList;
    private Context context;
    private View view;

    public EspaciosAdapter(ArrayList<Espacio> models, Context context) {
        this.mList = models;
        this.context = context;
    }


    @NonNull
    @Override
    public EspacioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_view_poli, parent, false);
        return new EspacioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EspacioViewHolder holder, int position) {

        //Utils.setFont(context, (ViewGroup) view,"montserrat_regular.ttf");

        Espacio espacio = mList.get(position);

        holder.imgFoto.setImageResource(espacio.getImage());
        holder.txtTitulo.setText(espacio.getTitle());
        holder.txtPrecio.setText(espacio.getPrize());
        holder.txtDescrip.setText(espacio.getDesc());

        if(espacio.isSeleccionado()){
            holder.mCardView.setAlpha(0.5f);
        }else{
            holder.mCardView.setAlpha(1f);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class EspacioViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFoto;
        TextView txtTitulo, txtDescrip, txtPrecio;
        CardView mCardView;

        public EspacioViewHolder(View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.image);
            txtTitulo = itemView.findViewById(R.id.title);
            txtPrecio = itemView.findViewById(R.id.txtprize);
            txtDescrip = itemView.findViewById(R.id.desc);
            mCardView = itemView.findViewById(R.id.card);
        }
    }
}
