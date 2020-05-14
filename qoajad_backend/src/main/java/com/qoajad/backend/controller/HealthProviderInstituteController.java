package com.qoajad.backend.controller;

import com.qoajad.backend.model.external.health.HealthProviderInstitute;
import com.qoajad.backend.service.external.health.HealthProviderInstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
public class HealthProviderInstituteController {

    private final HealthProviderInstituteService hpiService;

    @Autowired
    public HealthProviderInstituteController(@Qualifier("defaultHealthProviderInstituteService")
                                                 final HealthProviderInstituteService hpiService) {
        this.hpiService = hpiService;
    }

    @RequestMapping(value = "/hpi/list_all", method = RequestMethod.GET)
    public ResponseEntity<List<HealthProviderInstitute>> retrieveAll() {
        ResponseEntity<List<HealthProviderInstitute>> response;
        try {
            response = new ResponseEntity<>(hpiService.retrieveAll(), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return response;
    }
}
