package io.damelyngdoh.multidatasourcespringapp.service.beta.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject;
import io.damelyngdoh.multidatasourcespringapp.repository.beta.BetaObjectRepository;
import io.damelyngdoh.multidatasourcespringapp.service.beta.BetaObjectService;
import lombok.AllArgsConstructor;

/**
 * Implementation of serivce interface.
 * 
 * @see BetaObjectService
 * @author Dame Lyngdoh
 */
@Service
@AllArgsConstructor
public class BetaObjectServiceImpl implements BetaObjectService {

    /**
     * Persistence interface.
     */
    private final BetaObjectRepository repo;

    /**
     * Method implementation.
     * @return Returns random BetaObject.
     * @see BetaObjectService#createNewObject()
     */
    @Override
    @Transactional(transactionManager = "betaTransactionManager")
    public BetaObject createNewObject() {
        BetaObject object = new BetaObject();
        object.setBetaData(RandomStringUtils.randomAlphabetic(10));
        return this.repo.save(object);
    }
    
}
