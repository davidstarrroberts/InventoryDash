package com.cs360.inventorydash;

import com.cs360.inventorydash.models.InventoryItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class inventoryItemTests {
    private InventoryItem validInventoryItem;
    private long validId;
    private String validName;
    private String validDescription;
    private int validQoh;
    private long validUserId;

    @Before
    public void createValidInventoryItem(){
        validId = 123;
        validName = "Test Name";
        validDescription = "Test Description";
        validQoh = 4;
        validUserId = 1234;

        validInventoryItem = new InventoryItem(
                validId,
                validName,
                validDescription,
                validQoh,
                validUserId
        );
    }

    //Asserts that getId properly retrieves the id set in constructor
    @Test
    public void getId_validId_true(){
        Assert.assertTrue("Error::getId did not retrieve a valid id from constructor",
                validId == validInventoryItem.getId());
    }

    //Asserts that getName properly retrieves the name set in constructor
    @Test
    public void getName_validName_true(){
        Assert.assertTrue("Error::getName did not retrieve a valid name from constructor",
                validName.equals(validInventoryItem.getName()));
    }

    //Asserts that setName properly stores a validName
    @Test
    public void setName_validName_true(){
        String validTestName = "Valid Test Name";
        validInventoryItem.setName(validTestName);
        Assert.assertTrue("Error::setName did not properly store a valid item name",
                validTestName.equals(validInventoryItem.getName()));
    }

    //Asserts that setName does not store an empty name
    @Test
    public void setName_tooShortName_false(){
        String tooShortName = "";
        Assert.assertFalse("Error::setName allowed a too short name to be stored",
                validInventoryItem.setName(tooShortName));
    }

    //Asserts that setName does not store a name with 64 characters
    @Test
    public void setName_tooLongName_false(){
        String tooLongName = "";
        for (int i = 0; i < 65; i++) {
            tooLongName = tooLongName + "i";
        }
        Assert.assertFalse("Error::setName allowed a too long name to be stored",
                validInventoryItem.setName(tooLongName));
    }

    //Asserts that getDescription retrieves a stored valid description from constructor
    @Test
    public void getDescription_validDescription_true(){
        Assert.assertTrue("Error::getDescription did not retrieve a valid description from constructor",
                validInventoryItem.getDescription().equals(validDescription));
    }

    //Asserts that setDescription stores a valid description
    @Test
    public void setDescription_validDescription_true(){
        String validTestDescription = "Valid Test Description";
        validInventoryItem.setDescription(validTestDescription);
        Assert.assertTrue("Error::setDescription did not store a valid description",
                validInventoryItem.getDescription().equals(validTestDescription));
    }

    //Asserts that setDescription does not store a description which has 0 char
    @Test
    public void setDescription_tooShortDescription_false(){
        String tooShortDescription = "";
        Assert.assertFalse("Error::setDescription stored a description which was 0 char",
                validInventoryItem.setDescription(tooShortDescription));
    }

    //Asserts that setDescription does not store a description longer than 256 char
    @Test
    public void setDescription_tooLongDescription_false(){
        String tooLongDescription = "";
        for (int i = 0; i < 256; i++){
            tooLongDescription = tooLongDescription + "a";
        }
        Assert.assertFalse("Error::setDescription stored a description which was 256 char",
                validInventoryItem.setDescription(tooLongDescription));
    }

    //Asserts that getQoh retrieves a stored valid qoh
    @Test
    public void getQoh_validQoh_true(){
        Assert.assertTrue("Error::getQoh did not retrieve a valid qoh",
                validInventoryItem.getQoh() == validQoh);
    }

    //Asserts that setQoh stores a valid qoh
    @Test
    public void setQoh_validQoh_true(){
        int validTestQoh = 123;
        validInventoryItem.setQoh(validTestQoh);
        Assert.assertTrue("Error::setQoh did not store a valid Qoh",
                validInventoryItem.getQoh() == validTestQoh);
    }

    //Asserts that setQoh does not store a negative qoh
    @Test
    public void setQoh_negativeQoh_false(){
        int negativeQoh = -1;
        Assert.assertFalse("Error::setQoh accepted a negative qoh",
                validInventoryItem.setQoh(negativeQoh));
    }

    //Asserts that getUserId properly retrieves the userId
    @Test
    public void getUserId_validUserId_true(){
        Assert.assertTrue("Error::getUserId did not retrieve a valid userId",
                validInventoryItem.getUserId() == validUserId);
    }

    //Asserts that setUserId properly stores a valid userId
    @Test
    public void setUserId_validUserId_true(){
        int validTestUserId = 231231;
        validInventoryItem.setUserId(validTestUserId);
        Assert.assertTrue("Error::setUserId did not set and store a valid userId",
                validInventoryItem.getUserId() == validTestUserId);
    }

    //Asserts that setUserId does not accept a negative value for userId
    @Test
    public void setUserId_negativeUserId_false(){
        int negativeUserId = -1;
        Assert.assertFalse("Error::setUserId allowed a negative userId to be stored",
                validInventoryItem.setUserId(negativeUserId));
    }

    //Asserts that InventoryItem(id, inventoryItem) copies the passed item's name and description
    @Test
    public void constructorOverride_validItem_true(){
        InventoryItem testItem = new InventoryItem(8, validInventoryItem);
        Assert.assertTrue("Error::InventoryItem(id, inventoryItem) did not copy the item",
                validInventoryItem.getName().equals(testItem.getName()) &&
                validInventoryItem.getDescription().equals(testItem.getDescription()));
    }

    //Asserts that InventoryItem.equals() returns true if passed the same object
    @Test
    public void equals_sameItem_true(){
        Assert.assertTrue("Error::InventoryItem.equals(object) did not accept the same object",
                validInventoryItem.equals(validInventoryItem));
    }

    //Asserts that InventoryItem.equals returns true if pass two instances of identical objects
    @Test
    public void equals_identicalObjects_true(){
        InventoryItem newItem = new InventoryItem(validInventoryItem);
        Assert.assertTrue("Error::InventoryItem.equals(object) did not accept identical objects",
                validInventoryItem.equals(newItem));
    }




}
