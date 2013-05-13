/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.model.entity.ItemEntity;

/**
 *
 * @author novakst6
 */
public class ItemComparatorById extends EntityComparator<ItemEntity> {

    public int compare(ItemEntity t, ItemEntity t1) {
        return t.getId().compareTo(t1.getId());
    }
    
}
