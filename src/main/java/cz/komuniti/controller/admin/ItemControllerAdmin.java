/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.FileEntity;
import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.entity.TagEntity;
import cz.komuniti.model.form.admin.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value = {"admin/item"})
@Secured(value={"ROLE_ADMIN","ROLE_ADMIN_CONTENT"})
public class ItemControllerAdmin extends TemplateController {

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showGET(
            @RequestParam(value = "page", required = false) Integer page,
            ModelMap m,
            @ModelAttribute(value = "fullTextFilterForm") ItemFulltextFilterForm fulltextForm,
            HttpSession session) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }

        ItemFulltextFilterForm fulltextSettings = (ItemFulltextFilterForm) session.getAttribute("itemAdminFulltextSettings");
        if (fulltextSettings != null) {
            fulltextForm.setKeywords(fulltextSettings.getKeywords());
        }

        try {

            if (fulltextForm.getKeywords().equals("")) {
                getPaginator().setMax(getItemManager().getCountDeleted(Boolean.FALSE));
                getPaginator().setPage(page);
                m.addAttribute("items", getItemManager().findPageDeleted(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.FALSE));
            } else {
                List<ItemEntity> findFullText = getItemManager().findFullText(Boolean.FALSE, Boolean.TRUE, fulltextForm.getKeywords());
                getPaginator().setMax(0);
                getPaginator().setPage(page);
                m.addAttribute("items", findFullText);
            }

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:index.htm";
        }
        return "admin/item/show";
    }

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.POST})
    public String showPOST(
            @ModelAttribute(value = "fullTextFilterForm") ItemFulltextFilterForm fulltextForm,
            HttpSession session) throws Exception {
        session.setAttribute("itemAdminFulltextSettings", fulltextForm);
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"add.htm"}, method = RequestMethod.GET)
    public String addGET(
            @ModelAttribute(value = "addFormModel") ItemAddForm formModel,
            ModelMap m) throws Exception {
        m.addAttribute("tags", getTagFinder().getTags());
        return "admin/item/add";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String excep() {
        return "admin/item/add";
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid ItemAddForm formModel,
            BindingResult errors,
            ModelMap m,
            HttpServletRequest request) throws Exception {
        try {
            System.out.println("ITEM ADD");
            if (!errors.hasErrors()) {
                ItemEntity item = new ItemEntity();
                item.setName(formModel.getName());
                item.setDescription(formModel.getDescription());
                item.setText(formModel.getText());
                item.setActive(formModel.getActive());
                item.setDeleted(Boolean.FALSE);
                item.setCreatedByUser(Boolean.FALSE);
                //tags
                List<TagEntity> tags = getTagManager().findByListId(formModel.getTags());
                Set<TagEntity> tag = new HashSet<TagEntity>();
                if (tags != null) {
                    if (!tags.isEmpty()) {
                        for (TagEntity t : tags) {
                            tag.add(t);
                        }
                    } else {
                        getInfoMessages().setWarnMessage("Nepodařilo se přiřadit štítky k objektu.");
                        return "redirect:show.htm";
                    }
                } else {
                    getInfoMessages().setWarnMessage("Nepodařilo se přiřadit štítky k objektu.");
                    return "redirect:show.htm";
                }
                item.setTags(tag);
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

                item.setFiles(files);

                getItemManager().add(item);
                getInfoMessages().setInfoMessage("Věc byla úspěšně uložena.");
                return "redirect:show.htm";
            } else {
                m.addAttribute("tags", getTagFinder().getTags());
                return "admin/item/add";
            }

        } catch (Exception e) {
            e.printStackTrace();
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.GET})
    public String detail(
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m) throws Exception {
        try {
            if (id == null) {
                getInfoMessages().setWarnMessage("Parametr id je nutné vyplnit.");
                return "redirect:show.htm";
            }
            ItemEntity item = getItemManager().findById(id);

            if (item == null) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            m.addAttribute("item", item);
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
        return "admin/item/detail";
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.GET})
    public String editGET(
            @RequestParam(value = "id", required = true) Long id,
            @ModelAttribute(value = "editFormModel") ItemEditForm formModel,
            ModelMap m) throws Exception {
        try {
            if (id == null) {
                getInfoMessages().setWarnMessage("Parametr id je nutné vyplnit.");
                return "redirect:show.htm";
            }

            ItemEntity item = getItemManager().findById(id);

            if (item == null) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            formModel.setId(id);
            formModel.setName(item.getName());
            formModel.setDescription(item.getDescription());
            formModel.setText(item.getText());
            formModel.setActive(item.getActive());

            List<Long> tags = new LinkedList<Long>();
            if (item.getTags() != null) {
                for (TagEntity t : item.getTags()) {
                    tags.add(t.getId());
                }
            }
            formModel.setTags(tags);
            formModel.setFilesOrig(item.getFiles());

            m.addAttribute("tags", getTagFinder().getTags());
            return "admin/item/edit";

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value = "editFormModel") ItemEditForm formModel,
            BindingResult errors,
            ModelMap m) throws Exception {
        try {
            if (!errors.hasErrors()) {
                ItemEntity item = getItemManager().findById(formModel.getId());
                if (item == null) {
                    getInfoMessages().setWarnMessage("Editovaný objekt se nepodařilo nalézt.");
                    return "redirect:show.htm";
                }

                item.setName(formModel.getName());
                item.setDescription(formModel.getDescription());
                item.setText(formModel.getText());
                item.setActive(formModel.getActive());

                //tags
                List<TagEntity> tags = getTagManager().findByListId(formModel.getTags());
                Set<TagEntity> tag = new HashSet<TagEntity>();
                if (tags != null) {
                    if (!tags.isEmpty()) {
                        for (TagEntity t : tags) {
                            tag.add(t);
                        }
                    } else {
                        getInfoMessages().setWarnMessage("Nepodařilo se přiřadit štítky k objektu.");
                        return "redirect:show.htm";
                    }
                } else {
                    getInfoMessages().setWarnMessage("Nepodařilo se přiřadit štítky k objektu.");
                    return "redirect:show.htm";
                }
                item.setTags(tag);

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

                item.getFiles().addAll(files);
                //delete files
                Set<FileEntity> filesItem = item.getFiles();
                List<Long> filesToDelete = formModel.getFilesToDelete();
                System.out.println("FILES to delete " + filesToDelete);
                if (filesToDelete != null) {
                    if (!filesToDelete.isEmpty()) {
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

                item.setFiles(filesItem);

                getItemManager().edit(item);

                getFileManager().delete(filesToDelete);

                getInfoMessages().setInfoMessage("Objekt byl úspěšně aktualizován.");
                return "redirect:show.htm";
            } else {
                m.addAttribute("tags", getTagFinder().getTags());
                return "admin/item/edit";
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
            if (id == null) {
                getInfoMessages().setWarnMessage("Parametr id je nutné vyplnit.");
                return "redirect:show.htm";
            }

            ItemEntity item = getItemManager().findById(id);

            if (item == null) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            item.setDeleted(Boolean.TRUE);

            getItemManager().edit(item);

            getInfoMessages().setInfoMessage("Objekt byl smazán.");
            return "redirect:show.htm";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"union.htm"}, method = {RequestMethod.GET})
    public String unionGET(
            @ModelAttribute(value = "unionForm") ItemUnionForm formModel,
            HttpSession session,
            ModelMap m,
            @RequestParam(value = "page", required = false) Integer page) throws Exception {
        try {
            if (isParameterNull(page)) {
                page = new Integer(1);
            }

            ItemUnionHolder unionSet = (ItemUnionHolder) session.getAttribute("unionSet");

            if (isParameterNull(unionSet)) {
                unionSet = new ItemUnionHolder();
                session.setAttribute("unionSet", unionSet);
            }
            
            if(unionSet != null){
                if(unionSet.getItems() != null){
                    if(!unionSet.getItems().isEmpty()){
                        for(ItemEntity i: unionSet.getItems().values()){
                            getItemManager().refresh(i);
                        }
                    }
                }
            }
            
            getPaginator().setMax(getItemManager().getCountActivate(Boolean.TRUE));
            getPaginator().setPage(page);
            unionSet.setPage(page);
            m.addAttribute("items", getItemManager().findPageActivate(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.TRUE));
            m.addAttribute("selected", unionSet.getItems().values());

            return "admin/item/union";

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"union.htm"}, method = {RequestMethod.POST})
    public String unionPOST(
            @ModelAttribute(value = "unionForm")
            @Valid ItemUnionForm formModel,
            BindingResult errors,
            ModelMap m,
            HttpSession session) throws Exception {
        try {

            ItemUnionHolder unionSet = (ItemUnionHolder) session.getAttribute("unionSet");

            if (isParameterNull(unionSet)) {
                getInfoMessages().setWarnMessage("Vypršel časový limit.");
                return "redirect:show.htm";
            }
            
            if(unionSet.getItems() != null){
                if(!unionSet.getItems().isEmpty()){
                    for(ItemEntity i: unionSet.getItems().values()){
                        getItemManager().refresh(i);
                    }
                }
            }

            if (!errors.hasErrors()) {
                ItemEntity primary = getItemManager().findById(formModel.getPrimaryId());

                if (isParameterNull(primary)) {
                    getInfoMessages().setWarnMessage("Nepodařilo se najít primární objekt.");
                    return "redirect:show.htm";
                }
                
                unionSet.getItems().remove(primary.getId());
                Collection<ItemEntity> values = unionSet.getItems().values();

                Set<OfferEntity> offers = new HashSet<OfferEntity>();
                for (ItemEntity i : values) {
                    offers.addAll(i.getOffers());
                }

                for (OfferEntity o : offers) {
                    o.setItem(primary);
                    getOfferManager().edit(o);
                }

                for (ItemEntity i : values) {
                    boolean delete = getItemManager().delete(i);
                    if (!delete) {
                        getInfoMessages().setErrorMessage("NELZE SMAZAT " + i.getName());
                    }
                }
                session.setAttribute("unionSet", null);
                getInfoMessages().setInfoMessage("Sloučení proběhlo v pořádku.");
                return "redirect:show.htm";
            } else {


                getPaginator().setMax(getItemManager().getCountActivate(Boolean.TRUE));
                getPaginator().setPage(unionSet.getPage());
                m.addAttribute("items", getItemManager().findPageActivate(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.TRUE));
                m.addAttribute("selected", unionSet.getItems().values());

                return "admin/item/union";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            e.printStackTrace();
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"unionAdd.htm"}, method = {RequestMethod.GET})
    public String unionAddGET(
            @RequestParam(value = "id", required = true) Long id,
            HttpSession session,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:union.htm";
            }

            ItemEntity item = getItemManager().findById(id);

            if (isParameterNull(item)) {
                getInfoMessages().setWarnMessage("Vybranou věc nelze najít.");
                return "redirect:union.htm";
            }

            ItemUnionHolder unionSet = (ItemUnionHolder) session.getAttribute("unionSet");
            if (isParameterNull(unionSet)) {
                getInfoMessages().setWarnMessage("Vzpr3el 4asov7 limit.");
                return "redirect:union.htm";
            }
            if (!unionSet.getItems().containsKey(item.getId())) {
                unionSet.getItems().put(item.getId(), item);
            } else {
                getInfoMessages().setWarnMessage("Tato věc je již vybrána.");
            }

            session.setAttribute("unionSet", unionSet);

            return "redirect:union.htm?page=" + unionSet.getPage();
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"itemDelete.htm"}, method = {RequestMethod.GET})
    public String itemdeleteGET(
            @RequestParam(value = "id", required = true) Long id,
            HttpSession session,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:union.htm";
            }

            ItemEntity item = getItemManager().findById(id);

            if (isParameterNull(item)) {
                getInfoMessages().setWarnMessage("Vybranou věc nelze najít.");
                return "redirect:union.htm";
            }

            ItemUnionHolder unionSet = (ItemUnionHolder) session.getAttribute("unionSet");
            if (isParameterNull(unionSet)) {
                getInfoMessages().setWarnMessage("Vzpršel časový limit.");
                return "redirect:union.htm";
            }

            unionSet.getItems().remove(id);

            session.setAttribute("unionSet", unionSet);

            return "redirect:union.htm?page=" + unionSet.getPage();

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"itemsFilter.htm"})
    public String filterItems(@RequestParam(value="keyword") String keyword, ModelMap m){
        System.out.println("K "+keyword);
        if(keyword.equals("")){
            return "admin/item/itemUnionFilter";
        }
        List<ItemEntity> result = getItemManager().findFullText(Boolean.FALSE, Boolean.TRUE, keyword);
        System.out.println("RESULT "+result);
        m.addAttribute("items", result);
        return "admin/item/itemUnionFilter";
    }
}
