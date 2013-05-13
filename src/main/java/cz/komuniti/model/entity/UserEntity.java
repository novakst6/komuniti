/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

/**
 *
 * @author novakst6
 */

@Entity
@Table(name="USERS")
@Indexed(index="User_Entity_Index")
public class UserEntity implements Serializable {
    
    private Long id;
    private String email;
    private String password;
    private Boolean active;
    private Boolean deleted;
    private Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();
    private RegionEntity region;
    private CenterEntity centrum;
    private String googleName;
    private Long version;

    public UserEntity(String email, String password, Set<UserRoleEntity> roles) {
        this();
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public UserEntity() {
        //friends = new HashSet<UserEntity>();
        //requestForFrendship = new HashSet<UserEntity>();
    }
    
    @Column(name="USER_NAME")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="USER_ID")
    @DocumentId(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="USER_PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="USER_ROLE_ID")})
    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
    }

    @Column(name="USER_ACTIVE")
    @Type(type="true_false")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name="USER_DELETED")
    @Type(type="true_false")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @IndexedEmbedded
    @ManyToOne(fetch= FetchType.LAZY)
    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public CenterEntity getCentrum() {
        return centrum;
    }

    public void setCentrum(CenterEntity centrum) {
        this.centrum = centrum;
    }

    @Column(name="USER_GOOGLE_NAME")
    @Field(index= Index.YES, analyze= Analyze.YES,store= Store.NO)
    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
    
}
