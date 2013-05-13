/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.ArticleEntity;
import cz.komuniti.model.entity.CommentEntity;
import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.form.BlogAddForm;
import cz.komuniti.model.form.BlogEditForm;
import cz.komuniti.model.form.BlogTextFilterForm;
import cz.komuniti.model.form.CommentAddForm;
import java.sql.Timestamp;
import java.util.List;
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

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value = {"blog"})
@Secured(value={"ROLE_USER"})
public class BlogController extends TemplateController {

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.GET})
    public String showGET(
            ModelMap m,
            @RequestParam(value = "page", required = false) Integer page,
            @ModelAttribute(value = "blogTextFilterForm") BlogTextFilterForm formModel,
            HttpSession session,
            Authentication auth) throws Exception {
        if (isParameterNull(page)) {
            page = new Integer(1);
        }

        if (isParameterNull(auth)) {
            getInfoMessages().setWarnMessage("Uživatel byl odhlášen.");
            return "redirect:/index.htm";
        }
        User u = (User) auth.getPrincipal();

        BlogTextFilterForm filterForm = (BlogTextFilterForm) session.getAttribute("blogFilterForm");
        if (filterForm == null) {
            filterForm = new BlogTextFilterForm();
            session.setAttribute("blogFilterForm", filterForm);
        }

        formModel.setKeywords(filterForm.getKeywords());

        if (filterForm.getKeywords().isEmpty()) {
            getPaginator().setMax(getArticleManager().getCountDeleted(Boolean.FALSE));
            getPaginator().setPage(page);
            m.addAttribute("articles", getArticleManager().findPageDeleted(getPaginator().getResult()[0], getPaginator().getResult()[1], Boolean.FALSE));
        } else {
            List<ArticleEntity> findFullText = getArticleManager().findFullText(Boolean.FALSE, filterForm.getKeywords());
            getPaginator().setMax(findFullText.size());
            getPaginator().setPage(page);
            m.addAttribute("articles", findFullText.subList(getPaginator().getResult()[0], getPaginator().getResult()[1]));
        }

        m.addAttribute("userName", u.getUsername());
        return "blog/show";
    }

    @RequestMapping(value = {"show.htm"}, method = {RequestMethod.POST})
    public String showPOST(
            HttpSession session,
            @ModelAttribute(value = "blogTextFilterForm") BlogTextFilterForm formModel) throws Exception {
        session.setAttribute("blogFilterForm", formModel);
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.GET})
    public String addGET(
            @ModelAttribute(value = "addFormModel") BlogAddForm formModel,
            ModelMap m) throws Exception {
        return "blog/add";
    }

    @RequestMapping(value = {"add.htm"}, method = {RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid BlogAddForm formModel,
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
                ArticleEntity article = new ArticleEntity();
                article.setAuthor(user);
                article.setDateOfInsert(new Timestamp(System.currentTimeMillis()));
                article.setDeleted(Boolean.FALSE);
                article.setEdited(Boolean.FALSE);
                article.setText(formModel.getText());
                article.setTitle(formModel.getTitle());
                boolean add = getArticleManager().add(article);
                if (add) {
                    getInfoMessages().setInfoMessage("Objekt byl úspěšně uložen.");
                } else {
                    getInfoMessages().setWarnMessage("Objekt se nezdařilo uložit.");
                }
            } else {
                return "blog/add";
            }

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"detail.htm"}, method = {RequestMethod.GET})
    public String detailGET(
            @ModelAttribute(value = "addFormModel") CommentAddForm formModel,
            @RequestParam(value = "id", required = true) Long id,
            ModelMap m) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
            ArticleEntity article = getArticleManager().findById(id);
            if (isParameterNull(article)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            if (article.getDeleted()) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            formModel.setId(id);
            m.addAttribute("article", article);
            
            return "blog/detail";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
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

                ArticleEntity article = getArticleManager().findById(formModel.getId());
                if (isParameterNull(article)) {
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

                article.getComments().add(comment);
                boolean edit = getArticleManager().edit(article);
                if (edit) {
                    getInfoMessages().setInfoMessage("Komentář byl úspěšně uložen k článku.");
                } else {
                    getInfoMessages().setWarnMessage("Komentář se nezdařilo uložit k článku.");
                }
                return "redirect:detail.htm?id=" + formModel.getId();
            } else {
                ArticleEntity article = getArticleManager().findById(formModel.getId());
                if (isParameterNull(article)) {
                    getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                    return "redirect:show.htm";
                }
                m.addAttribute("article", article);
                return "blog/detail";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.GET})
    public String editGET(
            @ModelAttribute(value = "addFormModel") BlogEditForm formModel,
            ModelMap m,
            Authentication auth,
            @RequestParam(value = "id") Long id) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
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

            ArticleEntity article = getArticleManager().findById(id);
            if (isParameterNull(article)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            if (!article.getAuthor().getEmail().equals(u.getUsername())) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            formModel.setId(id);
            formModel.setTitle(article.getTitle());
            formModel.setText(article.getText());


        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
            return "redirect:show.htm";
        }
        return "blog/edit";
    }

    @RequestMapping(value = {"edit.htm"}, method = {RequestMethod.POST})
    public String editPOST(
            @ModelAttribute(value = "addFormModel")
            @Valid BlogEditForm formModel,
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
                ArticleEntity article = getArticleManager().findById(formModel.getId());


                article.setEdited(Boolean.TRUE);
                article.setDateOfEdit(new Timestamp(System.currentTimeMillis()));
                article.setEditor(user);
                article.setText(formModel.getText());
                article.setTitle(formModel.getTitle());
                boolean add = getArticleManager().edit(article);
                if (add) {
                    getInfoMessages().setInfoMessage("Objekt byl úspěšně aktualizován.");
                } else {
                    getInfoMessages().setWarnMessage("Objekt se nezdařilo aktualizovat.");
                }
            } else {
                return "blog/edit";
            }

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());
        }
        return "redirect:show.htm";
    }

    @RequestMapping(value = {"delete.htm"}, method = {RequestMethod.GET})
    public String delete(
            ModelMap m,
            Authentication auth,
            @RequestParam(value = "id") Long id) throws Exception {
        try {
            if (isParameterNull(id)) {
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";
            }
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

            ArticleEntity article = getArticleManager().findById(id);
            if (isParameterNull(article)) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            if (!article.getAuthor().getEmail().equals(u.getUsername())) {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }

            article.setDeleted(Boolean.TRUE);

            getArticleManager().edit(article);
            getInfoMessages().setInfoMessage("Objekt byl smazán.");

        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR " + e.getMessage());

        }
        return "redirect:show.htm";
    }
    
}
