package com.gelo.validation.forms;

import com.gelo.validation.Alert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class AnswerOrderFormTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"12", "123123", "This is test description", true, Collections.emptyList()},
                {"12", "123123", "", false, Collections.singletonList(Alert.danger("required.description"))},
                {"12", "", "This is test description", false, Collections.singletonList(Alert.danger("required.price"))},
                {"", "123123", "This is test description", false, Collections.singletonList(Alert.danger("required.id"))},
                {"12", "123123asd", "This is test description", false, Collections.singletonList(Alert.danger("invalid.price"))}
        });
    }

    @Parameterized.Parameter
    public String id;

    @Parameterized.Parameter(1)
    public String price;

    @Parameterized.Parameter(2)
    public String description;

    @Parameterized.Parameter(3)
    public boolean valid;

    @Parameterized.Parameter(4)
    public List<Alert> expectedErrors;


    @Test
    public void valid() {
        AnswerOrderForm form = new AnswerOrderForm(id, description, price);

        Assert.assertEquals(form.valid(), this.valid);

        Assert.assertEquals(form.getErrorList(), expectedErrors);
    }
}