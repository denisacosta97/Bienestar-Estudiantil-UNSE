package com.unse.bienestarestudiantil.Databases;

import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProfesorDAO {
    @Insert
    void insert(Profesor profesor);

    @Update
    void update(Profesor profesor);

    @Query("SELECT * FROM " + Profesor.TABLE_PROFESOR + " WHERE " + Profesor.KEY_ID_PRO + " = :id ")
    Profesor get(int id);

    @Delete
    void delete(Profesor profesor);

    @Query("DELETE FROM "+ Profesor.TABLE_PROFESOR)
    void deleteAll();

    @Query("SELECT * FROM " + Profesor.TABLE_PROFESOR)
    List<Profesor> getAll();
}
