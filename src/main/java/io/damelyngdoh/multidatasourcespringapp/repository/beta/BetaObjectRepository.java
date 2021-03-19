package io.damelyngdoh.multidatasourcespringapp.repository.beta;

import org.springframework.data.jpa.repository.JpaRepository;

import io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject;

/**
 * Repository proxy for {@link io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject Beta object}.
 * 
 * @author Dame Lyngdoh
 */
public interface BetaObjectRepository extends JpaRepository<BetaObject,Long> {
    
}
