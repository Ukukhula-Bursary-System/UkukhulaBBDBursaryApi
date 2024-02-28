package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.BursaryApplicants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BursaryApplicantsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BursaryApplicantsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BursaryApplicants> findAll() {
        String sql = "SELECT [BursaryApplicantID], [BursaryAmount], [AverageMarks], [Motivation], [Status], [HeadOfDepartmentID], [FirstName], [LastName], [Email], [InstituteFundAllocationID], [BBDAdminID] FROM [dbo].[vBursaryApplicants]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BursaryApplicants.class));
    }
}
