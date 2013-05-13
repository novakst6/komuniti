/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import cz.komuniti.model.entity.FileEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */

public class OfferEditForm {
    private Long id;
    private String title;
    private String text;
    private List<Long> tagsId;
    private Long itemId;
    private Boolean active;
    private List<Long> filesToDelete;
    private Set<FileEntity> filesOrig;
    private CommonsMultipartFile[] files;
    private String[] descriptionOfFiles;

    public OfferEditForm() {
        tagsId = new LinkedList<Long>(); 
        filesToDelete = new LinkedList<Long>();
        descriptionOfFiles = new String[4];
        files = new CommonsMultipartFile[4];
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public List<Long> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<Long> tagsId) {
        this.tagsId = tagsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotEmpty(message="Název musí být vyplněn.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @AssertFalse(message="Musí být vybrána alespoň jedna kategorie.")
    public boolean isTagsEmpty()
    {
        return tagsId == null;
    }
    
    @AssertFalse(message="Musí být vybrána věc.")
    public boolean isItemEmpty()
    {
        return itemId == null;
    }

    public List<Long> getFilesToDelete() {
        return filesToDelete;
    }

    public void setFilesToDelete(List<Long> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    public Set<FileEntity> getFilesOrig() {
        return filesOrig;
    }

    public void setFilesOrig(Set<FileEntity> filesOrig) {
        this.filesOrig = filesOrig;
    }
    
    @AssertTrue(message="Velikost souboru překročila dovolenou velikost.")
    public boolean getCheckSize(){
        for(CommonsMultipartFile f: files){
            if(f == null){continue;}
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