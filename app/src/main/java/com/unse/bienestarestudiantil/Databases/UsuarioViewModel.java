package com.unse.bienestarestudiantil.Databases;

import androidx.lifecycle.LiveData;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

public class UsuarioViewModel {


    private Context mContext;
    private UsuarioRepository mRepository;

    public UsuarioViewModel(Context context) {
        mContext = context;
        mRepository = new UsuarioRepository();
    }

    public void insert(Usuario usuario) {

    }

    public void delete(Usuario usuario) {

    }

    public void update(Usuario usuario) {

    }

    public Usuario getById(int id) {
        return null;

    }

    public boolean isExist(int id) {
        Usuario usuario = getById(id);
        return usuario != null;
    }

    public List<Usuario> getAll() {
        return null;
    }

    public void deleteAll() {

    }
}
