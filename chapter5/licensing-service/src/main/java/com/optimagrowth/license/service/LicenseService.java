package com.optimagrowth.license.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import com.optimagrowth.license.utils.UserContextHolder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class LicenseService {

	@Autowired
	MessageSource messages;

	
	@Autowired
	ServiceConfig config;

	@Autowired
	OrganizationDiscoveryClient discoveryClient;
	
	@Autowired
	OrganizationFeignClient feignClient;
	
	@Autowired
	OrganizationRestTemplateClient restTemplateClient;
	
	@Cacheable(value = "licenceCache", key = "#licenseId")
	public License getLicense(String licenseId) {
		log.info("Getting license {}", licenseId);
		License license = new License();
		return license.withComment(config.getProperty());
	}
	
	@Cacheable(value = "organizationCache")
	public Organization getOrganization(String organizationId, String clientId) {
		log.info("Getting license Organization {} with client {}", organizationId, clientId);
		Organization org = null;
		switch (clientId) {
		
		case "feign":
			org = feignClient.getOrganization(organizationId);
			break;
		case "discover":
			org = discoveryClient.getOrganization(organizationId);
			break;

		default:
			org = restTemplateClient.getOrganization(organizationId);
			break;
		}
		return org;
		
	}
	
	@CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
//	@RateLimiter(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
//	@Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
//	@Bulkhead(name = "bulkheadLicenseService", type= Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
	public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException {
		log.debug("getLicensesByOrganization Correlation id: {}",
				UserContextHolder.getContext().getCorrelationId());
		randomlyRunLong();
		List<License> lis = new ArrayList<>();
		License license = new License();
		license.setLicenseId("1234");
		license.setOrganizationId(organizationId);
		license.setProductName("licence produc 1");
		lis.add(license);
		return lis;
	}
	private void randomlyRunLong() throws TimeoutException{
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if (randomNum==3) sleep();
	}
	private void sleep() throws TimeoutException{
		try {
			System.out.println("Sleep");
			Thread.sleep(5000);
			throw new java.util.concurrent.TimeoutException();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
	

	@SuppressWarnings("unused")
	private List<License> buildFallbackLicenseList(String organizationId, Throwable t){
		List<License> fallbackList = new ArrayList<>();
		License license = new License();
		license.setLicenseId("0000000-00-00000");
		license.setOrganizationId(organizationId);
		license.setProductName("Sorry no licensing information currently available");
		fallbackList.add(license);
		return fallbackList;
	}
	
}
