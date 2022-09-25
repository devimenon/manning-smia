package com.optimagrowth.license.events.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.enums.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.optimagrowth.license.events.CustomChannels;
import com.optimagrowth.license.events.model.OrganizationChangeModel;
import com.optimagrowth.license.service.LicenseService;
import com.optimagrowth.license.utils.ActionEnum;

import lombok.extern.slf4j.Slf4j;

@EnableBinding(CustomChannels.class)
@Slf4j
@Component
public class OrganizationChangeHandler {

	@Autowired
	LicenseService service;

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organization) {
    	
        log.debug("Received a message of type {}", organization.getType());
        log.debug("Received a message with an event {} from the organization service for the organization id {} ", 
        		organization.getAction(), organization.getOrganizationId());
        if(StringUtils.equals(organization.getAction(),ActionEnum.DELETED.name()) || 
        		StringUtils.equals(organization.getAction(),ActionEnum.UPDATED.name())) {
        	log.info("Deleting cache: {}", organization.getOrganizationId());
        	service.evictSingleCacheValue(organization.getOrganizationId(), "feign");
        	service.evictSingleCacheValue(organization.getOrganizationId(), "discover");
        }
        
    }


}
