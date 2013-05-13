/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.*;
import cz.komuniti.model.form.*;
import cz.komuniti.service.filter.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(value={"exchange"})
@Secured(value={"ROLE_USER"})
public class ExchangeController extends TemplateController {
    @Autowired
    private StateExchangeFilterCase0 filterCase0;
    @Autowired
    private StateExchangeFilterCase1 filterCase1;
    @Autowired
    private StateExchangeFilterCase2 filterCase2;
    @Autowired
    private StateExchangeFilterCase3 filterCase3;
    @Autowired
    private StateExchangeFilterCase4 filterCase4;
    @Autowired
    private StateExchangeFilterCase5 filterCase5;
    @Autowired
    private StateExchangeFilterCase6 filterCase6;
    @Autowired
    private StateExchangeFilterCase7 filterCase7;
    
   private StateExchangeFilter getStateExchangeFilter(int filterCase)
    {
        switch(filterCase){
            case 0:{return filterCase0;}
            case 1:{return filterCase1;}
            case 2:{return filterCase2;}   
            case 3:{return filterCase3;}
            case 4:{return filterCase4;}
            case 5:{return filterCase5;}
            case 6:{return filterCase6;}
            case 7:{return filterCase7;}
            default: {return filterCase0;}   
        }
    }

    @RequestMapping(value={"show.htm"},method={RequestMethod.GET})
    public String showFilterGET(
            @ModelAttribute(value="filterForm")
            ExchangeFilterForm filterForm,
            @ModelAttribute(value="exchangeTextFilterForm")
            ExchangeFulltextFilterForm fulltextForm,
            @RequestParam(value="page",required=false)
            Integer page,
            ModelMap m,
            HttpSession session
            ) throws Exception 
    {
        if(isParameterNull(page)){
            page = new Integer(1);
        }
        ExchangeFilterForm filterSettings = (ExchangeFilterForm) session.getAttribute("filterSettings");
        if(filterSettings != null){
            filterForm.setIdsItemTag(filterSettings.getIdsItemTag());
            filterForm.setIdsOfferTag(filterSettings.getIdsOfferTag());
            filterForm.setIdsRegions(filterSettings.getIdsRegions());
        }
        
        
                
        ExchangeFulltextFilterForm filterFulltext = (ExchangeFulltextFilterForm) session.getAttribute("fulltextSettings");
        if(filterFulltext != null){
            fulltextForm.setKeywords(filterFulltext.getKeywords());
        }
        
        Long startTime = System.currentTimeMillis();
        
        if(!fulltextForm.getKeywords().equals(""))
        {
            if(filterForm != null){
                if(filterForm.getIdsItemTag() != null)
            filterForm.getIdsItemTag().clear();
                if(filterForm.getIdsOfferTag() != null)
            filterForm.getIdsOfferTag().clear();
                if(filterForm.getIdsRegions() != null)
            filterForm.getIdsRegions().clear();
            }

            List<OfferEntity> result = getOfferManager().findFullText(Boolean.FALSE, Boolean.TRUE, fulltextForm.getKeywords());
            getPaginator().setMax(result.size());
            getPaginator().setPage(page);
            List<OfferEntity> subList = result.subList(getPaginator().getResult()[0], getPaginator().getResult()[1]);
            m.addAttribute("offers", subList);
        } else {
        
        if(filterSettings != null){
            List<Long> idsItemTag = filterSettings.getIdsItemTag();
            List<Long> idsOfferTag = filterSettings.getIdsOfferTag();
            List<Long> idsRegions = filterSettings.getIdsRegions();
            List<TagEntity> tags = new LinkedList<TagEntity>();
            if(idsItemTag != null){
                tags = getTagManager().findByListId(idsItemTag);
            }
            List<OfferTagEntity> offerTags = new LinkedList<OfferTagEntity>();
            if(idsOfferTag != null){
                offerTags = getOfferTagManager().findByListId(idsOfferTag);
            }
            List<RegionEntity> regions = new LinkedList<RegionEntity>();
            if(idsRegions != null){
                regions = getRegionManager().findByListId(idsRegions);
            }

            String filterInfo = "";
            for(TagEntity t: tags){
                filterInfo += t.getName()+"; ";
            }
            
            for(OfferTagEntity ot: offerTags){
                filterInfo += ot.getName()+"; ";
            }
            
            for(RegionEntity r: regions){
                filterInfo += r.getName()+"; ";
            }
            
            if(!filterInfo.equals("")){
                getInfoMessages().setInfoMessage("FILTR: "+filterInfo);
            }
        }
        
        StateExchangeFilter filter = getStateExchangeFilter(filterForm.getCase());
        getPaginator().setMax(filter.getCount(filterForm));
        getPaginator().setPage(page);      
        m.addAttribute("offers", filter.getResult(filterForm, page));
        }

        m.addAttribute("tags", getTagFinder().getTags());
        m.addAttribute("offerTags", getOfferTagManager().findAll());
        m.addAttribute("regions", getRegionFinder().getRegions());
        
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        m.addAttribute("userName", name);
        Long endTime = System.currentTimeMillis();
        getInfoMessages().setErrorMessage("FILTER "+(endTime-startTime)+" ms");
        
        return "exchange/show";
    }
   
