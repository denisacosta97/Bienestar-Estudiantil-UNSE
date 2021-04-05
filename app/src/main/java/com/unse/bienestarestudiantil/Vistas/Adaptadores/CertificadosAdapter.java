package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Certificado;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class CertificadosAdapter extends RecyclerView.Adapter<CertificadosAdapter.EventosViewHolder> {
    ArrayList<Certificado> mCertificados;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public CertificadosAdapter(ArrayList<Certificado> list, Context ctx, OnClickOptionListener listener) {
        this.mCertificados = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CertificadosAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certificado, parent, false);

        return new CertificadosAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificadosAdapter.EventosViewHolder holder, final int position) {
        Certificado cert = mCertificados.get(position);

        holder.txtDni.setText(String.valueOf(cert.getIdUsuario()));
        String fechita = cert.getDia() + "/" + cert.getMes() + "/" + cert.getAnio();
        holder.txtFecha.setText(fechita);
        holder.txtDesc.setText(cert.getDescripcion());
        holder.txtFechaEmit.setText(cert.getFechaRegistro());

    }


    @Override
    public long getItemId(int position) {
        return mCertificados.get(position).getIdUsuario();
    }

    @Override
    public int getItemCount() {
        return mCertificados.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView txtDni, txtFecha, txtDesc, txtFechaEmit;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtFechaEmit = itemView.findViewById(R.id.txtFechaEmit);

        }

    }
}
