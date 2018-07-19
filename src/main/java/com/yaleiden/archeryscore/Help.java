package com.yaleiden.archeryscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Help extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setTitle(getString(R.string.help));
		actionBar.setDisplayHomeAsUpEnabled(true);
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
	
	@Override
	public void onStop() {
		
			super.onStop();
		    System.gc();
	}
}
