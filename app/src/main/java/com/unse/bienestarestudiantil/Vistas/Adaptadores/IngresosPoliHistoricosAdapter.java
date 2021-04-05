package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.ItemDatoPileta;
import com.unse.bienestarestudiantil.Modelos.ItemFechaPileta;
import com.unse.bienestarestudiantil.Modelos.ItemListado;
import com.unse.bienestarestudiantil.R;

import java.util.List;

public class IngresosPoliHistoricosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ItemListado> lista;

    public IngresosPoliHistoricosAdapter(Context context, List<ItemListado> lista) {
        this.lista = lista;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ItemListado.TIPO_DATO:
                View view = inflater.inflate(R.layout.item_dato_pileta, viewGroup, false);
                viewHolder = new DateViewHolder(view);
                break;
            case ItemListado.TIPO_FECHA:
                View view2 = inflater.inflate(R.layout.item_fecha_pileta, viewGroup, false);
                viewHolder = new FechaViewHolder(view2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ItemListado.TIPO_FECHA:
                ItemFechaPileta itemFecha = (ItemFechaPileta) lista.get(position);
                FechaViewHolder fechaViewHolder = (FechaViewHolder) viewHolder;
                fechaViewHolder.txtFecha.setText(itemFecha.getFecha());
                break;

            case ItemListado.TIPO_DATO:
                ItemDatoPileta dateItem = (ItemDatoPileta) lista.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;
                dateViewHolder.txtDNI.setText(String.valueOf(dateItem.getPiletaIngreso().getDni()));
                dateViewHolder.txtCategoria.setText(getCategoria(dateItem.getPiletaIngreso().getCategoria() - 1));
                dateViewHolder.txtPrecio.setText(String.valueOf(dateItem.getPiletaIngreso().getPrecio1()));
                dateViewHolder.txtCantidad.setText(String.valueOf(dateItem.getPiletaIngreso().getCantidadTotal()));
                break;
        }
    }

    public String getCategoria(int i) {
        String[] categorias = {"Alumno", "Profesor", "Nodocente", "Egresado", "Particular",
                "Afiliado", "Jubilado", "Otro"};
        return categorias[i];
    }


    //ViewHolder for date row item
    class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDNI, txtPrecio, txtCategoria, txtCantidad;

        DateViewHolder(View v) {
            super(v);
            txtDNI = v.findViewById(R.id.txtDNI);
            txtCategoria = v.findViewById(R.id.txtCategoria);
            txtPrecio = v.findViewById(R.id.txtPrecio);
            txtCantidad = v.findViewById(R.id.txtCant);
        }
    }

    //View holder for general row item
    class FechaViewHolder extends RecyclerView.ViewHolder {
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
