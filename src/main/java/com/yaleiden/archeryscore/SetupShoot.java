package com.yaleiden.archeryscore;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class SetupShoot extends AppCompatActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = "SetupShoot";
	//private EditText editTextShoot1;
	private EditText editTextCourse;
	private EditText editTextTargets1;
	private Spinner spinnerScores;
	// private Button button1;
	private String USER_SHOOT;
	private String USER_COURSE;
	private String USER_TARGETS;
	private int USER_SCORING;
	//Context context = this;
	private AutoCompleteTextView AutotextView;
	private static final String[] FROM = new String[] { "ShootName", "_id" };
	private static final int[] TO = new int[] { R.id.text };
	private SimpleCursorAdapter ShootAdapter; // adapter
	private static final int ShootLoaderID = 4;
	// private static final int FilterLoaderID = 3;
	private String filterSelect;
	// private String[] projection;
	private Cursor filterCursor;
	private ScrollView ScrollView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, " Oncreate");
		}
		setContentView(R.layout.setup_shoot);
		
		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setTitle(getString(R.string.setup_shoot));
		ScrollView1 = (ScrollView) findViewById(R.id.ScrollView1);
		BackgroundSetter mSetter = new BackgroundSetter(this, ScrollView1);
		mSetter.applyBkg();
		SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
				0);
		USER_SHOOT = app_preferences.getString("USER_SHOOT", "Default");
		USER_COURSE = app_preferences.getString("USER_COURSE", "Default");
		USER_TARGETS = app_preferences.getString("USER_TARGETS", "20");
		USER_SCORING = app_preferences.getInt("USER_SCORING", 0);

		if(BuildConfig.DEBUG) {
			Log.d(TAG, " before AutoCompleteTextView");
		}
		TextView textViewTargets = (TextView) findViewById(R.id.textViewTargets);
		textViewTargets.setText(this.getString(R.string.number_of_targets) + " (Max 50)");
		AutotextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteShoot);
		//editTextShoot1 = (EditText) findViewById(R.id.editTextShoot1);
		editTextCourse = (EditText) findViewById(R.id.editTextCourse);
		editTextTargets1 = (EditText) findViewById(R.id.editTextTargets1);
		spinnerScores = (Spinner) findViewById(R.id.spinnerScores);
		
		ScoreArrays scoreArrays = new ScoreArrays();
		ArrayAdapter<String> scoreAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.spinner_custom_scoring, scoreArrays.getScoringText());
		
		spinnerScores.setAdapter(scoreAdapter);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, " before setting edittext");
		}
		//editTextShoot1.setText(USER_SHOOT);
		editTextCourse.setText(USER_COURSE);
		editTextTargets1.setText(USER_TARGETS);
		spinnerScores.setSelection(USER_SCORING);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, " after setting edittexts");
		}

		getSupportLoaderManager().initLoader(ShootLoaderID, null, this);

		ShootAdapter = new SimpleCursorAdapter(this,
				R.layout.list_item_custom_autocomplete, null, FROM, TO, 1);
		ShootAdapter.setCursorToStringConverter(new CursorToStringConverter() {
			@Override
			public CharSequence convertToString(Cursor cursor) {
				return cursor.getString(0);
			}
		});

		ShootAdapter.setFilterQueryProvider(new FilterQueryProvider() {

			@Override
			public Cursor runQuery(CharSequence constraint) {
				filterSelect = "ShootName LIKE '" + constraint + "%'";
				if(BuildConfig.DEBUG) {
					Log.d(TAG, "filterSelect " + filterSelect);
				}

				filterCursor = getContentResolver().query(
						ScoreProvider.NAME_URI, FROM, filterSelect, null, null);

				return filterCursor;
			}

		});

		AutotextView.setAdapter(ShootAdapter);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "after Autotext set ShootAdapter");
		}
		getSupportLoaderManager().restartLoader(ShootLoaderID,
				savedInstanceState, SetupShoot.this);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "after getsupporloadermanager");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_main_page) {
			startActivity(new Intent(this, MainActivity.class));
		} else if (itemId == R.id.menu_archive) {
			//startActivity(new Intent(this, ViewArchiveList.class));
			startActivity(new Intent(this, ViewArchive.class));
		} else if (itemId == R.id.action_settings) {
			startActivity(new Intent(this, Settings.class));
		}	else if (itemId == R.id.menu_help) {
				startActivity(new Intent(this, Help.class));
			 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void saveShoot(View v) {
		USER_SHOOT = AutotextView.getText().toString();
		//USER_SHOOT = editTextShoot1.getText().toString();
		USER_COURSE = editTextCourse.getText().toString();
		USER_TARGETS = editTextTargets1.getText().toString();
		USER_SCORING = spinnerScores.getSelectedItemPosition();
		SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
				0);
		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putString("USER_SHOOT", USER_SHOOT);
		editor.putString("USER_COURSE", USER_COURSE);
		editor.putString("USER_TARGETS", USER_TARGETS.trim());
		editor.putInt("USER_SCORING", USER_SCORING);
		editor.commit();

		ContentValues newName = new ContentValues();
		newName.put("ShootName", USER_SHOOT);
		newName.put("Division", "SHOOT");
		getContentResolver().insert(ScoreProvider.NAME_URI, newName);
		removeDuplicates();
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "after getcontentresolver..MainActivity intent next");
		}
		startActivity(new Intent(this, SetupArchers.class));
	}

	private int removeDuplicates() {

		int rowsDeleted = 0;
		String DSELECTION = "Division = 'SHOOT' AND _id NOT IN (SELECT MIN(_id) FROM "
				+ DatabaseSet.NAME_TABLE + " GROUP BY ShootName)";
		
		getContentResolver().delete(ScoreProvider.NAME_URI, DSELECTION, null);

		return rowsDeleted;
	}
	/*
	@SuppressLint("NewApi")
	public void applyBkg(int bkg_selected) {
		//int appBkg =0;
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "applyBkg " + bkg_selected);
		}
		
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			ScrollView1.setBackgroundDrawable(getResources().getDrawable(bkg_selected));
		} else {
			ScrollView1.setBackground(getResources().getDrawable(bkg_selected));
		}
	}
*/
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String SELECTION = "_id > -1";
		return new CursorLoader(this, ScoreProvider.NAME_URI, FROM, SELECTION,
				null, null);

	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {

		ShootAdapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

		ShootAdapter.swapCursor(null);

	}
}
