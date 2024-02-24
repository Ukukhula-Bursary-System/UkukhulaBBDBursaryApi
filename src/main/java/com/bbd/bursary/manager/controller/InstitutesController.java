package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.InstituteInfo;
import com.bbd.bursary.manager.repository.InstituteInfoRepository;
import com.bbd.bursary.manager.repository.InstituteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/institute")
public class InstitutesController {

    private final InstituteInterface instituteRepository;
    private final InstituteInfoRepository instituteInfoRepository;

    @Autowired
    public InstitutesController(InstituteInterface instituteRepository,
                                InstituteInfoRepository instituteInfoRepository) {
        this.instituteRepository = instituteRepository;
        this.instituteInfoRepository = instituteInfoRepository;
    }

    @GetMapping
    public ResponseEntity<List<InstituteInfo>> getAllInstitutes() {
        List<InstituteInfo> institutes = instituteInfoRepository.findAll();
        return new ResponseEntity<>(institutes, HttpStatus.OK);
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<List<InstituteInfo>> getAllInstitutesByStatus(@PathVariable long statusId) {
        List<InstituteInfo> institutesByStatus = instituteInfoRepository.findByStatus(statusId);
        return new ResponseEntity<>(institutesByStatus, HttpStatus.OK);
    }

    @PutMapping("/funds/{id}/{amount}")
    public ResponseEntity<String> allocateInstituteFunds(@PathVariable("amount") double amount, @PathVariable("id") int id) {

            int rowsAffected = instituteRepository.updateFunds(amount, id);
            if(rowsAffected > 0) {
                return new ResponseEntity<>("Funds allocated successfully", HttpStatus.OK);
            } else {
                System.out.print("rowsAffected" + rowsAffected);
                return new ResponseEntity<>("No student found with id=" + id, HttpStatus.NOT_FOUND);
            }
    }

    //Come back to this
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<String> updateInstitutePendingStatus(@PathVariable("status") final int status, @PathVariable("id") final int id ) {

        try {
            int rowsAffected = instituteRepository.updateStatus(id, status);
            return (rowsAffected > 0) ? new ResponseEntity<>("Status updated successfully", HttpStatus.OK) : new ResponseEntity<>("Status didnt updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No institute found with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable("id") long id) {
        try {
            int result = instituteRepository.deleteById(id);
            if(result == 0) {
                return new ResponseEntity<>("Cannot find institute with id" + id, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Institute was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Cannot delete institute.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
