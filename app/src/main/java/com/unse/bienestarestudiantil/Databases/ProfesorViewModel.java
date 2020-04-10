package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Profesor;

import java.util.List;

public class ProfesorViewModel {

    private Context mContext;
    private ProfesorRepository mRepository;

    public ProfesorViewModel(Context context) {
        mContext = context;
        mRepository = new ProfesorRepository(context);
    }

    public void insert(Profesor profesor) {
        mRepository.insert(profesor);
    }

    public void delete(Profesor profesor) {
        mRepository.delete(profesor);
    }

    public void update(Profesor profesor) {
        mRepository.update(profesor);
    }

    public Profesor getById(int id) {
        return mRepository.getById(id);
    }

    public boolean isExist(int id) {
        Profesor profesor = getById(id);
        return profesor != null;
    }

    public List<Profesor> getAll() {
        return mRepository.getAll();
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

}
