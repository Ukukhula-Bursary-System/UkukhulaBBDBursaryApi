package com.bbd.bursary.manager.controller;

import com.bbd.bursary.manager.model.BursaryApplicants;
import com.bbd.bursary.manager.model.Document;
import com.bbd.bursary.manager.model.Student;
import com.bbd.bursary.manager.repository.BBDAdminRepository;
import com.bbd.bursary.manager.repository.BursaryApplicantsRepository;
import com.bbd.bursary.manager.repository.DocumentRepository;
import com.bbd.bursary.manager.repository.StudentRepository;
import com.bbd.bursary.manager.service.EmailService;
import com.bbd.bursary.manager.service.FileStorageService;
import com.bbd.bursary.manager.util.ExpirationLink;
import com.bbd.bursary.manager.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final BBDAdminRepository bbdAdminRepository;
    private final EmailService emailService;
    private final FileStorageService fileStorageService;

    @Autowired
    public studentController(StudentRepository studentRepository,
                             BursaryApplicantsRepository bursaryApplicantsRepository,
                             DocumentRepository documentRepository,
                             BBDAdminRepository bbdAdminRepository,
                             EmailService emailService, FileStorageService fileStorageService) {
        this.studentRepository = studentRepository;
        this.bursaryApplicantsRepository = bursaryApplicantsRepository;
        this.documentRepository = documentRepository;
        this.bbdAdminRepository = bbdAdminRepository;
        this.emailService = emailService;
        this.fileStorageService = fileStorageService;
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

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isEmpty())
            return new ResponseEntity<>(
                Map.of("message", "Student with id " + studentId + " not found!"),
                HttpStatus.NOT_FOUND
            );

        String linkForDocumentUpload;
        try {
            Student student = studentOptional.get();
            linkForDocumentUpload = ExpirationLink.generateLink(
                student.getEmail()
            );
            String emailMessage = String.format(
                    "Dear %s %s,\n\n" +
                    "Please use the link below to upload the required documents for your BBD UKUKHULA bursary application:\n" +
                    "%s\n\n" +
                    "Kind Regards,\n" +
                    "BBD Ukukhula Bursary Team",
                    student.getFirstName(),
                    student.getLastName(),
                    linkForDocumentUpload
            );
            emailService.sendEmail(
                    student.getEmail(),
                    "BBD UkukhulaBursary Documents Upload",
                    emailMessage
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", "Failed to create link. Please try again."),
                    HttpStatus.EXPECTATION_FAILED
            );
        }

        return new ResponseEntity<>(
            Map.of("link", linkForDocumentUpload),
            HttpStatus.CREATED
        );
    }

    @PostMapping(value = "/document-upload/{token}")
    public ResponseEntity<?> uploadStudentDocuments(@PathVariable("token") String token,
                                                    @RequestParam("Transcript") MultipartFile transcript,
                                                    @RequestParam("IdentityDocument") MultipartFile identityDocument) {
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

        BursaryApplicants application = bursaryApplication.get();
        Document document = documentRepository.findByStudentApplicationId(application.getBursaryApplicantId())
                .orElse(new Document());

        try {
            String transcriptLocation = fileStorageService.save(transcript);
            String identityDocumentLocation = fileStorageService.save(identityDocument);

            document.setBursaryApplicationID(application.getBursaryApplicantId());
            document.setTranscript(transcriptLocation);
            document.setIdentityDocument(identityDocumentLocation);

            if (document.getDocumentId() != 0) {
                documentRepository.update(document);
            } else {
                documentRepository.save(document);
            }
            bursaryApplicantsRepository.updateStatusToPending(bursaryApplication.get().getBursaryApplicantId());
        } catch (SQLException | IOException e) {
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

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> getStudentApplicationFile(@PathVariable("filename") String filename) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD", "Reviewer")))
            return LoggedUser.unauthorizedResponse("/update/{studentID}/{status}");

        try {
            Resource file = fileStorageService.load(filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (MaxUploadSizeExceededException e) {
            return new ResponseEntity<>(
                    Map.of("message",
                            "Failed to save file. The file is too large. Please ensure it is less than 2MB."
                    ),
                    HttpStatus.EXPECTATION_FAILED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", "failed to fetch file"),
                    HttpStatus.EXPECTATION_FAILED
            );
        }
    }

    @GetMapping("/documents/{studentId}")
    public ResponseEntity<?> getStudentDocumentsLocation(@PathVariable("studentId") long studentId) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD", "Reviewer")))
            return LoggedUser.unauthorizedResponse("/update/{studentID}/{status}");

        try {
            Optional<Document> document = documentRepository.findByStudentId(studentId);

            if (document.isEmpty())
                return new ResponseEntity<>(
                        Map.of("message", "Documents for student with id " + studentId + " not found"),
                        HttpStatus.NOT_FOUND
                );
            return new ResponseEntity<>(
                    document.get(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("message", "Documents for student with id " + studentId + " not found"),
                    HttpStatus.NOT_FOUND
            );
        }
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

    @GetMapping("/get-student-application/{studentId}")
    public ResponseEntity<?> getStudentApplication(@PathVariable("studentId") long studentId) {
        if (!LoggedUser.checkRole(List.of("Admin", "HOD")))
            return LoggedUser.unauthorizedResponse("/get-student-application/{studentId}");

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty())
            return new ResponseEntity<>(
                    Map.of("message", "Student with id " + studentId + " not found!"),
                    HttpStatus.NOT_FOUND
            );
        return new ResponseEntity<>(
                studentOptional.get(),
                HttpStatus.OK
        );
    }

    @GetMapping("/reviewer/{email}")
    public ResponseEntity<?> retrieveAllReviewersStudent(@PathVariable("email") String email) {
        if (!LoggedUser.checkRole(List.of("Reviewer")))
            return LoggedUser.unauthorizedResponse("/institute/funds");

        long reviewerId = bbdAdminRepository.findByEmail(email)
                .get().getBBDAdminId();

        List<Student> students = studentRepository.findByReviewerId(reviewerId);

        return new ResponseEntity<>(students, students.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }
}
