package com.xencosworks.fittset.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Delete
    void deleteMultible(Exercise... exercises);

    @Query("DELETE FROM exercise_table")
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table WHERE muscleGroup == :muscleGroup")
    LiveData<List<Exercise>> getAllExercisesOfGroup(int muscleGroup);

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

}
