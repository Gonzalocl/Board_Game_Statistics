package com.gonzalocl.boardgamestatistics.ui;

import android.os.Bundle;
import android.preference.Preference;

import com.gonzalocl.boardgamestatistics.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }



}
