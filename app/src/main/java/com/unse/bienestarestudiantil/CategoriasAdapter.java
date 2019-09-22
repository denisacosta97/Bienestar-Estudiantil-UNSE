package com.unse.bienestarestudiantil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriasAdapter  extends RecyclerView.Adapter<CategoriasAdapter.EventosViewHolder> {

    private ArrayList<Categoria> arrayList;
    private Context context;

    public CategoriasAdapter(ArrayList<Categoria> list, Context ctx) {
        context = ctx;
        arrayList = list;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {

        Categoria s = arrayList.get(position);

        holder.txtTitulo.setText(s.getNombre());

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;

        EventosViewHolder(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);


        }
    }
}
