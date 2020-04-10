package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Profesor;

import java.util.List;

public class ProfesorRepository {

    private ProfesorDAO mProfesorDAO;
    private BDBienestar bdBienestar;

    public ProfesorRepository(Context application) {
        bdBienestar = BDBienestar.getDatabase(application);
        mProfesorDAO = bdBienestar.getProfesorDAO();
    }

    public void insert(final Profesor profesor) {
        mProfesorDAO.insert(profesor);
    }

    public void delete(final Profesor profesor) {
        mProfesorDAO.delete(profesor);
    }

    public void update(final Profesor profesor) {
        mProfesorDAO.update(profesor);
    }

    public Profesor getById(final int id) {
        return mProfesorDAO.get(id);

    }

    public boolean isExist(int id) {
        Profesor profesor = getById(id);
        return profesor != null;
    }

    public List<Profesor> getAll() {
        return mProfesorDAO.getAll();
    }

    public void deleteAll(){
        mProfesorDAO.deleteAll();
    }

}
