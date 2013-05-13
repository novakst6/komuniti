/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import cz.komuniti.model.entity.ItemEntity;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 *
 * @author novakst6
 */
@Scope(value="session",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ItemUnionHolder {
    
    private Integer page = 1;
    private Map<Long,ItemEntity> items;

    public ItemUnionHolder() {
        items = new HashMap<Long, ItemEntity>();
    }

    public Map<Long,ItemEntity> getItems() {
        return items;
    }

    public void setItems(Map<Long,ItemEntity> items) {
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    
}
