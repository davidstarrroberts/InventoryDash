package com.cs360.inventorydash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360.inventorydash.InventoryDashDatabase.InventoryDashDatabase;
import com.cs360.inventorydash.models.InventoryItem;
import com.cs360.inventorydash.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    /**
     * Instantiates listeners array for this fragment
     */
    private static final List<InventoryItemListener> listeners = new ArrayList<>();

    /**
     * Adds the edited and deleted listeners to the interface.
     */
    public interface InventoryItemListener {
        void handleEdited(InventoryItem original, InventoryItem edited);
        void handleDeleted(InventoryItem deleted);
    }

    /**
     * The selected item
     */
    private InventoryItem mInventoryItem;
    private User mUser;

    /**
     * Allows other activities/fragments to initialize with itemId and userId
     * @param inventoryItemId - The inventory item who's details are being displayed
     * @param userId - The user who is currently in the application
     * @return the fragment to be inflated
     */
    public static DetailsFragment newInstance(int inventoryItemId, long userId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("inventoryItemId", inventoryItemId);
        args.putLong("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Registers a new listener
     * @param listener the listener to be added to listener registry
     */
    public static void register(InventoryItemListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener
     * @param listener - the listener to be removed.
     */
    public static void remove(InventoryItemListener listener) {
        listeners.remove(listener);
    }


    /**
     * Instantiates the details fragment. Instantiates mUser and mInventoryItem
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int inventoryItemId = 1;
        long userId = 1;
        if (getArguments() != null) {
            inventoryItemId = getArguments().getInt("inventoryItemId");
            userId = getArguments().getLong("userId");
        }

        mInventoryItem = InventoryDashDatabase.getInstance(getContext())
                .getInventoryItem(inventoryItemId);
        mUser = InventoryDashDatabase.getInstance(getContext())
                .getUser(userId);
    }

    /**
     * Creates the view for DetailsFragment
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View - the created view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.lblItemName);
        nameTextView.setText(mInventoryItem.getName());

        TextView descriptionTextView = (TextView) view.findViewById(R.id.itemDescription);
        descriptionTextView.setText(mInventoryItem.getDescription());

        TextView qohTextView = (TextView) view.findViewById(R.id.itemQoh);
        qohTextView.setText(Integer.toString(mInventoryItem.getQoh()));

        ImageButton btnIncrementInventory = view.findViewById(R.id.incrementInventory);

        /**
         * The increment button onClick listener will increase the items QOH
         */
        btnIncrementInventory.setOnClickListener(l -> {
            InventoryItem edited = new InventoryItem(
                    mInventoryItem.getId(),
                    mInventoryItem.getName(),
                    mInventoryItem.getDescription(),
                    mInventoryItem.getQoh() + 1,
                    mInventoryItem.getUserId()
            );


            boolean isEdited = InventoryDashDatabase.getInstance(getContext()).
                    editInventoryItem(mInventoryItem.getId(), edited);
            if(!isEdited){
                Toast.makeText(getContext(),
                        "Error Increasing QOH",Toast.LENGTH_SHORT).show();
            } else {
                listeners.forEach(listener -> listener.handleEdited(mInventoryItem, edited));
                mInventoryItem = edited;
                nameTextView.setText(edited.getName());
                descriptionTextView.setText(edited.getDescription());
                qohTextView.setText(Long.toString(edited.getQoh()));
            }

        });

        ImageButton btnDeIncrementInventory = view.findViewById(R.id.deIncrementInventory);
        /**
         * The deincrement button will decrease the item's qoh if the qoh is greater than 0.
         * Will notify the user if notifications are enabled.
         */
        btnDeIncrementInventory.setOnClickListener(l -> {
            int currQoh = mInventoryItem.getQoh();
            boolean outOfStockNotifications = mUser.getShowNotifications();

            if (currQoh == 0){
                Toast.makeText(getContext(),
                        "Quantity on Hand is already 0", Toast.LENGTH_SHORT).show();
                return;
            } else if (currQoh == 1 && outOfStockNotifications){
                String message = "Quantity on hand of " + mInventoryItem.getName() +
                        " has decreased to 0";
                Toast.makeText(getContext(),
                        message, Toast.LENGTH_LONG).show();
            }

            InventoryItem edited = new InventoryItem(
                    mInventoryItem.getId(),
                    mInventoryItem.getName(),
                    mInventoryItem.getDescription(),
                    mInventoryItem.getQoh() - 1,
                    mInventoryItem.getUserId()
            );

            boolean isEdited = InventoryDashDatabase.getInstance(getContext()).
                    editInventoryItem(mInventoryItem.getId(), edited);
            if(!isEdited){
                Toast.makeText(getContext(),
                        "Error Removing QOH",Toast.LENGTH_SHORT).show();
            } else {
                listeners.forEach(listener -> listener.handleEdited(mInventoryItem, edited));
                mInventoryItem = edited;
                nameTextView.setText(edited.getName());
                descriptionTextView.setText(edited.getDescription());
                qohTextView.setText(Long.toString(edited.getQoh()));
            }

        });

        Button btnEdit = view.findViewById(R.id.btnEdit);
        /**
         * The edit button's onclick listener will inflate the edit interaction.
         * Will update the item to reflect the passed values.
         */
        btnEdit.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Edit Inventory Item's Details");

            View editView = inflater.inflate(R.layout.inventory_item_details, null);
            builder.setView(editView);

            EditText txtDescription = editView.findViewById(R.id.txtDescription);
            txtDescription.setText(mInventoryItem.getDescription());
            EditText txtName = editView.findViewById(R.id.txtName);
            txtName.setText(mInventoryItem.getName());

            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                String name = txtName.getText().toString();
                String description = txtDescription.getText().toString();
                InventoryItem edited = new InventoryItem(
                        mInventoryItem.getId(),
                        name,
                        description,
                        mInventoryItem.getQoh(),
                        mInventoryItem.getUserId()
                );

                if (InventoryDashDatabase.getInstance(getContext())
                        .inventoryItemNameExists(edited.getName(), edited.getUserId())) {
                    Toast.makeText(getContext(), "That item name already exists",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (!InventoryDashDatabase.getInstance(getContext()).
                        editInventoryItem(mInventoryItem.getId(), edited)) {
                    Toast.makeText(getContext(), "Error editing inventory item",
                            Toast.LENGTH_SHORT).show();
                } else {
                    listeners.forEach(listener -> listener.handleEdited(mInventoryItem, edited));
                    mInventoryItem = edited;
                    nameTextView.setText(edited.getName());
                    descriptionTextView.setText(edited.getDescription());
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.create();

            builder.show();
        });

        /**
         * The delete onclick listener will delete the item if selected.
         */
        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(l -> {
            boolean isDeleted = InventoryDashDatabase.getInstance(getContext()).
                    deleteInventoryItem(mInventoryItem.getId());

            if (!isDeleted){
                Toast.makeText(getContext(), "Error deleting inventory item",
                        Toast.LENGTH_SHORT).show();
            } else {
                listeners.forEach(listener -> listener.handleDeleted(mInventoryItem));
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}