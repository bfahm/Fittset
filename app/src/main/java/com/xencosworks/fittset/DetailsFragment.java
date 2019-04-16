package com.xencosworks.fittset;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseViewModel;
import com.xencosworks.fittset.helpers.ExerciseRecyclerViewAdapter;

import java.util.List;


public class DetailsFragment extends Fragment{
    /**
     * NOTE ABOUT 'STATIC' MODIFIER:
     * Static variable is a variable which belongs to the class and initialized ONLY ONCE
       at the START of the execution.
            • It is a variable which belongs to the class and not to object(instance)
            • These variables will be initialized first, before the initialization
              of any instance variables
            • A single copy to be shared by all instances of the class
            • A static variable can be accessed directly by the class name and doesn’t need any object
     */
    public static int idFromParentPage = -1;

    private static String LOGTAG = DetailsFragment.class.getSimpleName();

    private String textMuscleGPlaceH = "";
    private TextView muscleGPlaceH;

    private View headerContainer;
    private View noSelectionView;
    private View noContentView;

    private ExerciseRecyclerViewAdapter adapter;
    private ExerciseViewModel exerciseViewModel;
    private RecyclerView recyclerView;

    public static final int ADD_EXERCISE_REQUEST = 1;
    public static final int EDIT_EXERCISE_REQUEST = 2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Only inflate layout here.
        View rootView = inflater.inflate(R.layout.activity_details, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Initialize view here.
        super.onViewCreated(view, savedInstanceState);

        headerContainer = view.findViewById(R.id.details_header_container);
        headerContainer.setVisibility(View.INVISIBLE);
        muscleGPlaceH = view.findViewById(R.id.details_muscleg_placeholder);

        //RecyclerView & Data Container Initialization----------------------------------------------
        recyclerView = view.findViewById(R.id.data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.GONE); //Initially not visible till user chooses a muscle day

        adapter = new ExerciseRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        initializeFancyRecyclerView();
        handleRedirectsFromRecyclerView();
        //------------------------------------------------------------------------------------------

        noSelectionView = view.findViewById(R.id.empty_view_no_selection);
        noContentView = view.findViewById(R.id.empty_view_no_content);

        Button newExerciseEmptyContent = view.findViewById(R.id.details_button_add_new_exercise);
        newExerciseEmptyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputForm.class);
                intent.putExtra(InputForm.EXTRA_MUSCLE_GROUP, idFromParentPage);
                startActivity(intent);
            }
        });
    }

    private void initializeFancyRecyclerView() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.v(LOGTAG, ""+direction);
                if(direction == 8){
                    exerciseViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Exercise Deleted", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                try {
                    Bitmap icon;
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        View itemView = viewHolder.itemView;
                        //Limit the swipe position
                        float limitXaxis = dX / 3;
                        itemView.setTranslationX(limitXaxis);

                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 50;

                        Paint backgroundColor = new Paint();
                        backgroundColor.setColor(Color.parseColor("#D32F2F"));

                        Paint foregroundText = new Paint();
                        foregroundText.setColor(Color.parseColor("#FFFFFF"));
                        foregroundText.setTextSize(60);

                        RectF background = new RectF((float) itemView.getRight() + limitXaxis,
                                (float) itemView.getTop(),
                                (float) itemView.getRight(),
                                (float) itemView.getBottom()); //Background dimensions

                        c.drawRect(background, backgroundColor);

                        c.drawText("NEW BEST", itemView.getRight() - 370,
                                (float)(itemView.getTop() + itemView.getHeight() / 1.75), foregroundText);
                    } else {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void handleRedirectsFromRecyclerView(){
        adapter.setOnItemClickListener(new ExerciseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(getActivity(), InputForm.class);
                intent.putExtra(InputForm.EXTRA_ID, exercise.getId());
                intent.putExtra(InputForm.EXTRA_NAME, exercise.getExerciseName());
                intent.putExtra(InputForm.EXTRA_NOTES, exercise.getExerciseNotes());
                intent.putExtra(InputForm.EXTRA_MUSCLE_GROUP, exercise.getMuscleGroup());
                intent.putExtra(InputForm.EXTRA_MAX_WEIGHT, exercise.getMaxWeight());
                intent.putExtra(InputForm.EXTRA_SETS, exercise.getSets());
                intent.putExtra(InputForm.EXTRA_REPS, exercise.getReps());
                startActivity(intent);
            }
        });
    }

    //Reset the state of the fragment to default
    @Override
    public void onDetach() {
        super.onDetach();
        idFromParentPage=-1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        idFromParentPage=-1;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        switch (idFromParentPage){
            case 1:
                textMuscleGPlaceH = "Chest";
                break;
            case 2:
                textMuscleGPlaceH = "Shoulders";
                break;
            case 3:
                textMuscleGPlaceH = "Back";
                break;
            case 4:
                textMuscleGPlaceH = "Biceps";
                break;
            case 5:
                textMuscleGPlaceH = "Triceps";
                break;
            case 6:
                textMuscleGPlaceH = "Legs";
                break;
            case 7:
                textMuscleGPlaceH = "Abs";
                break;
        }
        if(getActivity() != null && isAdded()) {
            if (isVisibleToUser) {
                muscleGPlaceH.setText(textMuscleGPlaceH);

                // The following block handling showing the exercises and hiding the empty views.
                if(idFromParentPage!=-1){
                    headerContainer.setVisibility(View.VISIBLE);
                    noSelectionView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    exerciseViewModel.getExercisesByGroup(idFromParentPage).observe(this, new Observer<List<Exercise>>() {
                        @Override
                        public void onChanged(@Nullable List<Exercise> exercises) {
                            adapter.setExercise(exercises);
                            if(exercises.size()>0){
                                noContentView.setVisibility(View.GONE);
                            }else {
                                noContentView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        }
    }
}