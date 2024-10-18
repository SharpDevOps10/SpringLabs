package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.DAO.LostItemDao;
import com.daniorerio.lost_found.entities.ContactInformation;
import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class LostItemRepository implements LostItemDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LostItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LostItem> lostItemRowMapper = (rs, rowNum) -> {
        LostItem lostItem = new LostItem(
                rs.getLong("li_id"),
                rs.getString("item_name"),
                rs.getString("item_description"),
                Arrays.asList((String[]) rs.getArray("item_keywords").getArray())
        );

        lostItem.setContactInformation(new ContactInformation(
                rs.getLong("ci_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("email")
        ));

        lostItem.setLocation(new Location(
                rs.getLong("loc_id"),
                rs.getString("city"),
                rs.getString("address"),
                rs.getString("zip_code")
        ));

        return lostItem;
    };

    @Override
    public LostItem addItem(LostItem lostItem) throws SQLException {
        String sql = "INSERT INTO lost_items (item_name, item_description, item_keywords, contact_information_id, locations_id) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class,
                lostItem.getItemName(),
                lostItem.getItemDescription(),
                Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().createArrayOf("text", lostItem.getItemKeywords().toArray()),
                lostItem.getContactInformation().getId(),
                lostItem.getLocation().getId()
        );

        if (generatedId == null) throw new SQLException("Failed to generate ID for the lost item.");

        lostItem.setId(generatedId);
        return lostItem;
    }

    @Override
    public Optional<LostItem> findById(long id) {
        String sql = "SELECT " +
                "li.id AS li_id, " +
                "li.item_name, " +
                "li.item_description, " +
                "li.item_keywords, " +
                "ci.id AS ci_id, " +
                "ci.first_name, " +
                "ci.last_name, " +
                "ci.phone_number, " +
                "ci.email, " +
                "loc.id AS loc_id, " +
                "loc.city, " +
                "loc.address, " +
                "loc.zip_code " +
                "FROM lost_items li " +
                "JOIN contact_information ci ON li.contact_information_id = ci.id " +
                "JOIN locations loc ON li.locations_id = loc.id " +
                "WHERE li.id = ?";
        return jdbcTemplate.query(sql, lostItemRowMapper, id).stream().findFirst();
    }

    @Override
    public List<LostItem> findAllItems() {
        String sql = "SELECT " +
                "li.id AS li_id, " +
                "li.item_name, " +
                "li.item_description, " +
                "li.item_keywords, " +
                "ci.id AS ci_id, " +
                "ci.first_name, " +
                "ci.last_name, " +
                "ci.phone_number, " +
                "ci.email, " +
                "loc.id AS loc_id, " +
                "loc.city, " +
                "loc.address, " +
                "loc.zip_code " +
                "FROM lost_items li " +
                "JOIN contact_information ci ON li.contact_information_id = ci.id " +
                "JOIN locations loc ON li.locations_id = loc.id " +
                "ORDER BY li.id";

        return jdbcTemplate.query(sql, lostItemRowMapper);
    }

    @Override
    public void updateItem(LostItem lostItem) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE lost_items SET ");
        List<Object> params = new ArrayList<>();

        if (lostItem.getItemName() != null) {
            sql.append("item_name = ?, ");
            params.add(lostItem.getItemName());
        }
        if (lostItem.getItemDescription() != null) {
            sql.append("item_description = ?, ");
            params.add(lostItem.getItemDescription());
        }
        if (lostItem.getItemKeywords() != null) {
            sql.append("item_keywords = ?, ");
            params.add(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().createArrayOf("text", lostItem.getItemKeywords().toArray()));
        }
        if (lostItem.getContactInformation() != null && lostItem.getContactInformation().getId() != null) {
            sql.append("contact_information_id = ?, ");
            params.add(lostItem.getContactInformation().getId());
        }
        if (lostItem.getLocation() != null && lostItem.getLocation().getId() != null) {
            sql.append("locations_id = ?, ");
            params.add(lostItem.getLocation().getId());
        }
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        params.add(lostItem.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void deleteItem(long id) {
        String sql = "DELETE FROM lost_items WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<LostItem> findByItemName(String itemName) {
        String sql = "SELECT " +
                "li.id AS li_id, " +
                "li.item_name, " +
                "li.item_description, " +
                "li.item_keywords, " +
                "ci.id AS ci_id, " +
                "ci.first_name, " +
                "ci.last_name, " +
                "ci.phone_number, " +
                "ci.email, " +
                "loc.id AS loc_id, " +
                "loc.city, " +
                "loc.address, " +
                "loc.zip_code " +
                "FROM lost_items li " +
                "JOIN contact_information ci ON li.contact_information_id = ci.id " +
                "JOIN locations loc ON li.locations_id = loc.id " +
                "WHERE li.item_name = ?";

        return jdbcTemplate.query(sql, lostItemRowMapper, itemName);
    }

    @Override
    public List<LostItem> findByItemKeywords(String keywords) throws SQLException {
        List<String> keywordList = Arrays.stream(keywords.split(","))
                .map(String::trim)
                .toList();

        String sql = "SELECT " +
                "li.id AS li_id, " +
                "li.item_name, " +
                "li.item_description, " +
                "li.item_keywords, " +
                "ci.id AS ci_id, " +
                "ci.first_name, " +
                "ci.last_name, " +
                "ci.phone_number, " +
                "ci.email, " +
                "loc.id AS loc_id, " +
                "loc.city, " +
                "loc.address, " +
                "loc.zip_code " +
                "FROM lost_items li " +
                "JOIN contact_information ci ON li.contact_information_id = ci.id " +
                "JOIN locations loc ON li.locations_id = loc.id " +
                "WHERE li.item_keywords && ?";

        return jdbcTemplate.query(sql, lostItemRowMapper,
                Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().createArrayOf("text", keywordList.toArray()));
    }
}
//