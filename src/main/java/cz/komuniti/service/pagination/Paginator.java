/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.pagination;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service(value="Paginator")
@Scope(value="session",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Paginator implements Serializable {
    
    private static final long serialVersionUID = 4L;
    
    private Integer page = 1;
    private Integer sizePage = 30;
    private Integer max = 0;

    public Integer[] getResult() {
        if(((page - 1)*sizePage) >= max || page < 1) {
            return new Integer[] {0,0};
        }
        if(page*sizePage > max){
            return new Integer[] {((page - 1)*sizePage),max};
        } else {
            return new Integer[] {((page - 1)*sizePage),((page - 1)*sizePage) + sizePage};
        }
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSizePage(int sizePage) {
        this.sizePage = sizePage;
    }

    public int getSizePage() {
        return sizePage;
    }
    
    public int getMax(){
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
    
    public List<Integer> getNavPages(){
        List<Integer> l = new LinkedList<Integer>();
        int count = 1;
        for (int i = 0; i < max; i += sizePage) {
          l.add(count++);
        }
        return l;
    }
    
}
