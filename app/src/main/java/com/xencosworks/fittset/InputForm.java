package com.xencosworks.fittset;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import com.scalified.fab.ActionButton;
import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseViewModel;


public class InputForm extends AppCompatActivity {

    private static String LOGTAG = InputForm.class.getSimpleName();

    private ExerciseViewModel exerciseViewModel;
    private Exercise exercise = null;

    private View AlteringExerciseContents;
    private ImageView AlteringExerciseArrow;

    private EditText exTitleIP;
    private EditText exNotesIP;
    private TextView musclesTv;
    private TextView maxWeightTv;
    private TextView setsRepsTv;


    private int mExerciseId = -1;
    private String mExerciseName = "";
    private String mExerciseNotes = "";

    private int tempMuscleGroup = 0;
    private int mMuscleGroup=0;

    private int tempMaxWeight = 1;
    private int mMaxWeight=0;

    private int tempSets = 4;
    private int mSets=0;

    private int tempReps = 10;
    private int mReps=0;

    private final String[] musclesArr = {"Unspecified", "Chest", "Shoulders", "Back", "Biceps", "Triceps", "Legs", "Abs"};

    public static String EXTRA_ID = "com.xencosworks.fittset.InputForm.EXTRA_ID";
    public static String EXTRA_NAME = "com.xencosworks.fittset.InputForm.EXTRA_NAME";
    public static String EXTRA_NOTES = "com.xencosworks.fittset.InputForm.EXTRA_NOTES";
    public static String EXTRA_MUSCLE_GROUP = "com.xencosworks.fittset.InputForm.EXTRA_MUSCLE_GROUP";
    public static String EXTRA_MAX_WEIGHT = "com.xencosworks.fittset.InputForm.EXTRA_MAX_WEIGHT";
    public static String EXTRA_SETS = "com.xencosworks.fittset.InputForm.EXTRA_SETS";
    public static String EXTRA_REPS = "com.xencosworks.fittset.InputForm.EXTRA_REPS";

    public static final int ADD_EXERCISE_REQUEST = 1;
    public static final int EDIT_EXERCISE_REQUEST = 2;

