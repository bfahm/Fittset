package com.xencosworks.fittset;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseViewModel;

import java.util.List;


public class MuscleDaysFragment extends Fragment{
    private static String LOGTAG = MuscleDaysFragment.class.getSimpleName();
    private View rootView;

    private ExerciseViewModel exerciseViewModel;

    private TextView chestCount;
    private TextView shouldersCount;
    private TextView backCount;
    private TextView biCount;
    private TextView triCount;
    private TextView legsCount;
    private TextView absCount;


    public MuscleDaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_muscle_days, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        chestCount = view.findViewById(R.id.muscle_days_chest_count);
        shouldersCount = view.findViewById(R.id.muscle_days_shoulders_count);
        backCount = view.findViewById(R.id.muscle_days_back_count);
        biCount = view.findViewById(R.id.muscle_days_bi_count);
        triCount = view.findViewById(R.id.muscle_days_tri_count);
        legsCount = view.findViewById(R.id.muscle_days_legs_count);
        absCount = view.findViewById(R.id.muscle_days_abs_count);

        exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                if(exercises.size()>0){
                    Log.v(LOGTAG, "ENTERED TO COUNTING");
                    countExercises();
                }
            }
        });
    }


    private void countExercises() {
        final TextView[] tvArray = {chestCount, shouldersCount, backCount, biCount, triCount, legsCount, absCount};
        for(int i = 0; i<7; i++){
            final int index = i;
            // using index+1 in the muscle group to skip counting the unknown group (which doesn't have a textview)
            exerciseViewModel.getExercisesByGroup(index+1).observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(@Nullable List<Exercise> exercises) {
                    int numberOfExercises = exercises.size();
                    if(numberOfExercises>0){
                        tvArray[index].setVisibility(View.VISIBLE);
                        tvArray[index].setText(numberOfExercises + " Exercises");
                    }else {
                        tvArray[index].setVisibility(View.VISIBLE);
                        tvArray[index].setText("No exercises yet.");
                    }
                }
            });
        }

    }


}
