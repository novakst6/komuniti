/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;


import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.entity.RegionEntity;
import cz.komuniti.model.form.ExchangeFilterForm;
import cz.komuniti.service.manager.RegionManager;
import cz.komuniti.service.util.RegionFinder;
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
//region, offer tags
@Service(value="StateExchangeFilterCase6")
public class StateExchangeFilterCase6 extends StateExchangeFilter {
    private RegionFinder regionFinder;
    private RegionManager regionManager;
    private List<Long> regions = new LinkedList<Long>(); 
    
    @Override
    public List<OfferEntity> getResult(ExchangeFilterForm filter, Integer page) {
        return offerManager.findPageActivateOfferTagRegion(pager.getResult()[0], pager.getResult()[1], Boolean.TRUE, filter.getIdsOfferTag(), regions);
    }

    @Override
    public int getCount(ExchangeFilterForm filter) {
        Set<Long> tagsSet = new HashSet<Long>();
        for(Long l: filter.getIdsRegions()){
            RegionEntity reg = regionManager.findById(l);
            List<Long> subTagsId = regionFinder.getSubRegionsId(reg);
            tagsSet.addAll(subTagsId);
        }
        regions.clear();
        regions.addAll(tagsSet);
        System.out.println("REGIONS "+regions);
        return offerManager.getCountActivateOfferTagRegion(Boolean.TRUE,filter.getIdsOfferTag(), regions);
    }

    public @Autowired void setRegionFinder(RegionFinder regionFinder) {
        this.regionFinder = regionFinder;
    }

    public @Autowired void setRegionManager(RegionManager regionManager) {
        this.regionManager = regionManager;
    }
}
