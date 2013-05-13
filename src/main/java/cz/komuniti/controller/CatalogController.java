/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.*;
import cz.komuniti.model.form.CatalogFilterForm;
import cz.komuniti.model.form.CatalogFulltextFilterForm;
import cz.komuniti.model.form.CommentAddForm;
import cz.komuniti.service.filter.StateCatalogFilter;
import cz.komuniti.service.filter.StateCatalogFilterCase0;
import cz.komuniti.service.filter.StateCatalogFilterCase1;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = {"catalog"})
@Secured(value={"ROLE_USER"})
public class CatalogController extends TemplateController {

    @Autowired
    private StateCatalogFilterCase0 filterCase0;
    @Autowired
    private StateCatalogFilterCase1 filterCase1;

    private StateCatalogFilter getStateCatalogFilter(int filterCase) {
        switch (filterCase) {
            case 0: {
                return filterCase0;
            }
            case 1: {
                return filterCase1;
            }
            default: {
                return filterCase0;
            }
        }
    }

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showFilterGET(
            @ModelAttribute(value = "filterForm") CatalogFilterForm filterForm,
            @ModelAttribute(value = "fullTextFilterForm") CatalogFulltextFilterForm fulltextForm,
            @RequestParam(value = "page", required = false) Integer page,
            ModelMap m,
            HttpSession session) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }

        m.addAttribute("tags", getTagFinder().getTags());

        CatalogFilterForm filterSettings = (CatalogFilterForm) session.getAttribute("filterSettingsCat");
        if (filterSettings != null) {
            filterForm.setIdsItemTag(filterSettings.getIdsItemTag());
        }

        CatalogFulltextFilterForm filterFulltext = (CatalogFulltextFilterForm) session.getAttribute("fulltextSettingsCat");
        if(filterFulltext != null){
            fulltextForm.setKeywords(filterFulltext.getKeywords());
        }
        
        Long startTime = System.currentTimeMillis();
        if(!fulltextForm.getKeywords().equals(""))
        {
            if(filterForm != null){
                if(filterForm.getIdsItemTag() != null){
                    filterForm.getIdsItemTag().clear();
                }
            }
            List<ItemEntity> result = getItemManager().findFullText(Boolean.FALSE, Boolean.TRUE, fulltextForm.getKeywords());
            getPaginator().setMax(result.size());
            getPaginator().setPage(page);
            List<ItemEntity> subList = result.subList(getPaginator().getResult()[0], getPaginator().getResult()[1]);
            m.addAttribute("items", subList);
            
        } else {
            if (filterSettings != null) {
            List<Long> idsItemTag = filterSettings.getIdsItemTag();
            
            List<TagEntity> tags = new LinkedList<TagEntity>();
            if(idsItemTag != null){
                 tags = getTagManager().findByListId(idsItemTag);
            }
            String filterInfo = "";
            for (TagEntity t : tags) {
                filterInfo += t.getName() + "; ";
            }

            if (!filterInfo.equals("")) {
                getInfoMessages().setInfoMessage("FILTR: " + filterInfo);
            }
            
            }

        StateCatalogFilter filter = getStateCatalogFilter(filterForm.getCase());
        getPaginator().setMax(filter.getCount(filterForm));
        getPaginator().setPage(page);

        m.addAttribute("items", filter.getResult(filterForm, page));
        }
        Long endTime = System.currentTimeMillis();
        getInfoMessages().setErrorMessage("FILTER "+(endTime-startTime)+" ms");
        return "catalog/show";
    }

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.POST})
    public String showFilterPOST(
            @ModelAttribute("filterForm") CatalogFilterForm filterForm,
            HttpSession session
            ) throws Exception {
        CatalogFulltextFilterForm filterFulltext = (CatalogFulltextFilterForm) session.getAttribute("fulltextSettingsCat");
        if(filterFulltext != null){
            filterFulltext.setKeywords("");
            session.setAttribute("fulltextSettingsCat", filterFulltext);
        }
        session.setAttribute("filterSettingsCat", filterForm);
        return "redirect:show.htm";
    }

    @RequestMapping(value={"fulltext.htm"},method={RequestMethod.POST})
    public String showFullTextFilterPOST(
            @ModelAttribute(value="fullTextFilterForm")
            CatalogFulltextFilterForm fulltextForm,
            HttpSession session
            ) throws Exception 
    
    {
        CatalogFilterForm filterSettings = (CatalogFilterForm) session.getAttribute("filterSettingsCat");
        if (filterSettings != null) {
            if(filterSettings.getIdsItemTag() != null){
                filterSettings.getIdsItemTag().clear();
            }
        }
        
        session.setAttribute("fulltextSettingsCat", fulltextForm);
        return "redirect:show.htm";
    }
    
    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.GET})
    public String detailGET(
            @ModelAttribute(value = "addFormModel") CommentAddForm formModel,
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je nutné vyplnit.");
                return "redirect:show.htm";
            }
            ItemEntity item = getItemManager().findById(id);

            if (isParameterNull(item)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            m.addAttribute("activeOffers", getOfferManager().findOffersByItem(Boolean.TRUE, id));
            m.addAttribute("item", item);
            Set<FileEntity> files = item.getFiles();
            Set<FileEntity> imgs = new HashSet<FileEntity>();
            for (FileEntity f : files) {
                if (f.getContentType().contains("image")) {
                    imgs.add(f);
                }
            }
            m.addAttribute("imgs", imgs);
            formModel.setId(id);
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
        return "catalog/detail";
    }

    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.POST})
    public String detailPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid CommentAddForm formModel,
            BindingResult errors,
            ModelMap m,
            Authentication auth) throws Exception {
        try {
            if (!errors.hasErrors()) {
                if (isParameterNull(auth)) {
                    getInfoMessages().setWarnMessage("Uživatel byl odhlášen.");
                    return "redirect:show.htm";
                }
                User u = (User) auth.getPrincipal();
                UserEntity user = getUserManager().findByEmail(u.getUsername());
                if (isParameterNull(user)) {
                    getInfoMessages().setWarnMessage("Uživatele se nepodařilo najít.");
                    return "redirect:show.htm";
                }

                ItemEntity item = getItemManager().findById(formModel.getId());
                if (isParameterNull(item)) {
                    getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                    return "redirect:show.htm";
                }

                CommentEntity comment = new CommentEntity();
                comment.setAuthor(user);
                comment.setBanned(Boolean.FALSE);
                comment.setDateOfInsert(new Timestamp(System.currentTimeMillis()));
                comment.setEdited(Boolean.FALSE);
                comment.setText(formModel.getText());
                boolean addC = getCommentManager().add(comment);
                if (addC) {
                    getInfoMessages().setInfoMessage("Komentář byl úspěšně uložen.");
                } else {
                    getInfoMessages().setWarnMessage("Komentář se nezdařilo uložit.");
                    return "redirect:detail.htm?id=" + formModel.getId();
                }

                item.getComments().add(comment);
                boolean edit = getItemManager().edit(item);
                if (edit) {
                    getInfoMessages().setInfoMessage("Komentář byl úspěšně uložen k věci.");
                } else {
                    getInfoMessages().setWarnMessage("Komentář se nezdařilo uložit k věci.");
                }
                return "redirect:detail.htm?id=" + formModel.getId();
            } else {
                ItemEntity item = getItemManager().findById(formModel.getId());
                if (isParameterNull(item)) {
                    getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                    return "redirect:show.htm";
                }
                m.addAttribute("activeOffers", getOfferManager().findOffersByItem(Boolean.TRUE, formModel.getId()));
                m.addAttribute("item", item);
                return "catalog/detail";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    

    public void setFilterCase0(StateCatalogFilterCase0 filterCase0) {
        this.filterCase0 = filterCase0;
    }

    public void setFilterCase1(StateCatalogFilterCase1 filterCase1) {
        this.filterCase1 = filterCase1;
    }
}
