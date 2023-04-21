package com.cs360.inventorydash.InventoryDashDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs360.inventorydash.models.InventoryItem;
import com.cs360.inventorydash.models.User;

import java.util.ArrayList;
import java.util.List;

public class InventoryDashDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "inventoryDash.db";

    private static InventoryDashDatabase instance;

    private InventoryDashDatabase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Enforces singleton pattern for the db accross the context.
     * @param context Context - The context which the db is unique in
     * @return InventoryDashDatabase - The database which exists in the context.
     */
    public static InventoryDashDatabase getInstance(Context context){
        if (instance == null) {
            instance = new InventoryDashDatabase(context);
        }
        return instance;
    }

    /**
     * Static class holding all names related to InventoryItemsTable
     */
    private static final class InventoryItemsTable {
        private static final String TABLE = "inventoryItems";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_DESCRIPTION = "description";
        private static final String COL_QOH = "qoh";
        private static final String COL_USERID = "userId";
    }

    /**
     * Static class holding all names related to Users
     */
    private static final class UsersTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";

        private static final String COL_EMAIL = "email";
        private static final String COL_PASSHASH = "passHash";
        private static final String COL_NAME = "name";
        private static final String COL_SHOWNOTIFICATIONS = "showNotifications";
    }

    /**
     * Series of sql statements executed on creation of the db. Creates necessary tables.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + InventoryItemsTable.TABLE + "( " +
                InventoryItemsTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InventoryItemsTable.COL_NAME + ", " +
                InventoryItemsTable.COL_DESCRIPTION + ", " +
                InventoryItemsTable.COL_QOH + ", " +
                InventoryItemsTable.COL_USERID + " INTEGER)"
        );


        db.execSQL("CREATE TABLE " + UsersTable.TABLE + "( " +
                UsersTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersTable.COL_EMAIL + ", " +
                UsersTable.COL_NAME + ", " +
                UsersTable.COL_PASSHASH + ", " +
                UsersTable.COL_SHOWNOTIFICATIONS + ")"
        );
    }

    /**
     * Drops necessary tables on database upgrade.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + InventoryItemsTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE);
        onCreate(db);
    }

    /*****************
     * InventoryItem *
     *****************/

    /**
     * Retrieves all items in the db.
     * @return A list of all InventoryItems in the db.
     */
    public List<InventoryItem> getInventoryItems(){
        List<InventoryItem> inventoryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + InventoryItemsTable.TABLE;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                long id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int qoh = cursor.getInt(3);
                long userId = cursor.getLong(4);

                InventoryItem inventoryItem = new InventoryItem(
                        id, name, description, qoh, userId
                );
                inventoryItems.add(inventoryItem);
            } while (cursor.moveToNext());
        }

        return inventoryItems;
    }

    /**
     * Retrieves all items in the db.
     * @return A list of all InventoryItems in the db.
     */
    public List<InventoryItem> getInventoryItems(Long userId){
        List<InventoryItem> inventoryItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + InventoryItemsTable.TABLE +
                " WHERE " + InventoryItemsTable.COL_USERID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(userId)});

        if (cursor.moveToFirst()){
            do {
                long id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int qoh = cursor.getInt(3);

                InventoryItem inventoryItem = new InventoryItem(
                        id, name, description, qoh, userId
                );
                inventoryItems.add(inventoryItem);
            } while (cursor.moveToNext());
        }

        return inventoryItems;
    }

    /**
     * Retrieves the InventoryItem with the matching ID from the db
     * @param inventoryItemId long - The id of the inventoryItem you would like to retrieve
     * @return InventoryItem - The InventoryItem with the id of the passed long.
     */
    public InventoryItem getInventoryItem(long inventoryItemId){
        InventoryItem inventoryItem = null;

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + InventoryItemsTable.TABLE +
                " WHERE " + InventoryItemsTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(inventoryItemId)});

        if (cursor.moveToFirst()){
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            int qoh = cursor.getInt(3);
            long userId = cursor.getLong(4);
            inventoryItem = new InventoryItem(
                    inventoryItemId, name, description, qoh, userId
            );
        }

        return inventoryItem;
    }

    public boolean inventoryItemNameExists(String name, long userId){
        boolean exists = false;
        SQLiteDatabase db = getReadableDatabase();
        System.out.println(name);
        System.out.println(userId);
        String sql = "SELECT * FROM " + InventoryItemsTable.TABLE +
                " WHERE " + InventoryItemsTable.COL_NAME + " = ?" +
                " AND " + InventoryItemsTable.COL_USERID + " = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{name, Long.toString(userId)});

        if ((cursor.moveToFirst()) || cursor.getCount() != 0) {
            System.out.println(cursor.getString(1));
            System.out.println(""+cursor.getLong(4));
            return name.equals(cursor.getString(1)) ||
            userId == cursor.getLong(4);
        }
        return exists;
    }

    /**
     * Adds a new InventoryItem to the db
     * @param inventoryItem InventoryItem - The Inventory Item to be added to the db.
     * @return long - The id of the InventoryItem added to the db.
     */
    public long addInventoryItem(InventoryItem inventoryItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryItemsTable.COL_NAME, inventoryItem.getName());
        values.put(InventoryItemsTable.COL_DESCRIPTION, inventoryItem.getDescription());
        values.put(InventoryItemsTable.COL_QOH, inventoryItem.getQoh());
        values.put(InventoryItemsTable.COL_USERID, inventoryItem.getUserId());


        long newId = db.insert(InventoryItemsTable.TABLE, null, values);


        return newId;
    }

    /**
     * Updates the InventoryItem matching the passed Id to reflect the passed inventoryItem
     * @param id long - the ID of the inventoryItem you would like to edir
     * @param inventoryItem InventoryItem - The inventory Item you would like to update to
     * @return boolean - Whether the item was successfully updated or not.
     */
    public boolean editInventoryItem(long id, InventoryItem inventoryItem){
        boolean isEdited = false;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryItemsTable.COL_NAME, inventoryItem.getName());
        values.put(InventoryItemsTable.COL_DESCRIPTION, inventoryItem.getDescription());
        values.put(InventoryItemsTable.COL_QOH, inventoryItem.getQoh());
        values.put(InventoryItemsTable.COL_USERID, inventoryItem.getUserId());

        int result = db.update(InventoryItemsTable.TABLE,  values,
                InventoryItemsTable.COL_ID + " = " +id, null);

        isEdited = result == 1;
        return isEdited;
    }

    /**
     * Deletes the inventoryItem with the passed id.
     * @param id long - the id of the InventoryItem you would like to delete
     * @return boolean - Whether the item was successfully deleted or not.
     */
    public boolean deleteInventoryItem(long id){
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(InventoryItemsTable.TABLE,
                InventoryItemsTable.COL_ID + " = " + id, null);
        return result == 1;
    }

    /****************
     * USER METHODS *
     ****************/

    /**
     * Retrieves the User with the matching userId from the db
     * @param userId long - The id of the inventoryItem you would like to retrieve
     * @return User - The User with the id of the passed long.
     */
    public User getUser(long userId){
        User user = null;

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + UsersTable.TABLE +
                " WHERE " + UsersTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(userId)});

        if (cursor.moveToFirst()){
            String email = cursor.getString(1);
            String name = cursor.getString(2);
            String passHash = cursor.getString(3);
            boolean showNotifications = cursor.getInt(4) > 0;
            user = new User(
                    userId, email, name, passHash, showNotifications
            );
        }

        return user;
    }

    /**
     * Retrieves the User with the matching userId from the db
     * @param userEmail long - The id of the inventoryItem you would like to retrieve
     * @return User - The User with the id of the passed long.
     */
    public User getUser(String userEmail){
        User user = null;

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + UsersTable.TABLE +
                " WHERE " + UsersTable.COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{userEmail});

        if (cursor.moveToFirst()){
            long userId = cursor.getLong(0);
            String name = cursor.getString(2);
            String passHash = cursor.getString(3);
            boolean showNotifications = cursor.getInt(4) > 0;
            user = new User(
                    userId, userEmail, name, passHash, showNotifications
            );
        }

        return user;
    }

    /**
     * Adds a new User to the db
     * @param user User - The user to be added to the db.
     * @return long - The id of the user added to the db.
     */
    public long addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersTable.COL_EMAIL, user.getEmail());
        values.put(UsersTable.COL_NAME, user.getName());
        values.put(UsersTable.COL_PASSHASH, user.getPassHash());
        values.put(UsersTable.COL_SHOWNOTIFICATIONS, user.getShowNotifications());

        long newId = db.insert(UsersTable.TABLE, null, values);


        return newId;
    }

    /**
     * Updates the User matching the passed Id to reflect the passed User
     * @param id long - the ID of the User you would like to edit
     * @param user User - The user you would like to update to
     * @return boolean - Whether the user was successfully updated or not.
     */
    public boolean editUser(long id, User user){
        boolean isEdited = false;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersTable.COL_NAME, user.getName());
        values.put(UsersTable.COL_EMAIL, user.getEmail());
        values.put(UsersTable.COL_NAME, user.getName());
        values.put(UsersTable.COL_PASSHASH, user.getPassHash());
        values.put(UsersTable.COL_SHOWNOTIFICATIONS, user.getShowNotifications());

        int result = db.update(UsersTable.TABLE,  values,
                UsersTable.COL_ID + " = " +id, null);

        isEdited = result == 1;
        return isEdited;
    }


}
