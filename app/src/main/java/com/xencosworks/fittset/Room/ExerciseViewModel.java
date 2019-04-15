package com.xencosworks.fittset.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository exerciseRepository;
    private LiveData<List<Exercise>> allExercises;


    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseRepository = new ExerciseRepository(application);
        allExercises = exerciseRepository.getAllExercises();
    }

    public void insert(Exercise exercise){
        exerciseRepository.insert(exercise);
    }

    public void update(Exercise exercise){
        exerciseRepository.update(exercise);
    }

    public void delete(Exercise exercise){
        exerciseRepository.delete(exercise);
    }

    public void deleteAllExercises(){
        exerciseRepository.deleteAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises(){
        return allExercises;
    }
}
