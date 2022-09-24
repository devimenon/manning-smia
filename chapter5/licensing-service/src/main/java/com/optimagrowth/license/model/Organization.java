package com.optimagrowth.license.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Organization implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7768832176158551819L;

	String id;

    String name;

    String contactName;

    String contactEmail;

    String contactPhone;


}
