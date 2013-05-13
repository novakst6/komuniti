/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author novakst6
 */
public abstract class GenericManager<E> implements IManager<E> {

    @Autowired
    protected SessionFactory sf;
    private Class<? extends E> entityClass;

    public void setEntityClass(Class<? extends E> clazz) {
        entityClass = clazz;
    }

    protected Session getCurrentSession() {
        return sf.getCurrentSession();
    }

    public boolean add(E entity) {
        Session s = getCurrentSession();
        try {
            s.save(entity);
            s.flush(); ///flush manual
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR ADD> "+e.getMessage());
        }
        return false;
    }

    public boolean edit(E entity) {
        Session s = getCurrentSession();
        try {
            s.update(entity);
            s.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR EDIT> "+e.getMessage());
        }
        return false;
    }

    public E findById(Long id) {
        Session s = getCurrentSession();
        try {
            E load = (E) s.load(entityClass, id);
            s.flush();
            return load;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR FINDBYID> "+e.getMessage());
        }
        return null;
    }

    public boolean delete(E entity) {
        Session s = getCurrentSession();
        try {
            s.delete(entity);
            s.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR DELETE> "+e.getMessage());
        }
        return false;
    }

    public List<E> findAll() {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM " + entityClass.getName() + " as e");
            List<E> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR FINDALL> "+e.getMessage());
            return new LinkedList<E>();
        }
    }

    public List<E> findByListId(List<Long> ids) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM " + entityClass.getName() + " as e where e.id in (:ids)");
            q.setParameterList("ids", ids);
            List<E> list = q.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR FINDBYLISTID> "+e.getMessage());
            return new LinkedList<E>();
        }
    }

    public boolean delete(List<Long> ids) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM " + entityClass.getName() + " as e where e.id in (:ids)");
            List<E> list = q.list();
            try {
                for (E t : list) {
                    s.delete(t);
                }
            } catch (DataAccessException dae) {
                //ignore
            }
            return true;
        } catch (Exception e) {
            System.out.println("ERROR DELETEBYLISTID> "+e.getMessage());
            return false;
        }
    }

    public List<E> findPage(int start, int page) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM " + entityClass.getName() + " as e");
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<E> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR FINDPAGE> "+e.getMessage());
            return new LinkedList<E>();
        }
    }

    public int getCount() {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM " + entityClass.getName());
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR GETCOUNT> "+e.getMessage());
            return -1;
        }
    }

    public void refresh(E entity){
        Session s = getCurrentSession();
        try {
            s.refresh(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    public Long getNextId(){
//        Session s = getCurrentSession();
//        try {
//            Query q = s.createQuery("SELECT max(e.id) FROM "+entityClass.getName()+" AS e");
//            List<Long> list = q.list();
//            for(Long l: list){
//                return ++l;
//            }
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    
//    public boolean addNewSession(E entity) {
//        Session s = sf.openSession();
//        try {
//            s.save(entity);
//            s.flush(); ///flush manual
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("ERROR> "+e.getMessage());
//        }
//        return false;
//    }
    
    public void setSf(SessionFactory sf) {
        this.sf = sf;
    }
}
