/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author novakst6
 */

@Entity
@Table(name="CONFIG")
public class AppConfigEntity implements Serializable{
    
    private Long id;
    private UserEntity contentModerator;
    private UserEntity commentModerator;
    private Long version;

    @ManyToOne
    public UserEntity getCommentModerator() {
        return commentModerator;
    }

    
    public void setCommentModerator(UserEntity commentModerator) {
        this.commentModerator = commentModerator;
    }
    
    @ManyToOne
    public UserEntity getContentModerator() {
        return contentModerator;
    }

    public void setContentModerator(UserEntity contentModerator) {
        this.contentModerator = contentModerator;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="CONFIG_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    
}
