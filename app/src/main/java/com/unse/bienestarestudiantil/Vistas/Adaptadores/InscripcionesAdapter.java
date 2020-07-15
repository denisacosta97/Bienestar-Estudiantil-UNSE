package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Inscripcion;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class InscripcionesAdapter extends RecyclerView.Adapter<InscripcionesAdapter.InscripcionViewHolder> {

    private Context mContext;
    private ArrayList<Inscripcion> mList;
    private ArrayList<Inscripcion> mListCopia;

    public InscripcionesAdapter(Context context, ArrayList<Inscripcion> inscripcions) {
        this.mContext = context;
        this.mList = inscripcions;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public InscripcionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inscripciones,
                viewGroup, false);

        return new InscripcionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InscripcionViewHolder holder, int position) {
        Inscripcion inscripcion = mList.get(position);

        holder.txtNumero.setText(String.format("# %s",inscripcion.getIdInscripcion()));
        holder.txtNombre.setText(inscripcion.getTitulo());
        holder.txtDni.setText(String.valueOf(inscripcion.getIdUsuario()));
        holder.txtEstado.setText(inscripcion.getNombreEstado());
        int i = R.color.colorTextDefault;
        switch (inscripcion.getIdEstado()) {
            case 1:
                i = R.color.colorGreen;
                break;
            case 2:
                i = R.color.colorRed;
                break;
            case 3:
                i = R.color.colorOrange;
                break;
        }
        holder.txtEstado.setTextColor(mContext.getResources().getColor(i));
        holder.txtEstado.setTypeface(null, Typeface.BOLD);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class InscripcionViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumero, txtNombre, txtDni, txtEstado;

        InscripcionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumero = itemView.findViewById(R.id.txtNroArchivo);
            txtNombre = itemView.findViewById(R.id.txtTitulo);
            txtDni = itemView.findViewById(R.id.txtDescripcion);
            txtEstado = itemView.findViewById(R.id.idEstado);
        }
    }

    public void setList(ArrayList<Inscripcion> list) {
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    public void filtrar(String txt, int tipo) {
        ArrayList<Inscripcion> result = new ArrayList<>();
        switch (tipo) {
            case Utils.LIST_RESET:
                mList.clear();
                mList.addAll(mListCopia);
                break;
            case Utils.LIST_DNI:
                for (Inscripcion item : mListCopia) {
                    String idUser = String.valueOf(item.getIdUsuario());
                    String idInscrip = String.valueOf(item.getIdInscripcion());
                    if (idUser.contains(txt) || idInscrip.contains(txt)) {
                        result.add(item);
                    }

                }
                mList.clear();
                mList.addAll(result);
                break;
            case Utils.LIST_NOMBRE:
                for (Inscripcion item : mListCopia) {
                    String nombre = String.format("%s", item.getTitulo());
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
}
