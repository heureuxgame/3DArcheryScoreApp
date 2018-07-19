package com.yaleiden.archeryscore;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MyBackupAgent extends BackupAgentHelper {

	static final String TAG = "MyBackupAgent";
    /**
     * We put a simple version number into the state files so that we can
     * tell properly how to read "old" versions if at some point we want
     * to change what data we back up and how we store the state blob.
     */
    static final int AGENT_VERSION = 1;
	// The name of the file
    static final String BACKUP_DATABASE = "scores";
    /** The location of the application's persistent data file */
    File mDataFile;
    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "myfiles";
 // Object for intrinsic lock
    
    // Allocate a helper and add it to the backup agent

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "MyBackupAgent OnCreate");
        }
		FileBackupHelper helper = new FileBackupHelper(this, DatabaseSet.DATABASE_NAME);
        addHelper(FILES_BACKUP_KEY, helper);
	}
	
	@Override
	   public File getFilesDir(){
	      File path = getDatabasePath(DatabaseSet.DATABASE_NAME);
	      return path.getParentFile();
	   }

	@Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
             ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper performs the backup operation
        synchronized (MainActivity.sDataLock) {
            super.onBackup(oldState, data, newState);
        }
    }

	@Override
    public void onRestore(BackupDataInput data, int appVersionCode,
            ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper restores the file from
        // the data provided here.
        synchronized (MainActivity.sDataLock) {
            super.onRestore(data, appVersionCode, newState);
        }
    }

}