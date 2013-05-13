/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.UserEntity;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class UserManager extends GenericManager<UserEntity> {

   @PostConstruct
   public void doIndex() throws InterruptedException{
   Session session = sf.openSession();
   FullTextSession fullTextSession = Search.getFullTextSession(session);
   fullTextSession.createIndexer(UserEntity.class).startAndWait();       
   fullTextSession.close();
   super.setEntityClass(UserEntity.class);
   }
   

    public UserEntity findByEmail(String email) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM UserEntity as u WHERE u.email = :email");
            q.setParameter("email", email);
            List<UserEntity> list = q.list();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR FIND BY EMAIL> " + e.getMessage());
            return null;
        }
    }

    public UserEntity findByEmailDeleted(String email, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM UserEntity as u WHERE u.email = :email and u.deleted = :deleted");
            q.setParameter("email", email);
            q.setParameter("deleted", deleted);
            List<UserEntity> list = q.list();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR FIND BY EMAIL DEL> " + e.getMessage());
            return null;
        }
    }

    public List<UserEntity> findPageUnactiveCenter(Long idCenter, int start, int page, Boolean deleted, Boolean active) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM UserEntity as e WHERE e.deleted = :deleted AND e.centrum.id = :idCenter AND e.active = :active");
            q.setParameter("deleted", deleted);
            q.setParameter("active", active);
            q.setParameter("idCenter", idCenter);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<UserEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR FIND PAGE UNACTIVE CENTER> " + e.getMessage());
            return new LinkedList<UserEntity>();
        }
    }

    public int getCountUnactiveCenter(Long idCenter, Boolean deleted, Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM UserEntity as e WHERE e.deleted = :deleted AND e.centrum.id = :idCenter AND e.active = :active");
            q.setParameter("deleted", deleted);
            q.setParameter("active", active);
            q.setParameter("idCenter", idCenter);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR COUNT UNACTIVE CENTER> " + e.getMessage());
            return 0;
        }
    }

    public List<UserEntity> findUsersInCenter(Long idCenter, Boolean deleted, Boolean active) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM UserEntity as e WHERE e.deleted = :deleted AND e.centrum.id = :idCenter AND e.active = :active");
            q.setParameter("deleted", deleted);
            q.setParameter("active", active);
            q.setParameter("idCenter", idCenter);
            List<UserEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR FIND USERS IN CENTER> " + e.getMessage());
            return new LinkedList<UserEntity>();
        }
    }
    
        public List<UserEntity> findPageDeleted(int start, int page, Boolean deleted){
        
        Session s = getCurrentSession();
        try {    
            Query q = s.createQuery("SELECT e FROM UserEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<UserEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR USERMANAGER FIND PAGE DELETED"+e.getMessage());
            return new LinkedList<UserEntity>();
        } 
    }
    
        public int getCountDeleted(Boolean deleted){
        Session s = getCurrentSession();
        try {            
            Query q = s.createQuery("SELECT count(*) FROM UserEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR USERMANAGER COUNT DELETE"+e.getMessage());
            return 0;
        }
    }
        
     public List<UserEntity> findFullText(Boolean deleted, String keywords){
        Session s = sf.getCurrentSession();     
        Transaction txFull = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(s);
            txFull = fullTextSession.beginTransaction();          
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(UserEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("email","googleName").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, UserEntity.class);
            List<UserEntity> list = q.list();
            List<UserEntity> filtered = new LinkedList<UserEntity>();
            for(UserEntity a: list){
                if(!a.getDeleted()){
                    filtered.add(a);
                }
            }
            txFull.commit();
            return filtered;
        } catch (Exception e) {
            if(txFull != null){
                txFull.rollback();
            }
            return new LinkedList<UserEntity>();
        }
    }
}
