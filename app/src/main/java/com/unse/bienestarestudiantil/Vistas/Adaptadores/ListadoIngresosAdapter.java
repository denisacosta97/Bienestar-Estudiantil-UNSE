package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.PiletaIngreso;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ListadoIngresosAdapter extends RecyclerView.Adapter<ListadoIngresosAdapter.ListadoIngresosViewHolder> {

    private Context mContext;
    private ArrayList<PiletaIngreso> mList;
    private ArrayList<PiletaIngreso> mListCopia;

    public ListadoIngresosAdapter(Context context, ArrayList<PiletaIngreso> inscripcions) {
        this.mContext = context;
        this.mList = inscripcions;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ListadoIngresosAdapter.ListadoIngresosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dato_pileta,
                viewGroup, false);

        return new ListadoIngresosAdapter.ListadoIngresosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListadoIngresosViewHolder holder, int position) {
        PiletaIngreso piletaIngreso = mList.get(position);

        holder.txtDni.setText(String.valueOf(piletaIngreso.getDni()));
        holder.txtCant.setText(String.valueOf(piletaIngreso.getCantidadTotal()));
        holder.txtCat.setText(getCategoria(piletaIngreso.getCategoria()));

        holder.txtFecha.setText(piletaIngreso.getFecha());
        if (piletaIngreso.getPrecio1() != -1) {
            holder.txtPrecio.setText(String.valueOf(piletaIngreso.getPrecio1()));
        } else {
            holder.txtPrecio.setVisibility(View.GONE);
        }
    }

    private String getCategoria(int valueOf) {
        String cat = "";
        switch (valueOf) {
            case 1:
                cat = "Alumno";
                break;
            case 2:
                cat = "Profesor";
                break;
            case 3:
                cat = "Nodocente";
                break;
            case 4:
                cat = "Egresado";
                break;
            case 5:
                cat = "Particular";
                break;
            case 6:
                cat = "Afiliado";
                break;
            case 7:
                cat = "Jubilado";
                break;
            case 8:
                cat = "Otro";
                break;
        }
        return cat;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ListadoIngresosViewHolder extends RecyclerView.ViewHolder {

        TextView txtDni, txtCant, txtCat, txtPrecio, txtFecha;

        ListadoIngresosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCant = itemView.findViewById(R.id.txtCant);
            txtCat = itemView.findViewById(R.id.txtCategoria);
            txtDni = itemView.findViewById(R.id.txtDNI);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }

    public void setList(ArrayList<PiletaIngreso> list) {
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }


}
