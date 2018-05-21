package com.gelo.validation.pagination;

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
public class ReviewPaginationFormTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1", "1", "true", "id", true, Collections.emptyList()},
                {"1", "1", "true", "author_id", true, Collections.emptyList()},
                {"", "1", "true", "title", false, Collections.singletonList(Alert.danger("required.pageStr"))},
                {"1", "", "true", "text", false, Collections.singletonList(Alert.danger("required.countStr"))},
                {"1", "1", "", "rating", false, Collections.singletonList(Alert.danger("required.ascendingStr"))},
                {"1", "1", "true", "", false, Collections.singletonList(Alert.danger("required.orderField"))}
        });
    }
    @Parameterized.Parameter
    public String pageStr;

    @Parameterized.Parameter(1)
    public String countStr;

    @Parameterized.Parameter(2)
    public String ascendingStr;

    @Parameterized.Parameter(3)
    public String orderField;

    @Parameterized.Parameter(4)
    public boolean validate;

    @Parameterized.Parameter(5)
    public List<Alert> expectedErrors;


    @Test
    public void valid() {
        ReviewPaginationForm form = new ReviewPaginationForm(pageStr, countStr, ascendingStr, orderField);

        Assert.assertEquals(form.valid(), validate);

        Assert.assertEquals(form.getErrorList(), expectedErrors);
    }
}