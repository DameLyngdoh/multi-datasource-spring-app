package io.damelyngdoh.multidatasourcespringapp.repository.alpha;

import org.springframework.data.jpa.repository.JpaRepository;

import io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject;

/**
 * Repository proxy for {@link io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject Alpha object}.
 * 
 * @author Dame Lyngdoh
 */
public interface AlphaObjectRepository extends JpaRepository<AlphaObject,Long> {
    
}
