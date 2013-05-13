/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;


import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.FileEntity;
import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.form.admin.FileAddForm;
import cz.komuniti.model.form.admin.FileEditForm;
import cz.komuniti.model.form.admin.FileFulltextFilterForm;
import cz.komuniti.service.info.InfoMessages;
import cz.komuniti.service.manager.FileManager;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value={"admin/file"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class FileControllerAdmin extends TemplateController {
    

    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showGET(
            @ModelAttribute(value="fileTextFilterForm") FileFulltextFilterForm fulltextForm,
            HttpSession session,
            ModelMap m,
            @RequestParam(value="page",required=false)
            Integer page
            ) throws Exception 
    {
        if(isParameterNull(page)){page = new Integer(1);}
         FileFulltextFilterForm fulltextSettings = (FileFulltextFilterForm) session.getAttribute("fileAdminFulltextSettings");
            if (fulltextSettings != null) {
            fulltextForm.setKeywords(fulltextSettings.getKeywords());
            }
        try {
            if(fulltextForm.getKeywords().equals("")){
                getPaginator().setMax(getFileManager().getCount());
                getPaginator().setPage(page);
                m.addAttribute("files", getFileManager().findPage(getPaginator().getResult()[0], getPaginator().getResult()[1]));
            } else {
                getPaginator().setMax(0);
                getPaginator().setPage(page);
                List<FileEntity> result = getFileManager().findFullText(Boolean.FALSE, fulltextForm.getKeywords());
                m.addAttribute("files", result);
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:/admin/index.htm";
        }
        return "admin/file/show";
    }
    
    @RequestMapping(value={"show.htm"},method={RequestMethod.POST})
    public String showPOST(
            @ModelAttribute(value="fileTextFilterForm") FileFulltextFilterForm fulltextForm,
            HttpSession session,
            ModelMap m
            ) throws Exception 
    {
        session.setAttribute("fileAdminFulltextSettings", fulltextForm);
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value="addFormModel")
            FileAddForm formModel,
            ModelMap m
            ) throws Exception 
    {
        return "admin/file/add";
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid FileAddForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors())
            {
                FileEntity file = new FileEntity();           
                file.setName(formModel.getFile().getOriginalFilename());
                file.setContentType(formModel.getFile().getContentType());
                file.setFileSize(formModel.getFile().getSize());
                file.setStream(formModel.getFile().getBytes());
                file.setDescription(formModel.getDescription());
                getFileManager().add(file);
                
                getInfoMessages().setInfoMessage("Soubor "+file.getName()+" byl úspěšně uložen.");
                return "redirect:show.htm";
            } else {
                return "admin/file/add";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"edit.htm"},method= RequestMethod.GET)
    public String editGET(
            @RequestParam(value="id",required=true)
            Long id,
            @ModelAttribute(value="editFormModel")
            FileEditForm formModel,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být zadaný.");
                return "redirect:show.htm";
            }
            
            FileEntity file = getFileManager().findById(id);
            
            if(isParameterNull(file))
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            formModel.setId(id);
            formModel.setDescription(file.getDescription());
            String content = file.getContentType();
                if(content.contains("image"))
                {
                    formModel.setImage(Boolean.TRUE);
                } else {
                    formModel.setImage(Boolean.FALSE);
                }
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
        }
        return "admin/file/edit";
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value="editFormModel")
            @Valid FileEditForm formModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors()){
                
                FileEntity file = getFileManager().findById(formModel.getId());
                
                if(isParameterNull(file))
                {
                    getInfoMessages().setWarnMessage("Editovaný objekt se nepodařilo najít.");
                    return "redirect:show.htm";
                }
                if(!formModel.getKeepFile()){
                file.setName(formModel.getFile().getOriginalFilename());
                file.setContentType(formModel.getFile().getContentType());
                file.setFileSize(formModel.getFile().getSize());
                file.setStream(formModel.getFile().getBytes());
                }
                file.setDescription(formModel.getDescription());

                getFileManager().edit(file);
                
                getInfoMessages().setInfoMessage("Záznamy byly úspěšně uloženy.");
                
                return "redirect:show.htm";
            } else {
                FileEntity file = getFileManager().findById(formModel.getId());
                
                if(isParameterNull(file))
                {
                    getInfoMessages().setWarnMessage("Editovaný objekt se nepodařilo najít.");
                    return "redirect:show.htm";
                }
                
                return "admin/file/edit";
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
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            
            FileEntity file = getFileManager().findById(id);
            
            if(isParameterNull(file))
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo nelézt.");
                return "redirect:show.htm";
            }
            OfferEntity offer = getOfferManager().findByFileId(Boolean.FALSE, id);
            ItemEntity item = getItemManager().findbyFileId(Boolean.FALSE, id);
            
            if(offer != null){
                offer.getFiles().remove(file);
                getOfferManager().edit(offer);
            }
            
            if(item != null){
                item.getFiles().remove(file);
                getItemManager().edit(item);
            }
            
            getFileManager().delete(file);
            
            getInfoMessages().setInfoMessage("Soubor byl úspěšně smazán.");
            
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
        return "redirect:show.htm";
    }
 
}
