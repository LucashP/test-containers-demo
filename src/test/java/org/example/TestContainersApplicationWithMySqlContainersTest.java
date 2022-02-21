package org.example;

import org.example.repository.SimpleEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class TestContainersApplicationWithMySqlContainersTest {

    @Container
    private static MySQLContainer CONTAINER = (MySQLContainer) new MySQLContainer("mysql:8.0.25")
            .withDatabaseName("my-database")
            .withUsername("mysql-user")
            .withPassword("mysql-password")
            .withExposedPorts(3306);

    @Autowired
    SimpleEntityRepository repository;

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", () -> CONTAINER.getJdbcUrl());
    }

    @Test
    void contextLoads() {
        System.out.println(repository.count());
    }
}