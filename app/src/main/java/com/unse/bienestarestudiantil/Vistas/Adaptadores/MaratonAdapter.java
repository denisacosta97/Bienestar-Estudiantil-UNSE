package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Maraton;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class MaratonAdapter extends RecyclerView.Adapter<MaratonAdapter.EventosViewHolder> {

    private ArrayList<Maraton> mList;
    private ArrayList<Maraton> mListCopia;
    private Context context;
    View view;
    OnClickOptionListener listener;

    public MaratonAdapter(ArrayList<Maraton> list, Context ctx, OnClickOptionListener listener) {
        this.mList = list;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MaratonAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maraton, parent, false);
        return new MaratonAdapter.EventosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MaratonAdapter.EventosViewHolder holder, final int position) {
        Maraton mar = mList.get(position);

        holder.txtId.setText(String.format("# %s", mar.getIdInscripcion()));
        holder.mDni.setText(String.valueOf(mar.getIdUsuario()));
        String name = mar.getNombre() + " " + mar.getApellido();
        holder.txtNomAp.setText(name);
        int edad = Utils.getEdad(Utils.getFechaDate(mar.getFechaNac()));
        holder.txtEdad.setText(String.valueOf(edad));
        holder.txtCarrera.setText(String.format("%s KM", mar.getCarrera()));

    }


    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdInscripcion();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mDni, txtEdad, txtNomAp, txtCarrera, txtId;

        EventosViewHolder(final View itemView, final OnClickOptionListener listener) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            mDni = itemView.findViewById(R.id.txtDni);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtCarrera = itemView.findViewById(R.id.txtCarrera);
            txtEdad = itemView.findViewById(R.id.txtEdad);

        }

    }

    public void filtrar(String txt, int tipo) {
        ArrayList<Maraton> result = new ArrayList<>();
        switch (tipo) {
            case Utils.LIST_RESET:
                mList.clear();
                mList.addAll(mListCopia);
                break;
            case Utils.LIST_DNI:
                for (Maraton item : mListCopia) {
                    if (String.valueOf(item.getIdUsuario()).contains(txt)) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;

            case Utils.LIST_NOMBRE:
                for (Maraton item : mListCopia) {
                    String nombre = String.format("%s %s ", item.getNombre(), item.getApellido());
                    if (nombre.trim().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;
        }
        notifyDataSetChanged();
    }

    public void setList(ArrayList<Maraton> list) {
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

}
