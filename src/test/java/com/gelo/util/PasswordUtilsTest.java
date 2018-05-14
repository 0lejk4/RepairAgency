package com.gelo.util;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilsTest {

    @Test
    public void testNotSame() {
        String realPassword = "1234qwer";
        String encryptedPassword = PasswordUtils.encryptPassword(realPassword);
        String anotherEncryptedPassword = PasswordUtils.encryptPassword(realPassword);

        Assert.assertNotEquals(encryptedPassword, anotherEncryptedPassword);
    }

    @Test
    public void passwordMatchEncypted() {
        String realPassword = "1234qwer";
        String encryptedPassword = PasswordUtils.encryptPassword(realPassword);
        Boolean match = PasswordUtils.checkPassword(realPassword, encryptedPassword);
        Assert.assertTrue(match);
    }
}
