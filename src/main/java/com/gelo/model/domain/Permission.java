package com.gelo.model.domain;

import java.util.Objects;

/**
 * The type Permission.
 */
public class Permission {
    private Long id;
    private PermissionType type;

    /**
     * Instantiates a new Permission.
     *
     * @param id   the id
     * @param type the type
     */
    public Permission(Long id, PermissionType type) {
        this.id = id;
        this.type = type;
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
    public PermissionType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(PermissionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                type == that.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type);
    }
}
