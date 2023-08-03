package com.example.organizationservice.service;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.mapper.OrganizationMapper;
import com.example.organizationservice.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements IOrganizationService {

    private OrganizationRepository organizationRepository;
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

        organization= organizationRepository.save(organization);

        OrganizationDto organizationDto1 = OrganizationMapper.mapToOrganizationDto(organization);
        return organizationDto1;
    }

    @Override
    public OrganizationDto getOrganizationDetailsByCode(String orgCode) {
        Organization byOrganizationCode = organizationRepository.findByOrganizationCode(orgCode);
        return OrganizationMapper.mapToOrganizationDto(byOrganizationCode);
    }

}
