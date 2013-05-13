/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

/**
 *
 * @author novakst6
 */

@Entity
@Table(name="MESSAGES")
@Indexed(index="Message_Entity_Index")
public class MessageEntity implements Serializable {
    
    private Long id;
    private UserEntity author;
    private UserEntity recipient;
    private UserEntity owner;
    private String subject;
    private String text;
    private Date sendDate;
    private Boolean deleted;
    private Boolean readed;
    private OfferEntity offer;
    private Long version;

    @Column(name="MESSAGE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="MESSAGE_ID")
    @DocumentId(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="MESSAGE_SUBJECT")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name="MESSAGE_TEXT", length=1024)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @IndexedEmbedded
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @IndexedEmbedded
    public UserEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(UserEntity recipient) {
        this.recipient = recipient;
    }

    @Column(name="MESSAGE_DELETED")
    @Type(type="true_false")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name="MESSAGE_READED")
    @Type(type="true_false")
    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @IndexedEmbedded
    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    public OfferEntity getOffer() {
        return offer;
    }

    public void setOffer(OfferEntity offer) {
        this.offer = offer;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
}
