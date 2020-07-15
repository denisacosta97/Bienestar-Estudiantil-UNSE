package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class UsuariosAdapters extends RecyclerView.Adapter<UsuariosAdapters.UsuariosViewHolder> {

    private ArrayList<Usuario> mList;
    private ArrayList<Usuario> mListCopia;
    private Context context;

    public UsuariosAdapters(ArrayList<Usuario> list, Context ctx) {
        context = ctx;
        mList = list;

    }


    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdUsuario();
    }

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios, parent, false);


        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder holder, int position) {

        Usuario user = mList.get(position);

        holder.txtTitulo.setText(String.format("%s\n%s", user.getApellido(), user.getNombre()));
        holder.txtDescripcion.setText(String.format("%s", user.getIdUsuario()));

        Glide.with(holder.imgIconoFoto.getContext())
                .load(R.drawable.ic_foto_usuario)
                .into(holder.imgIconoFoto);

        Glide.with(holder.imgIconoRol.getContext())
                .load(R.drawable.ic_estudiante)
                .into(holder.imgIconoRol);
    }

    public void filtrar(String txt, int tipo) {
        ArrayList<Usuario> result = new ArrayList<>();
        switch (tipo) {
            case Utils.LIST_RESET:
                mList.clear();
                mList.addAll(mListCopia);
                break;
            case Utils.LIST_DNI:
                for (Usuario item : mListCopia) {
                    if (String.valueOf(item.getIdUsuario()).contains(txt)) {
                        result.add(item);
                    }
                }
                mList.clear();
                mList.addAll(result);
                break;

            case Utils.LIST_NOMBRE:
                for (Usuario item : mListCopia) {
                    String nombre = String.format("%s %s", item.getNombre(), item.getApellido());
                    if (nombre.toLowerCase().contains(txt.toLowerCase())) {
                        result.add(item);
                    }
                }
                mList.clear();
                mList.addAll(result);
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
