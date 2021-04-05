package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.Modelos.TurnosUAPU;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.List;

public class HistorialTurnosUAPUAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ItemBase> lista;

    public HistorialTurnosUAPUAdapter(Context context, List<ItemBase> lista) {
        this.lista = lista;
        this.mContext = context;

    }

    public void change(ArrayList<ItemBase> list) {
        this.lista = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ItemBase.TIPO_DATO:
                View view = inflater.inflate(R.layout.item_dia, viewGroup, false);
                viewHolder = new DateViewHolder(view);
                break;
            case ItemBase.TIPO_FECHA:
                View view2 = inflater.inflate(R.layout.item_fecha, viewGroup, false);
                viewHolder = new FechaViewHolder(view2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ItemBase.TIPO_FECHA:
                ItemFecha itemFecha = (ItemFecha) lista.get(position);
                FechaViewHolder fechaViewHolder = (FechaViewHolder) viewHolder;
                fechaViewHolder.txtFecha.setText(String.format("%s", itemFecha.getAnio()));
                break;

            case ItemBase.TIPO_DATO:
                ItemDato dateItem = (ItemDato) lista.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                TurnosUAPU turno = dateItem.getTurnosUAPU();
                dateViewHolder.fecha.setText(Utils.getInfoDate(turno.getDia(), turno.getMes(), turno.getAnio()));
                break;

        }

    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha;

        DateViewHolder(View v) {
            super(v);
            fecha = itemView.findViewById(R.id.txtFecha);

        }
    }

    //View holder for general row item
    class FechaViewHolder extends RecyclerView.ViewHolder {
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
