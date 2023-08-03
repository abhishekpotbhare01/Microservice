package com.example.organizationservice.service;

import com.example.organizationservice.dto.OrganizationDto;

public interface IOrganizationService {
    OrganizationDto saveOrganization(OrganizationDto organizationDto);

    OrganizationDto getOrganizationDetailsByCode(String orgCode);
}
