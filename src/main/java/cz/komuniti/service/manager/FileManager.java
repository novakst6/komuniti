/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.FileEntity;
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
public class FileManager extends GenericManager<FileEntity> {
    
   @PostConstruct
   public void doIndex() throws InterruptedException{
   Session session = sf.openSession();
   FullTextSession fullTextSession = Search.getFullTextSession(session);
   fullTextSession.createIndexer(FileEntity.class).startAndWait();       
   fullTextSession.close();
   
   super.setEntityClass(FileEntity.class);
   } 
    
   public List<FileEntity> findFullText(Boolean deleted, String keywords){
        Session s = sf.getCurrentSession();     
        Transaction txFull = null;
        try {
            FullTextSession fullTextSession = Search.getFullTextSession(s);
            txFull = fullTextSession.beginTransaction();          
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(FileEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("name","contentType","description").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, FileEntity.class);
            List<FileEntity> list = q.list();
            txFull.commit();
            return list;
        } catch (Exception e) {
            if(txFull != null){
                txFull.rollback();
            }
            return new LinkedList<FileEntity>();
        }
    }
}
