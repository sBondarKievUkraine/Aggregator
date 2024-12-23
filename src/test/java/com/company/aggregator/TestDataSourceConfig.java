package com.company.aggregator;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@ConfigurationProperties(prefix = "test-config")
public class TestDataSourceConfig {

    private List<TestDataSourcesProperties> sources;

    public List<TestDataSourcesProperties> getSources() {
        return sources;
    }

    public void setSources(List<TestDataSourcesProperties> sources) {
        this.sources = sources;
    }


}