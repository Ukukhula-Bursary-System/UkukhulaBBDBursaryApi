package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.HeadOfDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeadOfDepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HeadOfDepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HeadOfDepartment> findAll() {
        String sql = "SELECT [InstituteID], [HeadOfDepartmentID], [Email], [InstituteName] FROM [dbo].[vHeadOfDepartment]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(HeadOfDepartment.class));
    }

    public List<HeadOfDepartment> findByInstituteId(long instituteId) {
        String sql = "SELECT [InstituteID], [HeadOfDepartmentID], [Email], [InstituteName] " +
                     "FROM [dbo].[vHeadOfDepartment] " +
                     "WHERE [InstituteID] = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(HeadOfDepartment.class), instituteId);
    }
}
