package com.yaleiden.archeryscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;


public class NewsWebActivity extends AppCompatActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_web_layout);
		

		ActionBar actionBar = getSupportActionBar();
		//actionBar.setTitle(getString(R.string.archived_scores));
		assert actionBar != null;
		actionBar.setTitle(R.string.news);
		actionBar.setDisplayHomeAsUpEnabled(true);


		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("https://sites.google.com/site/tournament3darcheryapp/");

	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.web, menu);
	    return super.onCreateOptionsMenu(menu);
	  }

	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_main_page) {
			startActivity(new Intent(this, MainActivity.class));
		}  else if (itemId == R.id.menu_help) {
				startActivity(new Intent(this, Help.class));
			 
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
 
}