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
public class UserCreationFormTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Oleg", "odubinskiy@ukr.net", "Ukraine", "1234qwer","1", true, Collections.emptyList()},
                {"", "odubinskiy@ukr.net", "Ukraine", "1234qwer", "1",false, Collections.singletonList(Alert.danger("required.name"))},
                {"Oleg", "", "Ukraine", "1234qwer","1", false, Collections.singletonList(Alert.danger("required.email"))},
                {"Oleg", "odubinskiy@ukr.net", "", "1234qwer","1", false, Collections.singletonList(Alert.danger("required.country"))},
                {"Oleg", "odubinskiy@ukr.net", "Ukraine", "", "1",false, Collections.singletonList(Alert.danger("required.password"))},
                {"Oleg", "odubinskiy@ukr.net", "Ukraine", "1234qwer", "5",false, Collections.singletonList(Alert.danger("invalid.roleId"))},
                {"Oleg", "odubinskiy@ukr.net", "Ukraine", "1234qwer", "",false, Collections.singletonList(Alert.danger("required.roleId"))}
        });
    }

    @Parameterized.Parameter
    public String name;

    @Parameterized.Parameter(1)
    public String email;

    @Parameterized.Parameter(2)
    public String country;

    @Parameterized.Parameter(3)
    public String password;

    @Parameterized.Parameter(4)
    public String roleId;

    @Parameterized.Parameter(5)
    public boolean validate;

    @Parameterized.Parameter(6)
    public List<Alert> expectedErrors;

    @Test
    public void valid() {
        UserCreationForm form = new UserCreationForm(name, email, country, password, roleId);

        Assert.assertEquals(form.valid(), validate);

        Assert.assertEquals(form.getErrorList(), expectedErrors);
    }
}