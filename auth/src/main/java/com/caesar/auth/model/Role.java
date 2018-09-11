package com.caesar.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseIdEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "permission_role", joinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
        @JoinColumn(name = "permission_id", referencedColumnName = "id") })
    private List<Permission> permissions;

}
