package com.gelo.validation.forms;

import com.gelo.validation.Alert;
import org.junit.Assert;
import org.junit.Test;

public class OrderFormTest {

    @Test
    public void testValid(){
        OrderForm form = new OrderForm("I am ok how are you");
        Assert.assertTrue(form.valid());
    }

    @Test
    public void testRequired(){
        OrderForm form = new OrderForm("");

        Assert.assertFalse(form.valid());

        Assert.assertEquals(form.getErrorList().get(0), Alert.danger("required.description"));

    }


    @Test
    public void testInvalid(){
        OrderForm form = new OrderForm("I am toooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooo    big");

        Assert.assertFalse(form.valid());

        Assert.assertEquals(form.getErrorList().get(0), Alert.danger("invalid.description"));

    }
}
