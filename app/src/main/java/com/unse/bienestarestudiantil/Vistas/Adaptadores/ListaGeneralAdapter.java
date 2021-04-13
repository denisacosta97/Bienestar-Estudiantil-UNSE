package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.Modelos.Doctor;
import com.unse.bienestarestudiantil.Modelos.Lista;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListaGeneralAdapter extends RecyclerView.Adapter<ListaGeneralAdapter.ListaViewHolder> {

    private ArrayList<Lista> lista;
    private Context mContext;
    public int tipo;

    public static final int DOCTOR = 1;
    public static final int HISTORIAL = 2;

    public ListaGeneralAdapter(ArrayList<Lista> lista, Context context, int tipo) {
        this.lista = lista;
        mContext = context;
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_general, parent, false);

        return new ListaGeneralAdapter.ListaViewHolder(view, tipo);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        if (tipo == ListaGeneralAdapter.DOCTOR) {
            Doctor doctor = (Doctor) lista.get(position);

            holder.txtNombre.setText(String.format("%s %s", doctor.getNombre(), doctor.getApellido()));
            holder.txtDni.setText(String.valueOf(doctor.getIdUsuario()));
        } else if (tipo == ListaGeneralAdapter.HISTORIAL) {
            Consulta consulta = (Consulta) lista.get(position);
            holder.imgIcon.setVisibility(View.GONE);
            holder.txtNombre.setText(consulta.getConsulta());
            Date date = Utils.getFechaDateWithHour(consulta.getFecha());
            if (date != null){
                String fecha = Utils.getFechaOrderOnly(date);
                String hora = Utils.getHora(date);
                holder.txtDni.setText(String.format("%s - %s", fecha, hora));
            }else{
                holder.txtDni.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDni;
        CardView mCardView;
        ImageView imgIcon;

        ListaViewHolder(View view, int tipo) {
            super(view);

            if (ListaGeneralAdapter.DOCTOR == tipo || ListaGeneralAdapter.HISTORIAL == tipo) {
                mCardView = view.findViewById(R.id.card);
                txtNombre = view.findViewById(R.id.txtNombre);
                txtDni = view.findViewById(R.id.txtDescripcion);
                imgIcon = view.findViewById(R.id.imgIcon);
            }
        }
    }
}
