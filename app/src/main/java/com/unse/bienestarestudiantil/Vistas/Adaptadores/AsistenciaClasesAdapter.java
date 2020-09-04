package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class AsistenciaClasesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private static final int TYPE_MES = 0;
    private static final int TYPE_CLASE = 0;

    private boolean mValid = true;
    private SparseArray<Meses> mSections = new SparseArray<Meses>();
    private ArrayList<Integer> clases;
    private RecyclerView mRecyclerView;


    public AsistenciaClasesAdapter(Context context,
                                   RecyclerView recyclerView) {

        mContext = context ;
        mRecyclerView = recyclerView;
        clases = new ArrayList<>();
        final GridLayoutManager layoutManager = (GridLayoutManager)(mRecyclerView.getLayoutManager());
        assert layoutManager != null;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (isSectionHeaderPosition(position))? layoutManager.getSpanCount() : 1 ;
            }
        });
    }

    class ClasesViewHolder extends RecyclerView.ViewHolder {
        private TextView txtClase;

        public ClasesViewHolder(View itemView) {
            super(itemView);
            txtClase = itemView.findViewById(R.id.txtTitulo);
        }
    }

    class MesesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitulo;

         MesesViewHolder(View view) {
            super(view);
            txtTitulo = view.findViewById(R.id.txtTitulo);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {

        View view = null;

        if (typeView == TYPE_MES) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistencia_mes, parent, false);
            return new MesesViewHolder(view);

        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistencia_clase, parent, false);
            return new ClasesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {

        if (isSectionHeaderPosition(position)) {
            MesesViewHolder holder = (MesesViewHolder) sectionViewHolder;
            holder.txtTitulo.setText(mSections.get(position).getTitle());
        }else{
            ClasesViewHolder holder = (ClasesViewHolder) sectionViewHolder;
            holder.txtClase.setText("Clase "+position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return isSectionHeaderPosition(position)
                ? TYPE_MES
                :  TYPE_CLASE;
    }


    public static class Meses {
        private int firstPosition;
        private int sectionedPosition;
        private String txtTitulo;

        public Meses(int firstPosition, String title) {
            this.firstPosition = firstPosition;
            this.txtTitulo = title;
        }

        public String  getTitle() {
            return txtTitulo;
        }
    }


    public void setSections(Meses[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Meses>() {
            @Override
            public int compare(Meses o, Meses o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (Meses section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    public boolean isSectionHeaderPosition(int position) {
        return mSections.get(position) != null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return (mValid ? clases.size() + mSections.size() : 0);
    }



}
