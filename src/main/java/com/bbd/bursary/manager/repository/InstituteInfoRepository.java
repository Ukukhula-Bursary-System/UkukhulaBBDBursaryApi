package com.bbd.bursary.manager.repository;

import com.bbd.bursary.manager.dto.InstituteInfoDTO;
import com.bbd.bursary.manager.exception.NotFoundException;
import com.bbd.bursary.manager.model.InstituteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InstituteInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InstituteInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<InstituteInfo> findById(long id) {
        String sql = "SELECT [InstituteID], [InstituteName], [Status], [email] FROM [dbo].[vInstituteInfo]" +
                     " WHERE [InstituteID] = ?";
        List<InstituteInfo> instituteInfos = jdbcTemplate.query(
                sql, BeanPropertyRowMapper.newInstance(InstituteInfo.class), id);

        if (instituteInfos.size() == 1)
            return Optional.of(instituteInfos.get(0));
        return Optional.empty();
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

    public Optional<Long> findIdByHeadOfDepartmentEmail(String email) {
        String sql = "SELECT [InstituteID] FROM [dbo].[vHeadOfDepartment] WHERE [Email] = ?";
        List<InstituteInfo> instituteId = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InstituteInfo.class), email);

        if (instituteId.isEmpty())
            return Optional.empty();
        return Optional.of(instituteId.get(0).getInstituteId());
    }

    public void save(InstituteInfoDTO instituteInfoDTO) throws SQLException {
        String sql = "EXEC [dbo].[uspSaveInstitute] " +
                     "@InstituteName = ?, " +
                     "@ApplicationStatusID = ?, " +
                     "@InstituteReviewer = ?, " +
                     "@RepresentativeFirstName = ?, " +
                     "@RepresentativeLastName = ?, " +
                     "@RepresentativePhoneNumber = ?, " +
                     "@RepresentativeEmail = ?";
        Object[] args = new Object[] {
                instituteInfoDTO.getInstituteName(),
                instituteInfoDTO.getApplicationStatusID(),
                instituteInfoDTO.getInstituteReviewerID(),
                instituteInfoDTO.getInstituteRepresentativeFirstName(),
                instituteInfoDTO.getInstituteRepresentativeLastName(),
                instituteInfoDTO.getInstituteRepresentativePhoneNumber(),
                instituteInfoDTO.getInstituteRepresentativeEmail()
        };
        int updateCount = jdbcTemplate.update(sql, args);

        if (updateCount == 0) throw new SQLException("Failed to save institute!");
    }

    public Optional<InstituteInfo> updateInstitute(long instituteId,
                                                   InstituteInfoDTO instituteInfoDTO) throws SQLException {
        findById(instituteId)
                .orElseThrow(() -> new NotFoundException("Institute with id " + instituteId + " was not found!"));
        String sqlStatusUpdate = "UPDATE [dbo].[Institute_Application] SET [ApplicationStatusId] = ? " +
                                 "WHERE [InstituteID] = ?";
        String sqlReviewerUpdate = "UPDATE [dbo].[Institute_Info] SET [BBDAdminID] = ? " +
                                   "WHERE [InstituteID] = ?";

        if (instituteInfoDTO.getApplicationStatusID() > 0) {
            Object[] args = new Object[] {
                    instituteInfoDTO.getApplicationStatusID(),
                    instituteId
            };
            int updateCount = jdbcTemplate.update(sqlStatusUpdate, args);

            if (updateCount == 0) throw new SQLException("Institute status update failed!");
        }

        if (instituteInfoDTO.getInstituteReviewerID() > 0) {
            Object[] args = new Object[] {
                    instituteInfoDTO.getInstituteReviewerID(),
                    instituteId
            };
            int updateCount = jdbcTemplate.update(sqlReviewerUpdate, args);

            if (updateCount == 0) throw new SQLException("Institute reviewer update failed!");
        }
        return findById(instituteId);
    }
}
