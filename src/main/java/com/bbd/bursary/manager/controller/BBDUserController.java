package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.dto.BBDAdminDTO;
import com.bbd.bursary.manager.model.BBDAdmin;
import com.bbd.bursary.manager.repository.BBDAdminRepository;
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

    @Autowired
    public BBDUserController(BBDAdminRepository bbdAdminRepository) {
        this.bbdAdminRepository = bbdAdminRepository;
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
