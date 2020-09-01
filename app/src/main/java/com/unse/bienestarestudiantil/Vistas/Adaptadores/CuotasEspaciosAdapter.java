package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.CuotaEspacio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class CuotasEspaciosAdapter extends RecyclerView.Adapter<CuotasEspaciosAdapter.CuotaEspacioViewHolder> {
    private ArrayList<CuotaEspacio> mList;
    private ArrayList<CuotaEspacio> mListCopia;
    private Context context;

    public CuotasEspaciosAdapter(ArrayList<CuotaEspacio> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public CuotasEspaciosAdapter.CuotaEspacioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuota_espacios, parent, false);
        return new CuotasEspaciosAdapter.CuotaEspacioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuotasEspaciosAdapter.CuotaEspacioViewHolder holder, int position) {
        CuotaEspacio reserva = mList.get(position);

        holder.txtIdPagoCuota.setText(String.valueOf(reserva.getIdPago()));
        holder.txtFechaCuota.setText(reserva.getFechaPagoCuota());
        String precio = "$" + reserva.getCant();
        holder.txtPrecio.setText(precio);

    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdPagoCuota();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class CuotaEspacioViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdPagoCuota, txtFechaCuota, txtPrecio;

        public CuotaEspacioViewHolder(View itemView) {
            super(itemView);
            txtIdPagoCuota = itemView.findViewById(R.id.txtIdCuota);
            txtFechaCuota = itemView.findViewById(R.id.txtFechaCuota);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
        }

    }

    public void change(ArrayList<CuotaEspacio> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
