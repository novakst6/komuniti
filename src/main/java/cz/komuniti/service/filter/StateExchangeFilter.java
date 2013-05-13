/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;


import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.form.ExchangeFilterForm;
import cz.komuniti.service.manager.OfferManager;
import cz.komuniti.service.pagination.Paginator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author novakst6
 */

public abstract class StateExchangeFilter {
    
    protected OfferManager offerManager; 
    protected Paginator pager;
    
    public abstract List<OfferEntity> getResult(ExchangeFilterForm filter, Integer page);
    public abstract int getCount(ExchangeFilterForm filter);

    public @Autowired void setOfferManager(OfferManager offerManager) {
        this.offerManager = offerManager;
    }

    public @Autowired void setPager(Paginator pager) {
        this.pager = pager;
    }

}
