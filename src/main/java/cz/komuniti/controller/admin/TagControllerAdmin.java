/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.TagEntity;
import cz.komuniti.model.form.admin.TagAddForm;
import cz.komuniti.model.form.admin.TagEditForm;
import java.util.*;
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
@RequestMapping(value={"admin/tag"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class TagControllerAdmin extends TemplateController {
    
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showGET(
            ModelMap m
            ) throws Exception 
    {
        m.addAttribute("tags", getTagFinder().getTags());
        return "admin/tag/show";
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value="addFormModel")
            TagAddForm addFormModel,
            @RequestParam(value="id",required=false)
            Long id
            ) throws Exception 
    {
        if(id == null){
            return "admin/tag/add";
        } else {
            try {
                TagEntity tag = getTagManager().findById(id);
                if(isParameterNull(tag))
                {
                    getInfoMessages().setWarnMessage("Štítek se zadaným id se nepodařilo najít.");
                    return "redirect:show.htm";
                }
                addFormModel.setParentId(id);
                addFormModel.setParentName(tag.getName());
                return "admin/tag/add";   
            } catch (Exception e) {
                getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
                return "redirect:show.htm";
            }
        }
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid TagAddForm addFormModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try{
            if(!errors.hasErrors()){
            TagEntity parent = null;
            if(!isParameterNull(addFormModel.getParentId())){
                parent = getTagManager().findById(addFormModel.getParentId());
            }
            TagEntity tag = new TagEntity();
            tag.setName(addFormModel.getName());
            tag.setParent(parent);
            getTagManager().add(tag);
            return "redirect:show.htm";
            } else {
                TagEntity tag = getTagManager().findById(addFormModel.getParentId()); 
                if(tag != null){
                addFormModel.setParentId(addFormModel.getParentId());
                addFormModel.setParentName(tag.getName());
                }
            return "admin/tag/add";
            }
        } catch (Exception e){
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.GET})
    public String editGET(
            @RequestParam(value="id",required=true)
            Long id,
            @ModelAttribute(value="editFormModel")
            TagEditForm formModel,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            
            TagEntity tag = getTagManager().findById(id);
            
            if(tag == null)
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            formModel.setId(tag.getId());
            formModel.setName(tag.getName());
            
            if(tag.getParent() != null)
            {
                formModel.setParentId(tag.getParent().getId());
            }
            
            m.addAttribute("tags", getTagFinder().getTags());
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm"; 
        }
        
        return "admin/tag/edit";
    }
    
    @RequestMapping(value={"edit.htm"},method= RequestMethod.POST)
    public String editPOST(
            @ModelAttribute(value="editFormModel")
            @Valid TagEditForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try {
        if(!errors.hasErrors())
        {
            if(formModel.getId() == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít id editovaného štítku.");
                return "redirect:show.htm";
            }
            if(formModel.getParentId() == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít id nadřazeného štítku.");
                return "redirect:show.htm";
            }
            
            TagEntity tag = getTagManager().findById(formModel.getId());
            
            if(isParameterNull(tag))
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít editovaný štítek.");
                return "redirect:show.htm";
            }
            
            TagEntity parent = getTagManager().findById(formModel.getParentId());
            if(isParameterNull(parent))
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít nadřazený štítek.");
                return "redirect:show.htm";
            }
            
            tag.setName(formModel.getName());
            
            LinkedList<TagEntity> subTags = getTagFinder().getSubTags(tag);
            if(subTags != null){
            for(TagEntity t: subTags){
                if(t.getId().longValue() == parent.getId().longValue())
                {
                    getTagManager().edit(tag);
                    getInfoMessages().setWarnMessage("Nelze přesunout podstrom štítků do vlasního podstromu.");
                    return "redirect:show.htm";
                }
            }
            }
            
            if(parent.getId().longValue() == tag.getId().longValue())
            {
                tag.setParent(null);
            } else {
                tag.setParent(parent);
            }
            
            getInfoMessages().setInfoMessage("Štítek byl aktualizován.");
            getTagManager().edit(tag);
            
        } else {
            m.addAttribute("tags", getTagFinder().getTags());
            return "admin/tag/edit";
        }      
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"delete.htm"},method= RequestMethod.GET)
    public String delete(
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception 
    {
        try {
            if(isParameterNull(id)){
                getInfoMessages().setWarnMessage("Parametr id je nutno vyplnit.");
                return "redirect:show.htm";
            }
            
            TagEntity tag = getTagManager().findById(id);
            
            if(tag == null){
                getInfoMessages().setWarnMessage("Region se nepodařilo nalézt.");
                return "redirect:show.htm";
            }
            
            if(tag.isChildernsEmpty())
            {
                getTagManager().delete(tag);
                getInfoMessages().setInfoMessage("Štítek "+tag.getName()+" byl smazán.");
            } else {
                getInfoMessages().setWarnMessage("Štítek "+tag.getName()+" nelze smazat, protože jsou na něm vázané další objekty.");
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }

}
