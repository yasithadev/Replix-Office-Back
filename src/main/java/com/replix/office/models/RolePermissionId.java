package com.replix.office.models;

import java.io.Serializable;
import java.util.Objects;

public class RolePermissionId implements Serializable {
    private Integer role;
    private Integer permission;

    // Default constructor, hashCode and equals methods
    // ...

    @Override
    public int hashCode() {
        return Objects.hash(role, permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionId that = (RolePermissionId) o;
        return Objects.equals(role, that.role) && Objects.equals(permission, that.permission);
    }
}

