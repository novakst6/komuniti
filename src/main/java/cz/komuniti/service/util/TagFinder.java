/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.model.entity.TagEntity;
import cz.komuniti.service.manager.TagManager;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service(value="TagFinder")
public class TagFinder {
    
    private TagManager tagManager;
    
    private LinkedList<TagNode> getNodes(TagEntity r, Integer depth){
        Set<TagEntity> list = tagManager.findChilderns(r);//r.getChilderns();
        if(list.isEmpty()){ return null;}
        Iterator<TagEntity> i = list.iterator();
        LinkedList<TagNode> nodes = new LinkedList<TagNode>();
        while(i.hasNext()){
            TagEntity temp = i.next();
            TagNode n = new TagNode();
            n.setIndex(depth);
            n.setTag(temp);
            nodes.add(n);
            LinkedList<TagNode> n1 = getNodes(temp, depth+1);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        return nodes;
    }
    
        public LinkedList<LinkedList<TagNode>> getTags(){
        LinkedList<LinkedList<TagNode>>  regions = new LinkedList<LinkedList<TagNode>>();
        Integer depth = 0;
        List<TagEntity> roots = tagManager.findAllRoots();      
        for(TagEntity r: roots){
            LinkedList<TagNode> temp = new LinkedList<TagNode>();
            depth = 0;
            TagNode n = new TagNode();
            n.setTag(r);
            n.setIndex(depth);
            temp.add(n);
            LinkedList<TagNode> nodes = getNodes(r, depth+1);
            if(nodes != null){
                temp.addAll(nodes);
            }
            regions.add(temp);
        }
        
        return regions;
    }

    public LinkedList<TagEntity> getSubTags(TagEntity r){
        Set<TagEntity> list = tagManager.findChilderns(r);//r.getChilderns();
        if(list.isEmpty()){ return null;}
        Iterator<TagEntity> i = list.iterator();
        LinkedList<TagEntity> nodes = new LinkedList<TagEntity>();
        while(i.hasNext()){
            TagEntity temp = i.next();
            nodes.add(temp);
            LinkedList<TagEntity> n1 = getSubTags(temp);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        return nodes;
    } 
    
    public List<Long> getSubTagsId(TagEntity r){
        Set<TagEntity> list = tagManager.findChilderns(r);//r.getChilderns();
        if(list.isEmpty()){ 
                    List<Long> idsTag = new LinkedList<Long>();
                    idsTag.add(r.getId());
                    return idsTag;
        }
        Iterator<TagEntity> i = list.iterator();
        LinkedList<TagEntity> nodes = new LinkedList<TagEntity>();
        while(i.hasNext()){
            TagEntity temp = i.next();
            nodes.add(temp);
            LinkedList<TagEntity> n1 = getSubTags(temp);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        List<Long> ids = new LinkedList<Long>();
        ids.add(r.getId());
        for(TagEntity t: nodes)
        {
            ids.add(t.getId());
        }
        return ids;
    } 
        
    public @Autowired void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }
        
        
}
