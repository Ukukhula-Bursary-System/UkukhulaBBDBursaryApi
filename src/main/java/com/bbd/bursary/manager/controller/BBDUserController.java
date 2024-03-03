package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.dto.BBDAdminDTO;
import com.bbd.bursary.manager.model.BBDAdmin;
import com.bbd.bursary.manager.model.HeadOfDepartment;
import com.bbd.bursary.manager.repository.BBDAdminRepository;
import com.bbd.bursary.manager.repository.HeadOfDepartmentRepository;
import com.bbd.bursary.manager.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class BBDUserController {

    private final BBDAdminRepository bbdAdminRepository;
    private final HeadOfDepartmentRepository headOfDepartmentRepository;

    @Autowired
    public BBDUserController(BBDAdminRepository bbdAdminRepository,
                             HeadOfDepartmentRepository headOfDepartmentRepository) {
        this.bbdAdminRepository = bbdAdminRepository;
        this.headOfDepartmentRepository = headOfDepartmentRepository;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllAdmins() {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/user");

        List<BBDAdmin> bbdAdmins = bbdAdminRepository.findAll();

        if (bbdAdmins.isEmpty())
            return new ResponseEntity<>(bbdAdmins, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bbdAdmins, HttpStatus.OK);
    }

    @GetMapping("/hod")
    public ResponseEntity<?> retrieveAllHeadOfDepartments() {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/user/hod");

        List<HeadOfDepartment> headOfDepartments = headOfDepartmentRepository.findAll();

        if (headOfDepartments.isEmpty())
            return new ResponseEntity<>(headOfDepartments, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(headOfDepartments, HttpStatus.OK);
    }

    @GetMapping("/hod/{instituteId}")
    public ResponseEntity<?> retrieveHeadOfDepartmentsByInstituteId(
            @PathVariable("instituteId") long instituteId) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
            return LoggedUser.unauthorizedResponse("/user/hod/" + instituteId);

        List<HeadOfDepartment> headOfDepartments = headOfDepartmentRepository
                .findByInstituteId(instituteId);

        if (headOfDepartments.isEmpty())
            return new ResponseEntity<>(headOfDepartments, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(headOfDepartments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveAnAdmin(@RequestBody BBDAdminDTO bbdAdminDTO) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/user");

        try {
            bbdAdminRepository.save(bbdAdminDTO);

            return new ResponseEntity<>(
                    Map.of("message", "Admin user successfully saved!"),
                    HttpStatus.CREATED
            );
        } catch (SQLException e) {
            return new ResponseEntity<>(
                    Map.of("message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
