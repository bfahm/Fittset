package com.xencosworks.fittset.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.xencosworks.fittset.data.ExerciseContract.ExerciseEntry;

public class ExerciseProvider extends ContentProvider {
    private static final String LOG_TAG = ExerciseProvider.class.getSimpleName();
    private ExerciseDbHelper mDbHelper;
    private SQLiteDatabase database;

    private static final int EXERCISES = 100;
    private static final int EXERCISE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ExerciseEntry.CONTENT_AUTHORITY, ExerciseEntry.PATH_ALL_EXERCISES, EXERCISES);
        sUriMatcher.addURI(ExerciseEntry.CONTENT_AUTHORITY, ExerciseEntry.PATH_ALL_EXERCISES + "/#", EXERCISE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ExerciseDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                cursor = database.query(
                        ExerciseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case EXERCISE_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ExerciseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ExerciseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //Set notification on this cursor.
        //Used for notifying the content resolver when any row is changed at this
        //uri (defined up).
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXERCISES:
                database = mDbHelper.getWritableDatabase();
                long result = database.insert(ExerciseEntry.TABLE_NAME, null, contentValues);
                if (result == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    Toast.makeText(getContext(), "Failed to insert row for " + uri, Toast.LENGTH_SHORT).show();
                    return null;
                }
                // Notify the Resolver of a change.
                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, result);
                //return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO: LESSON 4 NODE 8 AND CONT. LESSON 3
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        //TODO: LESSON 4 NODE 8 AND CONT. LESSON 3
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        //TODO: LESSON 4 NODE 8 AND CONT. LESSON 3
        return null;
    }
}
