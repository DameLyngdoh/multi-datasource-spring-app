package io.damelyngdoh.multidatasourcespringapp.service.alpha;

import java.util.List;

import javax.persistence.EntityManager;

import io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject;

/**
 * Service interface for {@link io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject Alpha object} 
 * management.
 * 
 * @author Dame Lyngdoh
 */
public interface AlphaObjectService {
    
    /**
     * Creates and persists a new 
     * {@link io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject Alpha object} 
     * with a random string associated with it.
     * @return Returns a new 'random' AlphaObject.
     */
    public AlphaObject createNewObject();

    /**
     * Gets a list of all AlphaObjects using an EntityManager.
     * @return List of all alpha objects.
     * @see EntityManager
     */
    public List<AlphaObject> getObjects();

}
