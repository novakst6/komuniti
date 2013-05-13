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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;


/**
 *
 * @author novakst6
 */
@Entity
@Table(name="ITEMS")
@Indexed(index="Item_Entity_Index")
public class ItemEntity implements Serializable {
    
    private Long id;
    private String name;
    private String description;
    private String text;
    private Set<TagEntity> tags;
    private Set<FileEntity> files;
    private Boolean active;
    private Boolean deleted;
    private Boolean createdByUser;
    private Set<OfferEntity> offers;
    private Set<CommentEntity> comments;
    private Long version;

    public ItemEntity() {
        tags = new HashSet<TagEntity>();
    }

    @Column(name="ITEM_DESCRIPTION",length=1024)
    @Field(analyze= Analyze.YES,index= org.hibernate.search.annotations.Index.YES,store= Store.NO)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ITEM_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="ITEM_NAME")
    @Field(analyze= Analyze.YES,index= org.hibernate.search.annotations.Index.YES,store= Store.NO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="ITEM_TEXT",length=131072)
    @Field(analyze= Analyze.YES,index= org.hibernate.search.annotations.Index.YES,store= Store.NO)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.LAZY)
    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    @OneToMany(fetch= FetchType.LAZY)
    public Set<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<FileEntity> files) {
        this.files = files;
    }

    @Column(name="ITEM_ACTIVE",nullable=false)
    @Type(type="true_false")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name="ITEM_DELETED")
    @Type(type="true_false")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name="ITEM_CREATED_BY_USER")
    @Type(type="true_false")
    public Boolean getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(Boolean createdByUser) {
        this.createdByUser = createdByUser;
    }

    @OneToMany(mappedBy="item",fetch= FetchType.LAZY)
    public Set<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferEntity> offers) {
        this.offers = offers;
    }

    @ManyToMany(fetch= FetchType.LAZY)
    @OrderBy(value="dateOfInsert desc")
    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
