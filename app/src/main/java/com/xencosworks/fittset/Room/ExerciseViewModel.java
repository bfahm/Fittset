package com.xencosworks.fittset.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository exerciseRepository;


    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseRepository = new ExerciseRepository(application);
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
        return exerciseRepository.getAllExercises();
    }

    public LiveData<List<Exercise>> getExercisesByGroup(int muscleGroup){
        return exerciseRepository.getExercisesByGroup(muscleGroup);
    }
}
