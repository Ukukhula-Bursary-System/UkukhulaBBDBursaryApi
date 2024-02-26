package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.BBDAdmin;
import com.bbd.bursary.manager.repository.BBDAdminRepository;
import com.bbd.bursary.manager.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
