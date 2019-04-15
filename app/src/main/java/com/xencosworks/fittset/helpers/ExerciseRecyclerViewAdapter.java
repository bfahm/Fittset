package com.xencosworks.fittset.helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xencosworks.fittset.R;
import com.xencosworks.fittset.Room.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inflator_details_item, viewGroup, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder exerciseHolder, int position) {
        Exercise currentExercise = exercises.get(position);
        exerciseHolder.exerciseTitle.setText(currentExercise.getExerciseName());
        exerciseHolder.exerciseSubtitle.setText(currentExercise.getMuscleGroup()+"");
        exerciseHolder.exerciseMaxWeight.setText(currentExercise.getMaxWeight()+"");
        exerciseHolder.exerciseLastWeight.setText(currentExercise.getLastWeight()+"");
        //TODO: View the right data, this is temporary just for testing
    }

    @Override
    public int getItemCount() {
        Log.v("-------------------", exercises.size()+"");
        return exercises.size();
    }

    public void setExercise(List<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged(); //TODO: Change Later, this doesn't have animations and lots of features.
    }


    class ExerciseHolder extends RecyclerView.ViewHolder{
        private TextView exerciseTitle;
        private TextView exerciseSubtitle;
        private TextView exerciseMaxWeight;
        private TextView exerciseLastWeight;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.inflator_details_title);
            exerciseSubtitle = itemView.findViewById(R.id.inflator_details_custom_routine);
            exerciseMaxWeight = itemView.findViewById(R.id.inflator_details_max_weight);
            exerciseLastWeight = itemView.findViewById(R.id.inflator_details_last_weight);
        }
    }
}
