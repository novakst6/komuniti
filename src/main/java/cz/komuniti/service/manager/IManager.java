/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author novakst6
 */
public interface IManager<E> extends Serializable {
    public boolean add(E entity);
    public boolean edit(E entity);
    public boolean delete(E entity);
    public E findById(Long id);
    public List<E> findAll();
    
    public List<E> findByListId(List<Long> ids);
    public boolean delete(List<Long> ids);
    
    public List<E> findPage(int start, int page);
    public int getCount();
}
