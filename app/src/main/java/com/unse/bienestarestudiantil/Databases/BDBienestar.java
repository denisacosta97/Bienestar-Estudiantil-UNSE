package com.unse.bienestarestudiantil.Databases;

import android.content.Context;

import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Usuario.class, Alumno.class, Egresado.class, Profesor.class}, version = 1, exportSchema = false)
abstract class BDBienestar extends RoomDatabase {

    public abstract UsuarioDAO getUserDao();

    public abstract AlumnoDAO getAlumnoDao();

    public abstract ProfesorDAO getProfesorDAO();

    public abstract EgresadoDAO getEgresadoDAO();

   // public abstract RolDAO getRolDAO();

    private static volatile BDBienestar INSTANCE;
    private static String DATABASE_NAME = "bienestar";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BDBienestar getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BDBienestar.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BDBienestar.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
