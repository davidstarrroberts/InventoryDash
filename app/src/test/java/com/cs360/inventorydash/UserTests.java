package com.cs360.inventorydash;

import android.widget.Adapter;

import com.cs360.inventorydash.Utilities.Utility;
import com.cs360.inventorydash.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTests {
    private User validUser;
    private long validUserId;
    private String validEmail;
    private String validName;
    private String validPassHash;
    private boolean validShowNotifications;

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
    }

    @Test
    public void getId_validId_true(){
        Assert.assertTrue("Error::User.getId did not retrieve a valid stored id",
                validUser.getId() == validUserId);
    }

    @Test
    public void getEmail_validEmail_true(){
        Assert.assertTrue("Error::User.getEmail did not retrieve a valid stored email",
                validUser.getEmail().equals(validEmail));
    }

    @Test
    public void setEmail_validEmail_true(){
        String validTestEmail = "validTestEmail@test.com";
        validUser.setEmail(validTestEmail);
        Assert.assertTrue("Error::User.setEmail did not store a valid email",
                validUser.getEmail().equals(validTestEmail));
    }

    @Test
    public void setEmail_noLocal_false(){
        String noLocalEmail = "@test.com";
        Assert.assertFalse("Error::User.setEmail stored an email with no local",
                validUser.setEmail(noLocalEmail));
    }

    @Test
    public void setEmail_noDomain_false(){
        String noDomainEmail = "invalidTest";
        Assert.assertFalse("Error::User.setEmail stored an email with no domain",
                validUser.setEmail(noDomainEmail));
    }

    @Test
    public void getName_validName_true(){
        Assert.assertTrue("Error::User.getName did not retrieve valid name",
                validUser.getName().equals(validName));
    }

    @Test
    public void setName_validName_true(){
        String validTestName = "Valid Test Name";
        validUser.setName(validTestName);
        Assert.assertTrue("Error::User.setName did not store a valid name",
                validUser.getName().equals(validTestName));
    }

    @Test
    public void setName_tooShort_false(){
        String tooShortName = "";
        Assert.assertFalse("Error::User.setName stored a 0 char String",
                validUser.setName(tooShortName));
    }

    @Test
    public void setName_tooLong_false(){
        String tooLongName = "";
        for (int i = 0; i < 65; i++){
            tooLongName = tooLongName + "1";
        }
        Assert.assertFalse("Error::User.setName stored a 65 char String",
                validUser.setName(tooLongName));
    }

    @Test
    public void setPasswordHash_validHash_true(){
        String validTestPass = Utility.secureHash(validPassHash);
        validUser.setPasswordHash(validPassHash);
        Assert.assertTrue("Error::The passHash stored by setPassword was not hashed",
                validUser.getPassHash().equals(validTestPass));
    }

    @Test
    public void validatePassword_validPassword_true(){
        String validPassword = "1234567";
        validUser.setPasswordHash(validPassword);
        Assert.assertTrue("Error::validatePassword did not validate matching passwords",
                validUser.validatePassword(validPassword));
    }

    @Test
    public void validatePassword_invalidPassword_false(){
        String invalidPassword = "1234567";
        Assert.assertFalse("Error::validatePassword validated non-matching passwords",
                validUser.validatePassword(invalidPassword));
    }

    @Test
    public void constructor_noId_true(){
        User testUser = new User(
                validEmail,
                validName,
                validPassHash,
                validShowNotifications
        );
        Assert.assertTrue("Error::Missing ID constructor did not populate User",
                (testUser.getId() == -1) &&
                        (testUser.getName().equals(validName)) &&
                        (testUser.getEmail().equals(validEmail)) &&
                        (testUser.getPassHash().equals(validPassHash))
                );
    }

    @Test
    public void copyUser_newUser_true(){
        User testUser = new User(-1, validUser);
        Assert.assertTrue("Error::Copy ser constructor did not populate User",
                (testUser.getId() == -1) &&
                        (testUser.getName().equals(validName)) &&
                        (testUser.getEmail().equals(validEmail)) &&
                        (testUser.getPassHash().equals(validPassHash))
        );
    }

    @Test
    public void equalsOverride_sameObject_true(){
        Assert.assertTrue("Error::User.equals does not validate the same object",
                validUser.equals(validUser));
    }

    @Test
    public void equalsOverride_identicalObject_true(){
        User testUser = new User(
                validUserId,
                validEmail,
                validName,
                validPassHash,
                validShowNotifications
        );
        Assert.assertTrue("Error::User.equals does not validate identical objects",
                validUser.equals(testUser));
    }

    @Test
    public void equalsOverrid_differentId_false(){
        User testUser = new User(
                1,
                validEmail,
                validName,
                validPassHash,
                validShowNotifications
        );
        Assert.assertFalse("Error::User.equals validates an object with a different id",
                validUser.equals(testUser));
    }


}
