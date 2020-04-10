package com.unse.bienestarestudiantil.Databases;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

public class UsuarioRepository {

    private UsuarioDAO mUsuarioDAO;
    private BDBienestar bdBienestar;

    public UsuarioRepository(Context application) {
        bdBienestar = BDBienestar.getDatabase(application);
        mUsuarioDAO = bdBienestar.getUserDao();
    }

    public void insert(final Usuario usuario) {
        mUsuarioDAO.insert(usuario);
    }

    public void delete(final Usuario usuario) {
        mUsuarioDAO.delete(usuario);
    }

    public void update(final Usuario usuario) {
        mUsuarioDAO.update(usuario);
    }

    public Usuario getById(final int id) {
        return mUsuarioDAO.get(id);

    }

    public boolean isExist(int id) {
        Usuario usuario = getById(id);
        return usuario != null;
    }

    public List<Usuario> getAll() {
        return mUsuarioDAO.getAll();
    }

    public void deleteAll(){
        mUsuarioDAO.deleteAll();
    }
}
