package com.example.organizationservice.controller;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.service.IOrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/org")
@AllArgsConstructor
public class OrganizationController {

    private IOrganizationService iOrganizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto>saveOrganization(@RequestBody OrganizationDto organizationDto){

        OrganizationDto organizationDto1 = iOrganizationService.saveOrganization(organizationDto);
        return  new ResponseEntity<>(organizationDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{orgCode}")
    public ResponseEntity<OrganizationDto>fetchOrganizationDetails(@PathVariable("orgCode")String orgCode){

        OrganizationDto organizationDto1 = iOrganizationService.getOrganizationDetailsByCode(orgCode);
        return  new ResponseEntity<>(organizationDto1, HttpStatus.OK);
    }
}
