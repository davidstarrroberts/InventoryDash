package com.cs360.inventorydash;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360.inventorydash.InventoryDashDatabase.InventoryDashDatabase;
import com.cs360.inventorydash.models.InventoryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements DetailsFragment.InventoryItemListener {

    private InventoryItemAdapter adapter;

    /**
     * Handler for an edited item. Calls the edit inventory adapter.
     * @param original InventoryItem - The original object without edit
     * @param edited InventoryItem - The edited item.
     */
    @Override
    public void handleEdited(InventoryItem original, InventoryItem edited) {
        adapter.editInventoryItem(original, edited);
    }

    /**
     * Handler for deleted item. Calls the delete item adapter
     * @param deleted InventoryItem - The item which was deleted.
     */
    @Override
    public void handleDeleted(InventoryItem deleted) {
        adapter.deleteInventoryItem(deleted);
    }

    // For the activity to implement
    public interface OnInventoryItemSelectedListener {
        void onInventoryItemSelected(int bandId);
    }

    // Reference to the activity
    private OnInventoryItemSelectedListener mListener;

    /**
     * Used to create a new instance of the ListFragment withdthe UserId in bundle.
     * @param userId Long - The id of the currently logged in user.
     * @return Fragment - The ListFragment
     */
    public static ListFragment newInstance(long userId){
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        System.out.println(Long.toString(userId));
        args.putLong("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Registers the details fragment
     */
    public ListFragment(){
        DetailsFragment.register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInventoryItemSelectedListener) {
            mListener = (OnInventoryItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInventoryItemSelectedListener");
        }
    }

    /**
     * When detached will stop listeners.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Creates core view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The created view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send bands to recycler view
        adapter = new InventoryItemAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);

        /**
         * Sets onclick listener for fab create item button. Will open AlertDialog to create new item.
         */
        fab.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Add Inventory Item");

            View editView = inflater.inflate(R.layout.inventory_item_details, null);
            builder.setView(editView);

            EditText txtDescription = editView.findViewById(R.id.txtDescription);
            EditText txtName = editView.findViewById(R.id.txtName);

            /**
             * On selecting ok will validate the item and create it in db.
             */
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                String name = txtName.getText().toString().toLowerCase();
                long userId = getArguments().getLong("userId");
                String description = txtDescription.getText().toString();
                InventoryItem inventoryItem = new InventoryItem(name, description, 1, userId);
                if (InventoryDashDatabase.getInstance(getContext())
                        .inventoryItemNameExists(name, userId)){
                    Toast.makeText(getContext(),
                            "An item with that name already exists", Toast.LENGTH_SHORT).show();
                } else {
                    long result = InventoryDashDatabase.getInstance(getContext()).
                            addInventoryItem(inventoryItem);
                    adapter.addInventoryItem(new InventoryItem(result, inventoryItem));
                    if (result == -1) {
                        Toast.makeText(getContext(), "An error was encountered creating that item",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.create();

            builder.show();
        });

        return view;
    }

    /**
     * Extender for recycler view to inflate the individual item's listviews.
     */
    private class InventoryItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private InventoryItem mInventoryItem;

        private final TextView mNameTextView;
        private final TextView mQohTextView;

        public InventoryItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_inventory_item, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.lblItemName);
            mQohTextView = itemView.findViewById(R.id.lblQOH);
        }

        public void bind(InventoryItem inventoryItem) {
            mInventoryItem = inventoryItem;
            mNameTextView.setText(mInventoryItem.getName());
            mQohTextView.setText(Integer.toString(mInventoryItem.getQoh()));
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what inventoryItem was clicked
            mListener.onInventoryItemSelected((int)mInventoryItem.getId());
        }
    }

    /**
     * Populates the recycler view with the items retrieved from db.
     */
    private class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemHolder> {

        private final List<InventoryItem> mInventoryItems;


        public InventoryItemAdapter() {
            long userId = getArguments().getLong("userId");
            mInventoryItems = InventoryDashDatabase.getInstance(getContext()).getInventoryItems(userId);
        }

        @Override
        public InventoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InventoryItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(InventoryItemHolder holder, int position) {
            InventoryItem inventoryItem = mInventoryItems.get(position);
            holder.bind(inventoryItem);
        }

        @Override
        public int getItemCount() {
            return mInventoryItems.size();
        }

        public void addInventoryItem(InventoryItem inventoryItem){
            mInventoryItems.add(inventoryItem);
            notifyItemInserted(mInventoryItems.size() - 1);
        }

        public void editInventoryItem(InventoryItem original, InventoryItem edited){
            int index = 0;
            for (int i = 0; i < mInventoryItems.size(); i++){
                if (mInventoryItems.get(i).equals(original)){
                    index = i;
                }
            }

            mInventoryItems.add(index, edited);
            mInventoryItems.remove(original);
            notifyItemChanged(index);
        }

        public void deleteInventoryItem(InventoryItem inventoryItem){
            int index = mInventoryItems.indexOf(inventoryItem);
            mInventoryItems.remove(inventoryItem);
            notifyItemRemoved(index);
        }

    }
}