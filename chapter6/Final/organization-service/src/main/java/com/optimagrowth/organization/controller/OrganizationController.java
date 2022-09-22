package com.optimagrowth.organization.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;

@RestController
@RequestMapping(value="v1/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService service;

    @RolesAllowed({ "ADMIN", "USER" }) 
    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganization( @PathVariable("organizationId") String organizationId) {
    	Organization org = new Organization(); org.setContactName("somename");
        return ResponseEntity.ok(org);
    }
    
    @RolesAllowed("ADMIN")    
    @RequestMapping(value="/admin/{organizationId}",method = RequestMethod.GET)
   // @ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Organization> deleteLicense(@PathVariable("organizationId") String organizationId) {
		//
    	Organization org = new Organization(); org.setContactName("deleted somename");
        return ResponseEntity.ok(org);
	}

}
