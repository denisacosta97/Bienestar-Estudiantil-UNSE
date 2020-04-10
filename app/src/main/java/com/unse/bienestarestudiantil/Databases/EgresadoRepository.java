package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Egresado;

import java.util.List;

public class EgresadoRepository {
    
    private EgresadoDAO mEgresadoDAO;
    private BDBienestar bdBienestar;

    public EgresadoRepository(Context application) {
        bdBienestar = BDBienestar.getDatabase(application);
        mEgresadoDAO = bdBienestar.getEgresadoDAO();
    }

    public void insert(final Egresado egresado) {
        mEgresadoDAO.insert(egresado);
    }

    public void delete(final Egresado egresado) {
        mEgresadoDAO.delete(egresado);
    }

    public void update(final Egresado egresado) {
        mEgresadoDAO.update(egresado);
    }

    public Egresado getById(final int id) {
        return mEgresadoDAO.get(id);

    }

    public boolean isExist(int id) {
        Egresado egresado = getById(id);
        return egresado != null;
    }

    public List<Egresado> getAll() {
        return mEgresadoDAO.getAll();
    }

    public void deleteAll(){
        mEgresadoDAO.deleteAll();
    }

}
