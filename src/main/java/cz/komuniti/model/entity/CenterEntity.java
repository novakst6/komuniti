/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author novakst6
 */
@Entity
@Table(name="CENTER")
public class CenterEntity implements Serializable {
    private Long id;
    private String name;
    private List<UserEntity> members;
    private RegionEntity region;
    private String info;
    private UserEntity admin;
    private Long version;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="CENTRUM_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch= FetchType.LAZY,mappedBy="centrum")
    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    @Column(name="CENTRUM_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    @Column(name="CENTRUM_INFO",length=2048)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public UserEntity getAdmin() {
        return admin;
    }

    public void setAdmin(UserEntity admin) {
        this.admin = admin;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    @Transient
    public boolean isMembersEmpty()
    {
        return members.isEmpty();
    }
}
