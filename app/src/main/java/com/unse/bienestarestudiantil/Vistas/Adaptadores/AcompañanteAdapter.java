package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Interfaces.ObservadorPrecio;
import com.unse.bienestarestudiantil.Modelos.PiletaAcompañante;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;


public class AcompañanteAdapter extends RecyclerView.Adapter<AcompañanteAdapter.AcompañanteViewHolder> {

    ArrayList<PiletaAcompañante> mList;
    Context mContext;
    ObservadorPrecio mObservadorPrecio;

    /*String[] categorias = {"Afiliado1", "Docente", "Egresado", "Estudiante", "Jubilado",
            "Nodocente", "Particular", "Invitado", "Menor (10 años)"};*/

    String[] categorias = {"Alumno", "Profesor", "Nodocente", "Egresado", "Particular", "Afiliado",
            "Jubilado", "Otro"};

    public AcompañanteAdapter(ArrayList<PiletaAcompañante> list, Context context, ObservadorPrecio precio) {
        mList = list;
        mContext = context;
        mObservadorPrecio = precio;
    }

    @NonNull
    @Override
    public AcompañanteAdapter.AcompañanteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ingreso_poli, viewGroup, false);

        return new AcompañanteViewHolder(view, mObservadorPrecio);
    }

    @Override
    public void onBindViewHolder(@NonNull AcompañanteAdapter.AcompañanteViewHolder holder, int position) {

        PiletaAcompañante acompanante = mList.get(position);

        holder.txtPrecio.setText(String.format("$ %s", acompanante.getPrecioTotal()));
        holder.txtCantidad.setText(String.valueOf(acompanante.getCantidad()));
        holder.txtPop.setText(String.valueOf(categorias[acompanante.getCategoria()]));
        //holder.mSpinner.setSelection(acompanante.getCategoria());

    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int categoriaGral;

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getCategoriaGral(){
        return categoriaGral;
    }

    public void setCategoriaGral(int i) {
        this.categoriaGral = i;
    }

    class AcompañanteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener {

        ImageButton btnMas, btnMenos;
       // Spinner mSpinner;
        TextView txtPrecio, txtCantidad, txtPop;
        ObservadorPrecio mObservadorPrecio;
        //ArrayAdapter<String> dataAdapter2;

        AcompañanteViewHolder(View view, ObservadorPrecio observadorPrecio) {
            super(view);
            btnMas = view.findViewById(R.id.btnAddMay);
            btnMenos = view.findViewById(R.id.btnRemoveMay);
            txtPrecio = view.findViewById(R.id.txtPrecio);
            //mSpinner = view.findViewById(R.id.spineer);
            txtCantidad = view.findViewById(R.id.txtCantidad);
            txtPop = view.findViewById(R.id.txtPop);
            txtPop.setOnCreateContextMenuListener(this);
            txtPop.setOnClickListener(this);
            this.mObservadorPrecio = observadorPrecio;

            btnMenos.setOnClickListener(this);
            btnMas.setOnClickListener(this);
            //dataAdapter2 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categorias);
            //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           /* mSpinner.setAdapter(dataAdapter2);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int indice = getAdapterPosition();
                    if (indice != -1) {
                        mList.get(indice).setCategoria(position);
                        update();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAddMay:
                    mList.get(getAdapterPosition()).setCantidad(mList.get(
                            getAdapterPosition()).getCantidad() + 1);

                    break;
                case R.id.btnRemoveMay:
                    if (mList.get(getAdapterPosition()).getCantidad() <= 0) {
                        mList.get(getAdapterPosition()).setCantidad(0);
                    } else {
                        mList.get(getAdapterPosition()).setCantidad(mList.get(
                                getAdapterPosition()).getCantidad() - 1);
                    }

                    break;
                case R.id.txtPop:
                    v.showContextMenu();
                    //setPosition(getAdapterPosition());
                    break;
            }
            setPosition(getAdapterPosition());
            update();

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            for(int i = 0; i<categorias.length;i++){
                menu.add(Menu.NONE, i,0,categorias[i]);
            }
        }
    }

    public void update() {
        if(mList.size() != 0) {
            float precio = calcularPrecio(mList.get(getPosition()).getCategoria(), 0);
            mList.get(getPosition()).setPrecioTotal(precio * mList.get(this.getPosition())
                    .getCantidad());
            notifyDataSetChanged();
            mObservadorPrecio.actualizarPrecio();
        }
    }

    private float calcularPrecio(int categ, int tipo) {
        float precioTotal = 0;

        switch (categ+1) {
            case 6:
                //Afiliados
                precioTotal = 0;
                break;
            case 1:
            case 7:
                //Estudiantes
                if (tipo == 0) {
                    precioTotal = 50;
                } else if (tipo == 1) {
                    precioTotal = 250;
                } else {
                    precioTotal = 1000;
                }
                break;

            case 5:
                //Particular
                precioTotal = 200;
                break;
            case 8:
                if (getCategoriaGral() == 0){
                    precioTotal = 100;
                }else{
                    precioTotal = calcularPrecio(getCategoriaGral(),0);
                }
                break;
            default:
                //Docentes, Nodocentes y Egresados
                if (tipo == 0) {
                    precioTotal = 100;
                } else if (tipo == 1) {
                    precioTotal = 500;
                } else {
                    precioTotal = 1000;
                }
                break;
        }
        return precioTotal;
    }
    
}
