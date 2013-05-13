/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.OfferEntity;
import cz.komuniti.service.util.OfferComparatorById;
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
public class OfferManager extends GenericManager<OfferEntity> {

    @PostConstruct
    public void doIndex() throws InterruptedException {
        Session session = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer(OfferEntity.class).startAndWait();
        fullTextSession.close();

        super.setEntityClass(OfferEntity.class);
    }
    
    public List<OfferEntity> findPageActivateDeleted(int start, int page, Boolean active, Boolean deleted) {

        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e WHERE e.active = :active AND e.deleted = :deleted");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public List<OfferEntity> findAllActivateDeleted(Boolean active, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM OfferEntity as u WHERE u.active = :active AND e.deleted = :deleted");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateDeleted(Boolean active, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM OfferEntity as e WHERE e.active = :active AND e.deleted = :deleted");
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

    public List<OfferEntity> findPageActivate(int start, int page, Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e WHERE e.active = :active");
            q.setParameter("active", active);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public List<OfferEntity> findAllActivate(Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM OfferEntity as u WHERE u.active = :active");
            q.setParameter("active", active);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivate(Boolean active) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM OfferEntity as e WHERE e.active = :active");
            q.setParameter("active", active);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageDeleted(int start, int page, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public List<OfferEntity> findAllDeleted(Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM OfferEntity as u WHERE u.deleted = :deleted");
            q.setParameter("deleted", deleted);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountDeleted(Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM OfferEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateOfferTagItemTagRegion(int start, int page, Boolean active, List<Long> idTag, List<Long> idOfferTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t join e.item.tags as it WHERE e.active = :active AND t.id IN (:idOfferTag) AND it.id IN (:idTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idOfferTag", idOfferTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);

        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateOfferTagItemTagRegion(Boolean active, List<Long> idTag, List<Long> idOfferTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t join e.item.tags as it WHERE e.active = :active AND t.id IN (:idOfferTag) AND it.id IN (:idTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idOfferTag", idOfferTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);

            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateOfferTagItemTag(int start, int page, Boolean active, List<Long> idTag, List<Long> idOfferTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t join e.item.tags as it WHERE e.active = :active AND t.id IN (:idOfferTag) AND it.id IN (:idTag)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idOfferTag", idOfferTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateOfferTagItemTag(Boolean active, List<Long> idTag, List<Long> idOfferTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t join e.item.tags as it WHERE e.active = :active AND t.id IN (:idOfferTag) AND it.id IN (:idTag)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idOfferTag", idOfferTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateOfferTag(int start, int page, Boolean active, List<Long> idOfferTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t WHERE e.active = :active AND t.id IN (:idOfferTag)");
            q.setParameter("active", active);
            q.setParameterList("idOfferTag", idOfferTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateOfferTag(Boolean active, List<Long> idOfferTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t WHERE e.active = :active AND t.id IN (:idOfferTag)");
            q.setParameter("active", active);
            q.setParameterList("idOfferTag", idOfferTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateItemTag(int start, int page, Boolean active, List<Long> idTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND it.id IN (:idTag)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }

    }

    public int getCountActivateItemTag(Boolean active, List<Long> idTag) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND it.id IN (:idTag)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateRegion(int start, int page, Boolean active, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateRegion(Boolean active, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateOfferTagRegion(int start, int page, Boolean active, List<Long> idOfferTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t WHERE e.active = :active AND t.id IN (:idOfferTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idOfferTag", idOfferTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateOfferTagRegion(Boolean active, List<Long> idOfferTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.tags as t WHERE e.active = :active AND t.id IN (:idOfferTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idOfferTag", idOfferTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findPageActivateItemTagRegion(int start, int page, Boolean active, List<Long> idTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND it.id IN (:idTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            list.clear();
            list.addAll(set);
            Collections.sort(list, new OfferComparatorById());
            return list.subList(start, page);
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public int getCountActivateItemTagRegion(Boolean active, List<Long> idTag, List<Long> idRegion) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e join e.item.tags as it WHERE e.active = :active AND it.id IN (:idTag) AND e.author.region.id IN (:idRegion)");
            q.setParameter("active", active);
            q.setParameterList("idTag", idTag);
            q.setParameterList("idRegion", idRegion);
            List<OfferEntity> list = q.list();
            Set<OfferEntity> set = new HashSet<OfferEntity>();
            set.addAll(list);
            return set.size();
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return 0;
        }
    }

    public List<OfferEntity> findOffersByItem(Boolean active, Long idItem) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM OfferEntity as e WHERE e.active = :active AND e.item.id = :idItem");
            q.setParameter("active", active);
            q.setParameter("idItem", idItem);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }

    public List<OfferEntity> findFullText(Boolean deleted, Boolean active, String keywords) {
        Session s = sf.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(s);
        Transaction tx = null;
        try {
            tx = fullTextSession.beginTransaction();
            QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(OfferEntity.class).get();
            org.apache.lucene.search.Query query = qb.keyword().onFields("title", "text", "author.email", "author.region.name", "author.googleName", "tags.name", "item.name", "item.text", "item.description", "item.tags.name").matching(keywords).createQuery();
            Query q = fullTextSession.createFullTextQuery(query, OfferEntity.class);
            List<OfferEntity> list = q.list();
            //criteria
            List<OfferEntity> filter = new LinkedList<OfferEntity>();
            for (OfferEntity o : list) {
                if (!o.getDeleted()) {
                    if (o.getActive()) {
                        filter.add(o);
                    }
                }
            }
            tx.commit();
            return list;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return new LinkedList<OfferEntity>();
        }
    }
    
        public List<OfferEntity> findAllActivateDeletedbyUserId(Boolean active, Boolean deleted, Long userId) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM OfferEntity as u WHERE u.active = :active AND u.deleted = :deleted AND u.author.id = :userId");
            q.setParameter("active", active);
            q.setParameter("deleted", deleted);
            q.setParameter("userId", userId);
            List<OfferEntity> list = q.list();
            return list;
        } catch (Exception e) {
            System.out.println("ERROR> "+e.getMessage());
            return new LinkedList<OfferEntity>();
        }
    }
        
    public OfferEntity findByFileId(Boolean deleted, Long fileId) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT u FROM OfferEntity as u JOIN u.files as f WHERE u.deleted = :deleted AND f.id = :fileId");
            q.setParameter("deleted", deleted);
            q.setParameter("fileId", fileId);
            List<OfferEntity> list = q.list();
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
