package a2dp.vol.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.Objects;

import a2dp.vol.A2DPApplication;
import a2dp.vol.R;
import a2dp.vol.service.AudioService;

/**
 * @author Jim Roal This is the preference activity. It loads and saves the
 *         preferences
 */
public class PreferencesActivity extends PreferenceActivity {
	private A2DPApplication application;
	public static final String PREFS_NAME = "btVol";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.PreferenceActivity#onContentChanged()
	 */
	@Override
	public void onContentChanged() {
		// stop the service while changes are made
		stopService(new Intent(PreferencesActivity.this, AudioService.class));
		super.onContentChanged();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.PreferenceActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		// Commit the edits!
		editor.apply();

		this.application = (A2DPApplication) this.getApplication();

		// restart the service
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			startService(new Intent(application, AudioService.class));
		} else {
			startForegroundService(new Intent(application, AudioService.class));
		}
		// Tell the world we updated preferences

		final String IRun = "a2dp.vol.preferences.UPDATED";
		Intent i = new Intent();
		i.setAction(IRun);
		this.application.sendBroadcast(i);

		super.onDestroy();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        // Show the Up button in the action bar.
        setupActionBar();
		this.application = (A2DPApplication) this.getApplication();

	}
    private void setupActionBar() {

        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
