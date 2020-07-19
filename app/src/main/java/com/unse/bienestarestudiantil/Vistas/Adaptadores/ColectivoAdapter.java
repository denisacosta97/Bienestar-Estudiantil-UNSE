package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Colectivo;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ColectivoAdapter extends RecyclerView.Adapter<ColectivoAdapter.ColectivoViewHolder> {
    private ArrayList<Colectivo> mList;
    private ArrayList<Colectivo> mListCopia;
    private Context context;

    public ColectivoAdapter(ArrayList<Colectivo> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }


    @NonNull
    @Override
    public ColectivoAdapter.ColectivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colectivo, parent, false);
        return new ColectivoAdapter.ColectivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColectivoAdapter.ColectivoViewHolder holder, int position) {
        Colectivo colectivo = mList.get(position);

        holder.txtNameCol.setText(colectivo.getIdColectivo());
        holder.txtMatricula.setText(colectivo.getPatente());

    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdColectivo();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ColectivoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameCol, txtMatricula;

        public ColectivoViewHolder(View itemView) {
            super(itemView);
            txtNameCol = itemView.findViewById(R.id.txtNombreColec);
            txtMatricula = itemView.findViewById(R.id.txtMatricula);

        }

    }

    public void change(ArrayList<Colectivo> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
