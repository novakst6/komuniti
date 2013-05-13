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
import cz.komuniti.model.form.admin.UserAddForm;
import cz.komuniti.model.form.admin.UserEditForm;
import cz.komuniti.model.form.admin.UserFulltextFilterForm;
import cz.komuniti.model.form.admin.UserRightsForm;
import java.util.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
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
 *
 */
@Controller
@RequestMapping(value = "admin/users")
@Secured(value={"ROLE_ADMIN"})
public class UserControllerAdmin extends TemplateController {

    private Map<Long, String> getRolesMap(){
        Map<Long,String> rolesMap = new HashMap<Long, String>();
        List<UserRoleEntity> roles = getUserRoleManager().findAll();
        for(UserRoleEntity r: roles){
            rolesMap.put(r.getId(), r.getName());
        }
        return rolesMap;
    }
    
    private Map<Long,String> getCenters(){
        Map<Long,String> centerMap = new HashMap<Long, String>();
        List<CenterEntity> centers = getCenterManager().findAll();
        for(CenterEntity c: centers){
            centerMap.put(c.getId(), c.getName());
        }
        return centerMap;
    }
    
    @RequestMapping(value = {"show.htm"}, method = RequestMethod.GET)
    public String showGET(
            ModelMap m,
            @ModelAttribute(value="userAdminFulltextFilterForm") UserFulltextFilterForm fulltextFilterForm,
            @RequestParam(value = "page", required = false) Integer page,
            HttpSession session) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }
        try{
         UserFulltextFilterForm fulltextSettings = (UserFulltextFilterForm) session.getAttribute("userFulltextFilterForm");   
            if(fulltextSettings != null){
                fulltextFilterForm.setKeywords(fulltextSettings.getKeywords());
            }
         
        if(fulltextFilterForm.getKeywords().equals("")){
        getPaginator().setMax(getUserManager().getCountDeleted(Boolean.FALSE));
        getPaginator().setPage(page);
        List<UserEntity> users = getUserManager().findPageDeleted(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.FALSE);
        m.addAttribute("users", users);
        } else {
                //fulltext
                List<UserEntity> users = getUserManager().findFullText(Boolean.FALSE, fulltextFilterForm.getKeywords());
                m.addAttribute("users", users);
        }
        return "admin/user/show";
        } catch (Exception e){
            e.printStackTrace();
            getInfoMessages().setErrorMessage("ERROR ADMIN USER SHOW "+e.getMessage());
            return "redirect:/admin/index.htm";
        }
    }

    @RequestMapping(value = {"show.htm"}, method = RequestMethod.POST)
    public String showPOST(
            @ModelAttribute(value="userAdminFulltextFilterForm") UserFulltextFilterForm fulltextFilterForm,
            BindingResult err,
            HttpSession session
            ) throws Exception {
        
        session.setAttribute("userFulltextFilterForm", fulltextFilterForm);
        
        return "redirect:show.htm";
    }
    
    @RequestMapping(value = {"delete.htm"}, method = RequestMethod.GET)
    public String delete(
            @RequestParam(value = "id", required = true) Long id,
            Authentication auth) throws Exception {
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findById(id);
            if (isParameterNull(user)) {
                getInfoMessages().setWarnMessage("Objekt s id=" + id + " nebyl nalezen.");
            } else if (user.getEmail().equals(u.getUsername())) {
                getInfoMessages().setWarnMessage("Nepovedlo se smazat účet " + user.getEmail() + ". Nelze smazat vlastní účet.");
            } else {
                user.setDeleted(Boolean.TRUE);
                getUserManager().edit(user);
                getInfoMessages().setInfoMessage("Uživatel " + user.getEmail() + " byl úspěšně smazán.");
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR ADMIN DELETE: "+e.getMessage());
        }
        return "redirect:show.htm";

    }

    @RequestMapping(value={"add.htm"},method= RequestMethod.GET)
    public String addGET(
            @ModelAttribute(value="addFormModel")
            UserAddForm addFormModel,
            ModelMap m
            ) throws Exception 
    {
        m.addAttribute("regions", getRegionFinder().getRegions());
        m.addAttribute("centerMap",getCenters());
        
        return "admin/user/add";
    }
    
    @RequestMapping(value={"add.htm"},method= RequestMethod.POST)
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid UserAddForm addFormModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception 
    {
        if(!errors.hasErrors()){
            UserEntity user = new UserEntity();
            user.setEmail(addFormModel.getEmail());
            user.setGoogleName(addFormModel.getGoogleName());
            user.setActive(addFormModel.getActive());
            user.setDeleted(Boolean.FALSE);
            List<UserRoleEntity> list = getUserRoleManager().findAll();
            Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();
            for(UserRoleEntity r: list){
                if(r.getName().equals("ROLE_USER")){
                    roles.add(r);
                }
            }
            user.setRoles(roles);
            
            RegionEntity region = getRegionManager().findById(addFormModel.getRegionId());
            if(isParameterNull(region)){
                getInfoMessages().setWarnMessage("Zadaný region se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "admin/user/add";
            }
            user.setRegion(region);
            
            CenterEntity center = getCenterManager().findById(addFormModel.getCenterId());
            if(isParameterNull(center)){
                getInfoMessages().setWarnMessage("Zadané centerum se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "admin/user/add";
            }
            user.setCentrum(center);
            
            String password = getPasswordGenerator().getPassword();
            PasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
            String newPassword = passwordEncoder.encodePassword(
                    password, 
                    user.getEmail()
                    );
            user.setPassword(newPassword);
            getUserManager().add(user);
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(user.getEmail());
                msg.setSubject("Byl Vám vytvořen email na stránkách Komuniti");
                msg.setText("Vaše přihlašovací jméno je: "+user.getEmail()+"\n Heslo: "+password);
                getMailService().send(msg);
                getInfoMessages().setInfoMessage("Uživatel s emailem "+user.getEmail()+" byl úspěšně zaregistrován.\nHeslo bylo odesláno na zadanou emailovou adresu.");
            } catch (MailException e) {
                getInfoMessages().setWarnMessage("Uživatel s emailem "+user.getEmail()+" byl úspěšně zaregistrován.\nNepodařilo se však poslat email s heslem. Nejspíše se nepodařilo spojit se SMTP serverem.");
            }
        } else {
            m.addAttribute("regions", getRegionFinder().getRegions());
            m.addAttribute("centerMap",getCenters());
            return "admin/user/add";
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"rights.htm"},method= RequestMethod.GET)
    public String rightsGET(
            @RequestParam(value="id",required=true)
            Long id,
            @ModelAttribute(value="rightsFormModel")
            UserRightsForm rightsFormModel,
            ModelMap m
            ) throws Exception 
    {
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr \"id\" je povinný.");
            return "redirect:show.htm";
        }
        try {
            UserEntity user = getUserManager().findById(id);
            if(isParameterNull(user)){
                return "redirect:show.htm";
            }
            Set<UserRoleEntity> roles = user.getRoles();
            for(UserRoleEntity r: roles){
                rightsFormModel.getRoles().add(r.getId());
            }
            rightsFormModel.setId(id);
            List<UserRoleEntity> allRoles = getUserRoleManager().findAll();
            UserRoleEntity userRole = null;
            for(UserRoleEntity r: allRoles){
                if(r.getName().equals("ROLE_USER")){
                    userRole = r;
                }
            }
            
            if(userRole != null){
                allRoles.remove(userRole);
            }
            
            m.addAttribute("rolesMap", allRoles);
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("Error. " + e.getMessage());
            return "redirect:show.htm";
        }
        return "admin/user/rights";
    }
    
    @RequestMapping(value={"rights.htm"},method= RequestMethod.POST)
    public String rightsPOST(
            @ModelAttribute(value="rightsFormModel")
            @Valid UserRightsForm rightsFormModel,
            BindingResult errors
            ) throws Exception 
    {
        try {
            UserEntity user = getUserManager().findById(rightsFormModel.getId());
            if(isParameterNull(user)){
                getInfoMessages().setWarnMessage("Uživatel s id="+rightsFormModel.getId()+" nebyl nalezen.");
                return "redirect:show.htm";
            }
            List<Long> rol = rightsFormModel.getRoles();
            List<UserRoleEntity> list = new LinkedList<UserRoleEntity>();
            if(rol != null){
                list = getUserRoleManager().findByListId(rol);
            }
            List<UserRoleEntity> allRoles = getUserRoleManager().findAll();
            UserRoleEntity userRole = null;
            for(UserRoleEntity r: allRoles){
                if(r.getName().equals("ROLE_USER")){
                    userRole = r;
                }
            }
            Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();
            if(userRole != null){
                roles.add(userRole);
            }
            for(UserRoleEntity r: list){
                roles.add(r);
            }
            user.setRoles(roles);
            getUserManager().edit(user);
            getInfoMessages().setInfoMessage("Uživateli "+user.getEmail()+" byla úspěšně změněna práva.");
        } catch (Exception e) {
            getInfoMessages().setErrorMessage(e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"edit.htm"},method= RequestMethod.GET)
    public String editGET(
            @RequestParam(value="id",required=true)
            Long id,
            @ModelAttribute(value="editFormModel")
            UserEditForm editFormModel,
            ModelMap m
            ) throws Exception
    {
        if(isParameterNull(id)) {
            getInfoMessages().setWarnMessage("Chybí parametr \"id\"");
            return "redirect:show.htm";
        }
        try {
            UserEntity user = getUserManager().findById(id);
            if(isParameterNull(user)){
                getInfoMessages().setWarnMessage("Uživatele se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            if(user.getDeleted()){
                getInfoMessages().setWarnMessage("Uživatele se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            editFormModel.setEmail(user.getEmail());
            editFormModel.setGoogleName(user.getGoogleName());
            editFormModel.setActive(user.getActive());
            editFormModel.setId(id);
            editFormModel.setCenterId(user.getCentrum().getId());
            editFormModel.setRegionId(user.getRegion().getId());
            m.addAttribute("regions", getRegionFinder().getRegions());
            m.addAttribute("centerMap",getCenters());
            
            return "admin/user/edit";
        } catch (Exception e) {
            e.printStackTrace();
            getInfoMessages().setErrorMessage(e.getMessage());
            return "redirect:show.htm";
        }
        
        
    }
    
    @RequestMapping(value={"edit.htm"},method= RequestMethod.POST)
    public String editPOST(
            @ModelAttribute(value="editFormModel")
            @Valid UserEditForm editFormModel,
            BindingResult errors,
            ModelMap m
            ) throws Exception
    {
        if(!errors.hasErrors()){
            try {
                Long id = editFormModel.getId();
                UserEntity user = getUserManager().findById(id);
                
                user.setEmail(editFormModel.getEmail());
                user.setGoogleName(editFormModel.getGoogleName());
                user.setActive(editFormModel.getActive());
                
                RegionEntity region = getRegionManager().findById(editFormModel.getRegionId());
            if(isParameterNull(region)){
                getInfoMessages().setWarnMessage("Zadaný region se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "admin/user/edit";
            }
            user.setRegion(region);
            
            CenterEntity center = getCenterManager().findById(editFormModel.getCenterId());
            if(isParameterNull(center)){
                getInfoMessages().setWarnMessage("Zadané centerum se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "admin/user/edit";
            }
            
            user.setCentrum(center);
                
                if(getUserManager().edit(user)){
                    getInfoMessages().setInfoMessage("Uživatel byl úspěšně aktualizován.");
                } else {
                    getInfoMessages().setErrorMessage("Při aktualizaci došlo k chybě.");
                }
                return "redirect:show.htm";
            } catch (Exception e) {
                getInfoMessages().setErrorMessage("Při aktualizaci došlo k chybě.");
                return "redirect:show.htm";
            }
            
        } else {
            m.addAttribute("regions", getRegionFinder().getRegions());
            m.addAttribute("centerMap",getCenters());
            return "admin/user/edit";
        }
        
    }
    
    @RequestMapping(value="active.htm",method= RequestMethod.GET)
    public String active(
            @RequestParam(value="id",required=true)
            Long id
            ) throws Exception 
    {
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr \"id\" je povinný.");
            return "redirect:show.htm";
        }
        try {
            UserEntity user = getUserManager().findById(id);
            user.setActive(Boolean.TRUE);
            getUserManager().edit(user);
            getInfoMessages().setInfoMessage("Uživatel "+user.getEmail()+" je aktivovaný.");
        } catch (Exception e) {
            //
        }
        return "redirect:show.htm";
    }
    
    @RequestMapping(value="deactive.htm",method= RequestMethod.GET)
    public String deactive(
            @RequestParam(value="id",required=true)
            Long id,
            Authentication auth
            ) throws Exception 
    {
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr \"id\" je povinný.");
            return "redirect:show.htm";
        }
        
        try {
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findById(id);
            if(u.getUsername().equals(user.getEmail())){
                getInfoMessages().setWarnMessage("Nelze deaktivovat svůj vlastní účet.");
                return "redirect:show.htm";
            }
            user.setActive(Boolean.FALSE);
            getUserManager().edit(user);
            getInfoMessages().setInfoMessage("Uživatel "+user.getEmail()+" je deaktivovaný.");
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR ADMIN DEACTIVE "+e.getMessage());
        }
        return "redirect:show.htm";
    }
    
    
}
