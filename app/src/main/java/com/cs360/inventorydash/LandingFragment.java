package com.cs360.inventorydash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs360.inventorydash.InventoryDashDatabase.InventoryDashDatabase;
import com.cs360.inventorydash.Utilities.Utility;
import com.cs360.inventorydash.models.User;
import com.google.android.material.internal.EdgeToEdgeUtils;

/**
 * Fragment with basic login/signup functionality.
 */
public class LandingFragment extends Fragment {

    private static final String USER_ID = "userId";

    public LandingFragment() {
        // Required empty public constructor
    }

    /**
     * Allows any reference to the fragment to pass a UserID (for future use)
     * @param userId int - The unique identifier for that user.
     * @return - The fragment to be inflated.
     */
    public static LandingFragment newInstance(int userId) {
        LandingFragment fragment = new LandingFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Public accessor for creation.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the created view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landing, container, false);

        Button signInButton = view.findViewById(R.id.loginButton);
        /**
         * Listener which inflates AlertDialog to retrieve user's email and password.
         * Validates user and logs them into the platform.
         */
        signInButton.setOnClickListener(signIn -> {
            AlertDialog.Builder signInbuilder = new AlertDialog.Builder(getContext());
            signInbuilder.setTitle("InventoryDash Login");

            View loginView = inflater.inflate(R.layout.fragment_login, null);
            signInbuilder.setView(loginView);

            EditText txtEmail = loginView.findViewById(R.id.txtLoginEmail);
            EditText txtPassword = loginView.findViewById(R.id.txtLoginPassword);

            /**
             * Login button validates the user and performs login operations
             */
            signInbuilder.setPositiveButton("Login", (dialogInterface, i) -> {
               String email = txtEmail.getText().toString();
               String password = txtPassword.getText().toString();
               User user = validateUserLogin(email, password);

               if (user != null) {
                   Intent intent = new Intent(getActivity(), ListActivity.class);
                   intent.putExtra(USER_ID, user.getId());
                   SharedPreferences prefs = getContext()
                           .getSharedPreferences("oos_notifications", Context.MODE_PRIVATE);
                   prefs.edit().putBoolean("oos_notifications", user.getShowNotifications()).commit();
                   startActivity(intent);
               }
            });
            signInbuilder.setNegativeButton("cancel", null);

            signInbuilder.create();
            signInbuilder.show();
        });

        Button signUpButton = view.findViewById(R.id.signUpButton);

        /**
         * The signuyp button creates an alertdialog to create a new account for the user.
         */
        signUpButton.setOnClickListener(signUp -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sign Up for InventoryDash");

            View signUpView = inflater.inflate(R.layout.fragment_create_account, null);
            builder.setView(signUpView);

            EditText txtEmail = signUpView.findViewById(R.id.txtEmail);
            EditText txtName = signUpView.findViewById(R.id.txtUserName);
            EditText txtPassword = signUpView.findViewById(R.id.txtPassword);
            EditText txtPasswordConfirm = signUpView.findViewById(R.id.txtPasswordConfirmation);

            /**
             * Validates the passed values and, if valid, creates the user in the db.
             */
            builder.setPositiveButton("Create Account", (dialogInterface, i) -> {
                String email =  txtEmail.getText().toString();
                String name = txtName.getText().toString();
                String password = txtPassword.getText().toString();
                String passwordConfirm = txtPasswordConfirm.getText().toString();

                long newId = validateUserCreateAccount(email, name, password, passwordConfirm);

                if (newId != -1) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    intent.putExtra(USER_ID, newId);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("cancel", null);

            builder.create();
            builder.show();
        });


    return view;
    }

    /**
     * Utilizes the User methods to ensure that a user is valid/
     * @param email String - The passed value for the user's email address.
     * @param password String - The rawtext password
     * @return User - The user who has logged in. Null if invalid login.
     */
    private User validateUserLogin(String email, String password) {
        User user = InventoryDashDatabase.getInstance(getContext()).getUser(email);
        if (!Utility.validateEmail(email)){
            Toast.makeText(getContext(),
                    "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return null;
        } else if (user == null){
            Toast.makeText(getContext(), email + " was not found.",
                    Toast.LENGTH_SHORT).show();
            return null;
        } else if (user.validatePassword(password)){
            Toast.makeText(getContext(), "Welcome " + user.getName(),
                    Toast.LENGTH_SHORT).show();
            return user;
        } else {
            Toast.makeText(getContext(), "Invalid email and password.",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * Utilizes the User methods to validate the creation of a new user.
     * @param email String - the user's email address
     * @param name String - The user's entered name.
     * @param password String - The user's passed plaintext password
     * @param passwordConfirmation String - The user's passed plaintext password confirmation.
     * @return long - The id of the created user. If not valid will return -1.
     */
    private long validateUserCreateAccount(String email, String name,
                                           String password, String passwordConfirmation) {
        User newUser = new User();
        if (InventoryDashDatabase.getInstance(getContext()).getUser(email) != null) {
            Toast.makeText(getContext(),
                    "That email already exists in our systyem", Toast.LENGTH_SHORT).show();
        } else if (!newUser.setEmail(email)) {
            Toast.makeText(getContext(),
                    "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (!newUser.setName(name.trim())) {
            Toast.makeText(getContext(),
                    "Please ensure name is not blank or more than 64 characters",
                    Toast.LENGTH_SHORT).show();
        } else if (!passwordConfirmation.equals(password)){
            Toast.makeText(getContext(),
                    "Please ensure that the passwords match",
                    Toast.LENGTH_SHORT).show();
        } else {
            newUser.setPasswordHash(password);
            newUser.setShowNotifications(true);
            long newId = InventoryDashDatabase.getInstance(getContext()).addUser(newUser);
            Toast.makeText(getContext(), "Welcome " + newUser.getName(),
                    Toast.LENGTH_SHORT).show();
            return newId; }
        return -1;
    }
}