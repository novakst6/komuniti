/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller.admin;

import cz.komuniti.controller.TemplateController;
import cz.komuniti.model.entity.CenterEntity;
import cz.komuniti.model.entity.RegionEntity;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.entity.UserRoleEntity;
import cz.komuniti.model.form.admin.CenterAddForm;
import cz.komuniti.model.form.admin.CenterAdminForm;
import cz.komuniti.model.form.admin.CenterEditForm;
import cz.komuniti.model.form.admin.UserFulltextFilterForm;
import java.util.List;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value = {"admin/center"})
@Secured(value={"ROLE_ADMIN"})
public class CenterControllerAdmin extends TemplateController {

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showGET(
            ModelMap m,
            @RequestParam(value = "page", required = false) Integer page
            ) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }
        
        getPaginator().setMax(getCenterManager().getCount());
        getPaginator().setPage(page);
        List<CenterEntity> centers = getCenterManager().findPage(getPaginator().getResult()[0], getPaginator().getResult()[1]);
        m.addAttribute("centers", centers);
        return "admin/center/show";
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

            CenterEntity center = getCenterManager().findById(id);

            if (isParameterNull(center)) {
                getInfoMessages().setWarnMessage("Centrum se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            m.addAttribute("center", center);

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }

        return "admin/center/detail";
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value = "addFormModel") CenterAddForm addFormModel,
            ModelMap m) throws Exception {
        m.addAttribute("regions", getRegionFinder().getRegions());
        return "admin/center/add";
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid CenterAddForm addFormModel,
            BindingResult errors,
            ModelMap m) throws Exception {
        try {
            if (!errors.hasErrors()) {
                RegionEntity region = getRegionManager().findById(addFormModel.getRegionId());
                if (isParameterNull(region)) {
                    getInfoMessages().setWarnMessage("Zadaný region se nepodařilo najít.");
                    return "redirect:show.htm";
                }

                CenterEntity center = new CenterEntity();
                center.setName(addFormModel.getName());
                center.setInfo(addFormModel.getInfo());
                center.setRegion(region);
                getCenterManager().add(center);
                getInfoMessages().setInfoMessage("Centrum bylo uloženo.");

            } else {
                m.addAttribute("regions", getRegionFinder().getRegions());
                return "admin/center/add";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.GET})
    public String editGET(
            @ModelAttribute(value = "editFormModel") CenterEditForm editFormModel,
            @RequestParam(value = "id") Long id,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněný.");
            }

            CenterEntity center = getCenterManager().findById(id);

            if (isParameterNull(center)) {
                getInfoMessages().setWarnMessage("Centrum se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            editFormModel.setId(id);
            editFormModel.setName(center.getName());
            editFormModel.setInfo(center.getInfo());
            editFormModel.setRegionId(center.getRegion().getId());

            m.addAttribute("regions", getRegionFinder().getRegions());
            return "admin/center/edit";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }

    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value = "editFormModel")
            @Valid CenterEditForm editFormModel,
            BindingResult errors,
            ModelMap m) throws Exception {
        try {
            if (!errors.hasErrors()) {
                CenterEntity center = getCenterManager().findById(editFormModel.getId());
                if (isParameterNull(center)) {
                    getInfoMessages().setWarnMessage("Centrum se zadaným id se nepodařilo najít.");
                    return "redirect:show.htm";
                }

                RegionEntity region = getRegionManager().findById(editFormModel.getRegionId());
                if (isParameterNull(region)) {
                    getInfoMessages().setWarnMessage("Zadaný region se nepodařilo nalézt.");
                    return "redirect:show.htm";
                }

                center.setName(editFormModel.getName());
                center.setInfo(editFormModel.getInfo());
                center.setRegion(region);

                getCenterManager().edit(center);
                getInfoMessages().setInfoMessage("Záznamy byly uloženy.");
            } else {
                m.addAttribute("regions", getRegionFinder().getRegions());
                return "admin/center/edit";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"delete.htm"}, method = {RequestMethod.GET})
    public String delete(
            @RequestParam(value = "id", required = true) Long id) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }

            CenterEntity center = getCenterManager().findById(id);

            if (isParameterNull(center)) {
                getInfoMessages().setWarnMessage("Centrum se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            if (center.isMembersEmpty()) {
                getCenterManager().delete(center);
                getInfoMessages().setInfoMessage("Centrum " + center.getName() + " bylo smazáno.");
            } else {
                getInfoMessages().setWarnMessage("Centrum nemohlo být smazáno, protože jsou na něm závislé jiné objekty.");
            }

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"admin.htm"}, method = {RequestMethod.GET})
    public String adminGET(
            @RequestParam(value = "id", required = true) Long id,
            @ModelAttribute(value = "adminFormModel") CenterAdminForm formModel,
            ModelMap m,
            @ModelAttribute(value="centerAdminFulltextFilterForm") UserFulltextFilterForm fulltextForm,
            HttpSession session) throws Exception {
        try {
            
        UserFulltextFilterForm fulltextSettings = (UserFulltextFilterForm) session.getAttribute("centerUserFulltextFilter");
        if(fulltextSettings != null){
            fulltextForm.setKeywords(fulltextSettings.getKeywords());
        }
            
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }

            CenterEntity center = getCenterManager().findById(id);

            if (isParameterNull(center)) {
                getInfoMessages().setWarnMessage("Centrum se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            formModel.setCenterId(id);
            if(fulltextForm.getKeywords().equals("")){
                m.addAttribute("users", getUserManager().findUsersInCenter(id, Boolean.FALSE, Boolean.TRUE));
            } else {
                m.addAttribute("users",getUserManager().findFullText(Boolean.FALSE, fulltextForm.getKeywords()));
            }
           

            return "admin/center/admin";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }

    }
    
    
    @RequestMapping(value = {"fulltext.htm"}, method = RequestMethod.POST)
    public String showPOST(
            @ModelAttribute(value="centerAdminFulltextFilterForm") UserFulltextFilterForm fulltextFilterForm,
            BindingResult err,
            HttpSession session,
            @RequestParam(value="id") Long id
            ) throws Exception {
        
        session.setAttribute("centerUserFulltextFilter", fulltextFilterForm);
        
        return "redirect:admin.htm?id="+id;
    }

    @RequestMapping(value = {"admin.htm"}, method = {RequestMethod.POST})
    public String adminPOST(
            @ModelAttribute(value = "adminFormModel")
            @Valid CenterAdminForm formModel,
            BindingResult errors,
            ModelMap m) throws Exception {
        try {
            if (!errors.hasErrors()) {
                CenterEntity center = getCenterManager().findById(formModel.getCenterId());
                if (isParameterNull(center)) {
                    getInfoMessages().setWarnMessage("Editovaný objekt se nepodařilo nalézt.");
                    return "redirect:show.htm";
                }

                UserEntity user = getUserManager().findById(formModel.getAdminId());
                if (isParameterNull(user)) {
                    getInfoMessages().setWarnMessage("Uživatele se nepodařilo nalézt.");
                    return "redirect:show.htm";
                }
                List<UserRoleEntity> roles = getUserRoleManager().findAll();
                for (UserRoleEntity r : roles) {
                    if (r.getName().equals("ROLE_ADMIN_CENTRUM")) {
                        user.getRoles().add(r);
                    }
                }

                getUserManager().edit(user);

                center.setAdmin(user);
                getCenterManager().edit(center);

                getInfoMessages().setInfoMessage("Změny byly úspěšně uloženy. Admin centra je " + user.getEmail());

            } else {
                m.addAttribute("users", getUserManager().findUsersInCenter(formModel.getCenterId(), Boolean.FALSE, Boolean.TRUE));
                return "admin/center/admin";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }
}
