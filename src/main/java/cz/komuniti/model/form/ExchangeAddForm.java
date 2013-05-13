/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;

import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.AssertFalse;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
public class ExchangeAddForm {

    private String title;
    private String text;
    private List<Long> tagsId;
    private Long itemId;
    private Boolean active;
    private CommonsMultipartFile[] files;
    private String[] descriptionOfFiles;
    private Boolean ownItem;
    private Boolean fromCatalog;

    public ExchangeAddForm() {
        files = new CommonsMultipartFile[4];
        descriptionOfFiles = new String[4];
        tagsId = new LinkedList<Long>();
        fromCatalog = false;
        ownItem = false;
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

    public Boolean getFromCatalog() {
        return fromCatalog;
    }

    public void setFromCatalog(Boolean fromCatalog) {
        this.fromCatalog = fromCatalog;
    }
    
    @AssertFalse(message="Musí být vybrána alespoň jedna kategorie.")
    public boolean isTagsEmpty()
    {
        return tagsId == null;
    }
    
    
    @AssertFalse(message="Musí být vybrána věc.")
    public boolean isItemEmpty()
    {
        if(itemId == null){
            if(fromCatalog){
                return false;
            }
            if(ownItem){
                return false;
            } else {
                return true;
            }
        } else {
            return itemId == null;
        }
        
    }

    public Boolean getOwnItem() {
        return ownItem;
    }

    public void setOwnItem(Boolean ownItem) {
        this.ownItem = ownItem;
    }
    
    @AssertFalse(message="Soubor musí být menší 200000 bytů")
    public boolean isFileSizeOk(){
        boolean overSize = false;
        for (int i = 0; i < files.length; i++) {
            CommonsMultipartFile file = files[i];
            if(file == null){continue;}
            if(file.getSize() > 200000){
                overSize = true;
            }
        }
        return overSize;
    }
}


