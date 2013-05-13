/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author novakst6
 */
@Controller
@Secured(value = {"ROLE_ADMIN", "ROLE_ADMIN_CENTER", "ROLE_ADMIN_CONTENT"})
public class AdminController extends TemplateController {

    @RequestMapping(value = "admin/index.htm", method = RequestMethod.GET)
    public String admin() throws Exception {
        return "admin/admin";
    }
}
