/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.RegionEntity;
import cz.komuniti.model.form.admin.RegionAddFrom;
import cz.komuniti.model.form.admin.RegionEditForm;
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
@RequestMapping(value={"admin/region"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class RegionControllerAdmin extends TemplateController {
    
   private LinkedList<RegionEntity> getSubRegions(RegionEntity r){
        Set<RegionEntity> list = r.getChilderns();
        if(list.isEmpty()){ return null;}
        Iterator<RegionEntity> i = list.iterator();
        LinkedList<RegionEntity> nodes = new LinkedList<RegionEntity>();
        while(i.hasNext()){
            RegionEntity temp = i.next();
            nodes.add(temp);
            LinkedList<RegionEntity> n1 = getSubRegions(temp);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        return nodes;
    }
    
    private Map<Long,String> getAllRegions()
    {
        Map<Long,String> regionsMap = new HashMap<Long, String>();
        List<RegionEntity> regions = getRegionManager().findAll(); //poradi
        for(RegionEntity r: regions){
            regionsMap.put(r.getId(),r.getName()); 
        }
        return regionsMap;
    }
    
    @RequestMapping(value={"show.htm"},method= RequestMethod.GET)
    public String showGET(
            ModelMap m
            ) throws Exception 
    {
        m.addAttribute("regions", getRegionFinder().getRegions());
        return "admin/region/show";
    }

    @RequestMapping(value={"add.htm"},method= RequestMethod.GET)
    public String addGET(
            @ModelAttribute("addFormModel")
            RegionAddFrom addFormModel,
            @RequestParam(value="id",required=false)
            Long id
            ) throws Exception 
    {
        if(isParameterNull(id))
        {
            return "admin/region/add";
        } else {
            try {
               RegionEntity region = getRegionManager().findById(id);
               if(region == null)
               {
                   getInfoMessages().setWarnMessage("Region se zadaným id se nepodařilo najít.");
                   return "redirect:show.htm";
               }
               addFormModel.setParentId(id);
               addFormModel.setParentName(region.getName());
               return "admin/region/add";
            } catch (Exception e) {
               getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
               return "redirect:show.htm";
            }

        }
        
    }
    
    @RequestMapping(value={"add.htm"},method= RequestMethod.POST)
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid RegionAddFrom addFormModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try{
            if(!errors.hasErrors()){
                RegionEntity parent = null;
                if(!isParameterNull(addFormModel.getParentId())){
                    parent = getRegionManager().findById(addFormModel.getParentId());
                }
                
                RegionEntity region = new RegionEntity();
                region.setName(addFormModel.getName());
                region.setParent(parent);
                getRegionManager().add(region);
                return "redirect:show.htm";
            } else {
               RegionEntity region = getRegionManager().findById(addFormModel.getParentId());
               if(region != null)
               {
                    addFormModel.setParentId(addFormModel.getParentId());
                    addFormModel.setParentName(region.getName());
               }
                return "admin/region/add";
            }
        } catch (Exception e){
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.GET})
    public String editGET(
            @RequestParam(value="id", required=true)
            Long id,
            @ModelAttribute(value="editFormModel")
            RegionEditForm formModel,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            
            RegionEntity region = getRegionManager().findById(id);
            
            if(isParameterNull(region))
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            formModel.setId(region.getId());
            formModel.setName(region.getName());
            //the Node is root
            if(region.getParent() != null)
            {
                formModel.setParentId(region.getParent().getId());
            }
            
            m.addAttribute("regions", getRegionFinder().getRegions());
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
   
        return "admin/region/edit";
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value="editFormModel")
            @Valid RegionEditForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try{
        if(!errors.hasErrors())
        {
            if(formModel.getId() == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít id editovaného region.");
                return "redirect:show.htm";
            }
            if(formModel.getParentId() == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít id nadřazeného region.");
                return "redirect:show.htm";
            }
            
            RegionEntity region = getRegionManager().findById(formModel.getId());
            if(region == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít editovaný region.");
                return "redirect:show.htm";
            }
            
            RegionEntity parent = getRegionManager().findById(formModel.getParentId());
            if(parent == null)
            {
                getInfoMessages().setWarnMessage("Nepodařilo se najít nadřazený region.");
                return "redirect:show.htm";
            }

            region.setName(formModel.getName());

            LinkedList<RegionEntity> subRegions = getSubRegions(region);
            if(subRegions != null){ //pokud potomci, tak kontrola jestli nepresouvam do potomku

            for(RegionEntity r: subRegions){
                if(r.getId().longValue() == parent.getId().longValue())
                {
                    getRegionManager().edit(region);
                    getInfoMessages().setWarnMessage("Nelze přesunout podstrom regionů do vlasního podstromu.");
                    return "redirect:show.htm";
                }
            }
            }

            if(parent.getId().longValue() == region.getId().longValue())
            {
                region.setParent(null);
            } else {
                region.setParent(parent);
            }

            getInfoMessages().setInfoMessage("Region byl aktualizován.");
            getRegionManager().edit(region);
            
        } else {
            m.addAttribute("regions", getRegionFinder().getRegions());
            return "admin/region/edit";
        }
        } catch(Exception e){
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"delete.htm"},method={RequestMethod.GET})
    public String delete(
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception
    {
        try {
            if(id == null){
                getInfoMessages().setWarnMessage("Parametr id je nutno vyplnit.");
                return "redirect:show.htm";
            }
            
            RegionEntity region = getRegionManager().findById(id);
            
            if(region == null){
                getInfoMessages().setWarnMessage("Region se nepodařilo nalézt.");
                return "redirect:show.htm";
            }
            
            if(!region.getUsers().isEmpty()){
                getInfoMessages().setWarnMessage("Region nelze smazat, protože jsou na něj vázané další objekty.");
                return "redirect:show.htm";
            }
            
            if(region.isChildernsEmpty())
            {
                getRegionManager().delete(region);
                getInfoMessages().setInfoMessage("Region "+region.getName()+" byl smazán.");
            } else {
                getInfoMessages().setWarnMessage("Region "+region.getName()+" nelze smazat, protože jsou na něm vázané další objekty.");
            }
            
            
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    
}
