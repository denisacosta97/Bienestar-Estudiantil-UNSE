package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.IngresoCiber;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.Calendar;

public class IngresosAdapter extends RecyclerView.Adapter<IngresosAdapter.IngresosViewHolder> {

    private ArrayList<IngresoCiber> mList;
    private ArrayList<IngresoCiber> mListCopia;
    private Context context;

    public IngresosAdapter(ArrayList<IngresoCiber> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public IngresosAdapter.IngresosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingresos, parent, false);
        return new IngresosAdapter.IngresosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngresosAdapter.IngresosViewHolder holder, int position) {
        IngresoCiber ingr = mList.get(position);

        holder.txtdni.setText(String.valueOf(ingr.getDni()));
        holder.txtNombre.setText(ingr.getNombre());
        holder.txtApellido.setText(ingr.getApellido());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Utils.getFechaDateWithHour(ingr.getFecha()));
        String fechaHora = String.format("%02d/%02d/%s - %02d:%02d", calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        holder.txtFechaHora.setText(fechaHora);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getDni();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class IngresosViewHolder extends RecyclerView.ViewHolder {

        TextView txtdni, txtNombre, txtApellido, txtFechaHora;

        public IngresosViewHolder(View itemView) {
            super(itemView);
            txtdni = itemView.findViewById(R.id.txtDni);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtApellido = itemView.findViewById(R.id.txtApellido);
            txtFechaHora = itemView.findViewById(R.id.txtFechaHora);
        }

    }

    public void change(ArrayList<IngresoCiber> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }

}
