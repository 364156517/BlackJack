package edu.xu8uoregon.blackjack;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by junchengxu on 7/7/16.
 */

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        PreferenceManager savedValuesManager = getPreferenceManager();
        savedValuesManager.setSharedPreferencesName("SavedValues");

        addPreferencesFromResource(R.xml.activity_setting);

    }


}