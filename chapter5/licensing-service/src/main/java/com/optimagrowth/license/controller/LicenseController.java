package com.optimagrowth.license.controller;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.service.LicenseService;
import com.optimagrowth.license.utils.UserContextHolder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
@Slf4j
public class LicenseController {

	@Autowired
	private LicenseService licenseService;


	@Autowired
	ServiceConfig config;
	
	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense( @PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		
		License license = new License();
		return ResponseEntity.ok(license.withComment(config.getProperty()));
	}

	@RequestMapping(value="/{licenseId}/{clientId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicenseOrganization( @PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId, @PathVariable("clientId") String clientId) {
		
		License license = new License();
		Organization org = licenseService.getOrganization(organizationId, clientId);
		license.setProductName(org.getContactName());
		return ResponseEntity.ok(license.withComment(config.getProperty()));
	}
	@RequestMapping(value="/",method = RequestMethod.GET)
	public List<License> getLicenses( @PathVariable("organizationId") String organizationId) throws TimeoutException {
		log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return licenseService.getLicensesByOrganization(organizationId);
	}
}
