package com.cs360.inventorydash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_INVENTORY_ITEM_ID = "inventoryItemId";

    /**
     * Holder for the details fragment. Passes UserId intent.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);

        if (fragment == null) {
            int inventoryItemId = getIntent().getIntExtra(EXTRA_INVENTORY_ITEM_ID, 1);
            long userId = getIntent().getLongExtra("userId", 1);
            fragment = DetailsFragment.newInstance(inventoryItemId, userId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}