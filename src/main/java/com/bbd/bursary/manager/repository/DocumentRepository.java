package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class DocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DocumentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Document document) throws SQLException {
        String sql = "INSERT INTO [dbo].[Document] ([Transcript], [IdentityDocument], [BursaryApplicantID]) " +
                     "VALUES (?, ?, ?)";

        Object[] args = new Object[] {
                document.getTranscript(),
                document.getIdentityDocument(),
                document.getBursaryApplicationID()
        };
        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount < 1)
            throw new SQLException("Saving documents failed!");
    }
}
