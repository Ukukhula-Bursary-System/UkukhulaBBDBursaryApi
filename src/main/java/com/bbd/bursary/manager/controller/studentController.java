package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bbd.bursary.manager.repository.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class studentController {

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/all-applications")
  public ResponseEntity<List<Student>> AllStudentApplication() {

    List<Student> students = studentRepository.getAll();

    return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);
  }
    @GetMapping("/all/{status}")
     public ResponseEntity<List<Student>> AllFiltered(@PathVariable("status") String status) {
          List<Student> students = studentRepository.getAll();
          students = students.stream()
                  .filter(student -> student.getStatus().equals(status))
                    .toList()
          ;
        return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }



}
