package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

import org.slf4j.helpers.Util;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AtencionDiariaAdapter extends RecyclerView.Adapter<AtencionDiariaAdapter.AtencionHolder> {

    private ArrayList<AtencionDiaria> lista;
    private Context mContext;

    public AtencionDiariaAdapter(ArrayList<AtencionDiaria> lista, Context context) {
        this.lista = lista;
        mContext = context;
    }

    @NonNull
    @Override
    public AtencionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atencion_diaria, parent, false);

        return new AtencionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtencionHolder holder, int position) {

        AtencionDiaria atencionDiaria = lista.get(position);

        holder.txtNombre.setText(String.format("%s %s", atencionDiaria.getNombre(), atencionDiaria.getApellido()));
        holder.txtDNI.setText(String.valueOf(atencionDiaria.getIdUsuario()));
        holder.txtFacultad.setText(atencionDiaria.getFacultad());
        holder.txtMotivo.setText(atencionDiaria.getConsulta());
        holder.txtFecha.setText(""+Utils.getFechaOrder(Utils.getFechaDateWithHour(atencionDiaria.getFechaRegistro())));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class AtencionHolder extends RecyclerView.ViewHolder {

        TextView txtDNI, txtNombre, txtFacultad, txtMotivo, txtFecha;

        public AtencionHolder(@NonNull View itemView) {
            super(itemView);

            txtDNI = itemView.findViewById(R.id.txtDni);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtFacultad = itemView.findViewById(R.id.txtFacultad);
            txtMotivo = itemView.findViewById(R.id.txtDescripcion);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
