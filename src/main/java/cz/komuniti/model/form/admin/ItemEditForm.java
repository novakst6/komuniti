/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import cz.komuniti.model.entity.FileEntity;
import java.util.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
public class ItemEditForm {
    private Long id;
    private String name;
    private String description;
    private String text;
    private List<Long> tags;
    private CommonsMultipartFile[] files;
    private String[] descriptionOfFiles;
    private Set<FileEntity> filesOrig;
    private List<Long> filesToDelete;
    private Boolean active;

    public ItemEditForm() {
        tags = new LinkedList<Long>();
        files = new CommonsMultipartFile[4];
        filesOrig= new HashSet<FileEntity>();
        filesToDelete = new LinkedList<Long>();
        descriptionOfFiles = new String[4];
    }

    public List<Long> getFilesToDelete() {
        return filesToDelete;
    }

    public void setFilesToDelete(List<Long> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FileEntity> getFilesOrig() {
        return filesOrig;
    }

    public void setFilesOrig(Set<FileEntity> filesOrig) {
        this.filesOrig = filesOrig;
    }
    
    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    @Length(max=1024,message="Maximální počet zaků je {max}.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message="Jméno musí být vyplněno.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @AssertFalse(message="Musí být vybrána alespoň jedna kategorie.")
    public boolean isTagsEmpty()
    {
        return tags == null;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @AssertTrue(message="Velikost souboru překročila dovolenou velikost.")
    public boolean getCheckSize(){
        for(CommonsMultipartFile f: files){
            if(f.getSize() > 200000){
                return false;
            }
        }
        return true;
    }

    public String[] getDescriptionOfFiles() {
        return descriptionOfFiles;
    }

    public void setDescriptionOfFiles(String[] descriptionOfFiles) {
        this.descriptionOfFiles = descriptionOfFiles;
    }

    public CommonsMultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(CommonsMultipartFile[] files) {
        this.files = files;
    }


    
    

}
