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
//ALL
@Service("StateExchangeFilterCase0")
public class StateExchangeFilterCase0 extends StateExchangeFilter{
    
    public List<OfferEntity> getResult(ExchangeFilterForm filter, Integer page) {
        return offerManager.findPageActivateDeleted(pager.getResult()[0], pager.getResult()[1], Boolean.TRUE,Boolean.FALSE);
    }

    public int getCount(ExchangeFilterForm filter) {
        return offerManager.getCountActivateDeleted(Boolean.TRUE,Boolean.FALSE);
    }

    
}
