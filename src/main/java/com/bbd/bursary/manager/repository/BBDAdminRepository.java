package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.dto.BBDAdminDTO;
import com.bbd.bursary.manager.model.BBDAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public Optional<BBDAdmin> findByEmail(String email) {
        String sql = "SELECT " +
                     "  BBDAdmin.[BBDAdminID], BBDAdmin.[FirstName], BBDAdmin.[LastName], " +
                     "  BBDAdmin.[IsActive], BBDAdmin.[PhoneNumber], BBDAdmin.[Email], BBDAdmin.[Role] " +
                     "FROM " +
                     "  [dbo].[udfFindUser_DetailsByEmail](?) udf " +
                     "INNER JOIN " +
                     "  [dbo].[vBBDAdmin] BBDAdmin " +
                     "ON " +
                     "  udf.[Email] = BBDAdmin.[Email] ";
        List<BBDAdmin> admins = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BBDAdmin.class), email);

        if (admins.isEmpty())
            return Optional.empty();
        return Optional.of(admins.get(0));
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
