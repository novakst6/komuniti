/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;

import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 *
 * @author novakst6
 */

@Scope(value="session",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class CatalogFilterForm {
    
    private List<Long> idsItemTag;

    public CatalogFilterForm() {
        idsItemTag = new LinkedList<Long>();
    } 
    
    public List<Long> getIdsItemTag() {
        return idsItemTag;
    }

    public void setIdsItemTag(List<Long> idsItemTag) {
        this.idsItemTag = idsItemTag;
    }
    
    public int getCase(){
        if(idsItemTag == null){
            return 0;
        } else {
            if(idsItemTag.isEmpty()){
                return 0;
            } else {
                return 1;
            }
        }
    }
    
}
