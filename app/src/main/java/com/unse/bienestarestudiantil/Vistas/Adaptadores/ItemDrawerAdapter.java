package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.ItemDrawer;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ItemDrawerAdapter extends RecyclerView.Adapter<ItemDrawerAdapter.ItemViewHolder> {

    private ArrayList<ItemDrawer> mList;
    private Context mContext;

    public ItemDrawerAdapter(ArrayList<ItemDrawer> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawer, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {

        ItemDrawer itemDrawer = mList.get(i);

        if (itemDrawer.isSelect()) {
            Utils.changeColor(holder.mLayout.getBackground(), mContext, R.color.colorRedSelect);
            holder.txtTitulo.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        }
        else {
            holder.txtTitulo.setTextColor(mContext.getResources().getColor(R.color.colorTextDefault));
            Utils.changeColor(holder.mLayout.getBackground(), mContext, R.color.transparente);
        }
        Glide.with(holder.imgIcono.getContext()).load(itemDrawer.getIcono()).into(holder.imgIcono);
        holder.txtTitulo.setText(itemDrawer.getTitulo());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgIcono;
        private TextView txtTitulo;
        private LinearLayout mLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcono = itemView.findViewById(R.id.imgIcon);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mLayout = itemView.findViewById(R.id.latDatos);
        }
    }
}
