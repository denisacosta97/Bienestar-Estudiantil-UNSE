package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Profesor;

import java.util.List;

public class ProfesorViewModel {

   private Context mContext;
    private ProfesorRepository mRepository;

    public ProfesorViewModel(Context context) {
        mContext = context;
        mRepository = new ProfesorRepository();
    }

    public void insert(Profesor profesor) {

    }

    public void delete(Profesor profesor) {

    }

    public void update(Profesor profesor) {

    }

    public Profesor getById(int id) {
        return null;
    }

    public boolean isExist(int id) {
        Profesor profesor = getById(id);
        return profesor != null;
    }

    public List<Profesor> getAll() {
        return null;
    }

    public void deleteAll(){
    }

}
