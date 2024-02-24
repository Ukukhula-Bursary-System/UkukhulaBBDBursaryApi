package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Status> findAll() {
        String sql = "SELECT [ApplicationStatusID], [Status] FROM [dbo].[Application_Status]";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Status.class));
    }
}
