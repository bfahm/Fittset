package com.xencosworks.fittset.Room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Exercise.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {


    private static String DATABASE_NAME = "fittset_room_db";

    private static MainDatabase instance;

    public abstract ExerciseDao exerciseDao();

    /**
     * We are using synchronized function to limit multiple threads accessing instances of the db
     * fallbackToDestructiveMigration() will delete old data and replace database with new table
     *                                  and new schema. Be sure to modify this before publishing.*/
    public static synchronized MainDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MainDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //TODO: Change this when publishing
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //Create a room callback which basically gets called when the database is first created.
    //This has an onCreate Function, we'll populate the insertion task there.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //To be able to add data to database, it needs to be done in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ExerciseDao exerciseDao;
        private PopulateDbAsyncTask(MainDatabase db){
            exerciseDao = db.exerciseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.insert(new Exercise("Bench Press", Exercise.MUSCLE_G_CHEST, 4, 10));
            exerciseDao.insert(new Exercise("Back Shoulders", Exercise.MUSCLE_G_SHOULDERS, 4, 10));
            exerciseDao.insert(new Exercise("Inclined Legs", Exercise.MUSCLE_G_LEGS, 4, 50));
            return null;
        }
    }
}
