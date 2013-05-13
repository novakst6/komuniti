/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service("mailService")
public class MailService extends JavaMailSenderImpl{
    
    private MessageSource messageSource;

    public MailService() {
    }
    
    @PostConstruct
    private void init(){
        setHost(messageSource.getMessage("mail.host", new Object[]{}, Locale.getDefault()));
        setPort(Integer.parseInt(messageSource.getMessage("mail.port", new Object[]{}, Locale.getDefault())));
        setUsername(messageSource.getMessage("mail.username", new Object[]{}, Locale.getDefault()));
        setPassword(messageSource.getMessage("mail.password", new Object[]{}, Locale.getDefault()));
        java.util.Properties p = new Properties();
        p.put("mail.transport.protocol", messageSource.getMessage("mail.transport.protocol", new Object[]{}, Locale.getDefault()));
        p.put("mail.smtp.auth", messageSource.getMessage("mail.smtp.auth", new Object[]{}, Locale.getDefault()));
        p.put("mail.smtp.starttls.enable", messageSource.getMessage("mail.smtp.starttls.enable", new Object[]{}, Locale.getDefault()));
        p.put("mail.debug", messageSource.getMessage("mail.debug", new Object[]{}, Locale.getDefault()));
        setJavaMailProperties(p);
    }
    
   
    public @Autowired void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
   
   
}
