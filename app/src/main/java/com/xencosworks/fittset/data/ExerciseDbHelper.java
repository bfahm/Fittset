package com.xencosworks.fittset.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;

public class ExerciseDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fittset.db";

    private static final String CREATE_TABLE_INSTRUCTION = "CREATE TABLE " +
            ExerciseEntry.TABLE_NAME + "(" +
            ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ExerciseEntry.COLUMN_EXERCISE_NAME + " TEXT NOT NULL, " +
            ExerciseEntry.COLUMN_EXERCISE_NOTES + " TEXT, " +
            ExerciseEntry.COLUMN_MUSCLE_GROUP + " INTEGER DEFAULT 0, " +
            ExerciseEntry.COLUMN_MAX_WEIGHT + " INTEGER DEFAULT 0, " +
            ExerciseEntry.COLUMN_LAST_WEIGHT + " INTEGER DEFAULT 0, " +
            ExerciseEntry.COLUMN_SETS + " INTEGER DEFAULT 4, " +
            ExerciseEntry.COLUMN_REPS + " INTEGER DEFAULT 10, " +
            ExerciseEntry.COLUMN_CUSTOM_ROUTINE + " INTEGER DEFAULT 0)";

    private static final String DELETE_TABLE_INSTRUCTION =
            "DROP TABLE IF EXISTS " + ExerciseEntry.TABLE_NAME;

    public ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_INSTRUCTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /* Called upon incrementing database version (in code), so versions don't match the ones
            installed on the phone.
            TODO: Implement data migration techniques so users do not use data.
        */
        String DELETE_TABLE = "DROP TABLE"+ ExerciseEntry.TABLE_NAME;
        sqLiteDatabase.execSQL(DELETE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_INSTRUCTION);

    }
}
