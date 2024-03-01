package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.dto.InstituteInfoDTO;
import com.bbd.bursary.manager.dto.InstitutionFundAllocationDTO;
import com.bbd.bursary.manager.exception.NotFoundException;
import com.bbd.bursary.manager.model.InstituteInfo;
import com.bbd.bursary.manager.model.InstitutionFundAllocation;
import com.bbd.bursary.manager.model.Student;
import com.bbd.bursary.manager.repository.InstituteInfoRepository;
import com.bbd.bursary.manager.repository.InstitutionFundAllocationRepository;
import com.bbd.bursary.manager.repository.StudentRepository;
import com.bbd.bursary.manager.util.LoggedUser;
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
    public ResponseEntity<?> getAllInstitutes() {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute");

        List<InstituteInfo> institutes = instituteInfoRepository.findAll();

        if (institutes.isEmpty())
            return new ResponseEntity<>(institutes, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutes, HttpStatus.OK);
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<?> getAllInstitutesByStatus(@PathVariable long statusId) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute/" + statusId);

        List<InstituteInfo> institutesByStatus = instituteInfoRepository.findByStatus(statusId);

        if (institutesByStatus.isEmpty())
            return new ResponseEntity<>(institutesByStatus, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutesByStatus, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> saveInstitution(@RequestBody InstituteInfoDTO instituteInfoDTO) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute");

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
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute/" + instituteId);

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
    public ResponseEntity<?> allAllocatedInstitutesFunding() {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute/funds");

        List<InstitutionFundAllocation> institutionFundAllocations = institutionFundAllocationRepository
                .findAll();

        if (institutionFundAllocations.isEmpty())
            return new ResponseEntity<>(institutionFundAllocations, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutionFundAllocations, HttpStatus.OK);
    }

    @PostMapping("/funds")
    public ResponseEntity<?> saveInstituteFundAllocation(
            @RequestBody InstitutionFundAllocationDTO institutionFundAllocation) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute/funds");

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
    public ResponseEntity<?> allAllocatedInstitutesFundingByYear(
            @PathVariable("year") int year) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/institute/funds/" + year);

        List<InstitutionFundAllocation> institutionFundAllocations = institutionFundAllocationRepository
                .findAllByYear(year);

        if (institutionFundAllocations.isEmpty())
            return new ResponseEntity<>(institutionFundAllocations, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(institutionFundAllocations, HttpStatus.OK);
    }

    @GetMapping("funds/single-institute/{instituteId}")
    public ResponseEntity<?> getAllAllocatedFundingForAnInstitute(@PathVariable long instituteId) {
        if (!LoggedUser.checkRole(List.of("HOD")))
            return LoggedUser.unauthorizedResponse("funds/single-institute/" + instituteId);

        List<InstitutionFundAllocation> fundingHistory = institutionFundAllocationRepository
                .findForInstitute(instituteId);

        if (fundingHistory.isEmpty())
            return new ResponseEntity<>(fundingHistory, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(fundingHistory, HttpStatus.OK);
    }

    @GetMapping("funds/single-institute/{instituteId}/{year}")
    public ResponseEntity<?> getAllocatedFundingForAnInstituteForTheYear(@PathVariable long instituteId,
                                                                         @PathVariable int year) {
        if (!LoggedUser.checkRole(List.of("HOD")))
            return LoggedUser.unauthorizedResponse("funds/single-institute/" + instituteId + "/" + year);

        List<InstitutionFundAllocation> yearFunding = institutionFundAllocationRepository
                .findForInstituteForYear(instituteId, year);

        if (yearFunding.isEmpty())
            return new ResponseEntity<>(yearFunding, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(yearFunding, HttpStatus.OK);
    }
}
