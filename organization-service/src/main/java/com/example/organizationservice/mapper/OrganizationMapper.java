package com.example.organizationservice.mapper;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;

public class OrganizationMapper {

    public static OrganizationDto mapToOrganizationDto(Organization organization) {
          return       new OrganizationDto(
                        organization.getId(),
                        organization.getOrganizationName(),
                        organization.getOrganizationDesc(),
                        organization.getOrganizationCode(),
                        organization.getCreatedDate()
                );
    }

    public static Organization mapToOrganization(OrganizationDto organizationDto){
      return   Organization.builder()
                .id(organizationDto.getId())
                .organizationName(organizationDto.getOrganizationName())
                .organizationDesc(organizationDto.getOrganizationDesc())
                .organizationCode(organizationDto.getOrganizationCode())
                .build();
    }
}
