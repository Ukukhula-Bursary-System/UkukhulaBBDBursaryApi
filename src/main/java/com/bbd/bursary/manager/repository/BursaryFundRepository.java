package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.BursaryFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BursaryFundRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BursaryFundRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<BursaryFund> findByYear(int year) {
        String sql = "SELECT " +
                     "  [BursaryFundID], [FundAmount], [FundRemainingAmount], " +
                     "  [StudentMaxAllocation], [FinacialDate] AS FinancialDate " +
                     "FROM " +
                     "  [dbo].[Bursary_Fund] " +
                     "WHERE " +
                     "  YEAR([FinacialDate]) = ?";
        List<BursaryFund> bursaryFunds = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(BursaryFund.class), year);

        if (bursaryFunds.isEmpty())
            return Optional.empty();
        return Optional.of(bursaryFunds.get(0));
    }
}
