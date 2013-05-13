/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.search.annotations.*;

/**
 *
 * @author novakst6
 */
@Entity
@Table(name="TAG")
@Indexed(index="Tag_Entity_Index")
public class TagEntity implements Serializable{
        
    private Long id;
    private String name;
    private TagEntity parent;
    private Set<TagEntity> childerns;
    private Set<ItemEntity> items;
    private Long vesion;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
    @OrderBy("name ASC")
    public Set<TagEntity> getChilderns() {
        return childerns;
    }

    public void setChilderns(Set<TagEntity> childerns) {
        this.childerns = childerns;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="TAG_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="TAG_NAME")
    @Field(analyze= Analyze.YES,index= Index.YES,store= Store.NO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public TagEntity getParent() {
        return parent;
    }

    public void setParent(TagEntity parent) {
        this.parent = parent;
    }

    @ManyToMany(mappedBy="tags",fetch= FetchType.LAZY)
    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    @Version
    public Long getVesion() {
        return vesion;
    }

    public void setVesion(Long vesion) {
        this.vesion = vesion;
    }
       
    @Transient
    public boolean isChildernsEmpty(){
        return childerns.isEmpty();
    }
    
    
}
