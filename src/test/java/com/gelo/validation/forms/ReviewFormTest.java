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
public class ReviewFormTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1", "I am title", "I am text", "1", true, Collections.emptyList()},
                {"1asdasd", "I am title", "I am text", "1", false, Collections.singletonList(Alert.danger("invalid.masterId"))},
                {"1", "I am title", "I am text", "", false, Collections.singletonList(Alert.danger("required.rating"))},
                {"1", "I am title", "", "1", false, Collections.singletonList(Alert.danger("required.text"))},
                {"1", "", "I am text", "1", false, Collections.singletonList(Alert.danger("required.title"))},
                {"", "I am title", "I am text", "1", false, Collections.singletonList(Alert.danger("required.masterId"))}
        });
    }

    @Parameterized.Parameter
    public String masterId;

    @Parameterized.Parameter(1)
    public String title;

    @Parameterized.Parameter(2)
    public String text;

    @Parameterized.Parameter(3)
    public String rating;

    @Parameterized.Parameter(4)
    public boolean validate;

    @Parameterized.Parameter(5)
    public List<Alert> expectedErrors;


    @Test
    public void valid() {
        ReviewForm form = new ReviewForm(masterId, title, text, rating);

        Assert.assertEquals(form.valid(), validate);

        Assert.assertEquals(form.getErrorList(), expectedErrors);
    }
}