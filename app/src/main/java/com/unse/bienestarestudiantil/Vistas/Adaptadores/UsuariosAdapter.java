package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.AlumnoViewHolder> {

    private ArrayList<Usuario> mList;
    private ArrayList<Usuario> mListCopia;
    private Context mContext;
    private int tipo;

    public UsuariosAdapter(ArrayList<Usuario> list, Context context, int type) {
        this.mContext = context;
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        this.tipo = type;

    }

    @Override
    public int getItemViewType(int position) {
        return mList.size() == 0 ? 0 : 1;
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdUsuario();
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == 0)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listado_vacio, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_usuario, viewGroup, false);

        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int i) {
        if (mList.size() != 0) {

            Usuario alumno = mList.get(i);

            if (tipo == Utils.TIPO_CHOFER){
                holder.latFotos.setVisibility(GONE);
                holder.txtNombre.setText(String.format("%s %s", alumno.getNombre(), alumno.getApellido()));
                holder.txtDni.setText(String.format("%s", alumno.getIdUsuario()));
                holder.txtRol.setVisibility(View.VISIBLE);
                holder.txtRol.setTypeface(null, Typeface.BOLD);
                holder.txtRol.setText(String.format("%s", alumno.getValidez() == 1 ? "ACTIVO" : "INACTIVO"));
                holder.txtRol.setTextColor(mContext.getResources().getColor(alumno.getValidez() == 1 ? R.color.colorGreen: R.color.colorRed));

            }
            else if (tipo == Utils.TIPO_SOCIO){
                holder.latFotos.setVisibility(GONE);
                holder.txtNombre.setText(String.format("%s %s", alumno.getNombre(), alumno.getApellido()));
                holder.txtDni.setText(String.format("%s", alumno.getIdUsuario()));
                holder.txtRol.setVisibility(View.VISIBLE);
                holder.txtRol.setTypeface(null, Typeface.BOLD);
                holder.txtRol.setText(String.format("%s", alumno.getValidez() == 1 ? "ACTIVO" : "INACTIVO"));
                holder.txtRol.setTextColor(mContext.getResources().getColor(alumno.getValidez() == 1 ? R.color.colorGreen: R.color.colorRed));
            }
            else if (tipo == Utils.TIPO_ROLES){
                holder.latFotos.setVisibility(GONE);
                holder.txtNombre.setText(String.format("%s %s", alumno.getNombre(), alumno.getApellido()));
                holder.txtDni.setText(String.format("%s", alumno.getIdUsuario()));
                holder.txtRol.setVisibility(View.VISIBLE);
                holder.txtRol.setText(String.format("Cant. Roles %s", alumno.getValidez()));

            }else {
                holder.txtNombre.setText(String.format("%s %s", alumno.getNombre(), alumno.getApellido()));
                holder.txtDni.setText(String.format("%s", alumno.getIdUsuario()));
                holder.txtTipo.setText(Utils.getTipoUser(alumno.getTipoUsuario()));
                switch (tipo) {
                    case Utils.TIPO_USUARIO:
                        holder.txtRol.setVisibility(GONE);
                        break;
                }
                int icon = 0;
                switch (alumno.getTipoUsuario()) {
                    case 1:
                        icon = R.drawable.ic_tipo_usuario;
                        break;
                    case 2:
                        icon = R.drawable.ic_tipo_prof;
                        break;
                    case 3:
                        icon = R.drawable.ic_tipo_nodoc;
                        break;
                    case 4:
                        icon = R.drawable.ic_tipo_egresado;
                        break;
                    case 5:
                        icon = R.drawable.ic_tipo_particular;
                        break;
                }
                Glide.with(holder.imgIconRol.getContext()).load(icon).into(holder.imgIconRol);
            }

        }

    }

    public void setList(ArrayList<Usuario> list) {
        this.mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
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
            case Utils.LIST_LEGAJO:
                if (tipo == Utils.TIPO_ESTUDIANTE) {
                    if (txt.contains("-"))
                        txt = txt.replace("-", "/");
                    for (Usuario item : mListCopia) {
                        if (((Alumno) item).getLegajo().toLowerCase().contains(txt.toLowerCase())) {
                            result.add(item);
                        }

                    }
                }
                mList.clear();
                mList.addAll(result);
                break;
            case Utils.LIST_NOMBRE:
                for (Usuario item : mListCopia) {
                    String nombre = String.format("%s %s ", item.getNombre(), item.getApellido());
                    if (nombre.trim().toLowerCase().contains(txt.toLowerCase())) {
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
        return mList.size() == 0 ? 1 : mList.size();
    }

    static class AlumnoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtRol, txtDni, txtTipo;
        CardView mCardView;
        LinearLayout latFotos;
        ImageView imgIconRol;

        AlumnoViewHolder(View view) {
            super(view);

            mCardView = view.findViewById(R.id.card);
            txtNombre = view.findViewById(R.id.txtTitulo);
            txtDni = view.findViewById(R.id.txtDescripcion);
            txtRol = view.findViewById(R.id.txtRol);
            txtTipo = view.findViewById(R.id.txtRolName);
            imgIconRol = view.findViewById(R.id.imgIconRol);
            latFotos = view.findViewById(R.id.latFoto);


        }

    }
}
