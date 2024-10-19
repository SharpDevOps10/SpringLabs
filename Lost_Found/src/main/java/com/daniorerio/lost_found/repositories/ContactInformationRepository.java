package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.DAO.ContactInformationDao;
import com.daniorerio.lost_found.entities.ContactInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactInformationRepository implements ContactInformationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactInformationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ContactInformation> contactInformationRowMapper = (rs, rowNum) -> new ContactInformation(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone_number"),
            rs.getString("email")
    );

    @Override
    public ContactInformation createContactInformation(ContactInformation contactInformation) {
        String sql = "INSERT INTO contact_information (first_name, last_name, phone_number, email) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class,
                contactInformation.getFirstName(),
                contactInformation.getLastName(),
                contactInformation.getPhoneNumber(),
                contactInformation.getEmail());

        if (generatedId != null) contactInformation.setId(generatedId);

        return contactInformation;
    }

    @Override
    public Optional<ContactInformation> findById(Long id) {
        String sql = "SELECT * FROM contact_information WHERE id = ?";
        return jdbcTemplate.query(sql, contactInformationRowMapper, id).stream().findFirst();
    }

    @Override
    public ContactInformation updateContactInformation(ContactInformation contactInformation) {
        ContactInformation existingInformation = findById(contactInformation.getId()).orElse(null);

        if (existingInformation == null) return null;

        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("UPDATE contact_information SET ");

        if (contactInformation.getFirstName() != null) {
            sqlBuilder.append("first_name = ?, ");
            params.add(contactInformation.getFirstName());
        }
        if (contactInformation.getLastName() != null) {
            sqlBuilder.append("last_name = ?, ");
            params.add(contactInformation.getLastName());
        }
        if (contactInformation.getPhoneNumber() != null) {
            sqlBuilder.append("phone_number = ?, ");
            params.add(contactInformation.getPhoneNumber());
        }
        if (contactInformation.getEmail() != null) {
            sqlBuilder.append("email = ?, ");
            params.add(contactInformation.getEmail());
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE id = ?");
        params.add(contactInformation.getId());

        jdbcTemplate.update(sqlBuilder.toString(), params.toArray());
        return findById(contactInformation.getId()).orElse(null);
    }

    @Override
    public void deleteContactInformation(Long id) {
        String sql = "DELETE FROM contact_information WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ContactInformation> findByFirstName(String firstName) {
        String sql = "SELECT * FROM contact_information WHERE first_name = ?";
        return jdbcTemplate.query(sql, contactInformationRowMapper, firstName);
    }

    @Override
    public List<ContactInformation> findAll() {
        String sql = "SELECT * FROM contact_information";
        return jdbcTemplate.query(sql, contactInformationRowMapper);
    }
}