package com.xencosworks.fittset.helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xencosworks.fittset.R;
import com.xencosworks.fittset.Room.Exercise;

import java.util.ArrayList;
import java.util.List;

/**Previously tried to implement a ListView (Which extends RecyclerView) for animations in RecyclerView
 * but it did not work (did not show data in fragment, but showed them in regular activiy),
 * try implementing later.
 * TODO: https://youtu.be/xPPMygGxiEo
 */
public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    public OnItemClickListener listener;

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
        exerciseHolder.exerciseSubtitle.setText("Custom Routine");
        exerciseHolder.exerciseMaxWeight.setText(currentExercise.getMaxWeight()+" KG");
        exerciseHolder.exerciseLastWeight.setText(currentExercise.getLastWeight()+" KG");
        exerciseHolder.exerciseNewBest.setVisibility(View.VISIBLE);
        //TODO: View the right data, this is temporary just for testing
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercise(List<Exercise> exercises){
        this.exercises = exercises;
        notifyDataSetChanged(); //TODO: Change Later, this doesn't have animations and lots of features.
    }

    public Exercise getNoteAt(int position){
        return exercises.get(position);
    }


    class ExerciseHolder extends RecyclerView.ViewHolder{
        private TextView exerciseTitle;
        private TextView exerciseSubtitle;
        private TextView exerciseMaxWeight;
        private TextView exerciseLastWeight;
        private View exerciseNewBest;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.inflator_details_title);
            exerciseSubtitle = itemView.findViewById(R.id.inflator_details_custom_routine);
            exerciseMaxWeight = itemView.findViewById(R.id.inflator_details_max_weight);
            exerciseLastWeight = itemView.findViewById(R.id.inflator_details_last_weight);
            exerciseNewBest = itemView.findViewById(R.id.inflator_details_new_best);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //When an item is clicked, we just pass the exercise that was clicked to out custom listener.
                    int position = getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION) {
                        //when listened to a click, pass the corresponding exercise to be
                        //handled on the required activity/fragment
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Exercise exercise); //to be implemented in the required activity/fragment
                                             //where the passed exercise (retrieve from the actual listener in ExerciseHolder)
                                             //will be used there.
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
