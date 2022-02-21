package org.example

import org.example.repository.SimpleEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class TestContainersApplicationSpec extends Specification {

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    SimpleEntityRepository repository

    def "should load context"() {
        expect:
        applicationContext
    }

    def "should be zero"() {
        expect:
        repository.count() == 0
    }
}