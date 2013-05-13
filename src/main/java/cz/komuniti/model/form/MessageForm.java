/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;


import cz.komuniti.service.validator.FieldEmailInDBNoDelete;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
@FieldEmailInDBNoDelete(email="recepient",message="Zadaný uživatel neexistuje.")
public class MessageForm {
    private String recepient;
    private String subject;
    private String text;

    @NotEmpty(message="Email příjemce musí být vyplněn.")
    @Email(message="Jméno příjemce musí být shodné s jeho emailovou adresou.")
    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    @Size(max=1024,message="Délka textu je maximálně {max} znaků.")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
