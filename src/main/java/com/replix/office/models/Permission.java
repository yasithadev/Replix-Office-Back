package com.replix.office.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "permission_name", nullable = false, unique = true, length = 50)
    private String permissionName;

    @Column(name = "description", length = 255)
    private String description;

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

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<RolePermission> rolePermissions;

    // Getters and Setters
    // ...

}

