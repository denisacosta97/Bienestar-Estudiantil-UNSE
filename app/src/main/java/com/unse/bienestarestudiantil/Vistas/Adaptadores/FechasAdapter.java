package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.List;

public class FechasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<ItemBase> lista;

    public FechasAdapter(Context context, ArrayList<ItemBase> lista) {
        this.lista = lista;
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ItemBase.TIPO_DATO:
                //item_dato
                View view = inflater.inflate(R.layout.item_credenciales, viewGroup, false);
                viewHolder = new DateViewHolder(view);
                break;
            case ItemBase.TIPO_FECHA:
                View view2 = inflater.inflate(R.layout.item_fecha, viewGroup, false);
                viewHolder = new FechaViewHolder(view2);
                break;
        }

        return viewHolder;
    }

    public void change(ArrayList<ItemBase> e){
        this.lista = e;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ItemBase.TIPO_FECHA:
                ItemFecha itemFecha = (ItemFecha) lista.get(position);
                FechaViewHolder fechaViewHolder = (FechaViewHolder) viewHolder;
                fechaViewHolder.txtFecha.setText(itemFecha.getAnio());
                break;

            case ItemBase.TIPO_DATO:
                ItemDato dateItem = (ItemDato) lista.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                dateViewHolder.txtNroArchivo.setText(dateItem.getIdValue());
                break;
        }

    }


    //ViewHolder for date row item
    static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitulo, txtNroArchivo;

        DateViewHolder(View v) {
            super(v);
            txtTitulo = v.findViewById(R.id.txtTitulo);
            txtNroArchivo = v.findViewById(R.id.txtNroArchivo);

        }
    }

    //View holder for general row item
    static class FechaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFecha;

        FechaViewHolder(View v) {
            super(v);
            txtFecha = v.findViewById(R.id.txtFecha);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return lista.get(position).getTipo();
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }
}