    @RequestMapping(value={"show.htm"},method={RequestMethod.POST})
    public String showFilterPOST(
            @ModelAttribute("filterForm")
            ExchangeFilterForm filterForm,
            HttpSession session
            ) throws Exception 
    {

        ExchangeFulltextFilterForm filterFulltext = (ExchangeFulltextFilterForm) session.getAttribute("fulltextSettings");
        if(filterFulltext != null){
            filterFulltext.setKeywords("");
            session.setAttribute("fulltextSettings", filterFulltext);
        }
        session.setAttribute("filterSettings", filterForm);
        return "redirect:show.htm";
    }
    
   @RequestMapping(value={"fulltext.htm"},method={RequestMethod.POST})
    public String showFullTextFilterPOST(
            @ModelAttribute(value="exchangeTextFilterForm")
            ExchangeFulltextFilterForm fulltextForm,
            HttpSession session
            ) throws Exception 
    
    {
     ExchangeFilterForm filterSettings = (ExchangeFilterForm) session.getAttribute("filterSettings");
        if(filterSettings != null){
            if(filterSettings.getIdsItemTag() != null){
                filterSettings.getIdsItemTag().clear();
            }
            if(filterSettings.getIdsOfferTag() != null){
                filterSettings.getIdsOfferTag().clear();
            }
            if(filterSettings.getIdsRegions() != null){
                filterSettings.getIdsRegions().clear();
            }
        }
        
        session.setAttribute("fulltextSettings", fulltextForm);
        return "redirect:show.htm";
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.GET})
    public String addGET(
            @ModelAttribute("addFormModel")
            ExchangeAddForm formModel,
            @RequestParam(value="id",required=false)
            Long itemId,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(itemId != null){
                ItemEntity item = getItemManager().findById(itemId);
                if(item != null){
                    formModel.setItemId(itemId);
                    m.addAttribute("selectedItem",item);
                    formModel.setFromCatalog(Boolean.TRUE);
                }
            }
            m.addAttribute("items", getItemManager().findAllActivate(Boolean.TRUE));
            m.addAttribute("tags", getOfferTagManager().findAll());
            return "exchange/add";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"add.htm"},method={RequestMethod.POST})
    public String addPOST(
            @ModelAttribute(value="addFormModel")
            @Valid ExchangeAddForm formModel, 
            BindingResult errors,
            ModelMap m,
            Authentication auth,
            HttpSession session
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors())
            {
                
                List<OfferTagEntity> tags = getOfferTagManager().findByListId(formModel.getTagsId());
                if(tags.isEmpty()){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít kategorie nabídky.");
                    return "redirect:show.htm";
                }

                if(isParameterNull(auth)){
                    getInfoMessages().setErrorMessage("Uživatel byl odhlášen.");
                    return "redirect:/index.htm";
                }
                User usr = (User) auth.getPrincipal();
                String email = usr.getUsername();
                
                UserEntity user = getUserManager().findByEmail(email);
                if(isParameterNull(user)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít uživatele.");
                    return "redirect:show.htm";
                }
                
                Set<FileEntity> files = new HashSet<FileEntity>();
                if(formModel.getFiles() != null)
                {
                    for (int i = 0; i < formModel.getFiles().length; i++) {
                        CommonsMultipartFile f = formModel.getFiles()[i];
                        if(f == null) {continue;}
                        if(f.getSize() == 0){continue;}
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
                for(OfferTagEntity ot: tags)
                {
                    offer.getTags().add(ot);
                }
                
                offer.setInsertDate(new Timestamp(System.currentTimeMillis()));
                offer.setActive(Boolean.TRUE);
                offer.setDeleted(Boolean.FALSE);
                offer.setAuthor(user);
                offer.setText(formModel.getText());
                offer.setTitle(formModel.getTitle());
                offer.setFiles(files);
                
                if(!formModel.getOwnItem()){
                ItemEntity item = getItemManager().findById(formModel.getItemId());
                if(isParameterNull(item)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít věc.");
                    return "redirect:show.htm";
                }
                offer.setItem(item);
                getOfferManager().add(offer);
                
                } else {
                    session.setAttribute("offer", offer);
                    return "redirect:addItem.htm";
                }          
                
                getInfoMessages().setInfoMessage("Objekt byl úspěšne přidán.");
                
            } else {
                m.addAttribute("items", getItemManager().findAllActivate(Boolean.TRUE));
                m.addAttribute("tags", getOfferTagManager().findAll());
                return "exchange/add";
            }
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            e.printStackTrace();
        }
        return "redirect:show.htm";
    }
   
    @RequestMapping(value={"itemsFilter.htm"})
    public String filterItems(@RequestParam(value="keyword") String keyword, ModelMap m){
        if(keyword.equals("")){
            return "exchange/itemsFilter";
        }
        List<ItemEntity> result = getItemManager().findFullText(Boolean.FALSE, Boolean.TRUE, keyword);
        m.addAttribute("items", result);
        return "exchange/itemsFilter";
    }
    
    @RequestMapping(value={"addItem.htm"},method={RequestMethod.GET})
    public String addItemGET(
            @ModelAttribute("addFormModel")
            ExchangeAddItemForm formModel,
            ModelMap m,
            HttpSession session
            ) throws Exception 
    {
        OfferEntity offer = (OfferEntity) session.getAttribute("offer");
        if(isParameterNull(offer)){
            getInfoMessages().setWarnMessage("Nepodařilo se nalézt nabídku, na kterou se váže vytváření věci.");
            return "redirect:show.htm";
        }
        m.addAttribute("tags", getTagFinder().getTags());
        m.addAttribute("offer", offer);
        return "exchange/addItem";
    }
    
    @RequestMapping(value={"addItem.htm"},method={RequestMethod.POST})
    public String addItemPOST(
            @ModelAttribute(value="addFormModel")
            @Valid ExchangeAddItemForm formModel,
            BindingResult errors,
            ModelMap m,
            HttpSession session
            ) throws Exception 
    {
        OfferEntity offer = (OfferEntity) session.getAttribute("offer");
        if(isParameterNull(offer)){
            getInfoMessages().setWarnMessage("Nepodařilo se nalézt nabídku, na kterou se váže vytváření věci.");
            return "redirect:show.htm";
        }
        try {
            if(!errors.hasErrors()){
                ItemEntity item = new ItemEntity();
                item.setName(formModel.getName());
                item.setDescription(formModel.getDescription());
                item.setText(formModel.getText());
                item.setActive(Boolean.TRUE);
                item.setDeleted(Boolean.FALSE);
                item.setCreatedByUser(Boolean.TRUE);
                //tags
                List<TagEntity> tags = getTagManager().findByListId(formModel.getTags());
                Set<TagEntity> tag = new HashSet<TagEntity>();
                if(tags != null)
                {               
                    if(!tags.isEmpty())
                    {
                       for(TagEntity t: tags) 
                       {
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
                if(formModel.getFiles() != null)
                {
                    for (int i = 0; i < formModel.getFiles().length; i++) {
                        CommonsMultipartFile f = formModel.getFiles()[i];
                        if(f == null){ continue;}
                        if(f.getSize() == 0){continue;}
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
                
                offer.setItem(item);
                
                //refresh object from last sessions
                    for(FileEntity f : offer.getFiles()){
                        getFileManager().refresh(f);
                    }
                    for(OfferTagEntity ot : offer.getTags()){
                        getOfferTagManager().refresh(ot);
                    }
                    getUserManager().refresh(offer.getAuthor());
                       
                boolean add = getOfferManager().add(offer);
                
                getInfoMessages().setInfoMessage("Nabídka byla úspěšně uložena.");
                session.setAttribute("offer", null);
                //TODO odeslat email o nove veci
                AppConfigEntity conf = getAppConfManager().findById(1L);
                if(conf != null){
                    if(conf.getContentModerator() != null){
                    SimpleMailMessage msg = new SimpleMailMessage();
                    msg.setTo(conf.getContentModerator().getEmail());
                    msg.setText("Do burzy byla zadána nová věc "+item.getName()+" s ID = "+item.getId());
                    msg.setSubject("New Item :: "+item.getName());
        
                    try {
                        getMailService().send(msg);
                    } catch (MailException e) {
                    getInfoMessages().setErrorMessage("Emailovou zprávu se nepodařilo odeslat. Nepodařilo se kontaktovat SMTP server.");
                    }
                    }
                }
                return "redirect:show.htm";
            } else {
                m.addAttribute("tags", getTagFinder().getTags());
                return "exchange/addItem";
            }
        } catch (Exception e) {
            e.printStackTrace();
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }   
    }
    
    @RequestMapping(value={"detail.htm"},method={RequestMethod.GET})
    public String detail(
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:filter.htm";
            }
            OfferEntity offer = getOfferManager().findById(id);
            if(isParameterNull(offer)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:filter.htm";
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
            return "exchange/detail";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
        @RequestMapping(value={"response.htm"},method={RequestMethod.GET})
    public String responseGET(
            @ModelAttribute("newFormModel")
            ExchangeResponseForm formModel,
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněn.");
                return "redirect:show.htm";
            }
            OfferEntity offer = getOfferManager().findById(id);
            if(isParameterNull(offer)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            if(offer.getDeleted() || !offer.getActive())
            {
                getInfoMessages().setWarnMessage("Nabídka nebyla nalezena.");
                return "redirect:show.htm";
            }
            
            m.addAttribute("offer", offer);
 
            formModel.setOfferId(id);
            formModel.setRecepientId(offer.getAuthor().getId());
            formModel.setRecepient(offer.getAuthor().getEmail());
            
            return "exchange/response";
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"response.htm"},method={RequestMethod.POST})
    public String responsePOST(
            @ModelAttribute(value="newFormModel")
            @Valid ExchangeResponseForm formModel,
            BindingResult errors,
            Authentication auth,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(!errors.hasErrors()){
                OfferEntity offer = getOfferManager().findById(formModel.getOfferId());
                if(isParameterNull(offer)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít nabídku.");
                    return "redirect:show.htm";
                }
                if(isParameterNull(auth)){
                    getInfoMessages().setErrorMessage("Uživatel byl odhlášen.");
                    return "redirect:/index.htm";
                }               
                User u = (User) auth.getPrincipal();
                UserEntity user = getUserManager().findByEmail(u.getUsername());
                UserEntity recepient = offer.getAuthor();
                MessageEntity msg = new MessageEntity();
                MessageEntity msg2 = new MessageEntity();
                msg.setAuthor(user);
                msg2.setAuthor(user);
                msg.setOwner(user);
                msg2.setOwner(recepient);
                msg.setRecipient(recepient);
                msg2.setRecipient(recepient);
                String subject = formModel.getSubject();
                String title = offer.getTitle();
                if(title.length() > 30)
                {
                   title = title.substring(0, 30);
                }
                String prefixSubject = "Odpověď na nabídku "+title+"... ";
                msg.setSubject(prefixSubject+subject);
                msg2.setSubject(prefixSubject+subject);
                msg.setOffer(offer);
                msg2.setOffer(offer);
                msg.setText(formModel.getText());
                msg2.setText(formModel.getText());
                msg.setSendDate(new Date(System.currentTimeMillis()));
                msg2.setSendDate(new Date(System.currentTimeMillis()));
                msg.setDeleted(Boolean.FALSE);
                msg2.setDeleted(Boolean.FALSE);
                msg.setReaded(Boolean.FALSE);
                msg2.setReaded(Boolean.FALSE);
                getMessageManager().add(msg);
                getMessageManager().add(msg2);
                getInfoMessages().setInfoMessage("Zpráva byla odeslána uživateli "+offer.getAuthor().getEmail());
                return "redirect:show.htm";
            } else {
             OfferEntity offer = getOfferManager().findById(formModel.getOfferId());
            if(isParameterNull(offer)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }         
                m.addAttribute("offer", offer);
                return "exchange/response";
            }
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
        
    }
    
    @RequestMapping(value={"end.htm"},method={RequestMethod.GET})
    public String endOfferGET(
            @RequestParam(value="id")
            Long id,
            Authentication auth
            ) throws Exception 
    {
        try {
            if(isParameterNull(auth)){
                getInfoMessages().setWarnMessage("Nepodařilo se najít uživatele, pravděpodobně jste byl odhlášen.");
                return "redirect:show.htm";
            }
            
            User u = (User) auth.getPrincipal();
            UserEntity user = getUserManager().findByEmail(u.getUsername());
            
            if(isParameterNull(user)){
                getInfoMessages().setWarnMessage("Nepodařilo se najít uživatele, pravděpodobně jste byl odhlášen.");
                return "redirect:show.htm";
            }
            
            if(isParameterNull(id)){
                getInfoMessages().setWarnMessage("Parametr id je povinný.");
                return "redirect:show.htm";        
            }
            
            OfferEntity offer = getOfferManager().findById(id);
            if(isParameterNull(offer)){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            if(offer.getDeleted()){
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:show.htm";
            }
            
            if(offer.getAuthor().getId().longValue() != user.getId().longValue()){
                getInfoMessages().setWarnMessage("Tuto nabídku nelze ukončit. Ukončit smíte jenom své nabídky.");
                return "redirect:show.htm";
            }            
            
            if(!offer.getActive().booleanValue()){
                getInfoMessages().setWarnMessage("Nabídka je již uzavřena.");
                return "redirect:show.htm";
            }
            
            offer.setActive(Boolean.FALSE);
            getOfferManager().edit(offer);
            getInfoMessages().setInfoMessage("Nabídka byla úspěšně ukončena.");
            return "redirect:show.htm";
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
        
        @RequestMapping(value={"edit.htm"},method={RequestMethod.GET})
    public String editOfferGET(
            @ModelAttribute(value="editFormModel")
            ExchangeEditForm formModel,
            Authentication auth,
            @RequestParam(value="id",required=true)
            Long id,
            ModelMap m
            ) throws Exception 
    {
        try {

        if(isParameterNull(auth)){
            getInfoMessages().setWarnMessage("Uživatel byl odhlášen.");
            return "redirect:show.htm";
        }
        
        if(isParameterNull(id)){
            getInfoMessages().setWarnMessage("Parametr id je povinný.");
            return "redirect:show.htm";
        }
        
        OfferEntity offer = getOfferManager().findById(id);
        if(isParameterNull(offer)){
            getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
            return "redirect:show.htm";
        }
        
        User u = (User) auth.getPrincipal();
        UserEntity user = getUserManager().findByEmail(u.getUsername());
        
        if(user.getId().longValue() != offer.getAuthor().getId().longValue()){
            getInfoMessages().setWarnMessage("Nemáte oprávnění editovat tento objekt.");
            return "redirect:show.htm";
        }
        
        formModel.setTitle(offer.getTitle());
        formModel.setText(offer.getText());
        formModel.setId(id);
        for(OfferTagEntity tag: offer.getTags()){
                formModel.getTagsId().add(tag.getId());
        }
        formModel.setFilesOrig(offer.getFiles());
        m.addAttribute("tags", getOfferTagManager().findAll());
        return "exchange/edit";
                    
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }
    
    @RequestMapping(value={"edit.htm"},method={RequestMethod.POST})
    public String editOfferPOST(
            @ModelAttribute(value="editFormModel")
            ExchangeEditForm formModel,
            BindingResult errors,
            Authentication auth,
            ModelMap m
            ) throws Exception 
    {
        try {
            if(isParameterNull(auth)){
                getInfoMessages().setWarnMessage("Byl jste odhlášen.");
                return "redirect:show.htm";
            }
            
            if(!errors.hasErrors()){
                OfferEntity offer = getOfferManager().findById(formModel.getId());
                if(isParameterNull(offer)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít editovaný objekt.");
                    return "redirect:show.htm";
                }
                List<OfferTagEntity> tags = getOfferTagManager().findByListId(formModel.getTagsId());
                if(tags.isEmpty()){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít kategorie nabídky.");
                    return "redirect:show.htm";
                }
                
                User u = (User) auth.getPrincipal();
                
                UserEntity user = getUserManager().findByEmail(u.getUsername());
                if(isParameterNull(user)){
                    getInfoMessages().setWarnMessage("Nepodařilo se najít přihlášeného uživatele.");
                    return "redirect:show.htm";
                }
                
                Set<OfferTagEntity> tagNew = new HashSet<OfferTagEntity>();
                for(OfferTagEntity ot: tags)
                {
                   tagNew.add(ot);
                }
                offer.setTags(tagNew);
                offer.setInsertDate(new Timestamp(System.currentTimeMillis()));
                offer.setActive(Boolean.TRUE);
                offer.setText(formModel.getText());
                offer.setTitle(formModel.getTitle());
                offer.setEdited(Boolean.TRUE);
                offer.setEditedDate(new Timestamp(System.currentTimeMillis()));
                offer.setEditedBy(user);
                  //files
                Set<FileEntity> files = new HashSet<FileEntity>();
                if(formModel.getFiles() != null)
                {
                    for (int i = 0; i < formModel.getFiles().length; i++) {
                        CommonsMultipartFile f = formModel.getFiles()[i];
                        if(f == null) {continue;}
                        if(f.getSize() == 0){continue;}
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
                if(filesToDelete != null)
                {
                    if(!filesToDelete.isEmpty()){
                    List<FileEntity> filesToBeDelete = getFileManager().findByListId(filesToDelete);
                    for(FileEntity f: filesToBeDelete)
                    {
                        for(FileEntity fi: filesItem)
                        {
                            if(f.getId().longValue() == fi.getId().longValue())
                            {
                                filesItem.remove(fi);
                                getInfoMessages().setInfoMessage("Soubor "+fi.getName()+" byl smazán.");
                                break;
                            }
                        }
                    }
                    }
                }
                offer.setFiles(filesItem);
                
                getOfferManager().edit(offer);
                
                boolean delete = getFileManager().delete(filesToDelete);
                
                getInfoMessages().setInfoMessage("Změny byly úspěšně uloženy.");
                return "redirect:show.htm";
                
            } else {
                m.addAttribute("tags", getTagManager().findAll());
                return "exchange/edit";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:show.htm";
        }
    }     
   
    public void setFilterCase0(StateExchangeFilterCase0 filterCase0) {
        this.filterCase0 = filterCase0;
    }

    public void setFilterCase1(StateExchangeFilterCase1 filterCase1) {
        this.filterCase1 = filterCase1;
    }

    public void setFilterCase2(StateExchangeFilterCase2 filterCase2) {
        this.filterCase2 = filterCase2;
    }

    public void setFilterCase3(StateExchangeFilterCase3 filterCase3) {
        this.filterCase3 = filterCase3;
    }

    public void setFilterCase4(StateExchangeFilterCase4 filterCase4) {
        this.filterCase4 = filterCase4;
    }

    public void setFilterCase5(StateExchangeFilterCase5 filterCase5) {
        this.filterCase5 = filterCase5;
    }

    public void setFilterCase6(StateExchangeFilterCase6 filterCase6) {
        this.filterCase6 = filterCase6;
    }

    public void setFilterCase7(StateExchangeFilterCase7 filterCase7) {
        this.filterCase7 = filterCase7;
    }
   
}
