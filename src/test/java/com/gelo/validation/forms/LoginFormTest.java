package com.gelo.validation.forms;

import com.gelo.validation.Alert;
import com.gelo.validation.forms.LoginForm;
import org.junit.Assert;
import org.junit.Test;

public class LoginFormTest {

    @Test
    public void testValid(){
        LoginForm form = new LoginForm("odubinskiy@ukr.net", "1234qwer");
        Assert.assertTrue(form.valid());
        Assert.assertEquals(form.getErrorList().size() , 0);
    }

    @Test
    public void testInvalidEmail(){
        LoginForm form = new LoginForm("odubinskiy@ukr", "1234qwer");
        Assert.assertFalse(form.valid());
        Assert.assertEquals(form.getErrorList().get(0), Alert.danger("invalid.email"));
    }

    @Test
    public void testInvalidPassword(){
        LoginForm form = new LoginForm("odubinskiy@ukr.net", "1234r");
        Assert.assertFalse(form.valid());
        Assert.assertEquals(form.getErrorList().get(0) ,Alert.danger("invalid.password") );
    }


    @Test
    public void testRequiredPassword(){
        LoginForm form = new LoginForm("odubinskiy@ukr.net", null);
        Assert.assertFalse(form.valid());
        Assert.assertEquals(form.getErrorList().get(0) ,Alert.danger("required.password") );
    }


    @Test
    public void testRequiredEmail(){
        LoginForm form = new LoginForm("", "1234r");
        Assert.assertFalse(form.valid());
        Assert.assertEquals(form.getErrorList().get(0) ,Alert.danger("required.email") );
    }
}
