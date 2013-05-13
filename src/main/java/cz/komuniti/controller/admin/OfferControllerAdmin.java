/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.*;
import cz.komuniti.model.form.admin.OfferAddForm;
import cz.komuniti.model.form.admin.OfferEditForm;
import cz.komuniti.model.form.admin.OfferFulltextFilterForm;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value = {"admin/offer"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class OfferControllerAdmin extends TemplateController {

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showGET(
            @ModelAttribute(value="offerTextFilterForm") OfferFulltextFilterForm fulltextForm,
            HttpSession session,
            ModelMap m,
            @RequestParam(value = "page", required = false) Integer page) throws Exception {
        try {
            if (isParameterNull(page)) {
                page = new Integer(1);
            }
            
            OfferFulltextFilterForm fulltextSettings = (OfferFulltextFilterForm) session.getAttribute("offerAdminFulltextSettings");
            if (fulltextSettings != null) {
            fulltextForm.setKeywords(fulltextSettings.getKeywords());
            }
            if (fulltextForm.getKeywords().equals("")){
                getPaginator().setMax(getOfferManager().getCountDeleted(Boolean.FALSE)); //not deleted
                getPaginator().setPage(page);
                m.addAttribute("offers", getOfferManager().findPageDeleted(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.FALSE));
            } else {
                List<OfferEntity> findFullText = getOfferManager().findFullText(Boolean.FALSE, Boolean.TRUE, fulltextForm.getKeywords());
                getPaginator().setMax(0);
                getPaginator().setPage(page);
                m.addAttribute("offers", findFullText);
            }
            return "admin/offer/show";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "/index.htm";
        }

    }

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.POST})
    public String showPOST(
            @ModelAttribute(value="offerTextFilterForm") OfferFulltextFilterForm fulltextForm,
            HttpSession session) throws Exception {
        session.setAttribute("offerAdminFulltextSettings", fulltextForm);
        return "redirect:show.htm";
    }

    @RequestMapping(value={"itemsFilter.htm"})
    public String filterItems(@RequestParam(value="keyword") String keyword, ModelMap m){
        if(keyword.equals("")){
            return "admin/offer/itemsFilter";
        }
        List<ItemEntity> result = getItemManager().findFullText(Boolean.FALSE, Boolean.TRUE, keyword);
        m.addAttribute("items", result);
        return "exchange/itemsFilter";
    }
    
    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value = "addFormModel") OfferAddForm formModel,
            ModelMap m) throws Exception {
        try {
            m.addAttribute("items", getItemManager().findAllActivate(Boolean.TRUE));
            m.addAttribute("tags", getOfferTagManager().findAll());
            m.addAttribute("tagsI", getTagFinder().getTags());
            return "admin/offer/add";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid OfferAddForm formModel,
            BindingResult errors,
            ModelMap m,
            Authentication auth) throws Exception {
        try {
            if (!errors.hasErrors()) {
                List<OfferTagEntity> tags = getOfferTagManager().findByListId(formModel.getTagsId());
                if (tags.isEmpty()) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít kategorie nabídky.");
                    return "redirect:show.htm";
                }
                ItemEntity item = getItemManager().findById(formModel.getItemId());
                if (item == null) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít věc.");
                    return "redirect:show.htm";
                }

                if (auth == null) {
                    getInfoMessages().setErrorMessage("Uživatel byl odhlášen.");
                    return "redirect:/index.htm";
                }
                User usr = (User) auth.getPrincipal();
                String email = usr.getUsername();
                UserEntity user = getUserManager().findByEmail(email);
                if (user == null) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít uživatele.");
                    return "redirect:show.htm";
                }

                Set<FileEntity> files = new HashSet<FileEntity>();
                if (formModel.getFiles() != null) {
                    for (int i = 0; i < formModel.getFiles().length; i++) {
                        CommonsMultipartFile f = formModel.getFiles()[i];
                        if (f == null) {
                            continue;
                        }
                        if (f.isEmpty()) {
                            continue;
                        }
                        FileEntity fil = new FileEntity();
                        fil.setName(f.getOriginalFilename());
                        fil.setContentType(f.getContentType());
                        fil.setFileSize(f.getSize());
                        fil.setStream(f.getBytes());
                        fil.setDescription(formModel.getDescriptionOfFiles()[i]);
                        getFileManager().add(fil);
                        files.add(fil);
                    }

                }

                OfferEntity offer = new OfferEntity();
                for (OfferTagEntity ot : tags) {
                    offer.getTags().add(ot);
                }
                offer.setItem(item);
                offer.setInsertDate(new Timestamp(System.currentTimeMillis()));
                offer.setActive(formModel.getActive());
                offer.setDeleted(Boolean.FALSE);
                offer.setAuthor(user);
                offer.setText(formModel.getText());
                offer.setTitle(formModel.getTitle());
                offer.setFiles(files);
                offer.setEdited(Boolean.FALSE);
                getOfferManager().add(offer);

                getInfoMessages().setInfoMessage("Objekt byl úspěšne přidán.");

            } else {
                m.addAttribute("items", getItemManager().findAllActivate(Boolean.TRUE));
                m.addAttribute("tags", getOfferTagManager().findAll());
                return "admin/offer/add";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.GET})
    public String detail(
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            OfferEntity offer = getOfferManager().findById(id);
            if (isParameterNull(offer)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            m.addAttribute("offer", offer);
            
            Set<FileEntity> filesI = offer.getItem().getFiles();
           Set<FileEntity> imgsI = new HashSet<FileEntity>();
           for(FileEntity f: filesI){
               if(f.getContentType().contains("image")){
                   imgsI.add(f);
               }
           }
           m.addAttribute("imgsI", imgsI);
           
           Set<FileEntity> filesO = offer.getFiles();
           Set<FileEntity> imgsO = new HashSet<FileEntity>();
           for(FileEntity f: filesO){
               if(f.getContentType().contains("image")){
                   imgsO.add(f);
               }
           }
           m.addAttribute("imgsO", imgsO);
            
            return "admin/offer/detail";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.GET})
    public String editGET(
            @ModelAttribute(value = "editFormModel") OfferEditForm formModel,
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            OfferEntity offer = getOfferManager().findById(id);

            if (isParameterNull(offer)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            formModel.setActive(offer.getActive());
            formModel.setId(id);
            formModel.setText(offer.getText());
            formModel.setTitle(offer.getTitle());
            formModel.setItemId(offer.getItem().getId());
            for (OfferTagEntity tag : offer.getTags()) {
                formModel.getTagsId().add(tag.getId());
            }
            formModel.setFilesOrig(offer.getFiles());
            m.addAttribute("offer", offer);
            m.addAttribute("tags", getOfferTagManager().findAll());
            return "admin/offer/edit";

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.POST})
    public String editPOST(
            @ModelAttribute("editFormModel")
            @Valid OfferEditForm formModel,
            BindingResult errors,
            ModelMap m,
            Authentication auth) throws Exception {
        try {
            if (isParameterNull(auth)) {
                getInfoMessages().setWarnMessage("Byl jste odhlášen.");
                return "redirect:show.htm";
            }
            if (!errors.hasErrors()) {
                OfferEntity offer = getOfferManager().findById(formModel.getId());
                if (isParameterNull(offer)) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít editovaný objekt.");
                    return "redirect:show.htm";
                }
                List<OfferTagEntity> tags = getOfferTagManager().findByListId(formModel.getTagsId());
                if (tags.isEmpty()) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít kategorie nabídky.");
                    return "redirect:show.htm";
                }
                ItemEntity item = getItemManager().findById(formModel.getItemId());
                if (isParameterNull(item)) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít věc.");
                    return "redirect:show.htm";
                }

                User u = (User) auth.getPrincipal();
                UserEntity user = getUserManager().findByEmail(u.getUsername());
                if (isParameterNull(user)) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít přihlášeného uživatele.");
                    return "redirect:show.htm";
                }

                Set<OfferTagEntity> tagNew = new HashSet<OfferTagEntity>();
                for (OfferTagEntity ot : tags) {
                    tagNew.add(ot);
                }
                offer.setTags(tagNew);
                offer.setItem(item);
                offer.setActive(formModel.getActive());
                offer.setText(formModel.getText());
                offer.setTitle(formModel.getTitle());
                offer.setEdited(Boolean.TRUE);
                offer.setEditedDate(new Timestamp(System.currentTimeMillis()));
                offer.setEditedBy(user);
                //files
                Set<FileEntity> files = new HashSet<FileEntity>();
                if (formModel.getFiles() != null) {
                    for (int i = 0; i < formModel.getFiles().length; i++) {
                        CommonsMultipartFile f = formModel.getFiles()[i];
                        if (f == null) {
                            continue;
                        }
                        if (f.isEmpty()) {
                            continue;
                        }
                        FileEntity fil = new FileEntity();
                        fil.setName(f.getOriginalFilename());
                        fil.setContentType(f.getContentType());
                        fil.setFileSize(f.getSize());
                        fil.setStream(f.getBytes());
                        fil.setDescription(formModel.getDescriptionOfFiles()[i]);
                        getFileManager().add(fil);
                        files.add(fil);
                    }
                }

                offer.getFiles().addAll(files);

                //delete files
                Set<FileEntity> filesItem = offer.getFiles();
                List<Long> filesToDelete = formModel.getFilesToDelete();
                if (filesToDelete != null) {
                    if(!filesToDelete.isEmpty()){
                    List<FileEntity> filesToBeDelete = getFileManager().findByListId(filesToDelete);
                    for (FileEntity f : filesToBeDelete) {
                        for (FileEntity fi : filesItem) {
                            if (f.getId().longValue() == fi.getId().longValue()) {
                                filesItem.remove(fi);
                                getInfoMessages().setInfoMessage("Soubor " + fi.getName() + " byl smazán.");
                                break;
                            }
                        }
                    }
                    }
                }
                offer.setFiles(filesItem);
                getOfferManager().edit(offer);
                getFileManager().delete(filesToDelete);

                getInfoMessages().setInfoMessage("Změny byly úspěšně uloženy.");
                return "redirect:show.htm";
            } else {
                m.addAttribute("items", getItemManager().findAllActivate(Boolean.TRUE));
                m.addAttribute("tags", getOfferTagManager().findAll());
                return "admin/offer/edit";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"delete.htm"}, method = {RequestMethod.GET})
    public String delete(
            @RequestParam(value = "id", required = true) Long id) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }

            OfferEntity offer = getOfferManager().findById(id);

            if (isParameterNull(offer)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            offer.setDeleted(Boolean.TRUE);

            getOfferManager().edit(offer);

            getInfoMessages().setInfoMessage("Objekt " + offer.getTitle() + " byl úspěšně smazán.");

            return "redirect:show.htm";

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }
}
