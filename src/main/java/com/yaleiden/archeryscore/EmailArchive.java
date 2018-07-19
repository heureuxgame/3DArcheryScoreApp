package com.yaleiden.archeryscore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class EmailArchive {

	private final Context localContext;
private Activity activity;

public EmailArchive(Context context) {
	
	this.localContext = context;
}

public void beginArchive(){
	new ExportArchiveFileTask().execute("");
}
	
	private void emailArchive() {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/csv");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
		intent.putExtra(Intent.EXTRA_SUBJECT,
				localContext.getString(R.string.email_subject_archive));
		intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>")
				.append(localContext.getString(R.string.email_text)).append(" ")
				.append(localContext.getString(R.string.app_name)).append(".  </p><p>")
				.append(localContext.getString(R.string.email_get_app)).append("</b></p></a>")
				.append("<br><br><h2><a href = ").append(localContext.getString(R.string.app_link))
				.append(">").append(localContext.getString(R.string.download)).append(" ")
				.append(localContext.getString(R.string.app_name)).append("</a></h2>").toString()));
		File root = Environment.getExternalStorageDirectory();
		File file = new File(root + "/" + Constants.EXPORT_FOLDER,
				"ArcheryScoreArchive.csv");
		if (!file.exists() || !file.canRead()) {
			Toast.makeText(localContext,
                    localContext.getString(R.string.attachment_error),
                    Toast.LENGTH_SHORT).show();
			activity.finish();
			return;
		}
		Uri uri = Uri.fromFile(file);
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		localContext.startActivity(Intent.createChooser(intent, "Send email..."));
	}
	
	class ExportArchiveFileTask extends AsyncTask<String, Void, Boolean> {
		CSVWriter csvWrite;
		Cursor curCSV;
		File exportDir;
		File file;
		String arrStr[] = null;
		boolean FILE_CREATED = true;

		protected Boolean doInBackground(final String... args) {
			String TAG = "EmailArchive";
			if(BuildConfig.DEBUG) {
				Log.d(TAG, " doInBackground");
			}
			// File dbFile = new File(Environment.getDataDirectory() +
			// "/data/data/com.yaleiden.archeryscore/databases/scores.db");

			exportDir = new File(Environment.getExternalStorageDirectory(),
					Constants.EXPORT_FOLDER);
			if(BuildConfig.DEBUG) {
				Log.d(TAG, " " + exportDir.toString());
			}
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}

			file = new File(exportDir, "ArcheryScoreArchive.csv");
			try {
				if(BuildConfig.DEBUG) {
					Log.d(TAG, " new File 'try'");
				}
				file.createNewFile();
				csvWrite = new CSVWriter(new FileWriter(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FILE_CREATED = false;
				return true;
			}
			curCSV = localContext.getContentResolver().query(ScoreProvider.ARCHIVE_URI,
					null, "_id > -1", null, "_id");
			if(BuildConfig.DEBUG) {
				Log.d(TAG, " getContentResolver");
			}
			assert curCSV != null;
			csvWrite.writeNext(curCSV.getColumnNames());
			while (curCSV.moveToNext())

			{
				String[] arrStr = { curCSV.getString(0), curCSV.getString(1),
						curCSV.getString(2), curCSV.getString(3),
						curCSV.getString(4), curCSV.getString(5),
						curCSV.getString(6), curCSV.getString(7),
						curCSV.getString(8), curCSV.getString(9),
						curCSV.getString(10), curCSV.getString(11),
						curCSV.getString(12), curCSV.getString(13),
						curCSV.getString(14), curCSV.getString(15),
						curCSV.getString(16), curCSV.getString(17),
						curCSV.getString(18), curCSV.getString(19),
						curCSV.getString(20), curCSV.getString(21),
						curCSV.getString(22), curCSV.getString(23),
						curCSV.getString(24), curCSV.getString(25),
						curCSV.getString(26), curCSV.getString(27),
						curCSV.getString(28), curCSV.getString(29),
						curCSV.getString(30), curCSV.getString(31),
						curCSV.getString(32), curCSV.getString(33),
						curCSV.getString(34), curCSV.getString(35),
						curCSV.getString(36), curCSV.getString(37),
						curCSV.getString(38), curCSV.getString(39),
						curCSV.getString(40), curCSV.getString(41),
						curCSV.getString(42), curCSV.getString(43),
						curCSV.getString(44), curCSV.getString(45),
						curCSV.getString(46), curCSV.getString(47),
						curCSV.getString(48), curCSV.getString(49),
						curCSV.getString(50), curCSV.getString(51),
						curCSV.getString(52), curCSV.getString(53),
						curCSV.getString(54), curCSV.getString(55),
						curCSV.getString(56), curCSV.getString(57),
						curCSV.getString(58), curCSV.getString(59),
						curCSV.getString(60), curCSV.getString(61),
						curCSV.getString(62), curCSV.getString(63) };
				csvWrite.writeNext(arrStr);
				//if(BuildConfig.DEBUG) {
				//	Log.d(TAG, " arrStr " + arrStr.toString());
				//}
			}
			try {
				csvWrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curCSV.close();
			if(BuildConfig.DEBUG) {
				Log.d(TAG, " curCSV.close");
			}

			return true;

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (!FILE_CREATED) {
				Toast.makeText(localContext, R.string.check_sd,
                        Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(localContext, R.string.csv_saved,
                        Toast.LENGTH_LONG).show();
				emailArchive();
			}
		}

	}
	
}
