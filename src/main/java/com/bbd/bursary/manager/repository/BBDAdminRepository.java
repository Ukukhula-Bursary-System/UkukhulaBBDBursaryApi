package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.BBDAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BBDAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BBDAdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BBDAdmin> findAll() {
        String sql = "SELECT [BBDAdminID], [FirstName], [LastName], [IsActive], [Email], [Role] FROM vBBDAdmin";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BBDAdmin.class));
    }
}
