/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.ItemEntity;
import cz.komuniti.service.util.ItemComparatorById;
import java.util.*;
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
public class ItemManager extends GenericManager<ItemEntity> {

    @PostConstruct
    public void doIndex() throws InterruptedException {
        Session session = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer(ItemEntity.class).startAndWait();
        fullTextSession.close();

        super.setEntityClass(ItemEntity.class);
    }

    public List<ItemEntity> findPageActivateDeleted(int start, int page, Boolean active, Boolean deleted) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e WHERE e.active = :active AND e.deleted = :deleted");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public List<ItemEntity> findAllActivateDeleted(Boolean active, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM ItemEntity as u WHERE u.active = :active AND e.deleted = :deleted");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountActivateDeleted(Boolean active, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ItemEntity as e WHERE e.active = :active AND e.deleted = :deleted");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageActivate(int start, int page, Boolean active) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e WHERE e.active = :active");
            q.setParameter("active", active);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public List<ItemEntity> findAllActivate(Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM ItemEntity as u WHERE u.active = :active");
            q.setParameter("active", active);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountActivate(Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ItemEntity as e WHERE e.active = :active");
            q.setParameter("active", active);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageDeleted(int start, int page, Boolean deleted) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public List<ItemEntity> findAllDeleted(Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM ItemEntity as u WHERE u.deleted = :deleted");
            q.setParameter("deleted", deleted);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountDeleted(Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ItemEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageTags(int start, int page, List<Long> tags) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e left join e.tags as t WHERE t.id IN (:tags)");
            q.setParameterList("tags", tags);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountTags(List<Long> tags) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e left join e.tags as t WHERE t.id IN (:tags)");
            q.setParameterList("tags", tags);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageTagsActive(int start, int page, List<Long> tags, Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e join e.tags as t WHERE t.id IN (:tags) AND e.active = :active");
            q.setParameterList("tags", tags);
            q.setParameter("active", active);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new ItemComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountTagsActive(List<Long> tags, Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e join e.tags as t WHERE t.id IN (:tags) AND e.active = :active");
            q.setParameterList("tags", tags);
            q.setParameter("active", active);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageTagsCreatedByUser(int start, int page, List<Long> tags, Boolean user) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e join e.tags as t WHERE t.id IN (:tags) AND e.createdByUser = :user");
            q.setParameterList("tags", tags);
            q.setParameter("user", user);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new ItemComparatorById());
            //pager
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountTagsCreatedByUser(List<Long> tags, Boolean user) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e join e.tags as t WHERE t.id IN (:tags) AND e.createdByUser = :user");
            q.setParameterList("tags", tags);
            q.setParameter("user", user);
            List<ItemEntity> list = q.list();
            Set<ItemEntity> set = new HashSet<ItemEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findPageCreatedByUser(int start, int page, Boolean user) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e WHERE e.createdByUser = :user");
            q.setParameter("user", user);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }

    public int getCountCreatedByUser(Boolean user) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM ItemEntity as e WHERE e.createdByUser = :user");
            q.setParameter("user", user);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<ItemEntity> findTagsActive(List<Long> tags, Boolean active) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM ItemEntity as e join e.tags as t WHERE t.id IN (:tags) AND e.active = :active");
            q.setParameterList("tags", tags);
            q.setParameter("active", active);
            List<ItemEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }
    
     public List<ItemEntity> findFullText(Boolean deleted, Boolean active, String keywords) {
        Session s = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(s);
        Transaction tx = null;
        try {
            tx = fullTextSession.beginTransaction();
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(ItemEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("text", "tags.name", "name", "description").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, ItemEntity.class);
            List<ItemEntity> list = q.list();
            //criteria
            List<ItemEntity> filter = new LinkedList<ItemEntity>();
            for (ItemEntity i : list) {
                if (!i.getDeleted()) {
                    if (i.getActive()) {
                        filter.add(i);
                    }
                }
            }
            tx.commit();
            return list;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<ItemEntity>();
        }
    }
     
    public ItemEntity findbyFileId(Boolean deleted, Long fileId) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM ItemEntity as u JOIN u.files as f WHERE u.deleted = :deleted AND f.id = :fileId");
            q.setParameter("deleted", deleted);
            q.setParameter("fileId", fileId);
            List<ItemEntity> list = q.list();
            if(list.isEmpty()){
                return null;
            }           
            return list.get(0);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return null;
        }
    }
}
