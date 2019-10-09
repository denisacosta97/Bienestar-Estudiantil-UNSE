package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticias;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class NoticiasAdapter extends RecyclerView.Adapter <NoticiasAdapter.NoticiasViewHolder>{

    ArrayList<Noticias> mNoticias, mNoticiasCopia;
    Context context;

    public NoticiasAdapter(ArrayList<Noticias> list, Context context){
        this.mNoticias = list;
        this.context = context;
        this.mNoticiasCopia = new ArrayList<>();
        this.mNoticiasCopia.addAll(mNoticias);
    }



    @Override
    public NoticiasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);

        return new NoticiasViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return mNoticias.get(position).getIdTipoNoticias();
    }

    @Override
    public void onBindViewHolder(final NoticiasViewHolder holder, int position) {
        Noticias noticias = mNoticias.get(position);
        holder.txtTitulo.setText(noticias.getTitulo());
        holder.txtDescripcion.setText(noticias.getCuerpo());
        holder.txtFecha.setText(noticias.getFechahora());
        Glide.with(holder.imgFoto.getContext()).load(noticias.getUrlImagen()).into(holder.imgFoto);
        if (noticias.isButton()){
            holder.btnFuncion.setVisibility(View.VISIBLE);
        }else{
            holder.btnFuncion.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return mNoticias.get(position).getId();
    }

    public void filtrarNoticias(int tipo) {
        if(tipo == -1){
            mNoticias.clear();
            mNoticias.addAll(mNoticiasCopia);
        } else{
            ArrayList<Noticias> result = new ArrayList<>();
            for(Noticias item: mNoticiasCopia){
                if (item.getIdCategoria() == tipo){
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
        TextView txtTitulo, txtDescripcion, txtFecha;
        ImageView imgFoto;
        Button btnFuncion;

        public NoticiasViewHolder(final View itemView) {
            super(itemView);
            txtDescripcion = itemView.findViewById(R.id.txtCuerpo);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgFoto = itemView.findViewById(R.id.imgAlerta);
            btnFuncion = itemView.findViewById(R.id.btnFuncion);
            btnFuncion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getItemViewType()){
                        case Utils.NOTICIA_BUTTON_WEB:
                            showToast("Abre una pagina web");
                            break;
                        case Utils.NOTICIA_BUTTON_APP:
                            showToast("Abre una app determinada");
                            break;
                        case Utils.NOTICIA_BUTTON_TIENDA:
                            showToast("Abre la tienda");
                            break;
                    }
                }

                private void showToast(String x) {
                    Toast.makeText(context, x, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}
