package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.Status;
import com.bbd.bursary.manager.repository.StatusRepository;
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
@RequestMapping("/statuses")
public class StatusController {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @GetMapping
    public ResponseEntity<List<Status>> findAll() {
        List<Status> statuses = statusRepository.findAll();

        if (statuses.isEmpty())
            return new ResponseEntity<>(
                    statuses,
                    HttpStatus.NO_CONTENT
            );
        return new ResponseEntity<>(
                statuses,
                HttpStatus.OK
        );
    }
}
