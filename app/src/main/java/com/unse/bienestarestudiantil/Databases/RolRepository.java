package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Rol;

import java.util.List;

public class RolRepository {

    private RolDAO mRolDAO;
    private BDBienestar bdBienestar;

    public RolRepository(Context application) {
        bdBienestar = BDBienestar.getDatabase(application);
        mRolDAO = bdBienestar.getRolDAO();
    }

    public void insert(final Rol rol) {
        mRolDAO.insert(rol);
    }

    public void delete(final Rol rol) {
        mRolDAO.delete(rol);
    }

    public void update(final Rol rol) {
        mRolDAO.update(rol);
    }

    public List<Rol> getAllByUsuario(int id) {
        return mRolDAO.getAllByUser(id);
    }

    public Rol get(int id) {
        return mRolDAO.get(id);
    }

    public void deleteAll(){
        mRolDAO.deleteAll();
    }
}
