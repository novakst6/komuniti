/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.TagEntity;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Service;

/**
 *
 * @author ovakst6
 */
@Service
public class TagManager extends GenericManager<TagEntity> {

    @PostConstruct
    public void doIndex() throws InterruptedException {
        Session session = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer(TagEntity.class).startAndWait();
        fullTextSession.close();

        super.setEntityClass(TagEntity.class);
    }

    public List<TagEntity> findAllRoots() {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT r FROM TagEntity as r WHERE r.parent is null");
            List<TagEntity> list = q.list();
            return list;
        } catch (Exception e) {
            return new LinkedList<TagEntity>();
        }
    }

    public Set<TagEntity> findChilderns(TagEntity o) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT r FROM TagEntity as r WHERE r.id = :id");
            q.setParameter("id", o.getId());
            TagEntity t = (TagEntity) q.uniqueResult();
            Set<TagEntity> childerns = t.getChilderns();
            return childerns;
        } catch (Exception e) {
            return new HashSet<TagEntity>();
        }
    }
}
