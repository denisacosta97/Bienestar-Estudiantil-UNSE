package com.unse.bienestarestudiantil.Databases;

import com.unse.bienestarestudiantil.Modelos.Rol;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RolDAO {

    @Insert
    void insert(Rol rol);

    @Update
    void update(Rol rol);

    @Delete
    void delete(Rol rol);

    @Query("DELETE FROM " + Rol.TABLE_ROL)
    void deleteAll();

    @Query("SELECT * FROM " + Rol.TABLE_ROL + " WHERE " + Rol.KEY_ROL + " = :id ")
    Rol get(int id);

    @Query("SELECT * FROM " + Rol.TABLE_ROL + " WHERE " + Rol.KEY_USER + " = :id ")
    List<Rol> getAllByUser(int id);

}
