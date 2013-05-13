/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.*;

/**
 *
 * @author novakst6
 */
@Entity
@Table(name="REGION")
@Indexed(index="Region_Entity_Index")
public class RegionEntity implements Serializable {
    private Long id;
    private String name;
    private RegionEntity parent;
    private Set<RegionEntity> childerns;
    private Set<UserEntity> users;
    private Long version;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy(value="name")
    public Set<RegionEntity> getChilderns() {
        return childerns;
    }

    public void setChilderns(Set<RegionEntity> childerns) {
        this.childerns = childerns;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="REGION_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="REGION_NAME")
    @Field(analyze= Analyze.YES,index= Index.YES,store= Store.NO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public RegionEntity getParent() {
        return parent;
    }

    public void setParent(RegionEntity parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy="region",targetEntity= UserEntity.class,fetch= FetchType.LAZY)
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
    public boolean isChildernsEmpty(){
        return childerns.isEmpty();
    }
    
}
