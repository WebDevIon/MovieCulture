package com.example.android.movieculture.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.movieculture.R;
import com.example.android.movieculture.rest.ApiClient;

/**
 * This fragment serves as the display for all the user settings.
 * For now the user will be able to change the sort order of the movies by popularity
 * or by user reviews.
 *
 * In the future more menu options will be implemented.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    /**
     * Method used to set the summary of the preference to the current value
     * @param preference the preference that we want to set the summary of the current value
     * @param value the current value of the preference.
     */
    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // If the preference is a List Preference then we look up the correct value
            // in the preference's entries list.
            ListPreference listPreference = (ListPreference) preference;
            int preferenceIndex = listPreference.findIndexOfValue(stringValue);
            if (preferenceIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[preferenceIndex]);
            }
        } else {
            // If it's not a List Preference then we set the value to the
            // string that was passed to the method.
            preference.setSummary(stringValue);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Add the preferences defined in the XML file.
        addPreferencesFromResource(R.xml.pref_general);

        // Here we update the preferences summaries when we start the fragment.
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            // We only set the summaries to the preferences that are not Checkbox Preferences.
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    /**
     * Here we register the preference change listener
     */
    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Here we unregister the preference change listener
     */
    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Here we update the API Request URL accordingly to the preferences and we create
        // a Call<MovieResponse> object specific to the new URL.
        if (key.equals(getString(R.string.sort_key))) {
            DiscoveryActivity.mSearchParam = sharedPreferences.getString(key, "");
            ApiClient.getMoviesCall(DiscoveryActivity.mSearchParam);
        }

        // Here we update the preference summary when a preference is changed.
        Preference preference = findPreference(key);
        if (!(preference instanceof CheckBoxPreference)) {
            setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
        }
    }
}
