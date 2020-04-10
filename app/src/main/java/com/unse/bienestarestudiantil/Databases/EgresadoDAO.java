package com.unse.bienestarestudiantil.Databases;

import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EgresadoDAO {
    @Insert
    void insert(Egresado egresado);

    @Update
    void update(Egresado egresado);

    @Query("SELECT * FROM " + Egresado.TABLE_EGRESADO + " WHERE " + Egresado.KEY_ID_EGR + " = :id ")
    Egresado get(int id);

    @Delete
    void delete(Egresado egresado);

    @Query("DELETE FROM "+ Egresado.TABLE_EGRESADO)
    void deleteAll();

    @Query("SELECT * FROM " + Egresado.TABLE_EGRESADO)
    List<Egresado> getAll();
}
