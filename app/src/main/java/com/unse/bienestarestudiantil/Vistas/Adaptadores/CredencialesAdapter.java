package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class CredencialesAdapter extends RecyclerView.Adapter<CredencialesAdapter.CredencialViewHolder> {

    private ArrayList<Credencial> mArchivos;
    private Context context;
    private boolean isInscripcion = false;
    private OnClickListenerAdapter mOnClickListenerAdapter;

    public OnClickListenerAdapter getOnClickListenerAdapter() {
        return mOnClickListenerAdapter;
    }

    public void setOnClickListenerAdapter(OnClickListenerAdapter onClickListenerAdapter) {
        mOnClickListenerAdapter = onClickListenerAdapter;
    }

    public CredencialesAdapter(ArrayList<Credencial> list, Context ctx, boolean isInscripcion) {
        this.mArchivos = list;
        this.context = ctx;
        this.isInscripcion = isInscripcion;
    }

    @NonNull
    @Override
    public CredencialesAdapter.CredencialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (mArchivos.size() == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacio, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credenciales, parent, false);

        return new CredencialesAdapter.CredencialViewHolder(view, mOnClickListenerAdapter);
    }

    public void setList(ArrayList<Credencial> credencials){
        this.mArchivos = credencials;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final CredencialesAdapter.CredencialViewHolder holder, int position) {

        if (mArchivos.size() != 0) {
            Credencial credencial = mArchivos.get(position);
            if (!isInscripcion) {
                holder.mNumArchivo.setText(String.valueOf(position + 1));
                holder.txtTitulo.setText(credencial.getTitulo());
                holder.txtEstado.setVisibility(GONE);
                if (credencial.getValidez() == 0) {
                    holder.mLayout.setEnabled(false);
                    holder.txtTitulo.setEnabled(false);
                    holder.mPdf.setEnabled(false);
                    holder.mNumArchivo.setEnabled(false);
                } else {
                    holder.mLayout.setEnabled(true);
                }
            } else {
                holder.txtEstado.setVisibility(GONE);
                holder.mSwitch.setVisibility(View.VISIBLE);
                holder.mNumArchivo.setVisibility(GONE);
                holder.txtTitulo.setText(credencial.getTitulo());
                if (credencial.getValidez() == 0) {
                    Glide.with(holder.mSwitch.getContext()).load(R.drawable.ic_error).into(holder.mSwitch);
                    //holder.mSwitch.setBackground(context.getResources().getDrawable(R.drawable.ic_error));
                    //holder.txtEstado.setText("DESACTIVADO");
                } else {
                    Glide.with(holder.mSwitch.getContext()).load(R.drawable.ic_chek).into(holder.mSwitch);
                    //holder.mSwitch.setBackground(context.getResources().getDrawable(R.drawable.ic_chek));
                    //holder.txtEstado.setText("ACTIVADO");
                }
            }
        }else{
            holder.txtTitulo.setText("CREDENCIALES NO ASIGNADAS");
        }


    }

    @Override
    public long getItemId(int position) {
        return mArchivos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mArchivos.size() == 0 ? 1 : mArchivos.size();
    }

    static class CredencialViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo, mNumArchivo, txtEstado;
        LinearLayout mPdf;
        LinearLayout mLayout;
        ImageView mSwitch;
        OnClickListenerAdapter listener;

        CredencialViewHolder(View itemView, final OnClickListenerAdapter listene) {
            super(itemView);

            mNumArchivo = itemView.findViewById(R.id.txtNroArchivo);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mPdf = itemView.findViewById(R.id.imgIcon);
            txtEstado = itemView.findViewById(R.id.idEstado);
            mLayout = itemView.findViewById(R.id.layout);
            mSwitch = itemView.findViewById(R.id.switchCred);

            this.listener = listene;

           /* if (mSwitch != null){
                mSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null){
                            listener.onClick(getAdapterPosition());
                        }
                    }
                });
            }*/


        }
    }

}
