/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;


import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.model.form.ExchangeFilterForm;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
//OFFER TAG
@Service(value="StateExchangeFilterCase1")
public class StateExchangeFilterCase1 extends StateExchangeFilter{

    @Override
    public List<OfferEntity> getResult(ExchangeFilterForm filter, Integer page) {
        return offerManager.findPageActivateOfferTag(pager.getResult()[0], pager.getResult()[1], Boolean.TRUE, filter.getIdsOfferTag());
    }

    @Override
    public int getCount(ExchangeFilterForm filter) {
        return offerManager.getCountActivateOfferTag(Boolean.TRUE, filter.getIdsOfferTag());
    }
    
}
