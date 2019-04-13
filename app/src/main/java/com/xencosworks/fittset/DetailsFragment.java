package com.xencosworks.fittset;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.widget.Button;
import android.widget.ListView;
import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;
import com.xencosworks.fittset.helpers.ExerciseCursorAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.widget.TextView;


public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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
    public static int idFromParentPage = -5000;
    public static int isEmpty = -1;

    private static String LOGTAG = DetailsFragment.class.getSimpleName();

    private ExerciseCursorAdapter adapter;
    private Uri uri = ExerciseEntry.PATH_ALL_EXERCISES_URI;

    private String textMuscleGPlaceH = "";
    private TextView muscleGPlaceH;

    private View headerContainer;
    private View emptyView;
    private View noContentView;

    private ListView listView;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_details, container, false);

        //Attach listView to Adapter
        listView = rootView.findViewById(R.id.data_list_view);
        adapter = new ExerciseCursorAdapter(getActivity(), null);
        listView.setAdapter(adapter);

        //Kick off the loader
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().initLoader(1, null, this);
        //------------------------------------------------------------------------------------------
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String selection = ExerciseEntry.COLUMN_MUSCLE_GROUP + "=?";
        String[] selectionArgs = {""+ idFromParentPage};
        return new CursorLoader(getActivity(), uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()){
            case 0:
                adapter.swapCursor(cursor);
            case 1:
                int i = cursor.getCount();
                if(i>0){
                    noContentView.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null); // Called at reset (no data needed)
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        muscleGPlaceH = view.findViewById(R.id.details_muscleg_placeholder);
        headerContainer = view.findViewById(R.id.details_header_container);
        headerContainer.setVisibility(View.INVISIBLE);

        emptyView = view.findViewById(R.id.empty_view_no_selection);
        noContentView = view.findViewById(R.id.empty_view_no_content);

        Button newExerciseEmptyContent = view.findViewById(R.id.details_button_add_new_exercise);
        newExerciseEmptyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputForm.class);
                intent.putExtra("txtData", idFromParentPage+"");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        idFromParentPage=-5000;
        isEmpty=-1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        idFromParentPage=-5000;
        isEmpty=-1;
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
                getLoaderManager().restartLoader(0, null, this);
                muscleGPlaceH.setText(textMuscleGPlaceH);

                checkIfContent();

                if(idFromParentPage!=-5000){
                    headerContainer.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }
        }
    }

    private void checkIfContent(){
        if(isEmpty==1){
            noContentView.setVisibility(View.VISIBLE);
        }else if(isEmpty==0) {
            noContentView.setVisibility(View.GONE);
        }
    }

}

//TODO: Use projection at stable release
/*
displayDatabaseInfo -- History


private void displayDatabaseInfo() {
        String selection = ExerciseEntry.COLUMN_MUSCLE_GROUP + "=?";
        String[] selectionArgs = {""+ idFromParentPage};

        --We removed the direct call to the database (getReadableDatabase) and the direct query
        --to that database we called and replaced using the help of the content resolver.

        Cursor cursor = getContentResolver().query(uri, null, selection, selectionArgs, null);

        ListView listView = findViewById(R.id.test_list_view);
        adapter = new ExerciseCursorAdapter(this, cursor);
*/