package com.gelo.validation.formats;

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
public class LongFormatTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1", true, Collections.emptyList()},
                {"-123", false, Collections.singletonList(Alert.danger("invalid.number"))},
                {"asdasd", false, Collections.singletonList(Alert.danger("invalid.number"))},
                {"", false, Collections.singletonList(Alert.danger("required.number"))},
                {"123456789098764321", false, Collections.singletonList(Alert.danger("invalid.number"))}
        });
    }

    @Parameterized.Parameter(0)
    public String number;

    @Parameterized.Parameter(1)
    public boolean validate;

    @Parameterized.Parameter(2)
    public List<Alert> expectedErrors;

    @Test
    public void parseLong() {
        LongFormat format = new LongFormat(number);

        Assert.assertEquals(validate, format.valid());

        Assert.assertEquals(expectedErrors, format.getErrorList());
    }
}