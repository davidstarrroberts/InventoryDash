package com.cs360.inventorydash.models;

import android.content.Context;

import com.cs360.inventorydash.Utilities.Utility;

import java.util.Objects;

public class InventoryItem {
    private long id;
    private String name;
    private String description;
    private int qoh;
    private long userId;

    /**
     * Accessor for InventoryItem's Id
     * @return long - Unique number used to identify item in db.
     */
    public long getId(){
        return id;
    }

    /**
     * Accessor for InventoryItem's name
     * @return String - A 1-64 char string which a user can identify an item by.
     */
    public String getName(){
        return name;
    }

    /**
     * Setter for InventoryItem's name
     * @param name String - A 1-64 char string which a user can identify an item by.
     * @return Boolean - Whether the item's name was successfully set or not.
     */
    public boolean setName(String name){
        if (Utility.validateStringLength(name, 1, 64)){
            this.name = name;
            return true;
        }
        return false;
    }

    /**
     * Accessor for the InventoryItem's description
     * @return String - a 1 - 255 char string which describes the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for InventoryItem's description
     * @param description String - a 1 - 255 char string which describes the item.
     */
    public boolean setDescription(String description) {
        if (Utility.validateStringLength(description.trim(), 1, 255)) {
            this.description = description;
            return true;
        }
        return false;
    }

    /**
     * Accessor for InventoryItem's QOH
     * @return int - The item's current stock.
     */
    public int getQoh(){
        return this.qoh;
    }

    /**
     * Setter for InventoryItem's QOH
     * @param qoh - Int - The current stock of an item. Must be 0 or greater.
     * @return boolean - Whether the InventoryItem's qoh was updated or not.
     */
    public boolean setQoh(int qoh) {
        if (qoh > 0) {
            this.qoh = qoh;
            return true;
        }
        return false;
    }

    /**
     * Accessor for InventoryItem's UserId
     * @return long - ID of the user who created the item. Foreign key to user.
     */
    public long getUserId() {
        return this.userId;
    }

    /**
     * Setter for InventoryItem's userId
     * @param userId - long - The Id of the user who created the item. Foreign key to user.
     * @return boolean - Whether the userId was successfully updated or not.
     */
    public boolean setUserId(long userId){
        if (userId > 0) {
            this.userId = userId;
            return true;
        }
        return false;
    }

    /**
     * Constructor for InventoryItem
     * @param id - long - The Id assigned to the InventoryItem by the db.
     * @param name - String - A 1-64 char unique description of the item.
     * @param description - String - A 1-255 char description of the item.
     * @param qoh - int - The current amount of an item the user has in stock.
     * @param userId - long - The Id assigned to the User by the db.
     */
    public InventoryItem(long id, String name, String description, int qoh, long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qoh = qoh;
        this.userId = userId;
    }

    /**
     * Constructor overload for missing id
     * @param name - String - A 1-64 char unique description of the item.
     * @param description - String - A 1-255 char description of the item.
     * @param qoh - int - The current amount of an item the user has in stock.
     * @param userId - long - The Id assigned to the User by the db.
     */
    public InventoryItem(String name, String description, int qoh, long userId){
        this(-1, name, description, qoh, userId);
    }

    /**
     * Constructor overload for copying properties from one inventroyItem to another inventoryItem
     * @param id - long - The Id assigned to the InventoryItem by the db.
     * @param inventoryItem - InventoryItem - The item which you would like to copy the properties of
     */
    public InventoryItem(long id, InventoryItem inventoryItem){
        this(id,
                inventoryItem.name,
                inventoryItem.description,
                inventoryItem.qoh,
                inventoryItem.userId);
    }

    /**
     * Constructor overload for copying properties from on inventoryItem to a new inventoryItem
     * @param i
     */
    public InventoryItem(InventoryItem i) {
        this(i.id, i.name, i.description, i.qoh, i.userId);
    }

    /**
     * Override for determining if an object and inventoryItem have identical properties
     * @param o Object - The object which equality should be determined with
     * @return boolean - Whether the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem inventoryItem = (InventoryItem) o;
        return id == inventoryItem.id &&
                Objects.equals(name, inventoryItem.name) &&
                Objects.equals(description, inventoryItem.description) &&
                Objects.equals(qoh, inventoryItem.qoh) &&
                Objects.equals(userId, inventoryItem.userId);
    }

    /**
     * Hashcode used for determining if item is unique for that user.
     * @return int - A hash of the item's id, name, description and userId
     */
    @Override
    public int hashCode(){
        return Objects.hash(id, name, description, userId);
    }
}
