/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;

import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.entity.TagEntity;
import cz.komuniti.model.form.ExchangeFilterForm;
import cz.komuniti.service.manager.TagManager;
import cz.komuniti.service.util.TagFinder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
//ITEM TAG
@Service(value="StateExchangeFilterCase2")
public class StateExchangeFilterCase2 extends StateExchangeFilter {

    private TagFinder tagFinder;
    private TagManager tagManager;
    private List<Long> tags = new LinkedList<Long>();
    
    @Override
    public List<OfferEntity> getResult(ExchangeFilterForm filter, Integer page) {
        return offerManager.findPageActivateItemTag(pager.getResult()[0], pager.getResult()[1], Boolean.TRUE, tags);
    }

    @Override
    public int getCount(ExchangeFilterForm filter) {
        Set<Long> tagsSet = new HashSet<Long>();
        for(Long l: filter.getIdsItemTag()){
            TagEntity tag = tagManager.findById(l);
            List<Long> subTagsId = tagFinder.getSubTagsId(tag);
            tagsSet.addAll(subTagsId);
        }
        tags.clear();
        tags.addAll(tagsSet);
        System.out.println("TAGS "+tags);
        return offerManager.getCountActivateItemTag(Boolean.TRUE, tags);
    }

    public @Autowired void setTagFinder(TagFinder tagFinder) {
        this.tagFinder = tagFinder;
    }

    public @Autowired void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }
    
    
}
