package com.replix.office.models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

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

    // Getters and Setters
    // ...

}

