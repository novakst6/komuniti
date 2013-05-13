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
public class ExchangeResponseForm {
    private Long recepientId;
    private Long offerId;
    private String recepient;
    private String subject;
    private String text;

    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }

    @NotEmpty(message="Předmět nesmí být prázdný")
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

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getRecepientId() {
        return recepientId;
    }

    public void setRecepientId(Long recepientId) {
        this.recepientId = recepientId;
    }
    
    
}
