package hr.java.spring.boot.Example.repository;

import hr.java.spring.boot.Example.domain.Category;
import hr.java.spring.boot.Example.domain.Hardware;
import hr.java.spring.boot.Example.domain.SearchHardware;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class JdbcHardwareRepository implements HardwareRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Hardware> getAllHardware() {
        return jdbcTemplate.query("SELECT * FROM HARDWARE", new HardwareMapper());
    }

    @Override
    public List<Hardware> getHardwareByName(String hardwareName) {
        return jdbcTemplate.query("SELECT * FROM HARDWARE WHERE NAME = ?",
                new HardwareMapper(), hardwareName);
    }

    @Override
    public Optional<Hardware> getHardwareById(Long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hardwareId", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM HARDWARE WHERE ID = :hardwareId",
                new HardwareMapper(), parameters));
    }

    @Override
    public Hardware saveNewHardware(Hardware hardware) {
        final String SQL =
                "INSERT INTO HARDWARE (name, description, price, categoryId) OUTPUT INSERTED.id VALUES (?, ?, ?, ?)";
        Long generatedId = jdbcTemplate.queryForObject(SQL, Long.class, hardware.getName(), hardware.getDescription(), hardware.getPrice(), hardware.getCategory().getId());
        hardware.setId(generatedId);
        return hardware;
    }

    @Override
    public List<Hardware> filterByParameters(SearchHardware searchHardware) {
        return List.of();
    }

    @Override
    public Optional<Hardware> updateHardware(Hardware hardwareToUpdate, Long id) {
        if(hardwareByIdExists(id)) {
            final String SQL =
                    "UPDATE HARDWARE SET name = ?, description = ?, price = ?, categoryId = ? WHERE ID = ?";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setString(1, hardwareToUpdate.getName());
                ps.setString(2, hardwareToUpdate.getDescription());
                ps.setBigDecimal(3, hardwareToUpdate.getPrice());
                ps.setInt(4, hardwareToUpdate.getCategory().getId());
                ps.setLong(5, id);
                return ps;
            });
            hardwareToUpdate.setId(id);
            return Optional.of(hardwareToUpdate);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public boolean hardwareByIdExists(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM HARDWARE WHERE ID = ?", Integer.class, id);
        return count > 0;
    }

    @Override
    public boolean deleteHardwareById(Long id) {
        if(hardwareByIdExists(id)) {
            jdbcTemplate.update(
                    "DELETE FROM HARDWARE WHERE ID = ?", id);
            return true;
        }
        else {
            return false;
        }
    }

    private static class HardwareMapper implements RowMapper<Hardware> {

        public Hardware mapRow(ResultSet rs, int i) throws SQLException {

            Hardware newHardware = new Hardware();
            newHardware.setId(rs.getLong("ID"));
            newHardware.setName(rs.getString("NAME"));
            newHardware.setDescription(rs.getString("DESCRIPTION"));
            newHardware.setPrice(rs.getBigDecimal("PRICE"));

            Integer categoryId = rs.getInt("CATEGORYID");

            if(Category.CPU.getId().equals(categoryId)) {
                newHardware.setCategory(Category.CPU);
            }
            else {
                newHardware.setCategory(Category.OTHER);
            }

            return newHardware;
        }
    }
}
