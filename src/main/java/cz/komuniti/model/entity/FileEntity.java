/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author novakst6
 */

@Entity
@Table(name="FILES")
@Indexed(index="FILE_ENTITY")
public class FileEntity implements Serializable {
    
    private Long id;
    private String name;
    private String contentType;
    private Long fileSize;
    private byte[] stream;
    private String description;
    private Long version;

    @Column(name="FILE_DESCRIPTION")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="FILE_ID")
    @DocumentId(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="FILE_NAME")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="FILE_STREAM",length=262144)
    public byte[] getStream() {
        return stream;
    }

    public void setStream(byte[] stream) {
        this.stream = stream;
    }

    @Column(name="FILE_TYPE")
    @Field(index= Index.YES,analyze= Analyze.YES,store= Store.NO)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Column(name="FILE_SIZE")
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
  
}
