package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ArchivosAdapter extends RecyclerView.Adapter<ArchivosAdapter.EventosViewHolder>{

    private ArrayList<Archivo> mArchivos, mArchivosCopia;
    private Context context;

    public ArchivosAdapter(ArrayList<Archivo> list, Context ctx) {
        this.mArchivos = list;
        this.context = ctx;
        this.mArchivosCopia = new ArrayList<>();
        this.mArchivosCopia.addAll(mArchivos);
    }

    public ArrayList<Archivo> getArchivosCopia() {
        return mArchivosCopia;
    }

    public void setArchivosCopia(ArrayList<Archivo> archivosCopia) {
        mArchivosCopia.addAll(archivosCopia);
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestion_archivos, parent, false);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventosViewHolder holder, int position) {
        Archivo archivo = mArchivos.get(position);

        holder.mFecha.setText(archivo.getFechaModificacion().substring(0, 10));
        holder.mNombreArchivo.setText(archivo.getNombre());
        String ext = Utils.getExtension(archivo.getNombreArchivo());
        holder.txtExtension.setText(ext.length() != 0 ? ext.toUpperCase() : ext);
        holder.txtExtension.setBackgroundColor(context.getResources().getColor(Utils.getColorExtension(ext)));



    }

    public void filtrar(int tipo) {
        if (tipo == -1) {
            mArchivos.clear();
            mArchivos.addAll(mArchivosCopia);
        } else {
            ArrayList<Archivo> result = new ArrayList<>();
            for (Archivo item : mArchivosCopia) {
                if (item.getIdArea() == tipo) {
                    result.add(item);
                }

            }
            mArchivos.clear();
            mArchivos.addAll(result);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return mArchivos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mArchivos.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mNombreArchivo, mFecha, txtExtension;

        EventosViewHolder(View itemView) {
            super(itemView);

            mNombreArchivo = itemView.findViewById(R.id.txtTitulo);
            mFecha = itemView.findViewById(R.id.txtFecha);
            txtExtension = itemView.findViewById(R.id.txtExtension);

        }
    }

}
