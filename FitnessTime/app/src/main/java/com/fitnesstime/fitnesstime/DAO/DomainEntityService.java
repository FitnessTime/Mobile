package com.fitnesstime.fitnesstime.DAO;

import com.fitnesstime.fitnesstime.Dominio.DomainEntity;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by jskalic on 22/04/2016.
 */
public abstract class DomainEntityService<T extends DomainEntity, F extends AbstractDao<T, Long>> {

    protected F dao;
    protected DaoSession daoSession;

    protected DomainEntityService() {
        this.daoSession = SessionManager.getInstance().getSession();
        this.dao = getDAO();
    }

    protected abstract F getDAO();

    public void insert(T domain) {
        this.dao.insert(domain);
    }

    public List<T> getListAll() {
        return this.dao.queryBuilder().list();
    }
}
