/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;


import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.model.form.CatalogFilterForm;
import cz.komuniti.service.manager.ItemManager;
import cz.komuniti.service.pagination.Paginator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author novakst6
 */
public abstract class StateCatalogFilter {
    
    protected ItemManager itemManager;
    protected Paginator pager;
    
    public abstract List<ItemEntity> getResult(CatalogFilterForm filter, Integer page);
    public abstract int getCount(CatalogFilterForm filter);
    public abstract List<ItemEntity> getAll(CatalogFilterForm filter);

    public @Autowired void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public @Autowired void setPager(Paginator pager) {
        this.pager = pager;
    }
    
    
}
