package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itextpdf.kernel.geom.Line;
import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class CategoriasAdapter  extends RecyclerView.Adapter<CategoriasAdapter.EventosViewHolder> {

    private ArrayList<Categoria> arrayList;
    private Context context;

    public CategoriasAdapter(ArrayList<Categoria> list, Context ctx) {
        context = ctx;
        arrayList = list;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {

        Categoria s = arrayList.get(position);

        if (s.isEstado()){
            holder.mLayout.setBackground(context.getResources().getDrawable(R.drawable.button_border_select));
        }else{
            holder.mLayout.setBackground(context.getResources().getDrawable(R.drawable.button_border));
        }
        holder.txtTitulo.setText(s.getNombre());

    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getIdCategoria();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        LinearLayout mLayout;

        EventosViewHolder(View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.back);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);


        }
    }
}
