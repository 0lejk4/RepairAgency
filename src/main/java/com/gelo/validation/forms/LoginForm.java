package com.gelo.validation.forms;

import com.gelo.validation.Form;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

/**
 * The type Login form.
 * Is used when user is logging in.
 */
public class LoginForm extends Form {
    private String email;
    private String password;

    /**
     * Instantiates a new Login form.
     *
     * @param email    the email
     * @param password the password
     */
    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    @Valid(Regexps.EMAIL)
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    @Valid(value = Regexps.STR_LEN, params = {"8", "20"})
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
