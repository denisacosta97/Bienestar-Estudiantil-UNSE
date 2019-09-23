package com.unse.bienestarestudiantil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.NewssViewHolder>{

    ArrayList<News> mNews;
    Context context;

    public NewsAdapter(ArrayList<News> list, Context context){
        this.mNews = list;
        this.context = context;
    }

    public NewsAdapter(){

    }

    @Override
    public NewssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);

        return new NewssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewssViewHolder holder, int position) {
        News news = mNews.get(position);
        holder.txtTitulo.setText(news.getTitulo());
        holder.txtDescripcion.setText(news.getCuerpo());
        holder.txtFecha.setText(news.getFechahora());
        Glide.with(holder.imgFoto.getContext()).load(news.getImg()).into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewssViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtFecha, counterLike, counterDislike;
        ImageView imgFoto, imgCompartir;
        ImageButton like, dislike;

        public NewssViewHolder(View itemView) {
            super(itemView);
            txtDescripcion = itemView.findViewById(R.id.txtCuerpo);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgFoto = itemView.findViewById(R.id.imgAlerta);

        }

    }

}
