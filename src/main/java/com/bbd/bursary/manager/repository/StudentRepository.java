package com.bbd.bursary.manager.repository;
import com.bbd.bursary.manager.model.Document;
import com.bbd.bursary.manager.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Student> findById(long id) {
        String sql = "SELECT " +
                     "      Application.[StudentID], Application.[FirstName], Application.[LastName], " +
                     "      Application.[University], Application.[AverageMarks], Application.[BursaryAmount], " +
                     "      Application.[Motivation], Application.[status], ContactDetails.[Email] " +
                     "FROM  " +
                     "      [dbo].[Student_Bursary_Application] Application " +
                     "INNER JOIN " +
                     "      [dbo].[Student] Student " +
                     "ON " +
                     "      Student.[StudentID] = Application.[StudentID] " +
                     "INNER JOIN " +
                     "      [dbo].[User_Details] UserDetails " +
                     "ON " +
                     "      Student.[UserID] = UserDetails.[UserID] " +
                     "INNER JOIN " +
                     "      [dbo].[Contact_Details] ContactDetails " +
                     "ON " +
                     "      ContactDetails.[ContactDetailsID] = UserDetails.[ContactDetailsID] " +
                     "WHERE " +
                     "      Student.[StudentID] = ? ";
        List<Student> students = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Student.class), id);

        if (students.isEmpty())
            return Optional.empty();
        return Optional.of(students.get(0));
    }

    public  List<Student> getAll() {
        String sql = "SELECT [StudentID], [FirstName], [LastName],[University],[AverageMarks], [BursaryAmount], [Motivation], [status] FROM  [dbo].[Student_Bursary_Application]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Student.class));
    }

    public List<Student> getDetailedStudentInfo() {
        String sql = "SELECT [StudentID],ID_Number,[Email],[PhoneNumber], [FirstName]," +
                " [LastName], " +
                " University,AverageMarks," +
                " [BursaryAmount], [Motivation], [Status], [HeadOfDepartmentID]" +
                "  FROM [dbo].[GetFullStudentDetails]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Student.class));
    }

    public List<Student> findAllByHeadOfDepartmentEmail(String email) {
        String sql = "SELECT " +
                     "  udfStudent.[StudentID], udfStudent.[FirstName], udfStudent.[LastName], udfStudent.[University], " +
                     "  udfStudent.[AverageMarks], udfStudent.[BursaryAmount], udfStudent.[Motivation], " +
                     "  udfStudent.[status], udfStudent.[HeadOfDepartmentID], " +
                     "  Student.[ID_Number] AS [IdentityDocument], ContactDetails.[Email], ContactDetails.[PhoneNumber]" +
                     "FROM " +
                     "  [dbo].[udfFindStudentApplicationByHOD](?) udfStudent " +
                     "INNER JOIN " +
                     "  [dbo].[Student] Student " +
                     "ON    " +
                     "  udfStudent.[StudentID] = Student.[StudentID] " +
                     "INNER JOIN " +
                     "  [dbo].[User_Details] UserDetails " +
                     "ON " +
                     "  Student.[UserID] = UserDetails.[UserID] " +
                     "INNER JOIN " +
                     "  [dbo].[Contact_Details] ContactDetails " +
                     "ON " +
                     "  UserDetails.[ContactDetailsID] = ContactDetails.[ContactDetailsID] ";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Student.class), email);
    }

    public void updateStatus(int studentID, String status) {

        String sql1 = "SELECT [ApplicationStatusID] FROM [dbo].[Application_Status] WHERE [Status]=?";
        int statusID = jdbcTemplate.queryForObject(sql1, Integer.class, status);
        String sql2 = "UPDATE [dbo].[Bursary_Applicants] SET [BursaryApplicationStatusID] = ? WHERE StudentID=?";
        jdbcTemplate.update(sql2, statusID, studentID);

    }

    public Document getStudentDocuments(int BursaryApplicationStatusID) {
        String sql =  "SELECT  [DocumentID] , [Transcript], [IdentityDocument] FROM [dbo].[Document] WHERE [BursaryApplicationStatusID] = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Document.class), BursaryApplicationStatusID)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public  void save(Student student) {
      String sql = "EXEC [dbo].[uspSaveStudent] " +
                   "@FirstName = ?, " +
                   "@LastName = ?, " +
                   "@PhoneNumber = ?, " +
                   "@Email = ?, " +
                   "@InstituteID = ?, " +
                   "@IDNumber = ?, " +
                   "@RaceID = ?, " +
                   "@BursaryAmount = ?, " +
                   "@AverageMarks = ?, " +
                   "@Motivation = ?, " +
                   "@HeadOfDepartmentID = ?";

        Object[] args = new Object[] {
                student.getFirstName(),
                student.getLastName(),
                student.getPhoneNumber(),
                student.getEmail(),
                student.getInstituteId(),
                student.getIdentityDocument(),
                student.getRace(),
                student.getBursaryAmount(),
                student.getAverageMarks(),
                student.getMotivation(),
                student.getHeadOfDepartmentId()
        };

        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount == 0) throw new RuntimeException("Failed to save student!");
    }

    public int getHodIdByEmail(String email) {
        String sql = "select dbo.getHODIdByEmail(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }
}


