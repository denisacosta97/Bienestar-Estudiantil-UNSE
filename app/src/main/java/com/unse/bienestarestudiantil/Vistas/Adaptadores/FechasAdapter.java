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
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.Modelos.TurnosUAPU;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class FechasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TIPO_INSCRIPCIONES = 1;
    public static final int TIPO_DEPORTES_INSCRIPCIONES = 2;
    public static final int TIPO_TURNO = 3;
    public static final int TIPO_INGRESO = 4;
    public static final int TIPO_IMPRE = 5;
    public static final int TIPO_ATENCION = 6;
    public static final int TIPO_ATENCION_2 = 7;
    public static final int TIPO_PUNTO_C = 8;

    private Context mContext;
    private ArrayList<ItemBase> lista;
    private int tipo;

    public FechasAdapter(Context context, ArrayList<ItemBase> lista, int tipo) {
        this.lista = lista;
        this.mContext = context;
        this.tipo = tipo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ItemBase.TIPO_DATO:
                //item_dato
                View view = inflater.inflate(R.layout.item_credenciales, viewGroup, false);
                viewHolder = new DateViewHolder(view);
                break;
            case ItemBase.TIPO_FECHA:
                View view2 = inflater.inflate(R.layout.item_fecha, viewGroup, false);
                viewHolder = new FechaViewHolder(view2);
                break;
        }

        return viewHolder;
    }

    public void change(ArrayList<ItemBase> e) {
        this.lista = e;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ItemBase.TIPO_FECHA:
                ItemFecha itemFecha = (ItemFecha) lista.get(position);
                FechaViewHolder fechaViewHolder = (FechaViewHolder) viewHolder;
                fechaViewHolder.txtFecha.setText(String.format("%s", itemFecha.getAnio()));
                break;

            case ItemBase.TIPO_DATO:
                ItemDato dateItem = (ItemDato) lista.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                if (tipo == TIPO_INSCRIPCIONES) {
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                    dateViewHolder.txtNroArchivo.setText(String.format("#%s", dateItem.getIdValue()));
                    if (dateItem.getEstadoValueId() != 0) {
                        dateViewHolder.txtEstado.setVisibility(View.VISIBLE);
                        dateViewHolder.txtEstado.setText(dateItem.getEstadoValue());
                        int i = R.color.colorTextDefault;
                        switch (dateItem.getEstadoValueId()) {
                            case 2:
                                i = R.color.colorGreen;
                                break;
                            case 3:
                                i = R.color.colorRed;
                                break;
                            case 1:
                                i = R.color.colorOrange;
                                break;
                        }
                        dateViewHolder.txtEstado.setTextColor(mContext.getResources().getColor(i));
                        dateViewHolder.txtEstado.setTypeface(null, Typeface.BOLD);

                    } else dateViewHolder.txtEstado.setVisibility(View.GONE);
                } else if (tipo == TIPO_DEPORTES_INSCRIPCIONES) {
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                } else if (tipo == TIPO_TURNO) {
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                } else if (tipo == TIPO_INGRESO) {
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                } else if (tipo == TIPO_IMPRE) {
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                }else if (tipo == TIPO_ATENCION){
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                }else if (tipo == TIPO_ATENCION_2){
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                }else if (tipo == TIPO_PUNTO_C){
                    dateViewHolder.txtNroArchivo.setVisibility(View.GONE);
                    dateViewHolder.txtEstado.setVisibility(View.GONE);
                    dateViewHolder.txtTitulo.setText(dateItem.getTextValue());
                }

                break;
        }

    }


    //ViewHolder for date row item
    static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitulo, txtNroArchivo, txtEstado;

        DateViewHolder(View v) {
            super(v);
            txtTitulo = v.findViewById(R.id.txtTitulo);
            txtNroArchivo = v.findViewById(R.id.txtId);
            txtEstado = v.findViewById(R.id.idEstado);

        }
    }

    //View holder for general row item
    static class FechaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFecha;

        FechaViewHolder(View v) {
            super(v);
            txtFecha = v.findViewById(R.id.txtFecha);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return lista.get(position).getTipo();
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }
}
