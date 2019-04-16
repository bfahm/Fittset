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
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import com.scalified.fab.ActionButton;
import com.xencosworks.fittset.Room.Exercise;
import com.xencosworks.fittset.Room.ExerciseViewModel;


public class InputForm extends AppCompatActivity {

    private static String LOGTAG = InputForm.class.getSimpleName();

    private ExerciseViewModel exerciseViewModel;

    private final String[] musclesArray = {"Unspecified", "Chest", "Shoulders", "Back", "Biceps", "Triceps", "Legs", "Abs"};


    // Take input from here
    private EditText exerciseNameEt;
    private EditText exerciseNotesEt;

    // Show input here
    private TextView musclesGroupTv;
    private TextView maxWeightTv;
    private TextView setsRepsTv;

    // Fields to be added/edited to a new exercise object
    private String exerciseName;
    private String exerciseNotes;
    private int muscleGroup = 0;
    private int maxWeight = 0;
    private int exerciseReps = 10;
    private int exerciseSets = 4;

    // IDS to help in routing from different activities.
    public static String EXTRA_ID = "com.xencosworks.fittset.InputForm.EXTRA_ID";
    public static String EXTRA_NAME = "com.xencosworks.fittset.InputForm.EXTRA_NAME";
    public static String EXTRA_NOTES = "com.xencosworks.fittset.InputForm.EXTRA_NOTES";
    public static String EXTRA_MUSCLE_GROUP = "com.xencosworks.fittset.InputForm.EXTRA_MUSCLE_GROUP";
    public static String EXTRA_MAX_WEIGHT = "com.xencosworks.fittset.InputForm.EXTRA_MAX_WEIGHT";
    public static String EXTRA_SETS = "com.xencosworks.fittset.InputForm.EXTRA_SETS";
    public static String EXTRA_REPS = "com.xencosworks.fittset.InputForm.EXTRA_REPS";

    // This activity can be launched via three states.
    public static final int ADD_EXERCISE_REQUEST = 1;
    public static final int EDIT_EXERCISE_REQUEST = 2;
    public static final int EMPTY_DAY_REQUEST = 3;

    private int currentState = ADD_EXERCISE_REQUEST;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        initSetup();
        setUpFab();
        underlineEffect();
        currentState = ADD_EXERCISE_REQUEST; //RESET TO DEFAULT STATE
        intent = null;                       //RESET any previous intents
        intent = getIntent();

