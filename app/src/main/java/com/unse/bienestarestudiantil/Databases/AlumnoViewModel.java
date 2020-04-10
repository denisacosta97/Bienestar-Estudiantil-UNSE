package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Alumno;

import java.util.List;

public class AlumnoViewModel {

    private Context mContext;
    private AlumnoRepository mRepository;

    public AlumnoViewModel(Context context) {
        mContext = context;
        mRepository = new AlumnoRepository(context);
    }

    public void insert(Alumno alumno) {
        mRepository.insert(alumno);
    }

    public void delete(Alumno alumno) {
        mRepository.delete(alumno);
    }

    public void update(Alumno alumno) {
        mRepository.update(alumno);
    }

    public Alumno getById(int id) {
        return mRepository.getById(id);
    }

    public boolean isExist(int id) {
        Alumno alumno = getById(id);
        return alumno != null;
    }

    public List<Alumno> getAll() {
        return mRepository.getAll();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}
