package com.cs360.inventorydash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends AppCompatActivity implements ListFragment.OnInventoryItemSelectedListener {
    public static final String USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            long userId = getIntent().getLongExtra(USER_ID, 1);
            fragment = ListFragment.newInstance(userId);
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * Opens DetailsActivity with the userId and inventoryItemId.
     * @param inventoryItemId the id of the item which will be opened.
     */
    @Override
    public void onInventoryItemSelected(int inventoryItemId) {
        // Send the band ID of the clicked button to DetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_INVENTORY_ITEM_ID, inventoryItemId);
        long userId = getIntent().getLongExtra(USER_ID, 1);
        intent.putExtra(USER_ID, userId);
        startActivity(intent);
    }

    /***
     * @param menu The options menu in which you place your items.
     *
     * @return Whether the item was inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    /**
     * Passes userId to the menu.
     * @param item The menu item that was selected.
     *
     * @return Boolean - Whether the item was selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            long userId = getIntent().getLongExtra(USER_ID, 1);
            intent.putExtra(USER_ID, userId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}