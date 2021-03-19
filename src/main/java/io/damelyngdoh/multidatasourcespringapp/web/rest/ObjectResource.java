package io.damelyngdoh.multidatasourcespringapp.web.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.damelyngdoh.multidatasourcespringapp.domain.alpha.AlphaObject;
import io.damelyngdoh.multidatasourcespringapp.domain.beta.BetaObject;
import io.damelyngdoh.multidatasourcespringapp.service.alpha.AlphaObjectService;
import io.damelyngdoh.multidatasourcespringapp.service.beta.BetaObjectService;
import lombok.AllArgsConstructor;

/**
 * REST API interface for resources.
 * 
 * @author Dame Lyngdoh
 */
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class ObjectResource {
    
    /**
     * Service for handling Alpha object related operations.
     */
    private final AlphaObjectService alphaService;

    /**
     * Service for handling Beta object related operations.
     */
    private final BetaObjectService betaService;

    /**
     * REST API generating an alpha object.
     * @return
     */
    @GetMapping("alpha")
    public ResponseEntity<AlphaObject> getNewAlphaObject() {
        AlphaObject obj = this.alphaService.createNewObject();
        return ResponseEntity.ok().body(obj);
    }

    /**
     * REST API generating a beta object.
     * @return
     */
    @GetMapping("beta")
    public ResponseEntity<BetaObject> getNewBetaObject() {
        BetaObject obj = this.betaService.createNewObject();
        return ResponseEntity.ok().body(obj);
    }

    /**
     * REST API to get all AlphaObjects
     * @return Returns list containing all alpha objects.
     */
    @GetMapping("alpha/entitymanager")
    public ResponseEntity<List<AlphaObject>> getAlphaObjects() {
        List<AlphaObject> objects = this.alphaService.getObjects();
        return ResponseEntity.ok().body(objects);
    }
}
