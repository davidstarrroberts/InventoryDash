package com.cs360.inventorydash.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Utility {
    /**
     * A helper function used to determine the validity of a string
     * @param s String - The string which you wish to determine the validity of.
     * @param minChar int - The inclusive minimum number of characters to allow.
     * @param maxChar int - The inclusive maximum number of characters to allow.
     * @return boolean - Whether the passed string was valid or not.
     */
    public static boolean validateStringLength(String s, int minChar, int maxChar) {
        boolean valid = ((s.length() >= minChar) && (s.length() <= maxChar));
        return valid;
    }

    /**
     * A helper function which validates whether an email matches basic regex.
     * Regex ensures -
     *  Local
     *      uppercase and lowercase Latin letters A to Z and a to z
     *      digits 0 to 9
     *      Allow dot (.), underscore (_) and hyphen (-)
     *      dot (.) is not the first or last character
     *      dot (.) does not appear consecutively, e.g. mkyong..yong@example.com is not allowed
     *      Max 64 characters
     *  Domain
     *      uppercase and lowercase Latin letters A to Z and a to z
     *      digits 0 to 9
     *      hyphen (-) is not the first or last character
     *      dot (.) is not the first or last character
     *      dot (.) does not appear consecutively
     *      tld min 2 characters
     *
     * @param email A string of the email needing to be validated
     * @return a boolean which describes whether the passed email is valid or not.
     */
    public static boolean validateEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (Pattern.compile(emailRegex).matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

    //TODO: The below hashing mechanism feels like it should be placed in a more secure location.
    /**
     * A helper function which hashes a passed string to keep the item secure.
     * @param s The string which will be hashed.
     * @return A string containing the hash of the initially passed string.
     */
    public static String secureHash(String s){
        String cipher = "SHA-256";
        String checkSum = null;
        try {
            MessageDigest md = MessageDigest.getInstance(cipher);
            md.update(s.getBytes());
            byte[] digest = md.digest();
            checkSum = bytesToHex(digest);
        } catch (NoSuchAlgorithmException error) {
            error.printStackTrace();
        }
        return checkSum;
    }

    //TODO: The below hashing mechanism feels like it should be placed in a more secure location.
    /**
     * A helper function which converts bytes to their hex value.
     * @param bytes a list of individual bytes.
     * @return a string of the hex values from the bytes.
     */
    private static String bytesToHex(byte[] bytes){
        StringBuilder stringFactory = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int integerValue = 0xff & bytes[i];
            if (integerValue < 0x10) {
                stringFactory.append('0');
            }
            stringFactory.append(Integer.toHexString(integerValue));
        }
        return stringFactory.toString();
    }

}
