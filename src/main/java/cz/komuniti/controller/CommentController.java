/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.CommentEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
@Secured(value = {"ROLE_ADMIN", "ROLE_ADMIN_CONTENT"})
public class CommentController extends TemplateController {

    @RequestMapping(value = {"/catalog/block.htm"}, method = {RequestMethod.GET})
    public String blockCommentItem(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "idItem") Long idItem) throws Exception {
        if (isParameterNull(id)) {
            getInfoMessages().setWarnMessage("Parametr id je povinný.");
            return "redirect:show.htm";
        }
        if (isParameterNull(idItem)) {
            getInfoMessages().setWarnMessage("Parametr idItem je povinný.");
            return "redirect:show.htm";
        }

        CommentEntity comment = getCommentManager().findById(id);

        if (isParameterNull(comment)) {
            getInfoMessages().setWarnMessage("Parametr idArticle je povinný.");
            return "redirect:detail.htm?id=" + idItem;
        }

        comment.setBanned(Boolean.TRUE);
        getCommentManager().edit(comment);

        getInfoMessages().setInfoMessage("Komentář byl zablokován");

        return "redirect:detail.htm?id=" + idItem;
    }

    @RequestMapping(value = {"/blog/block.htm"}, method = {RequestMethod.GET})
    public String blockCommentArticle(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "idArticle") Long idArticle) throws Exception {
        if (isParameterNull(id)) {
            getInfoMessages().setWarnMessage("Parametr id je povinný.");
            return "redirect:show.htm";
        }
        if (isParameterNull(idArticle)) {
            getInfoMessages().setWarnMessage("Parametr idArticle je povinný.");
            return "redirect:show.htm";
        }

        CommentEntity comment = getCommentManager().findById(id);

        if (isParameterNull(comment)) {
            getInfoMessages().setWarnMessage("Parametr idArticle je povinný.");
            return "redirect:detail.htm?id=" + idArticle;
        }

        comment.setBanned(Boolean.TRUE);
        getCommentManager().edit(comment);

        getInfoMessages().setInfoMessage("Komentář byl zablokován");

        return "redirect:detail.htm?id=" + idArticle;
    }
}
