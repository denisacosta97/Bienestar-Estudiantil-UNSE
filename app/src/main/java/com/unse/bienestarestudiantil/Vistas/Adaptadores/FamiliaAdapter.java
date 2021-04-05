package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Familiar;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.FamiliaViewHolder> {

    private ArrayList<Familiar> mFamiliars;
    private Context mContext;

    public FamiliaAdapter(ArrayList<Familiar> familiars, Context context) {
        mFamiliars = familiars;
        mContext = context;
    }

    @NonNull
    @Override
    public FamiliaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_familiar, viewGroup, false);

        return new FamiliaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamiliaViewHolder holder, int i) {
        Familiar familiar = mFamiliars.get(i);

        holder.txtNombre.setText(String.format("%s %s", familiar.getNombre(), familiar.getApellido()));
        holder.txtDNI.setText(String.valueOf(familiar.getDni()));
        holder.txtFecha.setText(String.format("Fecha Nac: %s", familiar.getFechaNac()));
        holder.txtRelacion.setText(familiar.getRelacion());

    }

    @Override
    public int getItemCount() {
        return mFamiliars.size();
    }

    public static class FamiliaViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtDNI, txtRelacion, txtFecha;

        public FamiliaViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDNI = itemView.findViewById(R.id.txtDni);
            txtRelacion = itemView.findViewById(R.id.txtRol);
            txtFecha = itemView.findViewById(R.id.txtFechaNac);
        }
    }
}
