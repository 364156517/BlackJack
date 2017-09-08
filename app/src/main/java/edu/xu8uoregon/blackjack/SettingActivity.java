package edu.xu8uoregon.blackjack;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by junchengxu on 7/7/16.
 */
public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();

    }
}
