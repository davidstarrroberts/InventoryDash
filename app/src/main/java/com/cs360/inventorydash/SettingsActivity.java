package com.cs360.inventorydash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import android.os.Bundle;

import com.cs360.inventorydash.InventoryDashDatabase.InventoryDashDatabase;
import com.cs360.inventorydash.models.User;

public class SettingsActivity extends AppCompatActivity {
    private static long userId;

    /**
     * Creates the settings biew and retrieves the user_id from the intent.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        userId = getIntent().getLongExtra("user_id", 1);

    }

    /**
     * Implements new listener for switch on notifications. Updates db based on use.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_prefernces, rootKey);

            SwitchPreferenceCompat notificationsSwitch = (SwitchPreferenceCompat)
                    findPreference("oos_notifications");
            InventoryDashDatabase db = InventoryDashDatabase.getInstance(getContext());
            User user = db.getUser(userId);

            if (notificationsSwitch != null){
                notificationsSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        user.setShowNotifications(!user.getShowNotifications());
                        db.editUser(userId, user);
                        return true;
                    }
                });
            }

        }
    }
}