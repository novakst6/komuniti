/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.OfferTagEntity;
import javax.annotation.PostConstruct;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service
public class OfferTagManager extends GenericManager<OfferTagEntity> {
   @PostConstruct
   public void doIndex() throws InterruptedException{
   Session session = sf.openSession();
   FullTextSession fullTextSession = Search.getFullTextSession(session);
   fullTextSession.createIndexer(OfferTagEntity.class).startAndWait();       
   fullTextSession.close();
   
   super.setEntityClass(OfferTagEntity.class);
   }
   
   
}