    private int currentState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        initSetup();
        setUpFab();
        underlineEffect();
        initializeViews();

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            customCodeHelper(intent, EDIT_EXERCISE_REQUEST);
        }else if(intent.hasExtra(EXTRA_NAME)) {
            customCodeHelper(intent, ADD_EXERCISE_REQUEST);
        }
    }

    private void initializeViews(){
        exTitleIP = findViewById(R.id.input_form_ex_title);
        exNotesIP = findViewById(R.id.input_form_ex_notes);
        musclesTv = findViewById(R.id.input_form_ex_muscle_gp_tv);
        maxWeightTv = findViewById(R.id.input_form_ex_max_weight_tv);
        setsRepsTv = findViewById(R.id.input_form_ex_sets_reps_tv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void customCodeHelper(Intent intent, int requestCode){
        switch (requestCode){
            case ADD_EXERCISE_REQUEST:
                mMuscleGroup = intent.getIntExtra(EXTRA_MUSCLE_GROUP, -1);
                musclesTv.setText(musclesArr[mMuscleGroup]);

                currentState = ADD_EXERCISE_REQUEST;
                break;
            case EDIT_EXERCISE_REQUEST:
                mExerciseId = intent.getIntExtra(EXTRA_ID, -1);

                mExerciseName = intent.getStringExtra(EXTRA_NAME);
                exTitleIP.setText(mExerciseName);

                mExerciseNotes = intent.getStringExtra(EXTRA_NOTES);
                exNotesIP.setText(mExerciseNotes);

                mMuscleGroup = intent.getIntExtra(EXTRA_MUSCLE_GROUP, -1);
                musclesTv.setText(musclesArr[mMuscleGroup]);

                mReps = intent.getIntExtra(EXTRA_REPS, -1);
                mSets = intent.getIntExtra(EXTRA_SETS, -1);
                setsRepsTv.setText(getString(R.string.sets_reps, mSets, mReps));

                mMaxWeight = intent.getIntExtra(EXTRA_MAX_WEIGHT, -1);
                maxWeightTv.setText(getString(R.string.kg, mMaxWeight));

                currentState = EDIT_EXERCISE_REQUEST;
                break;
            default:
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_input_form, menu);
        return true;
    }

    private void initSetup(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_input_usage);
        setSupportActionBar(mToolbar);

        //For the Back Button, important because you set a custom toolbar
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpFab(){
        ActionButton actionButton = findViewById(R.id.action_button_input_form);
        actionButton.setButtonColor(getResources().getColor(R.color.colorAccent));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.colorAccentDark));
        actionButton.setImageResource(R.drawable.fab_plus_icon);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pullData(currentState);
                finish();
            }
        });
    }

    private void pullData(int requestCode){
        if(requestCode == ADD_EXERCISE_REQUEST){
            String exTitle = exTitleIP.getText().toString();
            String exNotes = exNotesIP.getText().toString();

            exercise = new Exercise(exTitle, exNotes, mMuscleGroup, mMaxWeight, mMaxWeight, mSets, mReps, 0);
            exerciseViewModel.insert(exercise);
            Log.e(LOGTAG, "--------------------------OTHER WAY");
        }else if(requestCode == EDIT_EXERCISE_REQUEST){
            exercise = new Exercise(mExerciseName, mExerciseNotes, mMuscleGroup, mMaxWeight, mMaxWeight, mSets, mReps, 0);
            exercise.setId(mExerciseId);
            exerciseViewModel.update(exercise);
            Log.e(LOGTAG, "--------------------------TRIED TO UPDATE");
        }

    }

    private void underlineEffect(){
        final View exGroupIP = findViewById(R.id.input_form_ex_muscle_gp);
        final View exMaxWeightIP = findViewById(R.id.input_form_ex_max_weight);
        final View exSets_RepsIP = findViewById(R.id.input_form_ex_sets_reps);

        exGroupIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exGroupIP.setBackgroundResource(R.drawable.underborder_active);
                exMaxWeightIP.setBackgroundResource(R.drawable.underborder);
                exSets_RepsIP.setBackgroundResource(R.drawable.underborder);
                showDialogMuscles();
            }
        });

        exMaxWeightIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exGroupIP.setBackgroundResource(R.drawable.underborder);
                exMaxWeightIP.setBackgroundResource(R.drawable.underborder_active);
                exSets_RepsIP.setBackgroundResource(R.drawable.underborder);
                showDialogWeight();
            }
        });


        exSets_RepsIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exGroupIP.setBackgroundResource(R.drawable.underborder);
                exMaxWeightIP.setBackgroundResource(R.drawable.underborder);
                exSets_RepsIP.setBackgroundResource(R.drawable.underborder_active);
                showDialogSetsReps();
            }
        });

        View alteringExercise = findViewById(R.id.input_form_custom_routine);
        AlteringExerciseContents = findViewById(R.id.input_form_altering_exercise_contents);
        AlteringExerciseArrow = findViewById(R.id.input_form_custom_routine_arrow);

        alteringExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AlteringExerciseContents.getVisibility()==View.VISIBLE){
                    AlteringExerciseContents.setVisibility(View.GONE);
                    AlteringExerciseArrow.setImageResource(R.drawable.ic_input_expand);
                }else {
                    AlteringExerciseContents.setVisibility(View.VISIBLE);
                    AlteringExerciseArrow.setImageResource(R.drawable.ic_input_collapse);
                }
            }
        });


    }

    private void showDialogMuscles(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_number_picker_muscles, null);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        NumberPicker numberPickerMuscleG = dialog.findViewById(R.id.number_picker_muscles);


        numberPickerMuscleG.setMinValue(0);
        numberPickerMuscleG.setMaxValue(musclesArr.length-1);
        numberPickerMuscleG.setDisplayedValues(musclesArr);
        numberPickerMuscleG.setValue(tempMuscleGroup);

        numberPickerMuscleG.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempMuscleGroup = newVal;
            }
        });

        musclesTv = findViewById(R.id.input_form_ex_muscle_gp_tv);

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_muscle);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                mMuscleGroup = tempMuscleGroup;
                musclesTv.setText(musclesArr[mMuscleGroup]);
            }
        });
    }

    private void showDialogWeight(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_number_picker_weight, null);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        NumberPicker numberPickerMaxWeight = dialog.findViewById(R.id.number_picker_weight);

        numberPickerMaxWeight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Log.e("WEIGHT", String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                tempMaxWeight = newVal;
            }
        });

        numberPickerMaxWeight.setValue(tempMaxWeight);

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_weight);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                mMaxWeight = tempMaxWeight;
                maxWeightTv.setText(getString(R.string.kg, mMaxWeight));
            }
        });

    }

    private void showDialogSetsReps(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_number_picker_setsreps, null);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        NumberPicker numberPickerSets = dialog.findViewById(R.id.number_picker_sets);
        numberPickerSets.setValue(tempSets);

        NumberPicker numberPickerReps = dialog.findViewById(R.id.number_picker_reps);
        numberPickerReps.setValue(tempReps);

        numberPickerReps.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempReps = newVal;
            }
        });


        numberPickerSets.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempSets = newVal;
            }
        });

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_setsreps);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                mReps = tempReps;
                mSets = tempSets;
                setsRepsTv.setText(getString(R.string.sets_reps, mSets, mReps));
            }
        });
    }

    //TODO: Avoid Recreating new variables in each dialog, and try to reduce memory usage - later update

    //TODO: Add card to view unknown groups or
                                //TODO: add prompt to disable adding unknown group
    //TODO: Add prompt to change unchanged fields and to save changes

    //TODO: BUG FIX: being redirected from the all exercises, the muscle group is already chosen
    //TODO:          but user must rechoose it in order to add the exercise successfully
}
