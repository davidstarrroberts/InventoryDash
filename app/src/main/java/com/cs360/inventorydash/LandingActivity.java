package com.cs360.inventorydash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class LandingActivity extends AppCompatActivity {
    private static final String USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.login_fragment_container);

        if (fragment == null) {
            int userId = getIntent().getIntExtra(USER_ID, 1);
            fragment = LandingFragment.newInstance(userId);
            fragmentManager.beginTransaction()
                    .add(R.id.login_fragment_container, fragment)
                    .commit();
        }
    }
}