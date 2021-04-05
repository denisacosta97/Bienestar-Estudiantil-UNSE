package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ServicioViewHolder> {

    private ArrayList<Servicio> mList;
    private Context mContext;
    int tipo;

    public ServiciosAdapter(ArrayList<Servicio> list, Context context, int tipo) {
        mList = list;
        mContext = context;
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {
        Servicio servicio = mList.get(position);
        holder.txtNombre.setText(servicio.getDescripcio());
        holder.txtFechaInicio.setText(String.format("Fecha de Inicio: %s",
                Utils.getFechaOrder(Utils.getFechaDateWithHour(servicio.getFechaInicio()))));
        holder.txtFechaFin.setText(String.format("Fecha de Fin: %s",
                Utils.getFechaOrder(Utils.getFechaDateWithHour(servicio.getFechaFin()))));
        holder.txtPatente.setText(servicio.getPatente());
        if (tipo == 1) holder.txtPatente.setVisibility(View.GONE);
        else holder.txtPatente.setVisibility(View.VISIBLE);
        String texto = "";
        int color = 0, image = 0;
        switch (servicio.getEstado()) {
            case 1:
                texto = "FINALIZADO";
                color = R.color.colorGreen;
                image = R.drawable.ic_chek;
                break;
            case 2:
                texto = "EN CURSO";
                color = R.color.colorOrange;
                image = R.drawable.ic_advertencia;
                break;
            case 3:
                texto = "ADVERTENCIA";
                image = R.drawable.ic_error;
                color = R.color.colorRed;
                break;
        }

        if (tipo == 1) {
            holder.txtEstado.setText(texto);
            holder.txtEstado.setTextColor(mContext.getResources().getColor(color));
            holder.txtEstado.setVisibility(View.VISIBLE);
        } else {
            holder.imgIcon.setVisibility(View.GONE);
            holder.txtEstado.setText(String.format("%s %s", servicio.getNombre(), servicio.getApellido()));
        }
        Glide.with(holder.imgIcon.getContext()).load(image).into(holder.imgIcon);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ServicioViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPatente, txtFechaInicio, txtFechaFin, txtEstado;
        ImageView imgIcon;

        public ServicioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtFechaFin = itemView.findViewById(R.id.txtFechaFin);
            txtFechaInicio = itemView.findViewById(R.id.txtFechaIni);
            txtPatente = itemView.findViewById(R.id.txtPatente);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
