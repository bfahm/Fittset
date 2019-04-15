package com.xencosworks.fittset.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String exerciseName;
    private String exerciseNotes;
    private int muscleGroup = 0;
    private int maxWeight = 0;
    private int lastWeight = 0;
    private int sets = 4;
    private int reps = 10;
    private int customRoutine = 0;

    // Muscle Group Constants (for later referencing)
    public static final int MUSCLE_G_UNKNOWN = 0;
    public static final int MUSCLE_G_CHEST = 1;
    public static final int MUSCLE_G_SHOULDERS = 2;
    public static final int MUSCLE_G_BACK = 3;
    public static final int MUSCLE_G_BI = 4;
    public static final int MUSCLE_G_TRI = 5;
    public static final int MUSCLE_G_LEGS = 6;
    public static final int MUSCLE_G_ABS = 7;

    @Ignore
    public Exercise(@NonNull String exerciseName, String exerciseNotes, int muscleGroup, int maxWeight, int lastWeight, int sets, int reps, int customRoutine) {
        this.exerciseName = exerciseName;
        this.exerciseNotes = exerciseNotes;
        this.muscleGroup = muscleGroup;
        this.maxWeight = maxWeight;
        this.lastWeight = lastWeight;
        this.sets = sets;
        this.reps = reps;
        this.customRoutine = customRoutine;
    }

    public Exercise(@NonNull String exerciseName, int muscleGroup, int sets, int reps) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.sets = sets;
        this.reps = reps;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseNotes() {
        return exerciseNotes;
    }

    public int getMuscleGroup() {
        return muscleGroup;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getLastWeight() {
        return lastWeight;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getCustomRoutine() {
        return customRoutine;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExerciseName(@NonNull String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseNotes(String exerciseNotes) {
        this.exerciseNotes = exerciseNotes;
    }

    public void setMuscleGroup(int muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setLastWeight(int lastWeight) {
        this.lastWeight = lastWeight;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setCustomRoutine(int customRoutine) {
        this.customRoutine = customRoutine;
    }
}