        // Call this function to preEdit fields for the user.
        if(intent.hasExtra(EXTRA_ID)){
            currentState = EDIT_EXERCISE_REQUEST;
            setUpFab(); // run again to change fab icon
            customCodeHelper(intent, currentState);
        }else if(intent.hasExtra(EXTRA_MUSCLE_GROUP)) {
            currentState = EMPTY_DAY_REQUEST;
            customCodeHelper(intent, currentState);
        }
    }

    private void initSetup(){
        Toolbar mToolbar = findViewById(R.id.toolbar_input_usage);
        setSupportActionBar(mToolbar);

        //For the Back Button, important because you set a custom toolbar
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //-----------------------------------------------------------------
        exerciseNameEt = findViewById(R.id.input_form_ex_title);
        exerciseNotesEt = findViewById(R.id.input_form_ex_notes);
        musclesGroupTv = findViewById(R.id.input_form_ex_muscle_gp_tv);
        maxWeightTv = findViewById(R.id.input_form_ex_max_weight_tv);
        setsRepsTv = findViewById(R.id.input_form_ex_sets_reps_tv);
    }

    private void setUpFab(){
        ActionButton actionButton = findViewById(R.id.action_button_input_form);
        actionButton.setButtonColor(getResources().getColor(R.color.colorAccent));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.colorAccentDark));
        actionButton.setImageResource(R.drawable.fab_plus_icon);
        if (currentState == EDIT_EXERCISE_REQUEST){
            actionButton.setImageResource(R.drawable.ic_edit_white_24dp);
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pullData(currentState);
            }
        });
    }

    private void pullData(int requestCode){
        //Fetch unUpdated data first
        exerciseName = exerciseNameEt.getText().toString();
        exerciseNotes = exerciseNotesEt.getText().toString();
        //Other fields are already updated  via @showDialog() functions

        switch (requestCode){
            // Both cases needs adding a new exercise
            case EMPTY_DAY_REQUEST:
            case ADD_EXERCISE_REQUEST:
                if(exerciseName.length()!=0 && maxWeight!=0 && muscleGroup!=0){
                    exerciseViewModel.insert(new Exercise(exerciseName, exerciseNotes,
                            muscleGroup, maxWeight, maxWeight, exerciseSets, exerciseReps, 0));
                    Toast.makeText(this, "New Exercise Added", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(this, "Make sure to update required fields first", Toast.LENGTH_LONG).show();
                }

                break;

            // But this case needs editing an existing exercise
            case EDIT_EXERCISE_REQUEST:
                if(exerciseName.length()!=0 && maxWeight!=0 && muscleGroup!=0){
                    Exercise exercise = new Exercise(exerciseName, exerciseNotes,
                            muscleGroup, maxWeight, maxWeight, exerciseSets, exerciseReps, 0);
                    exercise.setId(intent.getIntExtra(EXTRA_ID, -1));
                    exerciseViewModel.update(exercise);
                    Toast.makeText(this, "Exercise Updated", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(this, "Make sure to update required fields first", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void customCodeHelper(Intent intent, int requestCode){
        switch (requestCode){
            case EMPTY_DAY_REQUEST:
                muscleGroup = intent.getIntExtra(EXTRA_MUSCLE_GROUP, 0);
                refreshUi();
                break;
            case EDIT_EXERCISE_REQUEST:
                exerciseName = intent.getStringExtra(EXTRA_NAME);
                exerciseNotes = intent.getStringExtra(EXTRA_NOTES);
                muscleGroup = intent.getIntExtra(EXTRA_MUSCLE_GROUP, 0);
                maxWeight = intent.getIntExtra(EXTRA_MAX_WEIGHT, 0);
                exerciseSets = intent.getIntExtra(EXTRA_SETS, 0);
                exerciseReps = intent.getIntExtra(EXTRA_REPS, 0);
                refreshUi();
        }
    }

    private void refreshUi() {
        exerciseNameEt.setText(exerciseName);
        exerciseNotesEt.setText(exerciseNotes);
        musclesGroupTv.setText(musclesArray[muscleGroup]);
        maxWeightTv.setText(getString(R.string.kg, maxWeight));
        setsRepsTv.setText(getString(R.string.sets_reps, exerciseSets, exerciseReps));
    }

    /**
     * @underlineEffect() handles drawing an altering effect when user taps different textboxes
     * and then calls the @showDialog() functions which in turn pull data which user inputed and
     * updates the main InputForm.java class fields initialized with default values on top of
     * the document.*/

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
    }

    private void showDialogMuscles(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_number_picker_muscles, null);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        NumberPicker numberPickerMuscleG = dialog.findViewById(R.id.number_picker_muscles);


        numberPickerMuscleG.setMinValue(0);
        numberPickerMuscleG.setMaxValue(musclesArray.length-1);
        numberPickerMuscleG.setDisplayedValues(musclesArray);
        numberPickerMuscleG.setValue(muscleGroup);

        numberPickerMuscleG.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                muscleGroup = newVal;
            }
        });

        musclesGroupTv = findViewById(R.id.input_form_ex_muscle_gp_tv);

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_muscle);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                musclesGroupTv.setText(musclesArray[muscleGroup]);
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
                maxWeight = newVal;
            }
        });

        numberPickerMaxWeight.setValue(maxWeight);

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_weight);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                maxWeightTv.setText(getString(R.string.kg, maxWeight));
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
        numberPickerSets.setValue(exerciseSets);

        NumberPicker numberPickerReps = dialog.findViewById(R.id.number_picker_reps);
        numberPickerReps.setValue(exerciseReps);

        numberPickerReps.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                exerciseReps = newVal;
            }
        });


        numberPickerSets.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                exerciseSets = newVal;
            }
        });

        Button confirmButton = dialog.findViewById(R.id.input_form_dialog_choose_setsreps);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                setsRepsTv.setText(getString(R.string.sets_reps, exerciseSets, exerciseReps));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_input_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_input_delete_exercise:
                Exercise exercise = new Exercise(null, null, 0, 0, 0, 0, 0, 0);
                exercise.setId(intent.getIntExtra(EXTRA_ID, -1));
                exerciseViewModel.delete(exercise);
                Toast.makeText(this, "Deleted this exercise", Toast.LENGTH_LONG).show();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    //TODO: Add card to view unknown groups or
                                //TODO: add prompt to disable adding unknown group
    //TODO: Add prompt to change unchanged fields and to save changes
    //TODO: Altering Exercise option.
}
