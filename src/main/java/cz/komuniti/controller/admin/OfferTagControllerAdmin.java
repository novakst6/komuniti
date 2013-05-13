/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.OfferTagEntity;
import cz.komuniti.model.form.admin.OfferTagAddForm;
import cz.komuniti.model.form.admin.OfferTagEditForm;
import javax.validation.Valid;
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
@RequestMapping(value={"admin/offerTag"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class OfferTagControllerAdmin extends TemplateController {

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showGET(
            ModelMap m,
            @RequestParam(value = "page", required = false) 
            Integer page
            ) throws Exception 
    {
        try {
            if (page == null) {
                page = new Integer(1);
            }
            getPaginator().setMax(getOfferTagManager().getCount());
            getPaginator().setPage(page);
            m.addAttribute("tags", getOfferTagManager().findPage(getPaginator().getResult()[0], getPaginator().getResult()[1]));
            return "admin/offerTag/show";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "/index.htm";
        }
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.GET})
    public String addGET(
                @ModelAttribute(value="addFormModel")
                OfferTagAddForm formModel,
                ModelMap m
            ) throws Exception 
    {
        return "admin/offerTag/add";
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.POST})
       public String addPOST(
                @ModelAttribute(value="addFormModel")
                @Valid OfferTagAddForm formModel,
                BindingResult errors,
                ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors())
            {
                OfferTagEntity offerTag = new OfferTagEntity();
                offerTag.setName(formModel.getName());
                getOfferTagManager().add(offerTag);
                getInfoMessages().setInfoMessage("Objekt byl úspěšně přidán.");
                return "redirect:show.htm";
            } else {
                return "admin/offerTag/add";
            }   
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    @RequestMapping(value={"edit.htm"},method={RequestMethod.GET})
    public String editGET(
            @ModelAttribute(value="editFormModel")
            OfferTagEditForm formModel,
            ModelMap m,
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception 
    {
        try {
            if(id == null)
            {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            
            OfferTagEntity tag = getOfferTagManager().findById(id);
            
            if(tag == null)
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            formModel.setName(tag.getName());
            
            return "admin/offerTag/edit";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";            
        }
        
    }
    
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value="editFormModel")
            @Valid OfferTagEditForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors())
            {
                OfferTagEntity tag = getOfferTagManager().findById(formModel.getId());
                if(tag == null)
                {
                    getInfoMessages().setWarnMessage("Editovaný objekt se nepodařilo nalézt.");
                    return "redirect:show.htm";
                }
                
                tag.setName(formModel.getName());
                
                getOfferTagManager().edit(tag);
                
                getInfoMessages().setInfoMessage("Objekt byl úspěšně aktualizován.");
                
                return "redirect:show.htm";
            } else {
                return "admin/offerTag/edit";
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
            if(id == null)
            {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            
            OfferTagEntity tag = getOfferTagManager().findById(id);
            
            if(tag == null)
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            if(tag.isOffesEmpty()){
                getOfferTagManager().delete(tag);
                getInfoMessages().setInfoMessage("Objekt byl úspěšně smazán.");
            }else {
                getInfoMessages().setWarnMessage("Objekt nejde smazat. Protože jsou na něm závislé nabídky.");
            }
            
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }

}
