package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.InstituteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstituteInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InstituteInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<InstituteInfo> findAll() {
        String sql = "SELECT [InstituteID], [InstituteName], [Status], [email] FROM [dbo].[vInstituteInfo]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstituteInfo.class));
    }

    public List<InstituteInfo> findByStatus(long statusId) {
        String sql = "SELECT [InstituteID], [InstituteName], [Status], [email] FROM [dbo].[vInstituteInfo] " +
                     "WHERE [ApplicationStatusID] = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstituteInfo.class), statusId);
    }
}
