/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.MessageEntity;
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
public class MessageManager extends GenericManager<MessageEntity> {

   @PostConstruct
   public void doIndex() throws InterruptedException{
   Session session = sf.openSession();
   FullTextSession fullTextSession = Search.getFullTextSession(session);
   fullTextSession.createIndexer(MessageEntity.class).startAndWait();       
   fullTextSession.close();
   super.setEntityClass(MessageEntity.class);
   }

    public List<MessageEntity> findInbox(String email, Boolean deleted, int firstResult, int maxResult) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM MessageEntity as e WHERE e.deleted = :deleted AND e.recipient.email = :email AND e.owner.email = :email ORDER BY e.sendDate DESC");
            q.setParameter("deleted", deleted);
            q.setParameter("email", email);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResult);
            List<MessageEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<MessageEntity>();
        }
    }

    public List<MessageEntity> findOutbox(String email, Boolean deleted, int firstResult, int maxResult) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM MessageEntity as e WHERE e.deleted = :deleted AND e.author.email = :email AND e.owner.email = :email ORDER BY e.sendDate DESC");
            q.setParameter("deleted", deleted);
            q.setParameter("email", email);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResult);
            List<MessageEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<MessageEntity>();
        }
    }

    public int getCountInbox(String email, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM MessageEntity as e WHERE e.deleted = :deleted AND e.recipient.email = :recipient AND e.owner.email = :recipient");
            q.setParameter("deleted", deleted);
            q.setParameter("recipient", email);
            List list = q.list();
            Long count = (Long) list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }

    }

    public int getCountOutbox(String email, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM MessageEntity as e WHERE e.deleted = :deleted AND e.author.email = :author AND e.owner.email = :author");
            q.setParameter("deleted", deleted);
            q.setParameter("author", email);
            List list = q.list();
            Long count = (Long) list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public int getCountInboxUnreaded(String email, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM MessageEntity as e WHERE e.deleted = :deleted AND e.recipient.email = :recipient AND e.owner.email = :recipient AND e.readed = false");
            q.setParameter("deleted", deleted);
            q.setParameter("recipient", email);
            List list = q.list();
            Long count = (Long) list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }
    
    public int getCountOutboxUnreaded(String email, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM MessageEntity as e WHERE e.deleted = :deleted AND e.owner.email = :recipient AND e.author.email = :recipient AND e.readed = false");
            q.setParameter("deleted", deleted);
            q.setParameter("recipient", email);
            List list = q.list();
            Long count = (Long) list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }
    
    public List<MessageEntity> findFullText(String keywords){
        Session s = sf.getCurrentSession();     
        Transaction txFull = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(s);
            txFull = fullTextSession.beginTransaction();          
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(MessageEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("subject","owner.googleName","owner.email").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, MessageEntity.class);
            List<MessageEntity> list = q.list();
            txFull.commit();
            return list;
        } catch (Exception e) {
            if(txFull != null){
                txFull.rollback();
            }
            return new LinkedList<MessageEntity>();
        }
    }
}
