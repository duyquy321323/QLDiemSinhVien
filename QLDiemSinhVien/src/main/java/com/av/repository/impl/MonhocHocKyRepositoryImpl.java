package com.av.repository.impl;

import com.av.pojo.Monhoc;
import com.av.pojo.MonhocHocky;
import com.av.repository.MonhocHocKyRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class MonhocHocKyRepositoryImpl implements MonhocHocKyRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment Env;

    @Override
    public MonhocHocky findById(Integer idMonHocHocKy) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<MonhocHocky> q = b.createQuery(MonhocHocky.class);

        Root r = q.from(MonhocHocky.class);
        q.select(r);

        q.where(b.equal(r.get("idMonHocHocKy"), idMonHocHocKy));

        Query query = session.createQuery(q);
        try {
            MonhocHocky monhocHocky = (MonhocHocky) query.getSingleResult();
            return monhocHocky;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
