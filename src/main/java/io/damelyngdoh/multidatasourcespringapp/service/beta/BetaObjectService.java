package io.damelyngdoh.multidatasourcespringapp.service.beta;

import io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject;

/**
 * Service interface for {@link io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject Beta object} 
 * management.
 * 
 * @author Dame Lyngdoh
 */
public interface BetaObjectService {
    
    /**
     * Creates and persists a new 
     * {@link io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject Beta object} 
     * with a random string associated with it.
     * @return Returns a new 'random' BetaObject.
     */
    public BetaObject createNewObject();

}
