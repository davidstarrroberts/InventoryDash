package com.cs360.inventorydash;

import com.cs360.inventorydash.Utilities.Utility;

import org.junit.Assert;
import org.junit.Test;

public class UtilityTests {
    @Test
    public void validateStringLength_tooShort_false(){
        String s = "";
        int minChar = 1;
        int maxChar = 255;
        Assert.assertFalse("Error::validateStringLength validated a too short string",
                Utility.validateStringLength(s, minChar, maxChar));
    }

    @Test
    public void validateStringLength(){
        String s = "";
        for (int i = 0; i < 256; i++){
            s += "a";
        }
        int minChar = 1;
        int maxChar = 255;
        Assert.assertFalse("Error::validateStringLength validated a too long string",
                Utility.validateStringLength(s, minChar, maxChar));
    }
}
