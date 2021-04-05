package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.Doctor;
import com.unse.bienestarestudiantil.Modelos.ServiciosU;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ServiciosUPAAdapter extends RecyclerView.Adapter<ServiciosUPAAdapter.EventosViewHolder> {

    private ArrayList<ServiciosU> serviciosUPA;
    private ArrayList<Doctor> mDoctors;
    private Context context;
    View view;

    public ServiciosUPAAdapter(ArrayList<ServiciosU> list, ArrayList<Doctor> doc, Context ctx) {
        this.serviciosUPA = list;
        this.mDoctors = doc;
        this.context = ctx;
    }

    @NonNull
    @Override
    public ServiciosUPAAdapter.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio_upa, parent, false);

        return new ServiciosUPAAdapter.EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosUPAAdapter.EventosViewHolder holder, int position) {
        ServiciosU upa = serviciosUPA.get(position);
        try {
            Doctor doctor = mDoctors.get(position);
            String name = doctor.getNombre() + " " + doctor.getApellido();
            holder.mDesc.setText(name);
        } catch (IndexOutOfBoundsException e) {

        }

        holder.mName.setText(upa.getTitulo());

    }

    @Override
    public long getItemId(int position) {
        return serviciosUPA.get(position).getIdServicio();
    }

    @Override
    public int getItemCount() {
        return serviciosUPA.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mDesc;

        EventosViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txtName);
            mDesc = itemView.findViewById(R.id.txtDesc);
        }
    }

}
