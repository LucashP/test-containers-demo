package org.example

import org.example.repository.SimpleEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@Testcontainers
class TestContainersApplicationWithMySqlContainersSpec extends Specification {

    @Shared
    def static CONTAINER = (MySQLContainer) new MySQLContainer("mysql:8.0.25")
            .withDatabaseName("my-database")
            .withUsername("mysql-user")
            .withPassword("mysql-password")
            .withExposedPorts(3306)

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    SimpleEntityRepository repository

    def setupSpec() {
        CONTAINER.start()
    }

    @DynamicPropertySource
    def static overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", () -> CONTAINER.getJdbcUrl())
    }

    def "should load context"() {
        expect:
        applicationContext
    }

    def "should be zero"() {
        expect:
        repository.count() == 0
    }
}