/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author novakst6
 */
@Entity
@Table(name="USER_ROLE")
public class UserRoleEntity implements Serializable{
    
    private Long id;
    private String name;
    private Long version;
    private Set<UserEntity> users = new HashSet<UserEntity>();

    public UserRoleEntity(String name) {
        this.name = name;
    }

    public UserRoleEntity() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="USER_ROLE_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="USER_ROLE_NAME")
    @NotNull(message="Název role nesmí být prázdný!")
    @Size(min=4, max=255, message="Název role musí být větší než 4 znaky a menší než 255 znaků")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @ManyToMany(mappedBy="roles",fetch= FetchType.LAZY)
    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Transient
    public boolean isUsersEmpty(){
        return users.isEmpty();
    }
    
}
