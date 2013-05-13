/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.model.entity.OfferEntity;

/**
 *
 * @author novakst6
 */
public class OfferComparatorById extends EntityComparator<OfferEntity> {

    public int compare(OfferEntity t, OfferEntity t1) {
        return t.getId().compareTo(t1.getId());
    }
    
}
