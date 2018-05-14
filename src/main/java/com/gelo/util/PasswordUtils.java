package com.gelo.util;

import org.jasypt.util.password.BasicPasswordEncryptor;


/**
 * The type Password utils.
 * Delegates methods of passwordEncryptor from Jasypt library.
 */
public class PasswordUtils {
    private final static BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    /**
     * Check password boolean.
     * Compares two password if they are the same.
     * @param password          the password
     * @param encryptedPassword the encrypted password
     * @return the boolean
     */
    public static boolean checkPassword(String password, String encryptedPassword){
        return passwordEncryptor.checkPassword(password,encryptedPassword);
    }

    /**
     * Encrypt password string.
     *
     * @param password the password
     * @return the string
     */
    public static String encryptPassword(String password){
        return passwordEncryptor.encryptPassword(password);
    }
}
