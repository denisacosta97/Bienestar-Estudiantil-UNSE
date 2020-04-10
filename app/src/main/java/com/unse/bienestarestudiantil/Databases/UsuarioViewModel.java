package com.unse.bienestarestudiantil.Databases;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

public class UsuarioViewModel {

    private Context mContext;
    private UsuarioRepository mRepository;

    public UsuarioViewModel(Context context) {
        mContext = context;
        mRepository = new UsuarioRepository(context);
    }

    public void insert(Usuario usuario) {
         mRepository.insert(usuario);
    }

    public void delete(Usuario usuario) {
         mRepository.delete(usuario);
    }

    public void update(Usuario usuario) {
         mRepository.update(usuario);
    }

    public Usuario getById(int id) {
        return mRepository.getById(id);
    }

    public boolean isExist(int id) {
        Usuario usuario = getById(id);
        return usuario != null;
    }

    public List<Usuario> getAll() {
        return mRepository.getAll();
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }
}
