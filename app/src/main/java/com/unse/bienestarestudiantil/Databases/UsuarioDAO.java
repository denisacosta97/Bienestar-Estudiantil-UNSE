package com.unse.bienestarestudiantil.Databases;

import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDAO {

    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("SELECT * FROM " + Usuario.TABLE_USER + " WHERE " + Usuario.KEY_ID_USER + " = :id ")
    Usuario get(int id);

    @Delete
    void delete(Usuario usuario);

    @Query("DELETE FROM "+Usuario.TABLE_USER)
    void deleteAll();

    @Query("SELECT * FROM " + Usuario.TABLE_USER)
    List<Usuario> getAll();
}
