package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Modelos.Alumno;

import java.util.ArrayList;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.AlumnoViewHolder> {

    private ArrayList<Alumno> mList;
    private ArrayList<Alumno> mListCopia;
    private Context mContext;

    public AlumnosAdapter(ArrayList<Alumno> list, Context context) {
        this.mContext = context;
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);

    }

    @Override
    public int getItemViewType(int position) {
        return mList.size() == 0 ? 0 : 1;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == 0)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listado_vacio, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alumno, viewGroup, false);

        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int i) {

        if (mList.size() != 0) {
            Alumno alumno = mList.get(i);
            holder.txtNombre.setText(alumno.getNombreCompleto2());
            holder.txtDni.setText(alumno.getId());
            holder.txtLegajo.setText(alumno.getLegajo());
        }

    }

    public void filtrar(String txt, int tipo) {
        ArrayList<Alumno> result = new ArrayList<>();
        switch (tipo) {
            case Utils.LIST_RESET:
                mList.clear();
                mList.addAll(mListCopia);
                break;
            case Utils.LIST_DNI:
                for (Alumno item : mListCopia) {
                    if (item.getId().contains(txt)) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;
            case Utils.LIST_LEGAJO:
                if (txt.contains("-"))
                    txt = txt.replace("-","/");
                for (Alumno item : mListCopia) {
                    if (item.getLegajo().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;
            case Utils.LIST_NOMBRE:
                for (Alumno item : mListCopia) {
                    if (item.getNombreCompleto1().toLowerCase().contains(txt.toLowerCase())) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 1 : mList.size();
    }

    class AlumnoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtLegajo, txtDni;
        CardView mCardView;

        AlumnoViewHolder(View view) {
            super(view);

            mCardView = view.findViewById(R.id.card);
            txtNombre = view.findViewById(R.id.txtTitulo);
            txtDni = view.findViewById(R.id.txtDescripcion);
            txtLegajo = view.findViewById(R.id.txtLegajo);


        }

    }
}
