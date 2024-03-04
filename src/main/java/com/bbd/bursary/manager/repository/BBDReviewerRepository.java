package com.bbd.bursary.manager.repository;
import com.bbd.bursary.manager.model.BBDReviewer;
import com.bbd.bursary.manager.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class BBDReviewerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BBDReviewerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BBDReviewer> findAllReviewerStudents() {
        String sql = "SELECT [firstName], [lastName] [bursaryAmount], [InstituteName], [Status], [BBDAdminID] FROM [dbo].[BursaryApplicantsV]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BBDReviewer.class));
    }

    public void updateStatus(long studentID, String status) {

        String sql1 = "SELECT [ApplicationStatusID] FROM [dbo].[Application_Status] WHERE [Status]=?";
        Student statusID = jdbcTemplate.queryForObject(sql1,Student.class, status);
        String sql2 = "UPDATE [dbo].[Bursary_Applicants] SET [BursaryApplicationStatusID] = ? WHERE StudentID=?";
        jdbcTemplate.update(sql2, statusID, studentID);

    }

}
