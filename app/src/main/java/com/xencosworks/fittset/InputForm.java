package com.xencosworks.fittset;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
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
import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;



public class InputForm extends AppCompatActivity {

    private static String LOGTAG = InputForm.class.getSimpleName();

    private View AlteringExerciseContents;
    private ImageView AlteringExerciseArrow;
    private TextView musclesTv;

    private int tempMuscleGroup = 0;
    private int mMuscleGroup=0;

    private int tempMaxWeight = 1;
    private int mMaxWeight=0;

    private int tempSets = 4;
    private int mSets=0;

    private int tempReps = 10;
    private int mReps=0;

    private Uri uri = ExerciseEntry.PATH_ALL_EXERCISES_URI;

    private int customCode = -1;

    private final String[] musclesArr = {"Unspecified", "Chest", "Shoulders", "Back", "Biceps", "Triceps", "Legs", "Abs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);
        initSetup();
        setUpFab();
        defineContent();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            String txtData = bundle.getString("txtData", "No value received");
            customCode = Integer.parseInt(txtData);
            customCodeHelper();
        }else{
            customCode= 0;
            customCodeHelper();
        }
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

    private void customCodeHelper(){
        tempMuscleGroup = customCode;
        musclesTv = findViewById(R.id.input_form_ex_muscle_gp_tv);
        musclesTv.setText(musclesArr[customCode]);
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
                pullData();
                finish();
            }
        });
    }

    private void pullData(){
        EditText exTitleIP = findViewById(R.id.input_form_ex_title);
        EditText exNotesIP = findViewById(R.id.input_form_ex_notes);

        String exTitle = exTitleIP.getText().toString();
        String exNotes = exNotesIP.getText().toString();

        ContentValues values = new ContentValues();
        values.put(ExerciseEntry.COLUMN_EXERCISE_NAME, exTitle);
        values.put(ExerciseEntry.COLUMN_EXERCISE_NOTES, exNotes);
        values.put(ExerciseEntry.COLUMN_MUSCLE_GROUP, mMuscleGroup);
        values.put(ExerciseEntry.COLUMN_MAX_WEIGHT, mMaxWeight);
        values.put(ExerciseEntry.COLUMN_LAST_WEIGHT, mMaxWeight);
        values.put(ExerciseEntry.COLUMN_SETS, mSets);
        values.put(ExerciseEntry.COLUMN_REPS, mReps);

        Uri newUri = getContentResolver().insert(uri, values);
    }

    private void defineContent(){
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

        final TextView maxWeightTv = findViewById(R.id.input_form_ex_max_weight_tv);

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

        final TextView setsRepsTv = findViewById(R.id.input_form_ex_sets_reps_tv);

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
}
