package com.replix.office.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_permission")
@IdClass(RolePermissionId.class)
public class RolePermission implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;

    @Column(name = "last_modified_on", insertable = false, updatable = false)
    private Timestamp lastModifiedOn;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_on", insertable = false, updatable = false)
    private Timestamp createdOn;
}
