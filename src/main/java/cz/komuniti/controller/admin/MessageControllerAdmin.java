/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;


import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.MessageEntity;
import cz.komuniti.model.form.admin.MessageFulltextFilterForm;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value={"admin/message"})
@Secured(value={"ROLE_ADMIN"})
public class MessageControllerAdmin extends TemplateController{
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showGET(
            @ModelAttribute(value="fulltextFilterForm") MessageFulltextFilterForm filterForm,
            @RequestParam(value="page",required=false)
            Integer page,
            ModelMap m,
            HttpSession session
            ) throws Exception 
    {
        if(isParameterNull(page)){ page = new Integer(1);}
        
        MessageFulltextFilterForm filterSettings = (MessageFulltextFilterForm) session.getAttribute("messageFulltextFilterForm");
        if(filterSettings != null){
            filterForm.setKeywords(filterSettings.getKeywords());
        }
        
        if(!filterForm.getKeywords().equals("")){
            //fulltext
            List<MessageEntity> fulltext = getMessageManager().findFullText(filterForm.getKeywords());
            m.addAttribute("messages", fulltext);
        }
        return "admin/message/show";
    }
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.POST})
    public String showPOST(
            @ModelAttribute(value="fulltextFilterForm") MessageFulltextFilterForm filterForm,
            BindingResult err,
            HttpSession session
            ) throws Exception { 
        session.setAttribute("messageFulltextFilterForm", filterForm);
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"detail.htm"},method= RequestMethod.GET)
    public String detail(
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception
    {
        try{
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Je nutné zadat id zprávy.");
            return "redirect:show.htm";
        }
        MessageEntity msg = getMessageManager().findById(id);
        if(isParameterNull(msg)){
            getInfoMessages().setWarnMessage("Tuto zprávu nelze nalézt.");
            return "redirect:inbox.htm";
        }
        m.addAttribute("message", msg);
        }catch(Exception e){
               getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
               return "redirect:show.htm";
        }
        return "admin/message/detail";
    }
        
}
