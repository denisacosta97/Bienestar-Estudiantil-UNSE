package com.unse.bienestarestudiantil.Databases;

import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AlumnoDAO {

    @Insert
    void insert(Alumno alumno);

    @Update
    void update(Alumno alumno);

    @Query("SELECT * FROM " + Alumno.TABLE_ALUMNO + " WHERE " + Alumno.KEY_ID_ALU + " = :id ")
    Alumno get(int id);

    @Delete
    void delete(Alumno alumno);

    @Query("DELETE FROM "+ Alumno.TABLE_ALUMNO)
    void deleteAll();

    @Query("SELECT * FROM " + Alumno.TABLE_ALUMNO)
    List<Alumno> getAll();
}

