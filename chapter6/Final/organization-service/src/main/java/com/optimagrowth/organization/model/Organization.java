package com.optimagrowth.organization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class Organization {
    String id;

    String name;

    String contactName;

    String contactEmail;

    String contactPhone;


}
