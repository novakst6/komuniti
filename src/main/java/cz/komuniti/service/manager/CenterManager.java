/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.CenterEntity;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class CenterManager extends GenericManager<CenterEntity> {

    @PostConstruct
    public void init() {
        super.setEntityClass(CenterEntity.class);
    }

    public List<CenterEntity> findPageDeleted(int start, int page, Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT e FROM CenterEntity as e WHERE e.deleted = :deleted");
            q.setParameter("deleted", deleted);
            q.setMaxResults(page);
            q.setFirstResult(start);
            List<CenterEntity> list = q.list();
            return list;
        } catch (Exception e) {
            return new LinkedList<CenterEntity>();
        }
    }

    public int getCountDeleted(Boolean deleted) {
        Session s = getCurrentSession();
        try {
            Query q = s.createQuery("SELECT count(*) FROM CenterEntity as e WHERE e.deleted = :deleted ");
            q.setParameter("deleted", deleted);
            List<Long> list = q.list();
            Long count = list.get(0);
            return count.intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
