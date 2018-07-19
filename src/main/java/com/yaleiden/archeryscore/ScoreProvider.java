package com.yaleiden.archeryscore;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

// DATA/data/ArcheryScore/databases/shoot
public class ScoreProvider extends ContentProvider {
    private static final String TAG = "ScoreProvider";
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".ScoreProvider";
    private static final String SCORE_AUTHORITY = "content://" + BuildConfig.APPLICATION_ID +".ScoreProvider/shoots";
    public static final Uri SCORE_URI = Uri.parse(SCORE_AUTHORITY);
    private static final String ARCHIVE_AUTHORITY = "content://" + BuildConfig.APPLICATION_ID +".ScoreProvider/archive";
    public static final Uri ARCHIVE_URI = Uri.parse(ARCHIVE_AUTHORITY);
    private static final String NAME_AUTHORITY = "content://" + BuildConfig.APPLICATION_ID + ".ScoreProvider/names";
    public static final Uri NAME_URI = Uri.parse(NAME_AUTHORITY);
    private static final int ScoreCode = 1;
    private static final int ArchiveCode = 2;
    private static final int NameCode = 3;
    private SQLiteDatabase db;
    private DbHelper dbHelper;


    private final static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "shoots", ScoreCode);
        uriMatcher.addURI(AUTHORITY, "archive", ArchiveCode);
        uriMatcher.addURI(AUTHORITY, "names", NameCode);
    }

    @Override
    public boolean onCreate() {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "  oncreate called");
        }
        dbHelper = new DbHelper(getContext());
        if(BuildConfig.DEBUG) {
            Log.d(TAG, " oncreate after dbhelper");
        }
        return true;
    }

    @Override
    public String getType(Uri uri) { // returns a MIME type string, or null if
        // there is no type.
        // we can ignore this...more for searching unknown content providers
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, " insert method ");
        }
        db = dbHelper.getWritableDatabase();
        Long id;
        switch (uriMatcher.match(uri)) {
            case ScoreCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " insert Score urimatch ");
                }
                id = db.insertWithOnConflict(DatabaseSet.SHOOTS_TABLE, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);

                if (id != -1)
                    uri = Uri.withAppendedPath(uri, Long.toString(id));
                getContext().getContentResolver().notifyChange(uri, null); ///this is the new thingy for the observer
                break;

            case ArchiveCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " insert Archive urimatch ");
                }
                id = db.insertWithOnConflict(DatabaseSet.ARCHIVE_TABLE, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);

                if (id != -1)
                    uri = Uri.withAppendedPath(uri, Long.toString(id));
                getContext().getContentResolver().notifyChange(uri, null); ///this is the new thingy for the observer
                break;


            case NameCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " insert Name urimatch ");
                }
                id = db.insertWithOnConflict(DatabaseSet.NAME_TABLE, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);

                if (id != -1)
                    uri = Uri.withAppendedPath(uri, Long.toString(id));
                break;
        }
        db.close();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, " insert method after db close");
        }
        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        System.out.println("at bulkinsert");
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        switch (uriMatcher.match(uri)) {
            case ScoreCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " bulk insert Score if ");
                }
                try {
                    for (ContentValues cv : values) {
                        long newID = db.insertOrThrow(DatabaseSet.SHOOTS_TABLE,
                                null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into "
                                    + uri);
                        }
                    }
                    db.setTransactionSuccessful();

                    numInserted = values.length;
                } finally {
                    db.endTransaction();
                }

                break;
            case ArchiveCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " bulk insert Archive if ");
                }
                try {
                    for (ContentValues cv : values) {
                        long newID = db.insertOrThrow(DatabaseSet.ARCHIVE_TABLE,
                                null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into "
                                    + uri);
                        }
                    }
                    db.setTransactionSuccessful();

                    numInserted = values.length;
                } finally {
                    db.endTransaction();
                }
                break;
            case NameCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " bulk insert Name if ");
                }
                try {
                    for (ContentValues cv : values) {
                        long newID = db.insertOrThrow(DatabaseSet.NAME_TABLE,
                                null, cv);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into "
                                    + uri);
                        }
                    }
                    db.setTransactionSuccessful();

                    numInserted = values.length;
                } finally {
                    db.endTransaction();
                }
                break;
        }
        return numInserted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delID = 0;
        db = dbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) == ScoreCode) {
            int cursor = db.delete(DatabaseSet.SHOOTS_TABLE, selection,
                    selectionArgs);
            getContext().getContentResolver().notifyChange(SCORE_URI,null);  ///contentobserver thingy

        }
        if (uriMatcher.match(uri) == ArchiveCode) {
            int cursor = db.delete(DatabaseSet.ARCHIVE_TABLE, selection,
                    selectionArgs);
            getContext().getContentResolver().notifyChange(SCORE_URI,null);  ///contentobserver thingy
        }
        if (uriMatcher.match(uri) == NameCode) {
            int cursor = db.delete(DatabaseSet.NAME_TABLE, selection,
                    selectionArgs);
        }
        return delID;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase(); // projection = columns to return
        // "null" gets all **may
        Cursor cursor = null; // "null" gets all **may// need to be {null}
        switch (uriMatcher.match(uri)) {

            case ScoreCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " query com.yaleiden.archeryscorecloud.Scores if");
                }
                cursor = db.query(DatabaseSet.SHOOTS_TABLE, projection,
                        selection, selectionArgs, null, null, sortOrder); // selection is
                // "where", Args are
                // statement, null,
                // null, order by
                cursor.setNotificationUri(getContext().getContentResolver(), uri);  ///contentobserver thingy
                break;
            case ArchiveCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " query Archive if ");
                }
                cursor = db.query(DatabaseSet.ARCHIVE_TABLE, projection, selection,
                        selectionArgs, null, null, sortOrder); // selection is
                // "where", Args are
                // statement, null,
                // null, order by
                cursor.setNotificationUri(getContext().getContentResolver(), uri);  ///contentobserver thingy
                break;
            case NameCode:
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " query Name if ");
                }
                cursor = db.query(DatabaseSet.NAME_TABLE, projection, selection,
                        selectionArgs, null, null, sortOrder); // selection is
                // "where", Args are
                // statement, null,
                // null, order by
                break;
        }
        return cursor;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.content.ContentProvider#update(android.net.Uri,
     * android.content.ContentValues, java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        db = dbHelper.getWritableDatabase(); // projection = columns to return
        // "null" gets all **may
        // need to be {null}
        if (uriMatcher.match(uri) == ScoreCode) {
            int cursor = db.update(DatabaseSet.SHOOTS_TABLE, values,
                    selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null); ///this is the new thingy for the observer
        }
        if (uriMatcher.match(uri) == ArchiveCode) {
            int cursor = db.update(DatabaseSet.ARCHIVE_TABLE, values,
                    selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null); ///this is the new thingy for the observer
        }
        if (uriMatcher.match(uri) == NameCode) {
            int cursor = db.update(DatabaseSet.NAME_TABLE, values,
                    selection, selectionArgs);
        }
        return 0;
    }

}
