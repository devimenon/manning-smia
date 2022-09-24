package com.optimagrowth.license.events.handler;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.optimagrowth.license.events.CustomChannels;
import com.optimagrowth.license.events.model.OrganizationChangeModel;

import lombok.extern.slf4j.Slf4j;

@EnableBinding(CustomChannels.class)
@Slf4j
public class OrganizationChangeHandler {


    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organization) {
    	
        log.debug("Received a message of type " + organization.getType());
        log.debug("Received a message with an event {} from the organization service for the organization id {} ", 
        		organization.getType(), organization.getOrganizationId());
    }


}
