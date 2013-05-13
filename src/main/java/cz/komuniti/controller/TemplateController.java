/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.service.info.InfoMessages;
import cz.komuniti.service.mail.MailService;
import cz.komuniti.service.manager.*;
import cz.komuniti.service.pagination.Paginator;
import cz.komuniti.service.util.PasswordGenerator;
import cz.komuniti.service.util.RegionFinder;
import cz.komuniti.service.util.TagFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 *
 * @author novakst6
 */

public class TemplateController {
    @Autowired
    private UserManager um;
    @Autowired
    private UserRoleManager urm;
    @Autowired
    private RegionManager rm;
    @Autowired
    private InfoMessages im;
    @Autowired
    private ArticleManager am;
    @Autowired
    private CenterManager cm;
    @Autowired
    private CommentManager com;
    @Autowired
    private FileManager fm;
    @Autowired
    private HelpManager hm;
    @Autowired
    private ItemManager itm;
    @Autowired
    private MessageManager mm;
    @Autowired
    private OfferManager om;
    @Autowired
    private TagManager tm;
    @Autowired
    private OfferTagManager otm;
    @Autowired
    private StandardPasswordEncoder spe;
    @Autowired
    private Paginator pag;
    @Autowired
    private RegionFinder rf;
    @Autowired
    private PasswordGenerator pg;
    @Autowired
    private MailService ms;
    @Autowired
    private TagFinder tf;
    @Autowired
    private AppConfigManager acm;
    
    public boolean isParameterNull(Object o){
        return o == null;
    }
    
    // GETTERS

    public InfoMessages getInfoMessages() {
        return im;
    }

    public RegionManager getRegionManager() {
        return rm;
    }

    public UserManager getUserManager() {
        return um;
    }

    public UserRoleManager getUserRoleManager() {
        return urm;
    }

    public ArticleManager getArticleManager(){
        return am;
    }

    public CenterManager getCenterManager(){
        return cm;
    }
    
    public CommentManager getCommentManager(){
        return com;
    }
    
    public FileManager getFileManager(){
        return fm;
    }
    
    public HelpManager getHelpManager(){
        return hm;
    }
    
    public ItemManager getItemManager(){
        return itm;
    }
    
    public MessageManager getMessageManager(){
        return mm;
    }
    
    public OfferManager getOfferManager(){
        return om;
    }
    
    public TagManager getTagManager(){
        return tm;
    }
    
    public OfferTagManager getOfferTagManager(){
        return otm;
    }
    
    public StandardPasswordEncoder getStandardPassworgEncoder(){
        return spe;
    }
    
    public Paginator getPaginator(){
        return pag;
    }
    
    public RegionFinder getRegionFinder(){
        return rf;
    }
    
    public PasswordGenerator getPasswordGenerator(){
        return pg;
    }
    
    public MailService getMailService(){
        return ms;
    }
    
    public TagFinder getTagFinder(){
        return tf;
    }
    
    public AppConfigManager getAppConfManager(){
        return acm;
    }
    
    //SETTERS
    public void setAm(ArticleManager am) {
        this.am = am;
    }
        
    public void setRm(RegionManager rm) {
        this.rm = rm;
    }

    public void setUm(UserManager um) {
        this.um = um;
    }

    public void setUrm(UserRoleManager urm) {
        this.urm = urm;
    }

    public void setIm(InfoMessages im) {
        this.im = im;
    }

    public void setCm(CenterManager cm) {
        this.cm = cm;
    }

    public void setCom(CommentManager com) {
        this.com = com;
    }

    public void setFm(FileManager fm) {
        this.fm = fm;
    }

    public void setHm(HelpManager hm) {
        this.hm = hm;
    }

    public void setItm(ItemManager itm) {
        this.itm = itm;
    }

    public void setMm(MessageManager mm) {
        this.mm = mm;
    }

    public void setOm(OfferManager om) {
        this.om = om;
    }

    public void setOtm(OfferTagManager otm) {
        this.otm = otm;
    }

    public void setTm(TagManager tm) {
        this.tm = tm;
    }

    public void setSpe(StandardPasswordEncoder spe) {
        this.spe = spe;
    }

    public void setPag(Paginator pag) {
        this.pag = pag;
    }

    public void setRf(RegionFinder rf) {
        this.rf = rf;
    }

    public void setPg(PasswordGenerator pg) {
        this.pg = pg;
    }

    public void setMs(MailService ms) {
        this.ms = ms;
    }

    public void setTf(TagFinder tf) {
        this.tf = tf;
    }

    public void setAcm(AppConfigManager acm) {
        this.acm = acm;
    }
    
    
}
