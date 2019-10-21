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

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Espacio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.List;

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

        //Utils.setFont(context, (ViewGroup) view,"Montserrat-Regular.ttf");


        holder.imageView.setImageResource(mList.get(position).getImage());
        holder.title.setText(mList.get(position).getTitle());
        holder.prize.setText(mList.get(position).getPrize());
        holder.desc.setText(mList.get(position).getDesc());

        if(mList.get(position).isSeleccionado()){
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


        ImageView imageView;
        TextView title, desc, prize;
        CardView mCardView;


        public EspacioViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            prize = itemView.findViewById(R.id.txtprize);
            desc = itemView.findViewById(R.id.desc);
            mCardView = itemView.findViewById(R.id.card);
        }
    }
}
