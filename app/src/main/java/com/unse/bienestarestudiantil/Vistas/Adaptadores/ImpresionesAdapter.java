package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.Impresion;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ImpresionesAdapter extends RecyclerView.Adapter<ImpresionesAdapter.ImpresionesViewHolder> {
    private ArrayList<Impresion> mList;
    private ArrayList<Impresion> mListCopia;
    private Context context;

    public ImpresionesAdapter(ArrayList<Impresion> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ImpresionesAdapter.ImpresionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_impresiones, parent, false);
        return new ImpresionesAdapter.ImpresionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImpresionesAdapter.ImpresionesViewHolder holder, int position) {
        Impresion impresion = mList.get(position);

        holder.txtdni.setText(String.valueOf(impresion.getDni()));
        holder.txtcantpag.setText(String.valueOf(impresion.getCantpag()));
        holder.txtdescripcion.setText(impresion.getDescripcion());
        holder.txtprecio.setText(String.valueOf(impresion.getPrecio()));
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getDni();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ImpresionesViewHolder extends RecyclerView.ViewHolder {

        TextView txtdni, txtcantpag, txtdescripcion, txtprecio;

        public ImpresionesViewHolder(View itemView) {
            super(itemView);
            txtdni = itemView.findViewById(R.id.txtDni);
            txtcantpag = itemView.findViewById(R.id.txtCantPag);
            txtdescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtprecio = itemView.findViewById(R.id.txtPrecio);
        }

    }

    public void change(ArrayList<Impresion> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
