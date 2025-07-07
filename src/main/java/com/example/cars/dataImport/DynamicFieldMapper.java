package com.example.cars.dataImport;

import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DynamicFieldMapper {
    private final JdbcTemplate jdbcTemplate;
    private final Map<String, Map<String, String>> tableColumnCache = new HashMap<>();

    public DynamicFieldMapper(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Map<String, String> getColumnMappings(String tableName) {
        return tableColumnCache.computeIfAbsent(tableName.toLowerCase(), tn -> {
            Map<String, String> mappings = new HashMap<>();
            jdbcTemplate.query("SELECT * FROM " + tn + " LIMIT 0",
                    (ResultSet rs) -> {
                        ResultSetMetaData meta = rs.getMetaData();
                        for (int i = 1; i <= meta.getColumnCount(); i++) {
                            mappings.put(
                                    meta.getColumnName(i).toLowerCase(),
                                    meta.getColumnClassName(i)
                            );
                        }
                        return null;
                    });
            return mappings;
        });
    }

    public Object convertValue(String tableName, String fieldName, String value) {
        if (value == null || value.isEmpty()) return null;

        String type = getColumnMappings(tableName).get(fieldName.toLowerCase());
        if (type == null) return value;

        try {
            return switch (type) {
                case "java.time.LocalDate" -> LocalDate.parse(value);
                case "java.lang.Boolean", "boolean" -> Boolean.parseBoolean(value);
                case "java.lang.Integer", "int" -> Integer.parseInt(value);
                case "java.lang.Long", "long" -> Long.parseLong(value);
                default -> value;
            };
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("Conversion failed for %s: %s", fieldName, e.getMessage())
            );
        }
    }
}