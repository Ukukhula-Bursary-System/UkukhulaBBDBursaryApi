package com.bbd.bursary.manager.repository;
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

    public void updateStatus(String status) {

    }
}
