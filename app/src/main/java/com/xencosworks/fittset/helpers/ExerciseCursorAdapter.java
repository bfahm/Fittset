package com.xencosworks.fittset.helpers;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;
import com.xencosworks.fittset.R;


public class ExerciseCursorAdapter extends CursorAdapter {

    public ExerciseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.inflator_details_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = view.findViewById(R.id.inflator_details_title);
        TextView tvCustRout = view.findViewById(R.id.inflator_details_custom_routine);
        TextView tvMaxW = view.findViewById(R.id.inflator_details_max_weight);
        TextView tvLastW = view.findViewById(R.id.inflator_details_last_weight);

        String exTitle = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseEntry.COLUMN_EXERCISE_NAME));
        //TODO: not currently setup
        int exCustRout = cursor.getInt(cursor.getColumnIndexOrThrow(ExerciseEntry.COLUMN_CUSTOM_ROUTINE));
        //TODO: Programmatically color background depending on achievement
        int exMaxW = cursor.getInt(cursor.getColumnIndexOrThrow(ExerciseEntry.COLUMN_MAX_WEIGHT));
        //TODO: Programmatically color fontColor depending on achievement
        String exLastW = cursor.getString(cursor.getColumnIndexOrThrow(ExerciseEntry.COLUMN_LAST_WEIGHT));

        //Data Customization for UX
        String exCustRout_edit = "No Routines Yet";
        String exMaxW_edit = exMaxW + " KG";
        String exLastW_edit = exLastW + " KG";

        //Binding Data

        tvTitle.setText(exTitle);
        tvCustRout.setText(exCustRout_edit);
        tvMaxW.setText(exMaxW_edit);
        tvLastW.setText(exLastW_edit);
    }
}
