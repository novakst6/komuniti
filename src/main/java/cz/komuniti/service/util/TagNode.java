/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.model.entity.TagEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author novaks6
 */

@Component
public class TagNode {
    private Integer index;
    private TagEntity tag;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }
    
    
}
