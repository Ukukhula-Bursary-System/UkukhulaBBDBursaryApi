package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.BursaryFund;
import com.bbd.bursary.manager.repository.BursaryFundRepository;
import com.bbd.bursary.manager.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/bursary-fund")
public class BursaryFundController {

    private final BursaryFundRepository bursaryFundRepository;

    @Autowired
    public BursaryFundController(BursaryFundRepository bursaryFundRepository) {
        this.bursaryFundRepository = bursaryFundRepository;
    }

    @GetMapping("/{year}")
    public ResponseEntity<?> retrieveBursaryFundForASpecificYear(@PathVariable("year") int year) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/bursary-fund/" + year);

        Optional<BursaryFund> bursaryFund = bursaryFundRepository.findByYear(year);

        if (bursaryFund.isEmpty())
            return new ResponseEntity<>(
                    Map.of("message", "Bursary fund for the year " + year + " was not found."),
                    HttpStatus.NOT_FOUND
            );
        return new ResponseEntity<>(
                bursaryFund.get(),
                HttpStatus.OK
        );
    }
}
