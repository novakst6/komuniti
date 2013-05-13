/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.RegionEntity;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class RegionManager extends GenericManager<RegionEntity> {

    @PostConstruct
    public void doIndex() throws InterruptedException {
        Session session = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer(RegionEntity.class).startAndWait();
        fullTextSession.close();

        super.setEntityClass(RegionEntity.class);
    }

    public List<RegionEntity> findAllRoots() {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT r FROM RegionEntity as r WHERE r.parent is null");
            List list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR FINDALLROOTS> "+e.getMessage());
            return new LinkedList<RegionEntity>();
        }
    }
}
