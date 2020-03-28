package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class GestionArchivosAdapter extends RecyclerView.Adapter<GestionArchivosAdapter.EventosViewHolder>{

    private ArrayList<Archivo> mArchivos, mArchivosCopia;
    private Context context;

    public GestionArchivosAdapter(ArrayList<Archivo> list, Context ctx) {
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

        holder.mNumArchivo.setText(String.valueOf(archivo.getId()));
        holder.mNombreArchivo.setText(archivo.getNombre());
        //holder.mFecha.setText(archivo.getFecha());

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

        TextView mNumArchivo, mNombreArchivo, mFecha;
        ImageView mPdf;

        EventosViewHolder(View itemView) {
            super(itemView);

            mNumArchivo = itemView.findViewById(R.id.txtNroArchivo);
            mNombreArchivo = itemView.findViewById(R.id.txtNameArchivo);
            mFecha = itemView.findViewById(R.id.txtFecha);
            mPdf = itemView.findViewById(R.id.imgIconPdf);

        }
    }

}
