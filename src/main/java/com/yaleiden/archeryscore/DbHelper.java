package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DbHelper extends SQLiteOpenHelper {
	private static final String TAG = "DbOpenHelper";

	// public constructor
	public DbHelper(Context context)// String name, CursorFactory factory, int
									// version)
	{
		super(context, DatabaseSet.DATABASE_NAME, null, DatabaseSet.DB_VERSION);
	}// end db open helper constructor

	// creates the contacts table when the db is created
	@Override
	public void onCreate(SQLiteDatabase db) {

		// query to create a new table named moisture ******may need to add
		// DatabaseConnector. to the
		// front of each field name
		String createQuery = "CREATE TABLE " + DatabaseSet.SHOOTS_TABLE + " "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "Name TEXT,"
				+ "BowType TEXT," + "Division TEXT," + "Total INTEGER,"
				+ "ShootName TEXT," + "Date TEXT," + "SortDate TEXT,"
				+ "CourseName TEXT," + "Targets Text," + "Scoring TEXT,"
				+ "T1 INTEGER," + "T2 INTEGER," + "T3 INTEGER," + "T4 INTEGER,"
				+ "T5 INTEGER," + "T6 INTEGER," + "T7 INTEGER," + "T8 INTEGER,"
				+ "T9 INTEGER," + "T10 INTEGER," + "T11 INTEGER,"
				+ "T12 INTEGER," + "T13 INTEGER," + "T14 INTEGER,"
				+ "T15 INTEGER," + "T16 INTEGER," + "T17 INTEGER,"
				+ "T18 INTEGER," + "T19 INTEGER," + "T20 INTEGER,"
				+ "T21 INTEGER," + "T22 INTEGER," + "T23 INTEGER,"
				+ "T24 INTEGER," + "T25 INTEGER," + "T26 INTEGER," 
				+ "T27 INTEGER," + "T28 INTEGER," + "T29 INTEGER," 
				+ "T30 INTEGER," + "T31 INTEGER," + "T32 INTEGER," 
				+ "T33 INTEGER," + "T34 INTEGER," + "T35 INTEGER," 
				+ "T36 INTEGER," + "T37 INTEGER," + "T38 INTEGER," 
				+ "T39 INTEGER," + "T40 INTEGER," + "T41 INTEGER," 
				+ "T42 INTEGER," + "T43 INTEGER," + "T44 INTEGER," 
				+ "T45 INTEGER," + "T46 INTEGER," + "T47 INTEGER," 
				+ "T48 INTEGER," + "T49 INTEGER,"
				+ "T50 INTEGER);";
		
		String archiveQuery = "CREATE TABLE " + DatabaseSet.ARCHIVE_TABLE + " "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "Name TEXT,"
				+ "BowType TEXT," + "Division TEXT," + "Total INTEGER,"
				+ "ShootName TEXT," + "Date TEXT," + "SortDate TEXT,"
				+ "CourseName TEXT," + "Targets Text," + "Scoring TEXT,"
				+ "T1 INTEGER," + "T2 INTEGER," + "T3 INTEGER," + "T4 INTEGER,"
				+ "T5 INTEGER," + "T6 INTEGER," + "T7 INTEGER," + "T8 INTEGER,"
				+ "T9 INTEGER," + "T10 INTEGER," + "T11 INTEGER,"
				+ "T12 INTEGER," + "T13 INTEGER," + "T14 INTEGER,"
				+ "T15 INTEGER," + "T16 INTEGER," + "T17 INTEGER,"
				+ "T18 INTEGER," + "T19 INTEGER," + "T20 INTEGER,"
				+ "T21 INTEGER," + "T22 INTEGER," + "T23 INTEGER,"
				+ "T24 INTEGER," + "T25 INTEGER," + "T26 INTEGER," 
				+ "T27 INTEGER," + "T28 INTEGER," + "T29 INTEGER," 
				+ "T30 INTEGER," + "T31 INTEGER," + "T32 INTEGER," 
				+ "T33 INTEGER," + "T34 INTEGER," + "T35 INTEGER," 
				+ "T36 INTEGER," + "T37 INTEGER," + "T38 INTEGER," 
				+ "T39 INTEGER," + "T40 INTEGER," + "T41 INTEGER," 
				+ "T42 INTEGER," + "T43 INTEGER," + "T44 INTEGER," 
				+ "T45 INTEGER," + "T46 INTEGER," + "T47 INTEGER," 
				+ "T48 INTEGER," + "T49 INTEGER,"
				+ "T50 INTEGER," + "Average TEXT," + "Percentage TEXT," + "Possible INTEGER);";

		String nameQuery = "CREATE TABLE " + DatabaseSet.NAME_TABLE + " "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "BowType TEXT," + "Division TEXT," 
				+ "ShootName TEXT," + "CourseName TEXT," + "Name TEXT);";
		db.execSQL(nameQuery);
		db.execSQL(createQuery); // execute the query
		db.execSQL(archiveQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "onUpgrade from" + oldVersion + " to " + newVersion);
		}
		db.execSQL("drop table if exists " + DatabaseSet.SHOOTS_TABLE);
		db.execSQL("drop table if exists " + DatabaseSet.ARCHIVE_TABLE);
		db.execSQL("drop table if exists " + DatabaseSet.NAME_TABLE);
		onCreate(db);
	} // end method on upgrade

}// end class DatabaseOpenHelper
