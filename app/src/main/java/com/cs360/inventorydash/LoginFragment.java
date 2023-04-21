package com.cs360.inventorydash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Fragment for user login. Can be inflated to enable user login.
 */
public class LoginFragment extends Fragment {
    private static final String USER_ID = "userId";

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Allows LoginFragment to be instantiates with userId argument
     * @param userId int - the ID of the user who is logging in.
     * @return fragment
     */
    public static LoginFragment newInstance(int userId) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Allows the userId t be retrieved.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int userId = getArguments().getInt(USER_ID);
        }
    }

    /**
     * Creates the fragment view
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_login, container, false);
    }
}