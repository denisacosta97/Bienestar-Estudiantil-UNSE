package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Certificado;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CertificadosAdapter extends RecyclerView.Adapter<CertificadosAdapter.EventosViewHolder> {

    ArrayList<Certificado> mCertificados;
    private Context context;
    View view;

    public CertificadosAdapter(ArrayList<Certificado> list, Context ctx) {
        this.mCertificados = list;
        this.context = ctx;
    }

    @NonNull
    @Override
    public CertificadosAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certificado, parent, false);

        return new CertificadosAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificadosAdapter.EventosViewHolder holder, final int position) {
        Certificado cert = mCertificados.get(position);

        holder.mDni.setText(String.valueOf(cert.getIdUsuario()));
        String fechita = Utils.getFechaFormat(cert.getFechaRegistro());
        holder.txtNomAp.setText(String.format("%s %s", cert.getNombre(), cert.getApellido()));
        holder.txtFacultad.setText(cert.getFacultad());
        holder.txtCarrera.setText(cert.getCarrera());
        holder.txtFechaEmision.setText(fechita);

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

        TextView mDni, txtFechaEmision, txtNomAp, txtCarrera, txtFacultad;

        EventosViewHolder(final View itemView) {
            super(itemView);

            mDni = itemView.findViewById(R.id.txtDni);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtFacultad = itemView.findViewById(R.id.txtFacultad);
            txtCarrera = itemView.findViewById(R.id.txtCarrera);
            txtFechaEmision = itemView.findViewById(R.id.txtFechaMod);

        }

    }
}
