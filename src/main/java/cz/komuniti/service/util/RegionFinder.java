/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;


import cz.komuniti.model.entity.RegionEntity;
import cz.komuniti.service.manager.RegionManager;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service(value="RegionFinder")
public class RegionFinder {
    
    private RegionManager regionManager;
    
    private LinkedList<RegionNode> getNodes(RegionEntity r, Integer depth){
        Set<RegionEntity> list = r.getChilderns();
        if(list.isEmpty()){ return null;}
        Iterator<RegionEntity> i = list.iterator();
        LinkedList<RegionNode> nodes = new LinkedList<RegionNode>();
        while(i.hasNext()){
            RegionEntity temp = i.next();
            RegionNode n = new RegionNode();
            n.setIndex(depth);
            n.setRegion(temp);
            nodes.add(n);
            LinkedList<RegionNode> n1 = getNodes(temp, depth+1);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        return nodes;
    }
    
    
    public LinkedList<LinkedList<RegionNode>> getRegions(){
        LinkedList<LinkedList<RegionNode>>  regions = new LinkedList<LinkedList<RegionNode>>();
        Integer depth = 0;
        List<RegionEntity> roots = regionManager.findAllRoots();       
        for(RegionEntity r: roots){
            LinkedList<RegionNode> temp = new LinkedList<RegionNode>();
            depth = 0;
            RegionNode n = new RegionNode();
            n.setRegion(r);
            n.setIndex(depth);
            temp.add(n);
            LinkedList<RegionNode> nodes = getNodes(r, depth+1);
            if(nodes != null){
                temp.addAll(nodes);
            }
            regions.add(temp);
        }
        
        return regions;
    }

    public LinkedList<RegionEntity> getSubRegions(RegionEntity r){
        Set<RegionEntity> list = r.getChilderns();
        if(list.isEmpty()){ return null;}
        Iterator<RegionEntity> i = list.iterator();
        LinkedList<RegionEntity> nodes = new LinkedList<RegionEntity>();
        while(i.hasNext()){
            RegionEntity temp = i.next();
            nodes.add(temp);
            LinkedList<RegionEntity> n1 = getSubRegions(temp);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        return nodes;
    } 
    
    public List<Long> getSubRegionsId(RegionEntity r){
        Set<RegionEntity> list = r.getChilderns();
        if(list.isEmpty()){ 
                    List<Long> idsReg = new LinkedList<Long>();
                    idsReg.add(r.getId());
                    return idsReg;
        }
        Iterator<RegionEntity> i = list.iterator();
        LinkedList<RegionEntity> nodes = new LinkedList<RegionEntity>();
        while(i.hasNext()){
            RegionEntity temp = i.next();
            nodes.add(temp);
            LinkedList<RegionEntity> n1 = getSubRegions(temp);
            if(n1 != null){
                nodes.addAll(n1);
            }
        }
        List<Long> ids = new LinkedList<Long>();
        ids.add(r.getId());
        for(RegionEntity t: nodes)
        {
            ids.add(t.getId());
        }
        return ids;
    } 
    
    public @Autowired void setRegionManager(RegionManager regionManager) {
        this.regionManager = regionManager;
    }
    
    
}
