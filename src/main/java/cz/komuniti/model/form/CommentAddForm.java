/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
public class CommentAddForm {
    private Long id;
    private String text;

    @NotEmpty(message="Text komentáře musí být vyplněn.")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
