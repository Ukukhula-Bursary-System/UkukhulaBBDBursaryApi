package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.dto.InstitutionFundAllocationDTO;
import com.bbd.bursary.manager.model.InstitutionFundAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
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

    public List<InstitutionFundAllocation> findForInstitute(long instituteId) {
        String sql = "SELECT [InstitutionFundAllocationID], [AllocatedAmount], " +
                "[AllocatedRemainingAmount], [InstituteName], [FinacialDate] as [FinancialDate]" +
                "FROM [dbo].[vInstitutionFundAllocation] WHERE [InstituteID] = ?";
        return jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(InstitutionFundAllocation.class), instituteId);
    }

    public List<InstitutionFundAllocation> findForInstituteForYear(long instituteId, long year) {
        String sql = "SELECT [InstitutionFundAllocationID], [AllocatedAmount], " +
                "[AllocatedRemainingAmount], [InstituteName], [FinacialDate] as [FinancialDate]" +
                "FROM [dbo].[vInstitutionFundAllocation] WHERE [InstituteID] = ? AND YEAR([FinacialDate]) = ?";
        return jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(InstitutionFundAllocation.class), instituteId, year);
    }

    public List<InstitutionFundAllocation> findAllByYear(int year) {
        String sql = "SELECT [InstitutionFundAllocationID], [AllocatedAmount], " +
                "[AllocatedRemainingAmount], [InstituteName], [FinacialDate] as [FinancialDate]" +
                "FROM [dbo].[vInstitutionFundAllocation] " +
                "WHERE YEAR([FinacialDate]) = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstitutionFundAllocation.class), year);
    }

    public void save(InstitutionFundAllocationDTO institutionFundAllocationDTO) throws SQLException {
        String sql = "INSERT INTO [dbo].[Institution_Fund_Allocation] (AllocatedAmount, AllocatedRemainingAmount, InstituteID, BursaryFundID)" +
                     " VALUES (?, ?, ?, [dbo].[udfGetBursaryFundId](YEAR(GETDATE())))";
        Object[] args = new Object[] {
                institutionFundAllocationDTO.getAllocatedAmount(),
                institutionFundAllocationDTO.getAllocatedAmount(),
                institutionFundAllocationDTO.getInstituteId()
        };
        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount == 0)
            throw new SQLException("Allocating institute funding failed!");
    }
}
