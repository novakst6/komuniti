/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.*;
import cz.komuniti.model.form.GoogleRegistrationForm;
import cz.komuniti.model.form.UserRegistrationForm;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author novakst6
 */
@Controller
public class AccountController extends TemplateController {

    private Map<Long, String> getCenters() {
        Map<Long, String> centerMap = new HashMap<Long, String>();
        List<CenterEntity> centers = getCenterManager().findAll();
        for (CenterEntity c : centers) {
            centerMap.put(c.getId(), c.getName());
        }
        return centerMap;
    }
    
    @RequestMapping(value="login.htm",method= RequestMethod.GET)
    public String login() throws Exception{
        return "account/login";
    }
    
    @RequestMapping(value={"registration.htm"},method= RequestMethod.GET)
    public String register(
            @ModelAttribute(value="userRegistrationForm")
            UserRegistrationForm userRegistrationForm,
            ModelMap m
            )throws Exception
    {
        
        m.addAttribute("regions", getRegionFinder().getRegions());
        m.addAttribute("centerMap",getCenters());
        
        return "account/registration";
    }
    
    @RequestMapping(value={"registrationOID.htm"},method= RequestMethod.GET)
    public String registerOID(
            @ModelAttribute(value="googleRegistrationForm")
            GoogleRegistrationForm googleRegistrationForm, 
            ModelMap m, 
            HttpServletRequest request, 
            Locale locale
            )throws Exception
    {
        String email = (String) request.getSession().getAttribute("email");
        if(isParameterNull(email)){
            return "redirect:index.htm";
        }
        String firstName = (String) request.getSession().getAttribute("firstName");
        String lastName = (String) request.getSession().getAttribute("lastName");
        googleRegistrationForm.setEmail(email);
        googleRegistrationForm.setFirstName(firstName);
        googleRegistrationForm.setLastName(lastName);
        
        m.addAttribute("regions", getRegionFinder().getRegions());
        m.addAttribute("centerMap",getCenters());
        return "account/registrationOID";
    }
    
