/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name="OFFER")
@Indexed(index="Offer_Entity_Index")
public class OfferEntity implements Serializable {
    private Long id;
    private String title;
    private String text;
    private Timestamp insertDate;
    private UserEntity author;   
    private Set<OfferTagEntity> tags;
    private ItemEntity item;
    private Boolean active;
    private Boolean deleted;
    private Set<FileEntity> files;
    private Boolean edited;
    private Timestamp editedDate;
    private UserEntity editedBy;
    private Long version;

    public OfferEntity() {
        tags = new HashSet<OfferTagEntity>();
    }
    
    @IndexedEmbedded
    @ManyToOne(fetch= FetchType.LAZY)
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="OFFER_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @IndexedEmbedded
    @ManyToOne(fetch= FetchType.LAZY)
    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @IndexedEmbedded
    @ManyToMany(fetch= FetchType.LAZY)
    public Set<OfferTagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<OfferTagEntity> tags) {
        this.tags = tags;
    }

    @Column(name="OFFER_TEXT",length=1024)
    @Field(analyze= Analyze.YES,index= Index.YES,store= Store.NO)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name="OFFER_TITTLE")
    @Field(analyze= Analyze.YES,index= Index.YES,store= Store.NO)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="OFFER_ACTIVE", nullable=false)
    @Type(type="true_false")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name="OFFER_DELETED", nullable=false)
    @Type(type="true_false")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(fetch= FetchType.LAZY)
    public Set<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<FileEntity> files) {
        this.files = files;
    }

    @Column(name="OFFER_EDITED")
    @Type(type="true_false")
    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public UserEntity getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(UserEntity editedBy) {
        this.editedBy = editedBy;
    }

    @Column(name="OFFER_EDITED_DATE")
    public Timestamp getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Timestamp editedDate) {
        this.editedDate = editedDate;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
    
}
