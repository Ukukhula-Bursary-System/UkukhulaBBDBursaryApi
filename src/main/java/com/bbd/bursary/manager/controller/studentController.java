package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.BursaryApplicants;
import com.bbd.bursary.manager.model.Document;
import com.bbd.bursary.manager.model.Student;
import com.bbd.bursary.manager.repository.BursaryApplicantsRepository;
import com.bbd.bursary.manager.repository.DocumentRepository;
import com.bbd.bursary.manager.repository.StudentRepository;
import com.bbd.bursary.manager.util.ExpirationLink;
import com.bbd.bursary.manager.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class studentController {

    private final StudentRepository studentRepository;
    private final BursaryApplicantsRepository bursaryApplicantsRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public studentController(StudentRepository studentRepository,
                             BursaryApplicantsRepository bursaryApplicantsRepository,
                             DocumentRepository documentRepository) {
        this.studentRepository = studentRepository;
        this.bursaryApplicantsRepository = bursaryApplicantsRepository;
        this.documentRepository = documentRepository;
    }

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

    @GetMapping("/generate-student-document-link/{studentId}")
    public ResponseEntity<?> generateStudentDocumentUploadLink(@PathVariable("studentId") long studentId) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
            return LoggedUser.unauthorizedResponse("/update/{studentID}/{status}");

        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            return new ResponseEntity<>(
                Map.of("message", "Student with id " + studentId + " not found!"),
                HttpStatus.NOT_FOUND
            );

        String linkForDocumentUpload;
        try {
            linkForDocumentUpload = ExpirationLink.generateLink(
                student.get().getEmail()
            );
        } catch (UnknownHostException e) {
            return new ResponseEntity<>(
                Map.of("message", "failed to create link!"),
                HttpStatus.EXPECTATION_FAILED
            );
        }

        return new ResponseEntity<>(
            Map.of("link", linkForDocumentUpload),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/document-upload/{token}")
    public ResponseEntity<?> uploadStudentDocuments(@PathVariable("token") String token,
                                                    @RequestBody Document document) {
        if (!ExpirationLink.isLinkValid(token))
            return new ResponseEntity<>(
                    Map.of("message", "link has expired!"),
                    HttpStatus.FORBIDDEN
            );

        String studentEmail = ExpirationLink.getEmail(token);

        Optional<BursaryApplicants> bursaryApplication = bursaryApplicantsRepository
                .findByEmail(studentEmail);

        if (bursaryApplication.isEmpty())
            return new ResponseEntity<>(
                    Map.of("message", "Create student application before you can upload documents"),
                    HttpStatus.BAD_REQUEST
            );

        document.setBursaryApplicationID(bursaryApplication.get().getBursaryApplicantId());

        try {
            documentRepository.save(document);
        } catch (SQLException e) {
            return new ResponseEntity<>(
                    Map.of("message", "Failed to upload documents"),
                    HttpStatus.EXPECTATION_FAILED
            );
        }
        return new ResponseEntity<>(
                Map.of("message", "Documents successfully uploaded"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getHodIdByEmail/{email}")
    public ResponseEntity<?> getHodIdByEmail(@PathVariable("email") String email) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
            return LoggedUser.unauthorizedResponse("this port");
        int hodId = studentRepository.getHodIdByEmail(email);
        return new ResponseEntity<>(hodId, HttpStatus.OK);
    }
    @PostMapping("/newStudentApplication")
    public ResponseEntity<?> saveStudentApplication(@RequestBody Student studentApplicationDTO) {
        if (!LoggedUser.checkRole(List.of("HOD")))
            return LoggedUser.unauthorizedResponse("/studentApplication");

        //check if student is in correct format
        if (studentApplicationDTO.getFirstName() == null || studentApplicationDTO.getLastName() == null ||
                studentApplicationDTO.getEmail() == null || studentApplicationDTO.getPhoneNumber() == null ||
                studentApplicationDTO.getIdentityDocument() == 0 || studentApplicationDTO.getRace() == null ||
                 studentApplicationDTO.getMotivation() == null || studentApplicationDTO.getAverageMarks() == 0 ||
                studentApplicationDTO.getHeadOfDepartmentID() == 0 || studentApplicationDTO.getBursaryAmount()==0)
                 {
            return new ResponseEntity<>(
                    Map.of("message", "Student application not in correct format"),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            studentRepository.save(studentApplicationDTO);
            return new ResponseEntity<>(
                    Map.of("message", "Student application successfully saved!"),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", "Failed to save student application"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
