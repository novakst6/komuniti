/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 *
 * @author novakst6
 */
@Entity
@Table(name="COMMENT")
public class CommentEntity implements Serializable {
    private Long id;
    private String text;
    private Boolean banned;
    private UserEntity author;
    private Date dateOfInsert;
    private Boolean edited;
    private UserEntity editor;
    private Date dateOfEdit;
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Column(name="COMMENT_BANNED")
    @Type(type="true_false")
    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="COMMENT_DATE_EDIT")
    public Date getDateOfEdit() {
        return dateOfEdit;
    }

    public void setDateOfEdit(Date dateOfEdit) {
        this.dateOfEdit = dateOfEdit;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="COMMENT_DATE_INSERT")
    public Date getDateOfInsert() {
        return dateOfInsert;
    }

    public void setDateOfInsert(Date dateOfInsert) {
        this.dateOfInsert = dateOfInsert;
    }

    @Column(name="COMMENT_EDITED")
    @Type(type="true_false")
    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity getEditor() {
        return editor;
    }

    public void setEditor(UserEntity editor) {
        this.editor = editor;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="COMMENT_TEXT",length=1024)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
}
