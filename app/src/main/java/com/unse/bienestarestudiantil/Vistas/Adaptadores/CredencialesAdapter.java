package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class CredencialesAdapter extends RecyclerView.Adapter<CredencialesAdapter.CredencialViewHolder> {

    private ArrayList<Credencial> mArchivos;
    private Context context;
    private OnClickListenerAdapter mListener;

    public CredencialesAdapter(ArrayList<Credencial> list, Context ctx, OnClickListenerAdapter listenerAdapter) {
        this.mArchivos = list;
        this.context = ctx;
        this.mListener = listenerAdapter;
    }

    @NonNull
    @Override
    public CredencialesAdapter.CredencialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credenciales, parent, false);

        return new CredencialesAdapter.CredencialViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CredencialesAdapter.CredencialViewHolder holder, int position) {
        Credencial credencial = mArchivos.get(position);

        holder.mNumArchivo.setText(String.valueOf(position + 1));
        holder.txtTitulo.setText(credencial.getTitulo());
        if (credencial.getValidez() == 0) {
            holder.mLayout.setEnabled(false);
            holder.txtTitulo.setEnabled(false);
            holder.mPdf.setEnabled(false);
        } else {
            holder.mLayout.setEnabled(true);
        }

    }

    @Override
    public long getItemId(int position) {
        return mArchivos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mArchivos.size();
    }

    static class CredencialViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, mNumArchivo;
        LinearLayout mPdf;
        LinearLayout mLayout;
        OnClickListenerAdapter mListenerAdapter;

        CredencialViewHolder(View itemView, OnClickListenerAdapter listenerAdapter) {
            super(itemView);

            mNumArchivo = itemView.findViewById(R.id.txtNroArchivo);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mPdf = itemView.findViewById(R.id.imgIcon);
            mLayout = itemView.findViewById(R.id.layout);
            mListenerAdapter = listenerAdapter;

            mPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerAdapter.onClick(getAdapterPosition());
                }
            });

        }
    }

}
