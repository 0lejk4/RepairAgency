package com.gelo.validation.forms;

import com.gelo.model.domain.Role;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.validation.Valid;
import com.gelo.validation.Validated;
import com.gelo.validation.constants.Regexps;
import com.gelo.model.domain.User;

/**
 * The type User creation form.
 */
public class UserCreationForm extends Validated {
    private String name;
    private String email;
    private String country;
    private String password;
    private String roleId;

    /**
     * Instantiates a new User creation form.
     *
     * @param name     the name
     * @param email    the email
     * @param country  the country
     * @param password the password
     * @param roleId   the role id
     */
    public UserCreationForm(String name, String email, String country, String password, String roleId) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.password = password;
        this.roleId = roleId;
    }

    /**
     * Parse user user.
     *
     * @return the user
     */
    public User parseUser(){
        Long roleId = Long.parseLong(this.roleId);
        return new User.UserBuilder().name(name)
                .email(email)
                .country(country)
                .password(password)
                .role(new Role(roleId, RoleType.values()[roleId.intValue()]))
                .build();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @Valid(value = Regexps.STR_LEN, params = {"3", "50"})
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets country.
     *
     * @return the country
     */
    @Valid(value = Regexps.STR_LEN, params = {"3", "25"})
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
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

    /**
     * Gets role id.
     *
     * @return the role id
     */
    @Valid(Regexps.ROLES)
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets role id.
     *
     * @param roleId the role id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
