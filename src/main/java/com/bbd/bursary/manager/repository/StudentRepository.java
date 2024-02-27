package com.bbd.bursary.manager.repository;
import com.bbd.bursary.manager.model.Document;
import com.bbd.bursary.manager.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public  List<Student> getAll() {
        String sql = "SELECT [StudentID], [FirstName], [LastName],[University],[AverageMarks], [BursaryAmount], [Motivation], [status] FROM  [dbo].[Student_Bursary_Application]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Student.class));
    }

    public void updateStatus(int studentID, String status) {

        String sql1 = "SELECT [ApplicationStatusID] FROM [dbo].[Application_Status] WHERE [Status]=?";
        int statusID = jdbcTemplate.queryForObject(sql1, Integer.class, status);
        String sql2 = "UPDATE [dbo].[Bursary_Applicants] SET [BursaryApplicationStatusID] = ? WHERE StudentID=?";
        jdbcTemplate.update(sql2, statusID, studentID);

    }
    public Document getStudentDocuments(int BursaryApplicationStatusID) {
        String sql =  "SLECT  [DocumentID] , [Trnsacript], [identityDocument] FROM [dbo].[Document] WHERE [BursaryApplicationStatusID] = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Document.class), BursaryApplicationStatusID)
                .stream()
                .findFirst()
                .orElse(null);


    }

}


