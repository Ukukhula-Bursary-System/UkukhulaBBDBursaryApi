package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.dto.BBDAdminDTO;
import com.bbd.bursary.manager.model.BBDAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
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

    public void save(BBDAdminDTO bbdAdminDTO) throws SQLException {
        String sql = "EXEC [dbo].[uspSaveAdmin]" +
                     "@FirstName = ?, " +
                     "@LastName = ?, " +
                     "@PhoneNumber = ?, " +
                     "@Email = ?, " +
                     "@RoleID = ?";
        Object[] args = new Object[] {
                bbdAdminDTO.getFirstName(),
                bbdAdminDTO.getLastName(),
                bbdAdminDTO.getPhoneNumber(),
                bbdAdminDTO.getEmail(),
                bbdAdminDTO.getRoleId()
        };
        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount == 0)
            throw new SQLException("Failed to save new admin!");
    }
}
