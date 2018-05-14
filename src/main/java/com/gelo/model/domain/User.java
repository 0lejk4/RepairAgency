package com.gelo.model.domain;

import com.gelo.model.dao.generic.Identified;

import java.io.Serializable;

/**
 * The type User.
 */
public class User implements Serializable, Identified<Long> {

    private static final long serialVersionUID = 6297385302078200511L;

    private Long id;
    private String name;
    private String email;
    private String country;
    private String password;
    private Integer activeOrdersCount;
    private Integer summaryOrdersCount;
    private Role role;

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.name = userBuilder.name;
        this.email = userBuilder.email;
        this.country = userBuilder.country;
        this.password = userBuilder.password;
        this.role = userBuilder.role;
        this.activeOrdersCount = userBuilder.activeOrdersCount;
        this.summaryOrdersCount = userBuilder.summaryOrdersCount;
    }

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private Long id;
        private String name;
        private String email;
        private String country;
        private String password;
        private Role role;
        private Integer activeOrdersCount;
        private Integer summaryOrdersCount;

        /**
         * Id user builder.
         *
         * @param id the id
         * @return the user builder
         */
        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Name user builder.
         *
         * @param name the name
         * @return the user builder
         */
        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Email user builder.
         *
         * @param email the email
         * @return the user builder
         */
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Country user builder.
         *
         * @param country the country
         * @return the user builder
         */
        public UserBuilder country(String country) {
            this.country = country;
            return this;
        }

        /**
         * Password user builder.
         *
         * @param password the password
         * @return the user builder
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Role user builder.
         *
         * @param role the role
         * @return the user builder
         */
        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        /**
         * Active orders count user builder.
         *
         * @param activeOrdersCount the active orders count
         * @return the user builder
         */
        public UserBuilder activeOrdersCount(Integer activeOrdersCount) {
            this.activeOrdersCount = activeOrdersCount;
            return this;
        }

        /**
         * Summary orders count user builder.
         *
         * @param summaryOrdersCount the summary orders count
         * @return the user builder
         */
        public UserBuilder summaryOrdersCount(Integer summaryOrdersCount) {
            this.summaryOrdersCount = summaryOrdersCount;
            return this;
        }


        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return new User(this);
        }
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets active orders count.
     *
     * @return the active orders count
     */
    public Integer getActiveOrdersCount() {
        return activeOrdersCount;
    }

    /**
     * Sets active orders count.
     *
     * @param activeOrdersCount the active orders count
     */
    public void setActiveOrdersCount(Integer activeOrdersCount) {
        this.activeOrdersCount = activeOrdersCount;
    }

    /**
     * Gets summary orders count.
     *
     * @return the summary orders count
     */
    public Integer getSummaryOrdersCount() {
        return summaryOrdersCount;
    }

    /**
     * Sets summary orders count.
     *
     * @param summaryOrdersCount the summary orders count
     */
    public void setSummaryOrdersCount(Integer summaryOrdersCount) {
        this.summaryOrdersCount = summaryOrdersCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
