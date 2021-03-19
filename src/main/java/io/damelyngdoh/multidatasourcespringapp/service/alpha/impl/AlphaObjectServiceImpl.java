package io.damelyngdoh.multidatasourcespringapp.service.alpha.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.damelyngdoh.multidatasourcespringapp.config.Constants;
import io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject;
import io.damelyngdoh.multidatasourcespringapp.repository.alpha.AlphaObjectRepository;
import io.damelyngdoh.multidatasourcespringapp.service.alpha.AlphaObjectService;

/**
 * Implementation of serivce interface.
 * 
 * @see AlphaObjectService
 * @author Dame Lyngdoh
 */
@Service
public class AlphaObjectServiceImpl implements AlphaObjectService {

    private final Logger log = LoggerFactory.getLogger(AlphaObjectServiceImpl.class);

    /**
     * Persistence interface.
     */
    @Autowired
     private AlphaObjectRepository repo;

    @PersistenceContext(unitName = Constants.ALPHA_PERSISTENCE_UNIT)
    private EntityManager entityManager;

    /**
     * Method implementation.
     * @return Returns random AlphaObject.
     * @see AlphaObjectService#createNewObject()
     */
    @Override
    @Transactional(transactionManager = "alphaTransactionManager")
    public AlphaObject createNewObject() {
        AlphaObject object = new AlphaObject(null, null);
        object.setAlphaData(RandomStringUtils.randomAlphabetic(10));
        object = this.repo.save(object);
        log.debug("Persisted new alpha object {}", object);
        return object;
    }

    /**
     * Method implementation.
     * @return List of all alpha objects.
     * @see EntityManager
     */
    @Override
    public List<AlphaObject> getObjects() {
        TypedQuery<AlphaObject> query = this.entityManager.createQuery("SELECT a from AlphaObject a", AlphaObject.class);
        return query.getResultList();
    }
    
}
