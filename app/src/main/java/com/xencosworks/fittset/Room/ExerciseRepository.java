package com.xencosworks.fittset.Room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * A Repository acts like an abstract layer that controls underlying ROOM Database
 * and can also implement additional databases (web databases)*/

public class ExerciseRepository {
    private ExerciseDao exerciseDao;

    public ExerciseRepository(Application application){
        MainDatabase mainDatabase = MainDatabase.getInstance(application);
        exerciseDao = mainDatabase.exerciseDao();
    }

    public void insert(Exercise exercise){
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void update(Exercise exercise){
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void delete(Exercise exercise){
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void deleteAllExercises(){
        new DeleteAllExerciseAsyncTask(exerciseDao).execute();
    }

    public LiveData<List<Exercise>> getAllExercises(){
        //LiveData returnable handle background activities automatically when READING from
        //the application's database, on the other hand, other CRUD operations need background
        //handling.
        return exerciseDao.getAllExercises();
    }

    public LiveData<List<Exercise>> getExercisesByGroup(int muscleGroup){
        return exerciseDao.getAllExercisesOfGroup(muscleGroup);
    }



    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;
        private  InsertExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;
        private  UpdateExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.update(exercises[0]);
            return null;
        }
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;
        private  DeleteExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }

    private static class DeleteAllExerciseAsyncTask extends AsyncTask<Void, Void, Void>{
        private ExerciseDao exerciseDao;
        private  DeleteAllExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.deleteAllExercises();
            return null;
        }
    }
}
