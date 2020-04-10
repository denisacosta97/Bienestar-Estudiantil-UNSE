package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Alumno;

import java.util.List;

public class AlumnoRepository {
    
    private AlumnoDAO mAlumnoDAO;
    private BDBienestar mBDBienestar;
    
    public AlumnoRepository(Context context){
        mBDBienestar = BDBienestar.getDatabase(context);
        mAlumnoDAO = mBDBienestar.getAlumnoDao();
    }

    public void insert(final Alumno alumno) {
        mAlumnoDAO.insert(alumno);
    }

    public void delete(final Alumno alumno) {
        mAlumnoDAO.delete(alumno);
    }

    public void update(final Alumno alumno) {
        mAlumnoDAO.update(alumno);
    }

    public Alumno getById(final int id) {
        return mAlumnoDAO.get(id);

    }

    public boolean isExist(int id) {
        Alumno alumno = getById(id);
        return alumno != null;
    }

    public List<Alumno> getAll() {
        return mAlumnoDAO.getAll();
    }

    public void deleteAll(){
        mAlumnoDAO.deleteAll();
    }

}
