package com.xencosworks.fittset;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseRepository;
import com.xencosworks.fittset.Room.ExerciseViewModel;
import com.xencosworks.fittset.Room.MainDatabase;
import com.xencosworks.fittset.helpers.ExerciseRecyclerViewAdapter;

import java.security.cert.Extension;
import java.util.List;

public class Test extends AppCompatActivity {

    private ExerciseViewModel exerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView recyclerView = findViewById(R.id.data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ExerciseRecyclerViewAdapter adapter = new ExerciseRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                adapter.setExercise(exercises);
            }
        });

    }
}
