package com.company.aggregator;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
public class UserAggregationService {

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String NAME = "name";

    public static final String SURNAME = "surname";

    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";

    private final DataSourceConfig dataSourceConfig;

    public UserAggregationService(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private String mapField(DataSourceProperties source, Map<String, Object> row, String key) {
        return row.get(source.getMapping().get(key)).toString();
    }

    private Stream<User> selectData(DataSourceProperties source) {
        return new JdbcTemplate(new DriverManagerDataSource(source.getUrl(), source.getUser(), source.getPassword()))
                .queryForList(String.format(SELECT_ALL_QUERY, source.getTable())).stream()
                .map(row -> new User(
                        mapField(source, row, ID),
                        mapField(source, row, USERNAME),
                        mapField(source, row, NAME),
                        mapField(source, row, SURNAME)));
    }

    public Collection<User> getAllUsers() {
        return dataSourceConfig.getSources().stream()
                .map(this::selectData)
                .flatMap(stream -> stream)
                .collect(Collectors.toList());
    }

}