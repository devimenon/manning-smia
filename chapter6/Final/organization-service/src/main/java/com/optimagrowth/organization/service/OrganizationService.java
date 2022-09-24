package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.events.source.SimpleSourceBean;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.utils.ActionEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationService {
	
    @Autowired
    SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
    	Organization opt =Organization.builder().id(organizationId)
    			.contactName(organizationId+"-name").build();
    	simpleSourceBean.publishOrganizationChange(ActionEnum.GET, organizationId);
        return opt;
    }	

    public Organization create(Organization organization){
    	organization.setId( UUID.randomUUID().toString());
        //organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED, organization.getId());
        return organization;

    }

    public void update(Organization organization){
    	//repository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED, organization.getId());
    }

    public void delete(String organizationId){
    	//repository.deleteById(organizationId);
    	simpleSourceBean.publishOrganizationChange(ActionEnum.DELETED, organizationId);
    }
    
    @SuppressWarnings("unused")
	private void sleep(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}


   }