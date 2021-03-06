package com.itant.autoclick.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.itant.autoclick.model.GuanKaPoint;
import com.itant.autoclick.model.UserInfo;

import com.itant.autoclick.dao.GuanKaPointDao;
import com.itant.autoclick.dao.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig guanKaPointDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final GuanKaPointDao guanKaPointDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        guanKaPointDaoConfig = daoConfigMap.get(GuanKaPointDao.class).clone();
        guanKaPointDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        guanKaPointDao = new GuanKaPointDao(guanKaPointDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(GuanKaPoint.class, guanKaPointDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        guanKaPointDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
    }

    public GuanKaPointDao getGuanKaPointDao() {
        return guanKaPointDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}
