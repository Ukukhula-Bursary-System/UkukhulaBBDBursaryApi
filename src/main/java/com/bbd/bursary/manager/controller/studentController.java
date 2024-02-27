package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.Student;
import com.bbd.bursary.manager.util.LoggedUser;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bbd.bursary.manager.repository.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class studentController {

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping("/all-applications")
  public ResponseEntity<?> AllStudentApplication() {
      if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
          return LoggedUser.unauthorizedResponse("/all/applications");

    List<Student> students = studentRepository.getAll();

    return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);
  }

  @GetMapping("/all-applications/hod/{email}")
  public ResponseEntity<?> AllStudentApplicationMadeByHOD(@PathVariable("email") String email){
      if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
          return LoggedUser.unauthorizedResponse("/all/applications");

      List<Student> students = studentRepository.findAllByHeadOfDepartmentEmail(email);
      return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);
  }

    @GetMapping("/all/{status}")
     public ResponseEntity<?> AllFiltered(@PathVariable("status") String status) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
            return LoggedUser.unauthorizedResponse("/all/{status}");

        List<Student> students = studentRepository.getAll();
        students = students.stream()
                .filter(student -> student.getStatus().equals(status))
                .toList();
        return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);


    }

    @PostMapping("/update/{studentID}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable("studentID") int studentID, @PathVariable("status") String status) {
        if (!LoggedUser.checkRole(List.of("Admin")))
            return LoggedUser.unauthorizedResponse("/update/{studentID}/{status}");

        studentRepository.updateStatus(studentID, status);
        return new ResponseEntity<>("Status updated", HttpStatus.OK);
    }
}
