package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    ArrayList<Noticia> mNoticias, mNoticiaCopia;
    Context context;

    public NoticiasAdapter(ArrayList<Noticia> list, Context context) {
        this.mNoticias = list;
        this.context = context;
        this.mNoticiaCopia = new ArrayList<>();
        this.mNoticiaCopia.addAll(mNoticias);
    }


    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia_left, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias_right, parent, false);

        Utils.setFont(context, (ViewGroup) view, Utils.MONSERRAT);

        return new NoticiasViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(final NoticiasViewHolder holder, int position) {
        Noticia noticia = mNoticias.get(position);
        holder.txtTitulo.setText(noticia.getTitulo());
        if (noticia.getCuerpo().length() > 100) {
            holder.txtDescripcion.setText(noticia.getCuerpo().substring(0, 100));
        } else
            holder.txtDescripcion.setText(noticia.getCuerpo());
        holder.txtFecha.setText(noticia.getFechahora());
        holder.etiqueta.setText(noticia.getEtiqueta());
        Glide.with(holder.imgFoto.getContext()).load(noticia.getUrlImagen()).into(holder.imgFoto);
//        if (noticia.isButton()){
//            holder.btnFuncion.setVisibility(View.VISIBLE);
//        }else{
//            holder.btnFuncion.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public long getItemId(int position) {
        return mNoticias.get(position).getId();
    }

    public void filtrarNoticias(int tipo) {
        if (tipo == -1) {
            mNoticias.clear();
            mNoticias.addAll(mNoticiaCopia);
        } else {
            ArrayList<Noticia> result = new ArrayList<>();
            for (Noticia item : mNoticiaCopia) {
                if (item.getIdCategoria() == tipo) {
                    result.add(item);
                }

            }
            mNoticias.clear();
            mNoticias.addAll(result);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNoticias.size();
    }

    public class NoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtFecha, etiqueta;
        ImageView imgFoto;
        Button btnFuncion;

        public NoticiasViewHolder(final View itemView) {
            super(itemView);

            txtDescripcion = itemView.findViewById(R.id.txtCuerpo);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgFoto = itemView.findViewById(R.id.imgAlerta);
            etiqueta = itemView.findViewById(R.id.txtEtiqueta);
////            btnFuncion = itemView.findViewById(R.id.btnFuncion);
////            btnFuncion.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    switch (getItemViewType()){
////                        case Utils.NOTICIA_BUTTON_WEB:
////                            showToast("Abre una pagina web");
////                            break;
////                        case Utils.NOTICIA_BUTTON_APP:
////                            showToast("Abre una app determinada");
////                            break;
////                        case Utils.NOTICIA_BUTTON_TIENDA:
////                            showToast("Abre la tienda");
////                            break;
////                    }
////                }
//
//                private void showToast(String x) {
//                    Toast.makeText(context, x, Toast.LENGTH_SHORT).show();
//                }
//            });

        }

    }

}
