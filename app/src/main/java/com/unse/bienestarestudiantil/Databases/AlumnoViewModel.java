package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Alumno;

import java.util.List;

public class AlumnoViewModel {

   private Context mContext;
    private AlumnoRepository mRepository;

    public AlumnoViewModel(Context context) {
        mContext = context;
        mRepository = new AlumnoRepository();
    }

    public void insert(Alumno alumno) {

    }

    public void delete(Alumno alumno) {

    }

    public void update(Alumno alumno) {

    }

    public Alumno getById(int id) {
        return null;
    }

    public boolean isExist(int id) {
        Alumno alumno = getById(id);
        return alumno != null;
    }

    public List<Alumno> getAll() {
        return null;
    }

    public void deleteAll() {

    }

}
