package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.dto.InstituteInfoDTO;
import com.bbd.bursary.manager.dto.InstitutionFundAllocationDTO;
import com.bbd.bursary.manager.exception.NotFoundException;
import com.bbd.bursary.manager.model.InstituteInfo;
import com.bbd.bursary.manager.model.InstitutionFundAllocation;
import com.bbd.bursary.manager.repository.InstituteInfoRepository;
import com.bbd.bursary.manager.repository.InstitutionFundAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/institute")
public class InstitutesController {
    private final InstituteInfoRepository instituteInfoRepository;
    private final InstitutionFundAllocationRepository institutionFundAllocationRepository;

    @Autowired
    public InstitutesController(InstituteInfoRepository instituteInfoRepository,
                                InstitutionFundAllocationRepository institutionFundAllocationRepository) {
        this.instituteInfoRepository = instituteInfoRepository;
        this.institutionFundAllocationRepository = institutionFundAllocationRepository;
    }

    @GetMapping
    public ResponseEntity<List<InstituteInfo>> getAllInstitutes() {
        List<InstituteInfo> institutes = instituteInfoRepository.findAll();

        if (institutes.isEmpty())
            return new ResponseEntity<>(institutes, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutes, HttpStatus.OK);
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<List<InstituteInfo>> getAllInstitutesByStatus(@PathVariable long statusId) {
        List<InstituteInfo> institutesByStatus = instituteInfoRepository.findByStatus(statusId);

        if (institutesByStatus.isEmpty())
            return new ResponseEntity<>(institutesByStatus, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutesByStatus, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveInstitution(@RequestBody InstituteInfoDTO instituteInfoDTO) {
        try {
            instituteInfoRepository.save(instituteInfoDTO);
            return new ResponseEntity<>(
                    Map.of("message", "Institute successfully saved!"),
                    HttpStatus.CREATED
            );
        } catch (SQLException e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/{instituteId}")
    public ResponseEntity<?> updateInstitution(@PathVariable long instituteId,
                                               @RequestBody InstituteInfoDTO instituteInfoDTO) {
        try {
            Optional<InstituteInfo> instituteInfo = instituteInfoRepository.updateInstitute(instituteId, instituteInfoDTO);
            return new ResponseEntity<>(
                    instituteInfo.get(),
                    HttpStatus.CREATED
            );
        } catch (SQLException e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (NotFoundException e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/funds")
    public ResponseEntity<List<InstitutionFundAllocation>> allAllocatedInstitutesFunding() {
        List<InstitutionFundAllocation> institutionFundAllocations = institutionFundAllocationRepository
                .findAll();

        if (institutionFundAllocations.isEmpty())
            return new ResponseEntity<>(institutionFundAllocations, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutionFundAllocations, HttpStatus.OK);
    }

    @PostMapping("/funds")
    public ResponseEntity<?> saveInstituteFundAllocation(
            @RequestBody InstitutionFundAllocationDTO institutionFundAllocation) {
        try {
            institutionFundAllocationRepository.save(institutionFundAllocation);
            return new ResponseEntity<>(
                    Map.of("message", "Institute funding successfully added!"),
                    HttpStatus.CREATED
            );
        } catch (SQLException e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/funds/{year}")
    public ResponseEntity<List<InstitutionFundAllocation>> allAllocatedInstitutesFundingByYear(
            @PathVariable("year") int year) {
        List<InstitutionFundAllocation> institutionFundAllocations = institutionFundAllocationRepository
                .findAllByYear(year);

        if (institutionFundAllocations.isEmpty())
            return new ResponseEntity<>(institutionFundAllocations, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutionFundAllocations, HttpStatus.OK);
    }
}
