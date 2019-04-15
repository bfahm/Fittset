package com.xencosworks.fittset.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Exercise.class}, version = 1)
public abstract class ExercisesDatabase extends RoomDatabase {


    private static String DATABASE_NAME = "fittset_room_db";

    private static ExercisesDatabase instance;

    public abstract ExerciseDao exerciseDao();

    /**
     * We are using synchronized function to limit multiple threads accessing instances of the db
     * fallbackToDestructiveMigration() will delete old data and replace database with new table
     *                                  and new schema. Be sure to modify this before publishing.*/
    public static synchronized ExercisesDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExercisesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //TODO: Change this when publishing
                    .build();
        }
        return instance;
    }

}