    @RequestMapping(value={"registrationOID.htm"},method= {RequestMethod.POST})
    public String addUserOID(
            @ModelAttribute(value="googleRegistrationForm")
            @Valid GoogleRegistrationForm googleRegistrationForm,
            BindingResult errors,
            HttpServletRequest request,
            Locale locale,
            ModelMap m
            )throws Exception
    {    
    try{    
    if(!errors.hasErrors()){
        UserEntity user = new UserEntity();
        user.setActive(Boolean.FALSE);
        user.setDeleted(Boolean.FALSE);
        user.setEmail(googleRegistrationForm.getEmail());
        user.setGoogleName(googleRegistrationForm.getFirstName() + " " + googleRegistrationForm.getLastName());
        String pass = getPasswordGenerator().getPassword();
        String encodePassword = getStandardPassworgEncoder().encode(
                    pass
                    );
        user.setPassword(encodePassword);
        Collection<UserRoleEntity> roles = getUserRoleManager().findAll();
        Set<UserRoleEntity> usrRoles = new HashSet<UserRoleEntity>();
        for(UserRoleEntity r: roles){
            if(r.getName().equals("ROLE_USER")){
                usrRoles.add(r);
            }
        }
        user.setRoles(usrRoles);
        
        RegionEntity region = getRegionManager().findById(googleRegistrationForm.getRegionId());
            if(isParameterNull(region)){
                getInfoMessages().setWarnMessage("Zadaný region se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "account/registrationOID";
            }
            user.setRegion(region);
            
            CenterEntity center = getCenterManager().findById(googleRegistrationForm.getCenterId());
            if(isParameterNull(center)){
                getInfoMessages().setWarnMessage("Zadané centerum se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "account/registrationOID";
            }
        
        user.setCentrum(center);
        
        getUserManager().add(user);
        getInfoMessages().setInfoMessage("Uživatel s emailovou adresou "+user.getEmail()+" byl úspěšně registrován!");
        getInfoMessages().setWarnMessage("Váš účet se po ověřením správcem stane aktivní! O této události budete informováni emailem.");
        if(!isParameterNull(center.getAdmin())){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(center.getAdmin().getEmail());
        msg.setText("Password: "+pass);
        msg.setSubject("New Registration :: "+user.getEmail());
        
        try {
            getMailService().send(msg);
        } catch (MailException e) {
            getInfoMessages().setErrorMessage("Emailovou zprávu se nepodařilo odeslat. Nepodařilo se kontaktovat SMTP server.");
        }
        }
    } else {
        m.addAttribute("regions", getRegionFinder().getRegions());
        m.addAttribute("centerMap",getCenters());
        return "account/registrationOID"; 
    }
    } catch(Exception e){
        getInfoMessages().setErrorMessage("ERROR REG OID "+e.getMessage());
    }
       return "redirect:index.htm";
    }
    
    @RequestMapping(value={"registration.htm"},method={RequestMethod.POST})
    public String addUser(
            @ModelAttribute(value="userRegistrationForm") 
            @Valid UserRegistrationForm userRegistrationForm, 
            BindingResult errors,
            Locale locale,
            ModelMap m
            )throws Exception
    {
        try{
        //Zasílání emailů předělat na konkrétní osobu
        if(!errors.hasErrors()){
            UserEntity user = new UserEntity();
            Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();
            List<UserRoleEntity> allroles = getUserRoleManager().findAll();
            for(UserRoleEntity r : allroles){
                if(r.getName().equals("ROLE_USER")){
                    roles.add(r);
                }
            }
            user.setRoles(roles);
            user.setEmail(userRegistrationForm.getEmail());
            String encodePassword = getStandardPassworgEncoder().encode(
                    userRegistrationForm.getPassword()
                    );
            user.setPassword(encodePassword);
            user.setActive(Boolean.FALSE);
            user.setDeleted(Boolean.FALSE);
            
            RegionEntity region = getRegionManager().findById(userRegistrationForm.getRegionId());
            if(isParameterNull(region)){
                getInfoMessages().setWarnMessage("Zadaný region se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "account/registration";
            }
            user.setRegion(region);
            
            CenterEntity center = getCenterManager().findById(userRegistrationForm.getCenterId());
            if(isParameterNull(center)){
                getInfoMessages().setWarnMessage("Zadané centerum se nepodařilo najít.");
                m.addAttribute("regions", getRegionFinder().getRegions());
                m.addAttribute("centerMap",getCenters());
                return "account/registration";
            }
            
            user.setCentrum(center);
            
            getUserManager().add(user);
            getInfoMessages().setInfoMessage("Uživatel s emailovou adresou "+user.getEmail()+" byl úspěšně registrován!");
            getInfoMessages().setWarnMessage("Váš účet se po ověřením správcem stane aktivní! O této události budete informováni emailem.");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("stenlik@gmail.com");
        msg.setText("Some text");
        msg.setSubject("New Registration");
        getMailService().send(msg);
        }
        else{
            m.addAttribute("regions", getRegionFinder().getRegions());
            m.addAttribute("centerMap",getCenters());
            return "account/registration";
        }
        } catch(Exception e){
            getInfoMessages().setErrorMessage("ERROR REG"+e.getMessage());
        }
        return "redirect:index.htm";
    }
    
    @Secured(value={"ROLE_ADMIN","ROLE_USER","ROLE_ADMIN_CONTENT","ROLE_ADMIN_CENTRUM"})
    @RequestMapping(value ={"profile.htm"}, method = RequestMethod.GET)
    public String profile(
            Authentication aut, 
            ModelMap m
            ) throws Exception {
        if(isParameterNull(aut)){
            return "redirect:index.htm";
        }
        User u = (User) aut.getPrincipal();
        UserEntity user = getUserManager().findByEmail(u.getUsername());
        if(isParameterNull(user)){
            return "redirect:index.htm";
        }
        List<OfferEntity> offers = getOfferManager().findAllActivateDeletedbyUserId(Boolean.TRUE, Boolean.FALSE, user.getId());
        m.addAttribute("userOffers", offers);
        m.addAttribute("user", user);
        return "account/profile";
    }
    
}
