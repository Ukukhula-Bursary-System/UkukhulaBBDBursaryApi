package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.InstitutionFundAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InstitutionFundAllocationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InstitutionFundAllocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<InstitutionFundAllocation> findAll() {
        String sql = "SELECT [InstitutionFundAllocationID], [AllocatedAmount], " +
                     "[AllocatedRemainingAmount], [InstituteName], [FinacialDate] as [FinancialDate]" +
                     "FROM [dbo].[vInstitutionFundAllocation]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstitutionFundAllocation.class));
    }

    public List<InstitutionFundAllocation> findAllByYear(int year) {
        String sql = "SELECT [InstitutionFundAllocationID], [AllocatedAmount], " +
                "[AllocatedRemainingAmount], [InstituteName], [FinacialDate] as [FinancialDate]" +
                "FROM [dbo].[vInstitutionFundAllocation] " +
                "WHERE YEAR([FinacialDate]) = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstitutionFundAllocation.class), year);
    }
}
