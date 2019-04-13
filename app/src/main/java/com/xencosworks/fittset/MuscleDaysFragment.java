package com.xencosworks.fittset;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;

public class MuscleDaysFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static String LOGTAG = MuscleDaysFragment.class.getSimpleName();
    private View rootView;
    private Uri uri = ExerciseEntry.PATH_ALL_EXERCISES_URI;

    private OnButtonClickListener mOnButtonClickListener;

    private TextView chestCount;
    private TextView shouldersCount;
    private TextView backCount;
    private TextView biCount;
    private TextView triCount;
    private TextView legsCount;
    private TextView absCount;

    private int iChest;
    private int iShoulders;
    private int iBack;
    private int iBi;
    private int iTri;
    private int iLegs;
    private int iAbs;

    private String sChest = "No exercises yet";
    private String sShoulders = "No exercises yet";
    private String sBack = "No exercises yet";
    private String sBiceps = "No exercises yet";
    private String sTriceps = "No exercises yet";
    private String sLegs = "No exercises yet";
    private String sAbs = "No exercises yet";

    private String[] sArray = {sChest, sShoulders, sBack, sBiceps, sTriceps, sLegs, sAbs};
    private int[] iArray = {iChest, iShoulders, iBack, iBi, iTri, iLegs, iAbs};


    public MuscleDaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_muscle_days, container, false);
        setupCards();

        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_CHEST, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_SHOULDERS, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_BACK, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_BI, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_TRI, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_LEGS, null, this);
        getLoaderManager().initLoader(ExerciseEntry.MUSCLE_G_ABS, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = ExerciseEntry.COLUMN_MUSCLE_GROUP + "=?";
        String[] selectionArgs = {""+ id};
        return new CursorLoader(getActivity(), uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chestCount = view.findViewById(R.id.muscle_days_chest_count);
        shouldersCount = view.findViewById(R.id.muscle_days_shoulders_count);
        backCount = view.findViewById(R.id.muscle_days_back_count);
        biCount = view.findViewById(R.id.muscle_days_bi_count);
        triCount = view.findViewById(R.id.muscle_days_tri_count);
        legsCount = view.findViewById(R.id.muscle_days_legs_count);
        absCount = view.findViewById(R.id.muscle_days_abs_count);

        inflateCountingData();
    }

    private void inflateCountingData(){
        iArray = new int[]{iChest, iShoulders, iBack, iBi, iTri, iLegs, iAbs};
        int codeCounter = 0;
        for(int i=0; i<7; i++){
            if(iArray[i]!=0) {
                //TODO: Apply the right string layout.
                sArray[i] = iArray[i] + " Exercises";
            }else {
                codeCounter+=1;
            }
        }
        int codeNoDataYet = -1;
        if (codeCounter==7){
            codeNoDataYet =1;
        }else{
            codeNoDataYet =0;
        }

        TextView[] tvArray = {chestCount, shouldersCount, backCount, biCount, triCount, legsCount, absCount};

        if(codeNoDataYet ==1){
            for(int i=0; i<7; i++){
                tvArray[i].setVisibility(View.GONE);
            }
        }else {
            for(int i=0; i<7; i++){
                tvArray[i].setVisibility(View.VISIBLE);
            }
        }
        for(int i=0; i<7; i++){
            tvArray[i].setText(sArray[i]);
        }
    }

    private int isCurrentEmpty(int exID){
        switch(exID) {
            case ExerciseEntry.MUSCLE_G_CHEST: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_SHOULDERS: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_BACK: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_BI: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_TRI: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_LEGS: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            case ExerciseEntry.MUSCLE_G_ABS: {
                if(iArray[exID-1]==0) {
                    return 1;
                }
            }
            default:
                return 0;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            case ExerciseEntry.MUSCLE_G_CHEST: {
                iChest = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_SHOULDERS: {
                iShoulders = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_BACK: {
                iBack = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_BI: {
                iBi = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_TRI: {
                iTri = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_LEGS: {
                iLegs = data.getCount();
                break;
            }
            case ExerciseEntry.MUSCLE_G_ABS: {
                iAbs = data.getCount();
                break;
            }
        }
        inflateCountingData();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

    private void setupCards(){
        View chestCard = rootView.findViewById(R.id.muscle_days_chest_card);
        chestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view, ExerciseEntry.MUSCLE_G_CHEST, isCurrentEmpty(ExerciseEntry.MUSCLE_G_CHEST));
            }
        });

        View shouldersCard = rootView.findViewById(R.id.muscle_days_shoulders_card);
        shouldersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_SHOULDERS, isCurrentEmpty(ExerciseEntry.MUSCLE_G_SHOULDERS));
            }
        });

        View backCard = rootView.findViewById(R.id.muscle_days_back_card);
        backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_BACK, isCurrentEmpty(ExerciseEntry.MUSCLE_G_BACK));
            }
        });

        View biCard = rootView.findViewById(R.id.muscle_days_bi_card);
        biCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_BI, isCurrentEmpty(ExerciseEntry.MUSCLE_G_BI));
            }
        });

        View triCard = rootView.findViewById(R.id.muscle_days_tri_card);
        triCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_TRI, isCurrentEmpty(ExerciseEntry.MUSCLE_G_TRI));
            }
        });

        View legsCard = rootView.findViewById(R.id.muscle_days_legs_card);
        legsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_LEGS, isCurrentEmpty(ExerciseEntry.MUSCLE_G_LEGS));
            }
        });

        View absCard = rootView.findViewById(R.id.muscle_days_abs_card);
        absCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onButtonClicked(view,ExerciseEntry.MUSCLE_G_ABS, isCurrentEmpty(ExerciseEntry.MUSCLE_G_ABS));
            }
        });

    }

    interface OnButtonClickListener{
        void onButtonClicked(View view, int code, int isEmpty);
    }

}

/*TODO: to be used in list_item.xml
private View expandableContent;
private ImageView expandableToggle;
private Button detailsButton;
private void setupExpandableCards(){
        expandableContent = findViewById(R.id.muscle_days_expandable);
        expandableToggle = findViewById(R.id.muscle_days_expandable_button);

        expandableToggle.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        if (expandableContent.getVisibility()== View.VISIBLE){
        expandableContent.setVisibility(View.GONE);
        expandableToggle.setImageResource(R.drawable.ic_input_expand);
        }else {
        expandableContent.setVisibility(View.VISIBLE);
        expandableToggle.setImageResource(R.drawable.ic_input_collapse);
        }
        }
        });
        }
*/
