package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class UsuariosAdapter  extends RecyclerView.Adapter<UsuariosAdapter.UsuariosViewHolder> {

    private ArrayList<Usuario> arrayList;
    private Context context;

    public UsuariosAdapter(ArrayList<Usuario> list, Context ctx) {
        context = ctx;
        arrayList = list;

    }


    @Override
    public long getItemId(int position) {
        return Integer.parseInt(arrayList.get(position).getId());
    }

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios, parent, false);


        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder holder, int position) {

        Usuario user = arrayList.get(position);

        holder.txtTitulo.setText(String.format("%s\n%s", user.getApellido(), user.getNombre()));
        holder.txtDescripcion.setText(user.getId());

        Glide.with(holder.imgIconoFoto.getContext())
                .load(R.drawable.ic_foto_usuario)
                .into(holder.imgIconoFoto);

        Glide.with(holder.imgIconoRol.getContext())
                .load(R.drawable.ic_estudiante)
                .into(holder.imgIconoRol);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class UsuariosViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, txtDescripcion;
        ImageView imgIconoFoto, imgIconoRol;
        CardView mCardView;

        UsuariosViewHolder(View itemView) {
            super(itemView);

            imgIconoRol = itemView.findViewById(R.id.imgIconRol);
            imgIconoFoto = itemView.findViewById(R.id.imgIcon);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mCardView = itemView.findViewById(R.id.card);


        }
    }

}
