package com.xencosworks.fittset.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ExerciseContract {

    public ExerciseContract() {
    }

    public static class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "Exercises";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EXERCISE_NAME = "name";
        public static final String COLUMN_EXERCISE_NOTES = "notes";
        public static final String COLUMN_MUSCLE_GROUP = "muscleFamily";
        public static final String COLUMN_MAX_WEIGHT = "maxWeight";
        public static final String COLUMN_LAST_WEIGHT = "lastWeight";
        public static final String COLUMN_SETS = "sets";
        public static final String COLUMN_REPS = "reps";
        public static final String COLUMN_CUSTOM_ROUTINE = "customRoutine";


        public static final int MUSCLE_G_UNKNOWN = 0;
        public static final int MUSCLE_G_CHEST = 1;
        public static final int MUSCLE_G_SHOULDERS = 2;
        public static final int MUSCLE_G_BACK = 3;
        public static final int MUSCLE_G_BI = 4;
        public static final int MUSCLE_G_TRI = 5;
        public static final int MUSCLE_G_LEGS = 6;
        public static final int MUSCLE_G_ABS = 7;

        // Same as what is in the manifest file.
        public static final String CONTENT_AUTHORITY = "com.xencosworks.fittset";
        public static final String PATH_ALL_EXERCISES = TABLE_NAME; // default path for editing and adding to the table
        public static final String PATH_MUSCLE_DAYS = "mDays";  // custom path for accessing certain types of exercises
        // TODO: add more paths for further customization and filtering in the table viewing.

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final Uri PATH_ALL_EXERCISES_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALL_EXERCISES);
        public static final Uri MUSCLE_DAYS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MUSCLE_DAYS);

        /*public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + PATH_PETS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + PATH_PETS;*/
    }
}
