package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    ArrayList<Noticia> mNoticias, mNoticiaCopia;
    Context context;
    int tipo;

    public NoticiasAdapter(ArrayList<Noticia> list, Context context, int tipo) {
        this.mNoticias = list;
        this.context = context;
        this.mNoticiaCopia = new ArrayList<>();
        this.mNoticiaCopia.addAll(mNoticias);
        this.tipo = tipo;
    }

    public void filtrarNoticias(int tipo) {
        if (tipo == -1) {
            mNoticias.clear();
            mNoticias.addAll(mNoticiaCopia);
        } else {
            ArrayList<Noticia> result = new ArrayList<>();
            for (Noticia item : mNoticiaCopia) {
                //if (item.getIdCategoria() == tipo) {
                //  result.add(item);
                //}

            }
            mNoticias.clear();
            mNoticias.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (tipo == Noticia.TIPO_ADMIN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia_admin, parent, false);

        } else {
            if (viewType == 1)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia_left, parent, false);
            else
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias_right, parent, false);

            Utils.setFont(context, (ViewGroup) view, Utils.MONSERRAT);
        }

        return new NoticiasViewHolder(view, tipo);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    public void setList(ArrayList<Noticia> list) {
        this.mNoticias = list;
        this.mNoticiaCopia = new ArrayList<>();
        this.mNoticiaCopia.addAll(mNoticias);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final NoticiasViewHolder holder, int position) {
        Noticia noticia = mNoticias.get(position);
        holder.txtTitulo.setText(noticia.getTitulo());
        holder.txtFecha.setText(Utils.getBirthday(
                Utils.getFechaDateWithHour(noticia.getFechaRegistro())

        ));
        holder.etiqueta.setText(noticia.getNombreArea());
        if (tipo == Noticia.TIPO_ADMIN) {
            holder.txtDescripcion.setText(noticia.getDescripcion().length() > 50 ?
                    noticia.getDescripcion().substring(0, 49) : noticia.getDescripcion());
            holder.txtEstado.setText(noticia.getValidez() == 1 ? "ACTIVO" : "NO ACTIVO");
            holder.txtEstado.setTextColor(context.getResources().getColor(noticia.getValidez() == 1
                    ? R.color.colorGreen : R.color.colorRed));
        } else {

            holder.txtDescripcion.setText(noticia.getSubtitlo().length() > 50 ?
                    noticia.getSubtitlo().substring(0, 49) : noticia.getSubtitlo());
            String URL = String.format(Utils.URL_IMAGE_NOTICIA, noticia.getImagen());
            Glide.with(holder.imgFoto.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .load(URL)
                    .into(holder.imgFoto);
        }


    }

    @Override
    public long getItemId(int position) {
        return mNoticias.get(position).getIdNoticia();
    }


    @Override
    public int getItemCount() {
        return mNoticias.size();
    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtFecha, etiqueta, txtEstado;
        ImageView imgFoto;

        public NoticiasViewHolder(final View itemView, int tipo) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgFoto = itemView.findViewById(R.id.imgAlerta);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            if (tipo == Noticia.TIPO_ADMIN) {
                etiqueta = itemView.findViewById(R.id.txtArea);
                txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            } else {
                txtDescripcion = itemView.findViewById(R.id.txtCuerpo);
                etiqueta = itemView.findViewById(R.id.txtEtiqueta);
            }

        }

    }

}
