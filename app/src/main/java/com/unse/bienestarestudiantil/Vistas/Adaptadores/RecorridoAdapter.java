package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class RecorridoAdapter extends RecyclerView.Adapter<RecorridoAdapter.TransporteViewHolder> {
    private ArrayList<Recorrido> mList;
    private ArrayList<Recorrido> mListCopia;
    private Context context;

    public RecorridoAdapter(ArrayList<Recorrido> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }


    @NonNull
    @Override
    public RecorridoAdapter.TransporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recorrido, parent, false);
        return new RecorridoAdapter.TransporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecorridoAdapter.TransporteViewHolder holder, int position) {
        Recorrido recorrido = mList.get(position);

        holder.txtLinea.setText(recorrido.getDescripcion());

    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdRecorrido();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TransporteViewHolder extends RecyclerView.ViewHolder {

        TextView txtLinea;

        public TransporteViewHolder(View itemView) {
            super(itemView);
            txtLinea = itemView.findViewById(R.id.txtLinea);

        }

    }

    public void change(ArrayList<Recorrido> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
