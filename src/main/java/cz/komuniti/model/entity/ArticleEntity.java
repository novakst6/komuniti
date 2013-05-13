/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Date;
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
@Table(name="ARTICLE")
@Indexed(index="Article_Entity_Index")
public class ArticleEntity implements Serializable {
    private Long id;
    private Date dateOfInsert;
    private String title;
    private String text;
    private UserEntity author;
    private Boolean edited;
    private Date dateOfEdit;
    private UserEntity editor;
    private Set<FileEntity> files = new HashSet<FileEntity>();
    private Set<CommentEntity> comments = new HashSet<CommentEntity>();
    private Boolean deleted;
    private Long version;

    @IndexedEmbedded
    @ManyToOne(fetch= FetchType.LAZY)
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Column(name="ARTICLE_DATE_EDIT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateOfEdit() {
        return dateOfEdit;
    }

    public void setDateOfEdit(Date dateOfEdit) {
        this.dateOfEdit = dateOfEdit;
    }

    @Column(name="ARTICLE_DATE_INSERT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateOfInsert() {
        return dateOfInsert;
    }

    public void setDateOfInsert(Date dateOfInsert) {
        this.dateOfInsert = dateOfInsert;
    }

    @Column(name="ARTICLE_EDITED")
    @Type(type="true_false")
    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public UserEntity getEditor() {
        return editor;
    }

    public void setEditor(UserEntity editor) {
        this.editor = editor;
    }

    @ManyToMany(fetch= FetchType.LAZY)
    public Set<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<FileEntity> files) {
        this.files = files;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ARTICLE_ID")
    @DocumentId(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    @Column(name="ARTICLE_TEXT",length=4096)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    @Column(name="ARTICLE_TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToMany(fetch= FetchType.LAZY)
    @OrderBy(value="dateOfInsert desc")
    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    @Column(name="ARTICLE_DELETED")
    @Type(type="true_false")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
    
}
