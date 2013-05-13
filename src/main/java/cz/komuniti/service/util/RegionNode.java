/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;


import cz.komuniti.model.entity.RegionEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author novakst6
 */
@Component
public class RegionNode {
        private Integer index;
        private RegionEntity region;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }
        
        
}
