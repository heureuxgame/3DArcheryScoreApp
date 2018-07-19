package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseSet {
	public static final String LOG_TAG = "DatabaseSet ";
	public static final String DATABASE_NAME = "scores.db";
	public static final int DB_VERSION = 6;
	public static final String SHOOTS_TABLE = "shoots";
	public static final String ARCHIVE_TABLE = "archive";
	public static final String NAME_TABLE = "names";
	SQLiteDatabase db;
	final DbHelper dbHelper;
	final Context context;

	public DatabaseSet(Context context) {
		if(BuildConfig.DEBUG) {
			Log.d(LOG_TAG, "  context called");
		}
		this.context = context;
		dbHelper = new DbHelper(context);
		if(BuildConfig.DEBUG) {
			Log.d(LOG_TAG, "  after context called");
		}
	}

	public void open() throws SQLException {
		// create or open a database for reading/writing
		db = dbHelper.getWritableDatabase();
	} // end method open

	// close database connection
	public void close() {
		if (db != null)
			db.close(); // close connection
	}// end method close

	public int delete(int id) {
		open();
		db.delete(DatabaseSet.SHOOTS_TABLE, null, null);
		db.delete(DatabaseSet.ARCHIVE_TABLE, null, null);
		db.delete(DatabaseSet.NAME_TABLE, null, null);
		//********************  does the other table need to be in here, too???
		close();
		return id;
	}

}// end class DatabaseConnector
