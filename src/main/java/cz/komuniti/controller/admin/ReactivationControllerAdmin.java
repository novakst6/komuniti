/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.form.admin.ReactivateShowForm;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author novakst6
 */

@Controller
@RequestMapping(value="/admin/reactivate")
@Secured(value={"ROLE_ADMIN"})
public class ReactivationControllerAdmin extends TemplateController {
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showGET(
            @ModelAttribute(value="showFormModel")
            ReactivateShowForm formModel
            ) throws Exception 
    {
        return "admin/reactivation/show";
    }
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.POST})
    public String showPOST(
            @ModelAttribute(value="showFormModel")
            @Valid ReactivateShowForm formModel,
            BindingResult errors
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors()) {
                UserEntity user = getUserManager().findByEmail(formModel.getEmail());
                if(isParameterNull(user)){
                    getInfoMessages().setWarnMessage("Uživatel nenalezen.");
                    return "redirect:show.htm";
                }
                
                if(!user.getDeleted()){
                    getInfoMessages().setWarnMessage("Tento uživatel je aktivní.");
                    return "redirect:show.htm";
                }
                
                user.setDeleted(Boolean.FALSE);
                
                getUserManager().edit(user);
                
                getInfoMessages().setInfoMessage("Uživatel "+user.getEmail()+" byl reaktivován.");
                return "redirect:show.htm";
            } else {
                return "admin/reactivation/show";
            }
        } catch(Exception e){
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
       
}
