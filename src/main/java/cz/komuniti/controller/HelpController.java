/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.HelpEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value = {"help"})
@Secured(value={"ROLE_USER"})
public class HelpController extends TemplateController {

    @RequestMapping(value = {"help.htm"}, method = {RequestMethod.GET})
    public String help(
            ModelMap m,
            @RequestParam(value = "page", required = false) Integer page) throws Exception {
        try {
            if (isParameterNull(page)) {
                page = new Integer(1);
            }
            getPaginator().setMax(getHelpManager().getCount());
            getPaginator().setPage(page);
            m.addAttribute("helps", getHelpManager().findPage(getPaginator().getResult()[0], getPaginator().getResult()[1]));
            return "help/help";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:index.htm";
        }
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
            HelpEntity help = getHelpManager().findById(id);
            if (isParameterNull(help)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            m.addAttribute("help", help);
            return "help/detail";
        } catch (Exception e) {
            e.printStackTrace();
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }
}
