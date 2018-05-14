package com.gelo.model.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * The type Role.
 */
public class Role implements Serializable{
    private static final long serialVersionUID = 62973123123121L;

    private Long id;
    private RoleType type;
    private Set<Permission> permissions;

    /**
     * Instantiates a new Role.
     *
     * @param id   the id
     * @param type the type
     */
    public Role(Long id, RoleType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Instantiates a new Role.
     *
     * @param id          the id
     * @param type        the type
     * @param permissions the permissions
     */
    public Role(Long id, RoleType type, Set<Permission> permissions) {
        this.id = id;
        this.type = type;
        this.permissions = permissions;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
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
     * Gets type.
     *
     * @return the type
     */
    public RoleType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(RoleType type) {
        this.type = type;
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                type == role.type &&
                Objects.equals(permissions, role.permissions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, permissions);
    }
}
