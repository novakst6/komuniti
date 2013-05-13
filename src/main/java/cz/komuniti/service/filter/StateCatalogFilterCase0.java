/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.filter;


import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.model.form.CatalogFilterForm;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service(value="StateCatalogFilterCase0")
public class StateCatalogFilterCase0 extends StateCatalogFilter {

    @Override
    public List<ItemEntity> getResult(CatalogFilterForm filter, Integer page) {
        return itemManager.findPageActivate(pager.getResult()[0], pager.getResult()[1], Boolean.TRUE);
    }

    @Override
    public int getCount(CatalogFilterForm filter) {
       return itemManager.getCountActivate(Boolean.TRUE);
    }

    @Override
    public List<ItemEntity> getAll(CatalogFilterForm filter) {
        return itemManager.findAllActivate(Boolean.TRUE);
    }
    
}
