package com.cs360.inventorydash;

import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;

import com.cs360.inventorydash.InventoryDashDatabase.InventoryDashDatabase;
import com.cs360.inventorydash.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryDashDatabaseUserTests {
    private User validUser;
    private long validUserId;
    private String validEmail;
    private String validName;
    private String validPassHash;
    private boolean validShowNotifications;
    InventoryDashDatabase db;

    @Before
    public void validUserSetup(){
        validUserId = 12345;
        validEmail = "validEmail@test.com";
        validName = "Test Name";
        validPassHash = "fsaddfsafsa2341sadfgasdgdsaasdf";
        validShowNotifications = true;
        validUser = new User(
                validUserId,
                validEmail,
                validName,
                validPassHash,
                validShowNotifications
        );

        db = InventoryDashDatabase.getInstance(
                InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
    }

    @Test
    public void addUser_validUser_true(){
        long id = db.addUser(validUser);
        User createdUser = db.getUser(id);
        Assert.assertTrue("Error::addUser did not insert valid user into db",
                createdUser.getEmail().equals(validEmail) &&
                createdUser.getName().equals(validName) &&
                createdUser.getPassHash().equals(validPassHash));
    }

    @Test
    public void getUser_validEmail_true(){
        User createdUser = db.getUser(validEmail);
        Assert.assertTrue("Error::getUser did not retrieve a user via email",
                createdUser != null);
    }

    @Test
    public void getUser_invalidEmail_false(){
        String badEmail = "sadf";
        User createdUser = db.getUser(badEmail);
        Assert.assertFalse("Error::getUser retrieved a user with a nonexistant email",
                createdUser != null);
    }
}
