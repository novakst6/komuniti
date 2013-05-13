/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
public class BlogAddForm {
    private String text;
    private String title;

    @Size(max=4096,message="Maximální počet znaků je {max}.")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotEmpty(message="Nadpis nesmí být prázdný.")
    @Size(max=255,message="Maximální délka nadpisu je {max} znaků.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
