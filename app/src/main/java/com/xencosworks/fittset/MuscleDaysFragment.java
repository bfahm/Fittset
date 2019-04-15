package com.xencosworks.fittset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MuscleDaysFragment extends Fragment{
    private static String LOGTAG = MuscleDaysFragment.class.getSimpleName();
    private View rootView;

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

        return rootView;
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


}
