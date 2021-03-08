package com.challenge.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    protected static PostgreSQLContainer postgreSQLContainer;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    @BeforeClass
    public static void setup() {

        startPostgres();

        initSpringSecuritySettings();
    }

    @AfterClass
    public static void after() {
        stopPostgres();
    }


    private static void startPostgres() {
        postgreSQLContainer = new PostgreSQLContainer("postgres:10");
        postgreSQLContainer.withInitScript("initdb.sql");
        postgreSQLContainer.start();

        System.setProperty("db.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("db.username", postgreSQLContainer.getUsername());
        System.setProperty("db.password", postgreSQLContainer.getPassword());
    }

    private static void stopPostgres() {
        postgreSQLContainer.stop();
    }

    private static void initSpringSecuritySettings() {
        System.setProperty("spring.security.user.name", "user");
        System.setProperty("spring.security.user.password", "password");
    }


}
