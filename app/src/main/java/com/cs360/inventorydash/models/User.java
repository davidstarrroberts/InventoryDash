package com.cs360.inventorydash.models;

import com.cs360.inventorydash.Utilities.Utility;

import java.util.Objects;

public class User {
    long id;
    String email;
    String name;
    String passHash;
    boolean showNotifications;

    /**
     * Accessor for InventoryItem's Id
     * @return long - Unique number used to identify user in db.
     */
    public long getId(){
        return this.id;
    }

    /**
     * Accessor for User's email
     * @return String - A string representing the user's unique email.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Setter for the User's email.
     * @param email String - A unique string representing the user's email.
     *              Must include local and domain. See Utility.validateEmail()
     *              for additional details.
     * @return boolean - Describes whether the email passed was valid and set or not.
     */
    public boolean setEmail(String email){
        if (Utility.validateEmail(email)){
            this.email = email;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Accessor for the User's name.
     * @return String - 1 - 64 char description of the user.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Setter for the User's name.
     * @param name String - 1 - 64 char description of the user.
     * @return boolean - Whether the name is valid and stored or not.
     */
    public boolean setName(String name){
        if (Utility.validateStringLength(name, 1, 64)){
            this.name = name;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Setter for passHash. Accepts a plaintext password between 6 and 64 chars
     *      Hashes the password and stores it.
     * @param password String - A plaintext password which the user will use to access their account.
     * @return boolean - Whether the passHash was set or not.
     */
    public boolean setPasswordHash(String password){
        if (Utility.validateStringLength(password, 6, 64)){
            this.passHash = Utility.secureHash(password);
            System.out.println("setPassHash :" + this.passHash);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Accepts a plain text password, hashes os and validates if it is a match to the user's hashPass.
     * @param password String - Plain text password to be validated.
     * @return boolean - Whether the password was valid or not.
     */
    public boolean validatePassword(String password){
        if (this.passHash.equals(Utility.secureHash(password))){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Accessor for the user's passHash
     * @return String - the user's passHash,.
     */
    public String getPassHash(){
        return this.passHash;
    }

    /**
     * Accessor for the user's show notification.
     * @return Boolean - whether the user would like to receive notifications or not.
     */
    public boolean getShowNotifications(){
        return this.showNotifications;
    }

    /**
     * Setter for whether the user would like to receive oos notifications or not.
     * @param showNotifications
     * @return boolean describing whether the user is receiving notifications or not.
     */
    public boolean setShowNotifications(Boolean showNotifications){
        this.showNotifications = showNotifications;
        return true;
    }

    public User(){};

    /**
     * Constructor for the User
     * @param id Long - The Id assigned to the User by the db.
     * @param email String - The unique email the user uses.
     * @param name String - A 1-64 char description of the user.
     * @param passHash String - A hash of the user's password.
     */
    public User(long id, String email, String name, String passHash, boolean showNotifications){
        this.id = id;
        this.email = email;
        this.name = name;
        this.passHash = passHash;
        this.showNotifications = showNotifications;
    }

    /**
     * Constructor overload for missing id
     * @param email String - The unique email the user uses.
     * @param name String - A 1-64 char description of the user.
     * @param passHash String - A hash of the user's password.
     */
    public User(String email, String name, String passHash, boolean showNotifications) {
        this(-1, email, name, passHash, showNotifications);
    }

    /**
     * Constructor overload for copying properties from one User to another User.
     * @param id - long - The ID assigned to the User by the db.
     * @param user - User - The user which you would like to copy the properties
     */
    public User(long id, User user) {
        this(id,
                user.email,
                user.name,
                user.passHash,
                user.showNotifications);
    }

    /**
     * Override for determining if an object and User have identical properties
     * @param o Object - The object which equality should be determined with
     * @return boolean - Whether the objects are equal.
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(passHash, user.passHash);
    }

    @Override
    public int hashCode() {return Objects.hash(id, email, name); }



}
