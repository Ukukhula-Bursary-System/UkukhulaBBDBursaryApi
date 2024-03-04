package com.bbd.bursary.manager.controller;


import com.bbd.bursary.manager.model.BBDReviewer;
import com.bbd.bursary.manager.repository.BBDReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student/reviewer")
public class BBDReviewerController {
    private final BBDReviewerRepository bbdReviewerRepository;

    @Autowired
    public BBDReviewerController(BBDReviewerRepository bbdReviewerRepository) {
        this.bbdReviewerRepository = bbdReviewerRepository;
    }


    @GetMapping("/{email}")
    public ResponseEntity<List<BBDReviewer>> findAll(@PathVariable("email") String email) {
        List<BBDReviewer> students = bbdReviewerRepository.findAllReviewerStudents(email);

        if (students.isEmpty())
            return new ResponseEntity<>(
                    students,
                    HttpStatus.NO_CONTENT
            );
        return new ResponseEntity<>(
                students,
                HttpStatus.OK
        );
    }
}
