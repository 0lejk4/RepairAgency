package com.gelo.validation.forms;

import com.gelo.model.domain.Role;
import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

/**
 * The type User creation form.
 * Is used when admin is registering new User
 */
public class UserCreationForm extends RegisterForm {
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
        super(name, email, country, password);
        this.roleId = roleId;
    }

    /**
     * Parse user user.
     *
     * @return the user
     */
    public User parseUser(){
        Long roleId = Long.parseLong(this.roleId);
        return new User.UserBuilder()
                .name(getName())
                .email(getEmail())
                .country(getCountry())
                .password(getPassword())
                .role(new Role(roleId, RoleType.values()[roleId.intValue()]))
                .build();
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
