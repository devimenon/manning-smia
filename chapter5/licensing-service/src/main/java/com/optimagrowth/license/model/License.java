package com.optimagrowth.license.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString @NoArgsConstructor 
public class License extends RepresentationModel<License> {

	private String licenseId;
	private String description;
	private String organizationId;
	private String productName;
	private String licenseType;
	private String comment;

	public License withComment(String comment){
		this.setComment(comment);
		return this;
	}
}