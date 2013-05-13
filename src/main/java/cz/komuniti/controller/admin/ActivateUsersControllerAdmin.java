/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.UserEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value="/admin/activateUsers")
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CENTER"})
public class ActivateUsersControllerAdmin extends TemplateController{
    
    @RequestMapping(value="show.htm",method= RequestMethod.GET)
    public String show(
            @RequestParam(value="page",required=false)
            Integer page,
            ModelMap m,
            Authentication auth
            ) throws Exception
    {        
        if(isParameterNull(page)){ page = new Integer(1);}

        try {
        User u = (User) auth.getPrincipal();
        UserEntity user = getUserManager().findByEmail(u.getUsername());
        Long idCenter = user.getCentrum().getId();
        
        getPaginator().setMax(getUserManager().getCountUnactiveCenter(idCenter, Boolean.FALSE,Boolean.FALSE));
        getPaginator().setPage(page);
        m.addAttribute("users", getUserManager().findPageUnactiveCenter(idCenter,   getPaginator().getResult()[0],   getPaginator().getResult()[1], Boolean.FALSE, Boolean.FALSE));
        
        return "admin/activateUsers/show";
                    
        } catch (Exception e) {
            return "redirect:admin/admin.htm";
        }
    }

    @RequestMapping(value="active.htm",method= RequestMethod.GET)
    public String active(
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception 
    {
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr \"id\" je povinný.");
            return "redirect:show.htm";
        }
        try {
            UserEntity user = getUserManager().findById(id);
            user.setActive(Boolean.TRUE);
            getUserManager().edit(user);
            getInfoMessages().setInfoMessage("Uživatel "+user.getEmail()+" je aktivovaný.");
        } catch (Exception e) {

        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value="deactive.htm",method= RequestMethod.GET)
    public String deactive(
            @RequestParam(value="id",required=true)
            Long id,
            Authentication auth
            ) throws Exception 
    {
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr id je povinný.");
            return "redirect:show.htm";
        }
        
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findById(id);
            if(u.getUsername().equals(user.getEmail())){
                getInfoMessages().setWarnMessage("Nelze deaktivovat svůj vlastní účet.");
                return "redirect:show.htm";
            }
            user.setActive(Boolean.FALSE);
            getUserManager().edit(user);
            getInfoMessages().setInfoMessage("Uživatel "+user.getEmail()+" je deaktivovaný.");
        } catch (Exception e) {
            
        }
        return "redirect:show.htm";
    }    
}
