/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.ArticleEntity;
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
public class ArticleManager extends GenericManager<ArticleEntity> {
   
   @PostConstruct
   public void doIndex() throws InterruptedException{
   Session session = sf.openSession();
   FullTextSession fullTextSession = Search.getFullTextSession(session);
   fullTextSession.createIndexer(ArticleEntity.class).startAndWait();       
   fullTextSession.close();
   super.setEntityClass(ArticleEntity.class);
   } 
   
    public List<ArticleEntity> findPageDeleted(int start, int page, Boolean deleted){
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ArticleEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ArticleEntity> list = q.list();
            return list;
        } catch (Exception e) {
            return new LinkedList<ArticleEntity>();
        }
    }
    
       public int getCountDeleted(Boolean deleted){
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ArticleEntity as e WHERE e.deleted = :deleted ");
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            return 0;
        }
    }
       
    public List<ArticleEntity> findPageDeleteByUserIds(int start, int page, Boolean deleted, List<Long> userIds){
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ArticleEntity as e WHERE e.deleted = :deleted AND e.author.id IN :(userIds)");
            q.setParameter("deleted", deleted);
            q.setParameterList("userIds", userIds);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ArticleEntity> list = q.list();
            return list;
        } catch (Exception e) {
            return new LinkedList<ArticleEntity>();
        }
    }   
    
    public int getCountDeletedUserIds(Boolean deleted,List<Long> userIds){
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ArticleEntity as e WHERE e.deleted = :deleted AND e.author.id IN :(userIds)");
            q.setParameter("deleted", deleted);
            q.setParameterList("userIds", userIds);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public List<ArticleEntity> findFullText(Boolean deleted, String keywords){
        Session s = sf.getCurrentSession();     
        Transaction txFull = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(s);
            txFull = fullTextSession.beginTransaction();          
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(ArticleEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("title","text","author.email","author.googleName").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, ArticleEntity.class);
            List<ArticleEntity> list = q.list();
            List<ArticleEntity> filtered = new LinkedList<ArticleEntity>();
            for(ArticleEntity a: list){
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
            return new LinkedList<ArticleEntity>();
        }
    }
}
