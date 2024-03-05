package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public Optional<Document> findByStudentId(long studentId) {
        String sql = "SELECT " +
                     "  [DocumentID], " +
                     "  [Transcript], " +
                     "  [IdentityDocument] " +
                     "FROM " +
                     "  [dbo].[Document] Document " +
                     "INNER JOIN " +
                     "  [dbo].[Bursary_Applicants] BursaryApplicants " +
                     "ON " +
                     "  Document.[BursaryApplicantID] = BursaryApplicants.[BursaryApplicantID] " +
                     "WHERE " +
                     "  [StudentID] = ?";
        List<Document> documents = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Document.class), studentId);

        if (documents.isEmpty())
            return Optional.empty();
        return Optional.of(documents.get(0));
    }


    public Optional<Document> findByStudentApplicationId(long applicationId) {
        String sql = "SELECT " +
                "  [DocumentID], " +
                "  [Transcript], " +
                "  [IdentityDocument] " +
                "FROM " +
                "  [dbo].[Document] " +
                "WHERE " +
                "  [BursaryApplicantID] = ?";
        List<Document> documents = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Document.class), applicationId);

        if (documents.isEmpty())
            return Optional.empty();
        return Optional.of(documents.get(0));
    }

    public void update(Document document) throws SQLException {
        String sql = "UPDATE [dbo].[Document] SET [Transcript] = ?, [IdentityDocument] = ? WHERE [DocumentID] = ?";

        Object[] args = new Object[] {
                document.getTranscript(),
                document.getIdentityDocument(),
                document.getDocumentId()
        };
        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount < 1)
            throw new SQLException("Saving documents failed!");
    }
}
