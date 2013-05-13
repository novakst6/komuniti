/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;

import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 *
 * @author novakst6
 */

@Scope(value="session",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ExchangeFilterForm {
    
    private List<Long> idsOfferTag;
    private List<Long> idsItemTag;
    private List<Long> idsRegions;

    public ExchangeFilterForm() {
        idsItemTag = new LinkedList<Long>();
        idsOfferTag = new LinkedList<Long>();
        idsRegions = new LinkedList<Long>();
    }

    
    
    public List<Long> getIdsItemTag() {
        return idsItemTag;
    }

    public void setIdsItemTag(List<Long> idsItemTag) {
        this.idsItemTag = idsItemTag;
    }

    public List<Long> getIdsOfferTag() {
        return idsOfferTag;
    }

    public void setIdsOfferTag(List<Long> idsOfferTag) {
        this.idsOfferTag = idsOfferTag;
    }

    public List<Long> getIdsRegions() {
        return idsRegions;
    }

    public void setIdsRegions(List<Long> idsRegions) {
        this.idsRegions = idsRegions;
    }
    
    public int getCase() {
        //0 - all null
        if(idsItemTag == null & idsOfferTag == null & idsRegions == null){
            return 0;
        } else
        //1 - offer tags
        if(idsItemTag == null & !(idsOfferTag == null) & idsRegions == null){
            return 1;
        } else
        //2 - item tag
        if(!(idsItemTag == null) & idsOfferTag == null & idsRegions == null){
            return 2;
        } else
        //3 - region
        if(idsItemTag == null & idsOfferTag == null & !(idsRegions == null)){
            return 3;
        } else
        //4 - offer tags, item tags
        if(!(idsItemTag == null) & !(idsOfferTag == null) & idsRegions == null){
            return 4;
        } else
        //5 - item tags, region
        if(!(idsItemTag == null) & idsOfferTag == null & !(idsRegions == null)){
            return 5;
        } else
        //6 - region, offer tags
        if(idsItemTag == null & !(idsOfferTag == null) & !(idsRegions == null)){
            return 6;
        } else
        //7 - offer tags, item tag, region
        if(!(idsItemTag == null) & !(idsOfferTag == null) & !(idsRegions == null)){
            if(idsItemTag.isEmpty() & idsOfferTag.isEmpty() & idsRegions.isEmpty()){
                return 0;
            } else {
                return 7;
            }
        } else {
            return 0;
        }
    }
}
