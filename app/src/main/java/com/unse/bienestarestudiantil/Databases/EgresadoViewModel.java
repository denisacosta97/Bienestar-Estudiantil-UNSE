package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Egresado;

import java.util.List;

public class EgresadoViewModel {

    private Context mContext;
    private EgresadoRepository mRepository;

    public EgresadoViewModel(Context context) {
        mContext = context;
        mRepository = new EgresadoRepository();
    }

    public void insert(Egresado egresado) {

    }

    public void delete(Egresado egresado) {

    }

    public void update(Egresado egresado) {

    }

    public Egresado getById(int id) {
        return null;
    }

    public boolean isExist(int id) {
        Egresado egresado = getById(id);
        return egresado != null;
    }

    public List<Egresado> getAll() {
        return null;
    }

    public void deleteAll(){

    }
}
