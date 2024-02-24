package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserDetails> findByEmail(String email) {
        String sql = "SELECT [UserID], [FirstName], [LastName], [IsActive], [PhoneNumber], [Email], [Role] FROM " +
                     "[dbo].[udfFindUser_DetailsByEmail](?)";
        List<UserDetails> userList = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(UserDetails.class),
                email
        );

        if (userList.size() != 1)
            return Optional.empty();
        return Optional.of(userList.getFirst());
    }
}
