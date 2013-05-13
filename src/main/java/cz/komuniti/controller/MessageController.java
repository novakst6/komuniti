/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.MessageEntity;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.form.MessageForm;
import java.sql.Date;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value = {"messages"})
@Secured(value={"ROLE_USER"})
public class MessageController extends TemplateController {

    @RequestMapping(value = {"inbox.htm"}, method = RequestMethod.GET)
    public String inbox(
            @RequestParam(value = "page", required = false) Integer page,
            ModelMap m,
            Authentication auth) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }
        try {
            if (isParameterNull(auth)) {
                getInfoMessages().setWarnMessage("Uživatel byl odhlášen.");
                return "redirect:/index.htm";
            }
            User u = (User) auth.getPrincipal();
            String email = u.getUsername();

            UserEntity user = getUserManager().findByEmail(email);

            getPaginator().setMax(getMessageManager().getCountInbox(email, Boolean.FALSE));
            getPaginator().setPage(page);
            m.addAttribute("inbox", getMessageManager().findInbox(email, Boolean.FALSE, getPaginator().getResult()[0], getPaginator().getResult()[1]));
            return "messages/inbox";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:/index.htm";
        }
    }

    @RequestMapping(value = {"outbox.htm"}, method = RequestMethod.GET)
    public String outbox(
            @RequestParam(value = "page", required = false) Integer page,
            ModelMap m,
            Authentication auth) throws Exception {
        if (page == null) {
            page = new Integer(1);
        }
        try {
            if (isParameterNull(auth)) {
                getInfoMessages().setWarnMessage("Uživatel byl odhlášen.");
                return "redirect:/index.htm";
            }
            User u = (User) auth.getPrincipal();
            String email = u.getUsername();
            getPaginator().setMax(getMessageManager().getCountOutbox(email, Boolean.FALSE));
            getPaginator().setPage(page);
            m.addAttribute("outbox", getMessageManager().findOutbox(email, Boolean.FALSE, getPaginator().getResult()[0], getPaginator().getResult()[1]));
            return "messages/outbox";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:/index.htm";
        }
    }

    @RequestMapping(value = {"new.htm"}, method = RequestMethod.GET)
    public String newGET(
            @ModelAttribute(value = "newFormModel") MessageForm newFormModel,
            @RequestParam(value="user",required= false) String user,
            ModelMap m
            ) throws Exception {
        if(isParameterNull(user)){
            return "messages/new";
        }
        newFormModel.setRecepient(user);
        return "messages/new";
    }

    @RequestMapping(value = {"new.htm"}, method = RequestMethod.POST)
    public String newPOST(
            @ModelAttribute(value = "newFormModel")
            @Valid MessageForm newFormModel,
            BindingResult errors,
            Authentication auth) throws Exception {
        if (errors.hasErrors()) {
            return "messages/new";
        }
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findByEmail(u.getUsername());
            UserEntity recepient = getUserManager().findByEmail(newFormModel.getRecepient());

            MessageEntity msg = new MessageEntity();
            MessageEntity msg2 = new MessageEntity();
            msg.setAuthor(user);
            msg2.setAuthor(user);

            msg.setOwner(user);
            msg2.setOwner(recepient);

            msg.setRecipient(recepient);
            msg2.setRecipient(recepient);

            String subject = newFormModel.getSubject();
            if (subject.equals("")) {
                msg.setSubject("[Bez předmětu]");
                msg2.setSubject("[Bez předmětu]");
            } else {
                msg.setSubject(newFormModel.getSubject());
                msg2.setSubject(newFormModel.getSubject());
            }
            msg.setText(newFormModel.getText());
            msg2.setText(newFormModel.getText());
            msg.setSendDate(new Date(System.currentTimeMillis()));
            msg2.setSendDate(new Date(System.currentTimeMillis()));
            msg.setDeleted(Boolean.FALSE);
            msg2.setDeleted(Boolean.FALSE);
            msg.setReaded(Boolean.FALSE);
            msg2.setReaded(Boolean.FALSE);
            getMessageManager().add(msg);
            if (user.getId().longValue() != recepient.getId().longValue()) {
                getMessageManager().add(msg2);
            }
            getInfoMessages().setInfoMessage("Zpráva byla odeslána uživateli " + newFormModel.getRecepient());
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("Při odesílání zprávy došlo k chybě. " + e.getMessage());
        }

        return "redirect:inbox.htm";
    }

    @RequestMapping(value = {"detail.htm"}, method = RequestMethod.GET)
    public String detail(
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m,
            Authentication auth) throws Exception {
        if (isParameterNull(id)) {
            getInfoMessages().setWarnMessage("Je nutné zadat id zprávy.");
            return "redirect:inbox.htm";
        }
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findByEmail(u.getUsername());
            MessageEntity msg = getMessageManager().findById(id);
            if (isParameterNull(msg)) {
                getInfoMessages().setWarnMessage("Tuto zprávu nelze nalézt.");
                return "redirect:inbox.htm";
            }
            if (msg.getDeleted()) {
                getInfoMessages().setWarnMessage("Tuto zprávu nelze nalézt.");
                return "redirect:inbox.htm";
            }
            if (!(user.getEmail().equals(msg.getAuthor().getEmail())
                    || user.getEmail().equals(msg.getRecipient().getEmail()))) {
                getInfoMessages().setWarnMessage("Tuto zprávu nelze zobrazit.");
                return "redirect:inbox.htm";
            }
            msg.setReaded(Boolean.TRUE);
            getMessageManager().edit(msg);
            m.addAttribute("message", msg);
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:inbox.htm";
        }
        return "messages/detail";
    }

    @RequestMapping(value = {"delete.htm"}, method = RequestMethod.GET)
    public String delete(
            @RequestParam(value = "id", required = true) Long id,
            Authentication auth) throws Exception {
        if (isParameterNull(id)) {
            getInfoMessages().setWarnMessage("Parametr id je povinný.");
            return "redirect:inbox.htm";
        }
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findByEmail(u.getUsername());

            MessageEntity msg = getMessageManager().findById(id);
            if (!((user.getEmail().equals(msg.getAuthor().getEmail()))
                    || (user.getEmail().equals(msg.getRecipient().getEmail())))) {
                getInfoMessages().setWarnMessage("Zprávu se nepodařilo smazat.");
                return "redirect:inbox.htm";
            }

            msg.setDeleted(Boolean.TRUE);
            getMessageManager().edit(msg);

            getInfoMessages().setInfoMessage("Zpráva byla smazána.");
        } catch (Exception e) {
            getInfoMessages().setWarnMessage("ERROR");
        }
        return "redirect:inbox.htm";
    }
}
