/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;


import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.AppConfigEntity;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.form.admin.ConfigForm;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value={"admin"})
@Secured(value={"ROLE_ADMIN"})
public class ConfigureControllerAdmin extends TemplateController {
    
    
    @RequestMapping(value={"config.htm"},method={RequestMethod.GET})
    public String configGET(
          @ModelAttribute(value="confFormModel")
          ConfigForm formModel,
          ModelMap m
            ) throws Exception 
    {
        AppConfigEntity conf = getAppConfManager().findById(1L);
        m.addAttribute("conf", conf);
        return "admin/config/show";
    }
    
     @RequestMapping(value={"config.htm"},method={RequestMethod.POST})
    public String configPOST(
            @ModelAttribute(value="confFormModel")
            @Valid ConfigForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors()){
                AppConfigEntity conf = getAppConfManager().findById(1L);
                if(conf == null){
                    conf = new AppConfigEntity();
                    getAppConfManager().add(conf);
                }
                String contentModerator = formModel.getContentModerator();
                UserEntity user = getUserManager().findByEmail(contentModerator);
                if(user == null){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít uživatele.");
                    return "redirect:config.htm";
                }
                
                conf.setContentModerator(user);
                getAppConfManager().edit(conf);
                getInfoMessages().setInfoMessage("Záznam byl uložen.");
                return "redirect:config.htm";
            } else {
                AppConfigEntity conf = getAppConfManager().findById(1L);
                m.addAttribute("conf", conf);
                return "admin/config/show";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:index.htm";
        }
    }  
    
}
