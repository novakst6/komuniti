/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import java.io.Serializable;

/**
 *
 * @author novakst6
 */
public class FileFulltextFilterForm implements Serializable {
    private String keywords = "";

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    
}
