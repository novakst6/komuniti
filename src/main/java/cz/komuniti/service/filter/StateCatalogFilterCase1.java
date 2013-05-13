/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;

import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.model.entity.TagEntity;
import cz.komuniti.model.form.CatalogFilterForm;
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
@Service(value="StateCatalogFilterCase1")
public class StateCatalogFilterCase1 extends StateCatalogFilter{

    private TagManager tagManager;
    private TagFinder tagFinder;
    private List<Long> tags = new LinkedList<Long>();
    
    @Override
    public List<ItemEntity> getResult(CatalogFilterForm filter, Integer page) {
        return itemManager.findPageTagsActive(pager.getResult()[0], pager.getResult()[1], tags, Boolean.TRUE);
    }

    @Override
    public int getCount(CatalogFilterForm filter) {
        Set<Long> tagsSet = new HashSet<Long>();
        for(Long l: filter.getIdsItemTag()){
            TagEntity tag = tagManager.findById(l);
            List<Long> subTagsId = tagFinder.getSubTagsId(tag);
            tagsSet.addAll(subTagsId);
        }
        tags.clear();
        tags.addAll(tagsSet);
        return itemManager.getCountTagsActive(tags, Boolean.TRUE);
    }

    public @Autowired void setTagFinder(TagFinder tagFinder) {
        this.tagFinder = tagFinder;
    }

    public @Autowired void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }

    @Override
    public List<ItemEntity> getAll(CatalogFilterForm filter) {
        Set<Long> tagsSet = new HashSet<Long>();
        for(Long l: filter.getIdsItemTag()){
            TagEntity tag = tagManager.findById(l);
            List<Long> subTagsId = tagFinder.getSubTagsId(tag);
            tagsSet.addAll(subTagsId);
        }
        tags.clear();
        tags.addAll(tagsSet);
        return itemManager.findTagsActive(tags, Boolean.TRUE);
    }
    
    
    
}
