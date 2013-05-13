/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;


import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.HelpEntity;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.form.admin.HelpAddForm;
import cz.komuniti.model.form.admin.HelpEditForm;
import java.sql.Timestamp;
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
@RequestMapping(value={"admin/help"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class HelpControllerAdmin extends TemplateController {
   
    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showGET(
            ModelMap m,
            @RequestParam(value="page",required=false)
            Integer page
            ) throws Exception 
    {
        try {
            if(isParameterNull(page))
            {
                page = new Integer(1);
            }
            getPaginator().setMax(getHelpManager().getCount());
            getPaginator().setPage(page);
            m.addAttribute("helps", getHelpManager().findPage(getPaginator().getResult()[0], getPaginator().getResult()[1]));
            return "admin/help/show";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "/index.htm";
        }
    }
    
    @RequestMapping(value={"detail.htm"},method={RequestMethod.GET})
    public String detail(
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception 
    {
       try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            HelpEntity help = getHelpManager().findById(id);
            if(help == null){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            m.addAttribute("help", help);
            return "admin/help/detail";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value="addFormModel")
            HelpAddForm addFormModel,
            ModelMap m
            ) throws Exception 
    {
        try {
            return "admin/help/add";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid HelpAddForm formModel, 
            BindingResult errors,
            ModelMap m,
            Authentication auth
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors())
            {
                if(isParameterNull(auth)){
                    getInfoMessages().setInfoMessage("Uživatel byl odhlášen.");
                    return "redirect:/index.htm";
                }
                
                User u = (User) auth.getPrincipal();
                UserEntity user = getUserManager().findByEmail(u.getUsername());
                
                if(isParameterNull(user)){
                    getInfoMessages().setInfoMessage("Uživatele se nepodařilo najít.");
                    return "redirect:/index.htm";
                }
                
                HelpEntity help = new HelpEntity();
                help.setInsertDate(new Timestamp(System.currentTimeMillis()));
                help.setText(formModel.getText());
                help.setTitle(formModel.getTitle());
                help.setAuthor(user);
                getHelpManager().add(help);
                getInfoMessages().setInfoMessage("Objekt byl úspěšne přidán.");
            } else {
                return "admin/help/add";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.GET})
    public  String editGET(
            @ModelAttribute(value="editFormModel")
            HelpEditForm formModel,
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception 
    {
    try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            HelpEntity help = getHelpManager().findById(id);
            
            if(isParameterNull(help)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            formModel.setText(help.getText());
            formModel.setTitle(help.getTitle());
            
            getHelpManager().edit(help);
            getInfoMessages().setInfoMessage("Změny byly úspěšně uloženy.");
            
            return "admin/help/edit";
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value={"edit.htm"},method={RequestMethod.POST})
    public String editPOST(
            @ModelAttribute("editFormModel")
            @Valid HelpEditForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
    try {
            if(!errors.hasErrors()){
                HelpEntity help = getHelpManager().findById(formModel.getId());
                if(isParameterNull(help)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít editovaný objekt.");
                    return "redirect:show.htm";
                }
                
                help.setText(formModel.getText());
                help.setTitle(formModel.getTitle());
                
                getHelpManager().edit(help);
                
                getInfoMessages().setInfoMessage("Změny byly úspěšně uloženy.");
                return "redirect:show.htm";
            } else {
                return "admin/help/edit";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";            
        }
    }
    
    @RequestMapping(value={"delete.htm"},method={RequestMethod.GET})
    public String delete(
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            
            HelpEntity help = getHelpManager().findById(id);
            
            if(isParameterNull(help)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }      
            getHelpManager().delete(help);           
            getInfoMessages().setInfoMessage("Objekt "+help.getTitle()+" byl úspěšně smazán.");           
            return "redirect:show.htm";
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }

}
